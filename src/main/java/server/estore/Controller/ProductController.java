package server.estore.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.estore.Model.Product.Dto.ProductDto;
import server.estore.Model.Product.Dto.ProductRes;
import server.estore.Model.Product.Product;
import server.estore.Model.Product.ProductMapper;
import server.estore.Model.Product_Image.ProductImage;
import server.estore.Model.Review.Dto.ReviewDto;
import server.estore.Model.Review.Dto.ReviewRes;
import server.estore.Model.Review.Review;
import server.estore.Model.Review.ReviewMapper;
import server.estore.Model.User.User;
import server.estore.Service.ProductImageService;
import server.estore.Service.ProductService;
import server.estore.Service.ReviewService;
import server.estore.Utils.AppUtil;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final ProductImageService productImageService;

    private final ReviewService reviewService;

    private final ReviewMapper reviewMapper;

    @GetMapping("/public")
    public PageResponse<ProductRes> getAll(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return new PageResponse<>(productService.getAll(pageable));
    }


    @GetMapping("/public/test")
    public PageResponse<ProductRes> getAllTest(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable

    ){
        return new PageResponse<>(productService.getAll(pageable));
    }

    @GetMapping("/public/filter")
    public PageResponse<ProductRes> getAllByFilter(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "categories", required = false) Set<Long> catIdList,
            @RequestParam(value = "brands", required = false) Set<Long> brandIdList
    ) {
        return new PageResponse<>(productService.getProductsByFilter(catIdList,brandIdList, pageable));
    }

    @GetMapping("/public/{id}")
    public ProductRes getById(@PathVariable("id") Long productId){
        return productService.getById(productId);
    }

    @GetMapping
    public List<ProductRes> getAllByIdList(@RequestParam("idList") Set<Long> idList){
        return productService.getProductResByIdList(idList);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductRes create(@Valid @RequestBody ProductDto dto){
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductRes update(@Valid @RequestBody ProductDto dto, @PathVariable("id") Long productId){
        return productService.update(dto, productId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable("id") Long productId){
        productService.delete(productId);
    }

    @PostMapping(value = "/{id}/images", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ProductImage> uploadImages(@PathVariable("id") Long productId, @RequestBody List<MultipartFile> files) {
        Product currentProduct = productService.findById(productId);
        return productImageService.uploadImages(currentProduct, files);
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteImage(@PathVariable("productId")Long productId, @PathVariable("imageId") Long imageId){
        productImageService.delete(productId, imageId);
    }

    @GetMapping("/public/{productId}/reviews")
    public PageResponse<ReviewRes> getProductReviews (
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable("productId") Long productId
    ) {
        Page<Review> reviews = reviewService.getReviews(productId, pageable);
        return new PageResponse<ReviewRes>(reviews.map(reviewMapper::toReviewRes));
    }

    @PostMapping("/{productId}/reviews")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ReviewRes postReview(@AuthenticationPrincipal User user, @PathVariable("productId") Long productId, @RequestBody ReviewDto dto){
        Product currentProduct = productService.findById(productId);
        Review newReview = reviewService.create(dto, user, currentProduct);
        return reviewMapper.toReviewRes(newReview);
    }

}
