package server.estore.Service;

import server.estore.Model.User.User;
import server.estore.Model.Verification.Dto.VerificationKeyDto;
import server.estore.Model.Verification.Verification;

public interface VerificationService {
    Verification create(User newUser);

    User confirm(VerificationKeyDto dto);
}
