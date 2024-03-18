package server.estore.Model.User.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {
    
    private String accessToken;
    
    private String refreshToken;

}

