package com.zezame.timasi.service.impl;

import com.zezame.timasi.constant.Message;
import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.product.CreateProductRequestDTO;
import com.zezame.timasi.dto.product.ProductResponseDTO;
import com.zezame.timasi.dto.product.UpdateProductRequestDTO;
import com.zezame.timasi.exceptiohandler.exception.DataIntegrationException;
import com.zezame.timasi.exceptiohandler.exception.DuplicateException;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.company.Product;
import com.zezame.timasi.repository.ProductRepository;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl extends BaseService implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> responses = products.stream()
                .map(this::mapToDto)
                .toList();
        return responses;
    }

    @Override
    public ProductResponseDTO getProductById(String id) {
        Product product = findProductById(id);
        ProductResponseDTO response = mapToDto(product);
        return response;
    }

    @Override
    public CreateResponseDTO createProduct(CreateProductRequestDTO request) {
        if (productRepository.existsByCode(request.getCode())) {
            throw new DuplicateException("Product Already Exists");
        }
        Product product = new Product();
        product.setCode(request.getCode());
        product.setName(request.getName());
        Product savedProduct = productRepository.save(prepareCreate(product));
        return new CreateResponseDTO(savedProduct.getId(), Message.CREATED.getName());
    }

    @Override
    public UpdateResponseDTO updateProduct(String id, UpdateProductRequestDTO request) {
        Product product = findProductById(id);

        if (!product.getVersion().equals(request.getVersion())) {
            throw new DataIntegrationException("Error Updating Product, Please Refresh The Page");
        }

        if (!product.getCode().equals(request.getCode())) {
            productRepository.findByCode(request.getCode())
                    .ifPresent(p -> {
                        throw new DuplicateException("Code Is Not Available");
                    });
        }

        product.setCode(request.getCode());
        product.setName(request.getName());
        Product updatedProduct = productRepository.saveAndFlush(prepareUpdate(product));
        return new UpdateResponseDTO(updatedProduct.getId(), Message.UPDATED.getName(), updatedProduct.getVersion());
    }

    @Override
    public CommonResponseDTO deleteProduct(String id) {
        Product product = findProductById(id);
        productRepository.delete(product);
        return new CommonResponseDTO(Message.DELETED.getName());
    }

    private ProductResponseDTO mapToDto(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO(
                product.getId(), product.getName(), product.getCode(), product.getVersion());

        return dto;
    }

    private Product findProductById(String id) {
        UUID productId = convertToUUID(id);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Prduct Not Found"));
        return product;
    }
}
