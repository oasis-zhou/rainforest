package rf.claim.ds.impl;


import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import rf.claim.ds.ClaimService;
import rf.claim.model.Claim;
import rf.claim.repository.ClaimDao;
import rf.claim.repository.pojo.TClaim;
import rf.foundation.numbering.NumberingService;
import rf.foundation.utils.JsonHelper;
import java.util.List;


@Service
public class ClaimServiceImpl implements ClaimService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NumberingService numberingService;

    @Autowired
    private ClaimDao claimDao;

    @Autowired
    private JsonHelper jsonHelper;

    @Override
    public void saveClaim(Claim claim) {
        TClaim po = claimDao.findByClaimNumber(claim.getClaimNumber());
        if(po == null) {
            po = new TClaim();
        }else {
            claim.setUuid(po.getUuid());
        }
        claim.setClaimTime(po.getClaimTime());
        claim.setProductCode(po.getProductCode());
        BeanUtils.copyProperties(claim, po);
        po.setStatusCode(claim.getStatus().name());
        String content = jsonHelper.toJSON(claim);
        po.setContent(content);

        claimDao.save(po);
    }


    @Override
    public Claim loadClaim(String claimNumber) {
        TClaim po = claimDao.findByClaimNumber(claimNumber);

        Claim claim = null;
        if(po != null) {
            claim = jsonHelper.fromJSON(po.getContent(), Claim.class);
        }

        return claim;
    }


    @Override
    public List<Claim> findClaimsByPolicyNumber(String policyNumber) {
        List<TClaim> list = claimDao.findByPolicyNumber(policyNumber);
        List<Claim> result = Lists.newArrayList();
        if(list!=null&&list.size()>0){
            for(TClaim tc:list){
                result.add(jsonHelper.fromJSON(tc.getContent(), Claim.class));
            }
        }
        return result;
    }

}
