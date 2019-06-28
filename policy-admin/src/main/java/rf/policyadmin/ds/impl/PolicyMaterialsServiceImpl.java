package rf.policyadmin.ds.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.policyadmin.ds.PolicyMaterialsService;
import rf.policyadmin.model.PolicyMaterials;
import rf.policyadmin.repository.PolicyMaterialsDao;
import rf.policyadmin.repository.pojo.TPolicyMaterials;

/**
 * Created by minsheng-zxg on 2017/11/24.
 */
@Service
public class PolicyMaterialsServiceImpl implements PolicyMaterialsService {
    @Autowired
    private PolicyMaterialsDao policyMaterialsDao;

    @Override
    public void save(PolicyMaterials policyMaterials) {
        TPolicyMaterials po = policyMaterialsDao.findById(policyMaterials.getUuid()).get();
        if(po == null){
            po = new TPolicyMaterials();
        }
        BeanUtils.copyProperties(policyMaterials, po);
        policyMaterialsDao.save(po);
    }

    @Override
    public PolicyMaterials loadPolicyMaterialsByUuid(String uuid) {
        TPolicyMaterials po = policyMaterialsDao.findById(uuid).get();
        if(po == null){
            return null;
        }else{
            PolicyMaterials policyMaterials = new PolicyMaterials();
            BeanUtils.copyProperties(po, policyMaterials);
            return policyMaterials;
        }
    }
}
