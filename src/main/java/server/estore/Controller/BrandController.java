package server.estore.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import server.estore.Model.Brand.Dto.BrandDto;
import server.estore.Model.Brand.Dto.BrandRes;
import server.estore.Service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/public")
    public List<BrandRes> getAll() {
        return brandService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BrandRes create(@Valid @RequestBody BrandDto dto){
        return brandService.create(dto);
    }
    
    
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BrandRes update(@Valid @RequestBody BrandDto dto, @PathVariable Long id){
        return brandService.update(dto, id);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id){
        brandService.delete(id);
    }
}
