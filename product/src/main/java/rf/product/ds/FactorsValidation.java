package rf.product.ds;

import java.util.Map;


public interface FactorsValidation {

    Map<String,String> verify(Map<String, Object> factor);
}
