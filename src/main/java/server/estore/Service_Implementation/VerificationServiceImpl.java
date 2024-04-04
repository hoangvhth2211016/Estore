package server.estore.Service_Implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.estore.Exception.ConflictException;
import server.estore.Exception.KeyExpiredException;
import server.estore.Exception.NotFoundException;
import server.estore.Model.User.User;
import server.estore.Model.Verification.Dto.VerificationKeyDto;
import server.estore.Model.Verification.Verification;
import server.estore.Repository.VerificationRepo;
import server.estore.Service.VerificationService;
import server.estore.Utils.AppUtil;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepo verificationRepo;

    @Override
    public Verification create(User newUser) {
        Verification verification = Verification
                .builder()
                .key(UUID.randomUUID().toString())
                .user(newUser)
                .build();
        return verificationRepo.save(verification);
    }

    @Override
    public User confirm(VerificationKeyDto dto) {

        Verification verification = verificationRepo
                .findByKey(dto.getKey())
                .orElseThrow(()-> new NotFoundException("Verification key"));

        AppUtil.verifyObj(verification.getUser().getEmail(), dto.getEmail());

        if(verification.getValidated()){
            throw new ConflictException("Key already validated");
        }

        if(verification.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new KeyExpiredException("Key expired");
        }

        verification.setValidated(true);
        verificationRepo.save(verification);
        return verification.getUser();
    }
}
