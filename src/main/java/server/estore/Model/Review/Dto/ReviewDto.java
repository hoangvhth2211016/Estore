package server.estore.Model.Review.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    @NotNull
    private Integer rating;

    @NotBlank
    private String content;

}
