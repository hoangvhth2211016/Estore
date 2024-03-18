package server.estore.Controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class PageResponse <T> {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private List<T> content;

    public PageResponse(Page<T> page){
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
        this.content = page.getContent();
    }
}
