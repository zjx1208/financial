package com.bawei.manager.service;

import com.bawei.entity.Product;
import com.bawei.entity.enums.ProductStatus;
import com.bawei.manager.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
//import javax.persistence.criteria.*;
import java.math.BigDecimal;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.List;

@Service
public class ProductService {
    private static Logger LOG = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository repository;

    public Product addProduct(Product product){
        LOG.debug("创建产品，参数：{}", product);
        checkProduct(product);
        setDefault(product);
        Product result = repository.save(product);
        LOG.debug("创建产品，结果：{}", result);
        return result;
    }
    /**
     * 设置默认值
     * 创建时间、更新时间、投资步长、锁定期
     * @param product
     */
    private void setDefault(Product product) {
        if (product.getCreateAt()==null){
            product.setCreateAt(new Date());
        }
        if (product.getUpdateAt()==null){
            product.setUpdateAt(new Date());
        }
        if (product.getStepAmount()==null){
            product.setStepAmount(BigDecimal.ZERO);
        }
        if (product.getLockTerm()==null){
            product.setLockTerm(0);
        }
        if (product.getStatus()==null){
            product.setStatus(ProductStatus.AUDITING.name());
        }
    }

    /**
     * 产品数据校验
     * 1. 非空校验
     * 2. 收益率要0-30以内
     * 3. 投资步长需为整数
     * @param product
     */
    private void checkProduct(Product product) {
//        Assert.notNull(product.getId(), ErrorEnum.ID_NOT_NULL.getCode());
        Assert.notNull(product.getName(), "名称不可为空");
        Assert.notNull(product.getThresholdAmount(), "起投金额不可为空");
        Assert.notNull(product.getStepAmount(), "投资步长不可为空");
        Assert.notNull(product.getLockTerm(), "锁定期不可为空");
        Assert.notNull(product.getRewardRate(), "收益率不可为空");
        Assert.notNull(product.getStatus(), "状态不可为空");
        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate())<0
                && BigDecimal.valueOf(30).compareTo(product.getRewardRate())>=0, "收益率范围错误");
        Assert.isTrue(BigDecimal.valueOf(product.getStepAmount().longValue()).compareTo(product.getStepAmount())==0,
                "投资步长需为整数");
    }
}
