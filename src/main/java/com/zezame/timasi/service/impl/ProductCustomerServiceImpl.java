package com.zezame.timasi.service.impl;

import com.zezame.timasi.constant.Message;
import com.zezame.timasi.constant.RoleCode;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.customer.ProductCustomerRequestDTO;
import com.zezame.timasi.exceptiohandler.exception.NotAllowedException;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.company.Product;
import com.zezame.timasi.model.company.ProductCustomer;
import com.zezame.timasi.model.company.User;
import com.zezame.timasi.repository.ProductCustomerRepository;
import com.zezame.timasi.repository.ProductRepository;
import com.zezame.timasi.repository.UserRepository;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.ProductCustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCustomerServiceImpl extends BaseService implements ProductCustomerService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductCustomerRepository productCustomerRepository;

    public ProductCustomerServiceImpl(ProductRepository productRepository, UserRepository userRepository, ProductCustomerRepository productCustomerRepository) {
        super();
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productCustomerRepository = productCustomerRepository;
    }

    @Override
    public CommonResponseDTO createProductCustomer(ProductCustomerRequestDTO request) {
        var customer = findCustomerById(request.getCustomerId());

        List<Product> products = request.getProductIds().stream()
                .map(this::findProductById)
                .toList();

        for (Product product : products) {
            var productCustomer = new ProductCustomer();
            productCustomer.setCustomer(customer);
            productCustomer.setProduct(product);
            productCustomerRepository.save(prepareCreate(productCustomer));
        }
        return new CommonResponseDTO(Message.CREATED.getName());
    }

    @Override
    public CommonResponseDTO updateProductCustomer(ProductCustomerRequestDTO request) {
        var customer = findCustomerById(request.getCustomerId());

        List<Product> products = request.getProductIds().stream()
                .map(this::findProductById)
                .toList();

        List<ProductCustomer> toBeDeleted = productCustomerRepository.findByCustomerAndProductNotIn(customer, products);
        productCustomerRepository.deleteAll(toBeDeleted);

        for (var product : products) {
            var productCustomer = productCustomerRepository.findByProduct(product)
                    .orElse(new ProductCustomer());
            productCustomer.setCustomer(customer);
            productCustomer.setProduct(product);
            productCustomerRepository.save(productCustomer.getId() != null ? prepareUpdate(productCustomer)
                    : prepareCreate(productCustomer));
        }
        return new CommonResponseDTO(Message.UPDATED.getName());
    }

    private User findCustomerById(String id) {
        UUID userId = convertToUUID(id);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
        if (!user.getRole().getCode().equals(RoleCode.CUST.name())) {
            throw new NotAllowedException("Invalid Customer");
        }
        return user;
    }

    private Product findProductById(String id) {
        UUID productId = convertToUUID(id);
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        return product;
    }
}
