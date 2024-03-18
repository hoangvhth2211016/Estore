package server.estore.Model.Review.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewRes {

    private Long id;

    private String content;

    private Integer rating;

    private String username;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
