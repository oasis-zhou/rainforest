package rf.saleshorizon.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.foundation.cache.GuavaCacheManager;
import rf.product.model.ProductSpec;
import rf.saleshorizon.ds.ProductService;

/**
 * @ClassName ProductServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private GuavaCacheManager cacheManager;

    @Override
    public ProductSpec pullFromChain(String productCode) {

        ProductSpec productSpec = (ProductSpec) cacheManager.getCacheByKey(productCode);

        if(productSpec == null) {
            //TODO 从区块链获取产品信息，缓存产品和费率表数据
        }
        return productSpec;
    }
}
