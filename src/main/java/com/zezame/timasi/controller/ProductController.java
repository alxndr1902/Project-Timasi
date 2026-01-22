package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.product.CreateProductRequestDTO;
import com.zezame.timasi.dto.product.ProductResponseDTO;
import com.zezame.timasi.dto.product.UpdateProductRequestDTO;
import com.zezame.timasi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        List<ProductResponseDTO> responses = productService.getProducts();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable String id) {
        ProductResponseDTO response = productService.getProductById(id);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createProduct(@Valid @RequestBody CreateProductRequestDTO request) {
        CreateResponseDTO responseDTO = productService.createProduct(request);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateResponseDTO> updateProduct(@PathVariable String id,
                                                           @Valid @RequestBody UpdateProductRequestDTO request) {
        UpdateResponseDTO response =  productService.updateProduct(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonResponseDTO> deleteProduct(@PathVariable String id) {
        CommonResponseDTO response = productService.deleteProduct(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
