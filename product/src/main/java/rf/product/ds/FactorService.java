package rf.product.ds;


import rf.product.model.FactorSpec;

public interface FactorService {

    FactorSpec findFactor(String code);
    void saveFactor(FactorSpec spec);
    void initFatorsFromExcel();
}
