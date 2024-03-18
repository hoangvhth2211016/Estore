package server.estore.Service_Implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import server.estore.Exception.BadRequestException;
import server.estore.Exception.ConflictException;
import server.estore.Exception.NotFoundException;
import server.estore.Model.Product.Dto.ProductDto;
import server.estore.Model.Product.Dto.ProductRes;
import server.estore.Model.Product.Product;
import server.estore.Model.Product.ProductMapper;
import server.estore.Repository.ProductRepo;
import server.estore.Service.ProductService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final ProductMapper productMapper;

    @Override
    public Page<ProductRes> getAll(Pageable pageable) {
        return productRepo.findAll(pageable).map(productMapper::toProductRes);
    }

    @Override
    public Page<ProductRes> searchProducts(String[] search, Pageable pageable) {
        return productRepo.searchProducts(search, pageable).map(productMapper::toProductRes);
    }

    @Override
    public Page<ProductRes> getProductsByFilter(Set<Long> catIdList, Set<Long> brandIdList, Pageable pageable) {
        if(catIdList == null || catIdList.isEmpty()){
            return productRepo.findByBrand_IdIn(brandIdList, pageable).map(productMapper::toProductRes);
        }
        if(brandIdList == null || brandIdList.isEmpty()) {
            return productRepo.findByCategory_IdIn(catIdList, pageable).map(productMapper::toProductRes);
        }
        return productRepo.findByBrand_IdInAndCategory_IdIn(brandIdList, catIdList, pageable).map(productMapper::toProductRes);
    }

    @Override
    public List<Product> getByIdList(Set<Long> idList) {
        return productRepo.findAllByIdIn(idList);
    }
    
    @Override
    public List<ProductRes> getProductResByIdList(Set<Long> idList) {
        return productRepo.findAllByIdIn(idList).stream().map(productMapper::toProductRes).toList();
    }

    @Override
    public Product findById(Long productId) {
        return productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product"));
    }

    @Override
    public ProductRes getById(Long productId) {
        Product product = findById(productId);
        return productMapper.toProductRes(product);
    }

    @Override
    public ProductRes create(ProductDto dto) {
        Product newProduct = productRepo.save(productMapper.fromDtoToEntity(dto));
        return productMapper.toProductRes(newProduct);
    }

    @Override
    public ProductRes update(ProductDto dto, Long productId) {
        Product currentProduct = findById(productId);
        Product updatedProduct =  productRepo.save(productMapper.updateEntity(dto, currentProduct));
        return productMapper.toProductRes(updatedProduct);
    }

    @Override
    public void delete(Long productId) {
        Product currentProduct = findById(productId);
        productRepo.delete(currentProduct);
    }

    @Override
    public Product updateStock(Product product, int quantity, String action) {
        switch (action){
            case "increase" -> {
                product.setStock(product.getStock()+quantity);
            }
            case "decrease" -> {
                if(quantity > product.getStock()){
                    throw new ConflictException("Not enough in stock");
                }
                product.setStock(product.getStock()-quantity);
            }
            default -> {
                throw new BadRequestException("Unrecognisable action");
            }
        }
        return productRepo.save(product);
    }


}
