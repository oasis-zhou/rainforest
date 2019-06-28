package rf.product.ds;

import org.springframework.stereotype.Service;
import rf.product.model.ProductSpec;

@Service
public interface ProductService {

   public ProductSpec findProduct(String productCode);
}
