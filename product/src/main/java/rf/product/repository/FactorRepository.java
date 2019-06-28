package rf.product.repository;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.Guid;
import rf.foundation.utils.JsonHelper;
import rf.product.model.FactorSpec;
import rf.product.repository.pojo.TFactor;

import java.util.List;

@Component
public class FactorRepository {

    @Autowired
    private FactorDao factorDao;

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private ImportFactorService parser;

    public FactorSpec findFactor(String code){
        TFactor po = factorDao.findByCode(code);

        FactorSpec spec = null;
        if(po != null){
            spec = jsonHelper.fromJSON(po.getContent(),FactorSpec.class);
        }
        return spec;
    }

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
