package rf.product.ds.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.foundation.cache.GuavaCacheManager;
import rf.foundation.utils.JsonHelper;
import rf.product.ds.ProductService;
import rf.product.model.ProductSpec;
import rf.product.repository.ProductDao;
import rf.product.repository.pojo.TProduct;


@Service
public class ProductServiceImpl implements ProductService {
    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductDao productDao;
    @Autowired
    private JsonHelper jsonHelper;
    @Autowired
    private GuavaCacheManager guavaCacheManager;

    @Override
    public ProductSpec findProduct(String productCode) {
        ProductSpec product = (ProductSpec) guavaCacheManager.getCacheByKey(productCode);

        if(product == null) {
            logger.debug("Load product " + productCode + " first.\n");
            TProduct po = productDao.findByCode(productCode);
            if (po != null && po.getContent() != null) {
                product = jsonHelper.fromJSON(po.getContent(), ProductSpec.class);
                guavaCacheManager.putCache(productCode,product);
            }
        }
        return product;
    }

    public void saveProduct(ProductSpec product){

        TProduct po = productDao.findByCode(product.getCode());
        if(po == null) {
            po = new TProduct();
            po.setUuid(product.getUuid());
        }else{
            product.setUuid(po.getUuid());
        }

        String content = jsonHelper.toJSON(product);
        po.setCode(product.getCode());
        po.setName(product.getName());
        po.setEffectiveDate(product.getEffectiveDate());
        po.setExpiredDate(product.getExpiredDate());
        po.setStatus(product.getStatus().name());
        po.setVersion(product.getVersion());
        po.setContent(content);

        productDao.save(po);

        guavaCacheManager.putCache(product.getCode(),product);
    }


}
