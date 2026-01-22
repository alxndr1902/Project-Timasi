package com.zezame.timasi.service;

import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.customer.ProductCustomerRequestDTO;

public interface ProductCustomerService {
    CommonResponseDTO createProductCustomer(ProductCustomerRequestDTO request);

    CommonResponseDTO updateProductCustomer(ProductCustomerRequestDTO request);
}
