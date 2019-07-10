package rf.saleshorizon.ds;

import rf.product.model.ProductSpec;

public interface ProductService {

    ProductSpec pullFromChain(String productCode);
}
