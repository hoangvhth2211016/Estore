package server.estore.Model.Verification.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VerificationKeyDto {

    @NotBlank
    private String key;

    @NotBlank
    private String email;

}

