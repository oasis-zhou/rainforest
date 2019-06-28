package rf.product.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.utils.JsonHelper;
import rf.product.model.ProductSpec;
import rf.product.repository.pojo.TProduct;


@Component
public class ProductRepository {

    private static Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private JsonHelper jsonHelper;

    public ProductSpec findProduct(String productCode) {
        logger.debug("Load product " + productCode + " first.\n");
        TProduct po = productDao.findByCode(productCode);

        ProductSpec product = null;
        if(po != null && po.getContent() != null){
            product = jsonHelper.fromJSON(po.getContent(), ProductSpec.class);
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

    }

}
