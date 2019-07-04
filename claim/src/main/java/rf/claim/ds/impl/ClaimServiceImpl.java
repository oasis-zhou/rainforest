package rf.claim.ds.impl;


import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import rf.claim.ds.ClaimService;
import rf.claim.model.Claim;
import rf.claim.model.NoticeOfLoss;
import rf.claim.model.QueryCondition;
import rf.claim.repository.ClaimDao;
import rf.claim.repository.pojo.TClaim;
import rf.claim.repository.pojo.TNoticeOfLoss;
import rf.foundation.model.ResponsePage;
import rf.foundation.numbering.NumberingService;
import rf.foundation.utils.JsonHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class ClaimServiceImpl implements ClaimService {

    private Logger logger = LoggerFactory.getLogger(getClass());

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

    @Override
    public ResponsePage<Claim> queryClaim(QueryCondition condition) {

        // 构造自定义查询条件
        Specification<TClaim> queryCondition = new Specification<TClaim>() {
            @Override
            public Predicate toPredicate(Root<TClaim> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (condition.getPolicyNumber() != null && !condition.getPolicyNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("policyNumber"),condition.getPolicyNumber()));
                }
                if (condition.getClaimNumber() != null && !condition.getClaimNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("claimNumber"),condition.getClaimNumber()));
                }
                if (condition.getNoticeNumber() != null && !condition.getNoticeNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("noticeNumber"),condition.getNoticeNumber()));
                }
                if (condition.getClaimant() != null && !condition.getClaimant().equals("")) {
                    predicateList.add(criteriaBuilder.like(root.get("claimant"),"%" + condition.getClaimant() + "%"));
                }
                if (condition.getClaimantIdNumber() != null && !condition.getClaimantIdNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("claimantIdNumber"),condition.getClaimantIdNumber()));
                }
                if (condition.getMobile() != null && !condition.getMobile().equals("")) {
                    predicateList.add(criteriaBuilder.like(root.get("mobile"),"%" + condition.getMobile() + "%"));
                }
                if (condition.getStatus() != null && !condition.getStatus().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("statusCode"),condition.getStatus()));
                }
                if (condition.getDateStart()!=null&&condition.getDateEnd()!=null){
                    predicateList.add(criteriaBuilder.between(root.get("claimTime"),condition.getDateStart(),condition.getDateEnd()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        Page<TClaim> page = claimDao.findAll(queryCondition, PageRequest.of(condition.getPageNo() - 1, condition.getPageSize(), Sort.by(Sort.Direction.DESC, "claimTime")));

        return buildResponsePage(page);
    }

    private ResponsePage<Claim> buildResponsePage(Page<TClaim> page){
        List<Claim> datas = page.getContent().stream()
                .map(po -> {
                    Claim claim = jsonHelper.fromJSON(po.getContent(), Claim.class);
                    return claim;
                })
                .collect(toList());
        return new ResponsePage<>(page, datas);
    }

}
