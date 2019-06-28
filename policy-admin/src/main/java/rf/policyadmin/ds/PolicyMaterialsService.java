package rf.policyadmin.ds;

import rf.policyadmin.model.PolicyMaterials;

/**
 * Created by minsheng-zxg on 2017/11/23.
 */
public interface PolicyMaterialsService {
    public void save(PolicyMaterials policyMaterials);
    public PolicyMaterials loadPolicyMaterialsByUuid(String uuid);
}
