package com.zezame.timasi.service;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.product.CreateProductRequestDTO;
import com.zezame.timasi.dto.product.ProductResponseDTO;
import com.zezame.timasi.dto.product.UpdateProductRequestDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getProducts();

    ProductResponseDTO getProductById(String id);

    CreateResponseDTO createProduct(CreateProductRequestDTO request);

    UpdateResponseDTO updateProduct(String id, UpdateProductRequestDTO request);

    CommonResponseDTO deleteProduct(String id);
}
