package com.alvarez_asencio.controller;

import com.alvarez_asencio.dto.ProductDTO;
import com.alvarez_asencio.model.Product;
import com.alvarez_asencio.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService service;
    private final ModelMapper mapper;

    // GET /v1/products -> 200
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() throws Exception {
        List<ProductDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return ResponseEntity.ok(list);
    }

    // GET /v1/products/{id} -> 200 / 404
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(convertToDto(service.findById(id)));
    }

    // POST /v1/products -> 201 / 400
    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO dto) throws Exception {
        dto.setIdProduct(null);
        Product saved = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getIdProduct())
                .toUri();
        return ResponseEntity.created(location).body(convertToDto(saved));
    }

    // PUT /v1/products/{id} -> 200 / 400 / 404
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @Valid @RequestBody ProductDTO dto) throws Exception {
        dto.setIdProduct(id);
        Product updated = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDto(updated));
    }

    // DELETE /v1/products/{id} -> 204 / 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ProductDTO convertToDto(Product product) {
        return mapper.map(product, ProductDTO.class);
    }

    private Product convertToEntity(ProductDTO dto) {
        return mapper.map(dto, Product.class);
    }
}
