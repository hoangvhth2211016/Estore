package server.estore.Model.Category.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class CategoryUpdateDto {
    
    @NotBlank
    private String name;
    
}
