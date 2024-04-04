package server.estore.Service_Implementation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.estore.Exception.*;
import server.estore.Model.Mail.MailTemplate;
import server.estore.Model.User.Dto.*;
import server.estore.Model.User.Role;
import server.estore.Model.User.User;
import server.estore.Model.User.UserMapper;
import server.estore.Model.Verification.Dto.VerificationKeyDto;
import server.estore.Model.Verification.Verification;
import server.estore.Producer.MailProducer;
import server.estore.Repository.UserRepo;
import server.estore.Service.*;

import java.nio.CharBuffer;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final MailProducer mailProducer;

    private final VerificationService verificationService;

    private final JwtService jwtService;

    private final CloudService cloudService;
    
    @Value("${store.app.url}")
    private String url;

    @Override
    public User register(RegisterDto dto) {
        if(userRepo.existsByEmail(dto.getEmail()) || userRepo.existsByUsername(dto.getUsername())){
            throw new AlreadyExistException("User");
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User userObj = userMapper.fromRegisterDtoToUser(dto);
        userObj.setRole(Role.CLIENT);

        User newUser = userRepo.save(userObj);

        Verification verification = verificationService.create(newUser);

        String verificationUrl = url + "/account/activate?key=" + verification.getKey()+"&email="+verification.getUser().getEmail();
       
        MailTemplate mailTemplate = MailTemplate.builder()
                .to(verification.getUser().getEmail())
                .subject("Verify your email to active your account.")
                .body("Please follow the link below to verify your account activation.\n" + verificationUrl)
                .build();

        mailProducer.send(mailTemplate);
        
        return newUser;
    }

    @Override
    public TokenDto login(LoginDto dto) {
        User user = userRepo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new NotFoundException("User"));
        if(!passwordEncoder.matches(CharBuffer.wrap(dto.getPassword()), user.getPassword())){
            throw new ConflictException("Password not match");
        }
        return TokenDto
                .builder()
                .accessToken(jwtService.createAccessToken(user))
                .refreshToken(jwtService.createRefreshToken(user))
                .build();
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User"));
    }

    @Override
    public String resetPassword(ResetPasswordDto dto) {
        User user = userRepo.findByEmail(dto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        String generatedPassword = RandomStringUtils.randomAlphanumeric(10);
        user.setPassword(passwordEncoder.encode(generatedPassword));
        userRepo.save(user);
        return generatedPassword;
    }

    @Override
    public void changePassword(User user, ChangePasswordDto dto) {
        if(!passwordEncoder.matches(CharBuffer.wrap(dto.getOldPassword()), user.getPassword())){
            throw new ConflictException("Password not match");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepo.save(user);
    }

    @Override
    public Page<UserRes> getAll(Pageable pageable) {
        return userRepo.findAll(pageable).map(userMapper::fromEntityToUserRes);
    }

    @Override
    public TokenDto activateAccount(VerificationKeyDto dto) {
        User user = verificationService.confirm(dto);

        user.setEnabled(true);
        userRepo.save(user);
        return TokenDto
                .builder()
                .accessToken(jwtService.createAccessToken(user))
                .refreshToken(jwtService.createRefreshToken(user))
                .build();
    }

    @Override
    public String uploadAvatar(MultipartFile file, User user) {
        String imgUrl = cloudService.uploadProfileImg(file, user.getUsername());
        user.setImgUrl(imgUrl);
        userRepo.save(user);
        return imgUrl;
    }
    
    @Override
    public TokenDto getNewAccessToken(HttpServletRequest req, HttpServletResponse res) {
        String authHeader = req.getHeader("Authorization");
        
        if (authHeader != null) {
            String[] headerElements = authHeader.split(" ");
            if (headerElements.length == 2 && "Bearer".equals(headerElements[0])) {
                String refreshToken = headerElements[1];
                String username = jwtService.extractUsername(refreshToken);
                if (username != null) {
                    User user = userRepo.findByUsername(username).orElseThrow(() -> new NotFoundException("User"));
                    if (jwtService.isTokenValid(refreshToken, user)) {
                        if(!jwtService.isTokenExpired(refreshToken)){
                            return TokenDto.builder()
                                    .accessToken(jwtService.createAccessToken(user))
                                    .refreshToken(refreshToken)
                                    .build();
                        }
                        throw new KeyExpiredException("Refresh token expired");
                    }
                }
                throw new BadRequestException("Invalid credentials");
            }
        }
        throw new BadRequestException("Unauthorized request");
    }
    
}
