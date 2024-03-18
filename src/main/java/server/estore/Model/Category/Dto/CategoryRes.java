package server.estore.Model.Category.Dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryRes implements Serializable {
    private Long id;

    private String name;
    
    private String path;

    private Long parentId;

    private List<CategoryRes> subCategories = new ArrayList<>();
}
