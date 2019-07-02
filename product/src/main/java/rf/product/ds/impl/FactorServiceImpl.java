package rf.product.ds.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rf.foundation.pub.Guid;
import rf.foundation.utils.JsonHelper;
import rf.product.ds.FactorService;
import rf.product.model.FactorSpec;
import rf.product.repository.FactorDao;
import rf.product.repository.ImportFactorService;
import rf.product.repository.pojo.TFactor;
import java.util.List;


@Service
public class FactorServiceImpl implements FactorService {

    @Autowired
    private FactorDao factorDao;

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private ImportFactorService parser;


    @Override
    @Cacheable(value="FACTOR" , key = "#code")
    public FactorSpec findFactor(String code){
        TFactor po = factorDao.findByCode(code);

        FactorSpec spec = null;
        if(po != null){
            spec = jsonHelper.fromJSON(po.getContent(),FactorSpec.class);
        }
        return spec;
    }

    @CacheEvict(value="FACTOR" , key = "#spec.code")
    public void saveFactor(FactorSpec spec){

        TFactor po = factorDao.findByCode(spec.getCode());

        if(po == null) {
            po = new TFactor();
            po.setUuid(Guid.generateStrId());
        }

        String uuid = po.getUuid();

        BeanUtils.copyProperties(spec, po);
        po.setUuid(uuid);
        po.setDataType(spec.getDataType().name());
        po.setCategory(spec.getCategory().name());

        String content = jsonHelper.toJSON(spec);
        po.setContent(content);

        factorDao.save(po);
    }

    public void initFatorsFromExcel(){
        List<FactorSpec> factorSpecs = parser.parseFactors();
        factorSpecs.forEach(spec -> {
            saveFactor(spec);
        });
    }
}
