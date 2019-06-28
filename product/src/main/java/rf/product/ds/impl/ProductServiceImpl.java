package rf.product.ds.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rf.product.ds.ProductService;
import rf.product.model.ProductSpec;
import rf.product.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    @Cacheable(value="product" , key = "#productCode")
    public ProductSpec findProduct(String productCode){

        return repository.findProduct(productCode);
    }


}
