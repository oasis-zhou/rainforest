package rf.product.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rf.product.ds.FactorService;
import rf.product.model.FactorSpec;
import rf.product.repository.FactorRepository;


@Service
public class FactorServiceImpl implements FactorService {

    @Autowired
    private FactorRepository repository;

    @Override
    @Cacheable(value="factor" , key = "#factorCode")
    public FactorSpec findFactor(String factorCode) {

        FactorSpec spec = repository.findFactor(factorCode);

        return spec;
    }
}
