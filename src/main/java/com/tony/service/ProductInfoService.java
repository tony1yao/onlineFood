package com.tony.service;

import com.sun.xml.internal.bind.v2.TODO;
import com.tony.dataobject.ProductInfo;
import com.tony.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
    ProductInfo findOne(String productId);

    List<ProductInfo> findAllInStock();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDTO> cartDTOList);

    void decreaseStock(List<CartDTO> cartDTOList);
}
