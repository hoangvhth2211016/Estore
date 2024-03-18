package server.estore.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.estore.Model.Mail.MailTemplate;
import server.estore.Model.User.Dto.LoginDto;
import server.estore.Model.User.Dto.RegisterDto;
import server.estore.Model.User.Dto.ResetPasswordDto;
import server.estore.Model.User.Dto.TokenDto;
import server.estore.Model.User.User;
import server.estore.Model.Verification.Dto.VerificationKeyDto;
import server.estore.Producer.MailProducer;
import server.estore.Service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    
    private final MailProducer mailProducer;

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterDto dto){
        return userService.register(dto);
    }

    @PostMapping("/login")
    public TokenDto login(@Valid @RequestBody LoginDto dto){
        return userService.login(dto);
    }

    @PostMapping("/account/activate")
    public TokenDto activate(@Valid @RequestBody VerificationKeyDto dto){
        return userService.activateAccount(dto);
    }

    @PatchMapping("/password")
    public String resetPassword(@Valid @RequestBody ResetPasswordDto dto){
        String generatedPassword = userService.resetPassword(dto);
        MailTemplate mailTemplate = MailTemplate
                .builder()
                .to(dto.getEmail())
                .subject("Request for reset password")
                .body("Dear customer,\n\nYour password has been reset. Please use the following password to log in:\n" + generatedPassword+"\n\n" +
                        "Best regard,\n\nEstore")
                .build();
        mailProducer.send(mailTemplate);
        return "A new password has been generated";
    }
    
    @PostMapping("/tokens/refresh")
    public TokenDto refreshToken(HttpServletRequest req, HttpServletResponse res){
        return userService.getNewAccessToken(req, res);
    }

}
