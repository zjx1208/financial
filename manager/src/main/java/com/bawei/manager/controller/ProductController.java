package com.bawei.manager.controller;

import com.bawei.entity.Product;
import com.bawei.manager.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Product addProduct(@RequestBody Product product){
        LOG.info("创建产品，参数：{}", product);
        Product result = productService.addProduct(product);
        LOG.info("创建产品，结果：{}", result);
        return result;
    }
}
