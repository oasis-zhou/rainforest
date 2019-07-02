package rf.inshorizon.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.inshorizon.ds.ProductService;
import rf.product.model.ProductSpec;

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
    private CacheManager cacheManager;

    @Override
    public ProductSpec pullFromChain(String productCode) {

        ProductSpec productSpec = (ProductSpec) cacheManager.getCacheDataByKey(productCode);

        if(productSpec == null) {
            //TODO 从区块链获取产品信息
        }
        return productSpec;
    }
}
