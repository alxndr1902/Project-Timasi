package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.DeleteResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.product.CreateProductRequestDTO;
import com.zezame.timasi.dto.product.ProductResponseDTO;
import com.zezame.timasi.dto.product.UpdateProductRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {
    @GetMapping
    public ResponseEntity<ProductResponseDTO> getProducts() {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable String id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createProduct(@Valid @RequestBody CreateProductRequestDTO request) {
        return null;
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateResponseDTO> updateProduct(@PathVariable String id,
                                                           @Valid @RequestBody UpdateProductRequestDTO request) {
        return null;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DeleteResponseDTO> deleteProduct(@PathVariable String id) {
        return null;
    }
}
