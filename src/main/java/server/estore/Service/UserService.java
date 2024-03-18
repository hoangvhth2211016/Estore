package server.estore.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import server.estore.Model.User.Dto.*;
import server.estore.Model.User.User;
import server.estore.Model.Verification.Dto.VerificationKeyDto;

import java.util.List;

public interface UserService {
    User register(RegisterDto dto);

    TokenDto login(LoginDto dto);

    User findById(Long id);

    String resetPassword(ResetPasswordDto dto);

    void changePassword(User user, ChangePasswordDto dto);

    Page<UserRes> getAll(Pageable pageable);

    TokenDto activateAccount(VerificationKeyDto dto);

    String uploadAvatar(MultipartFile file, User user);
    
    TokenDto getNewAccessToken(HttpServletRequest req, HttpServletResponse res);
}
