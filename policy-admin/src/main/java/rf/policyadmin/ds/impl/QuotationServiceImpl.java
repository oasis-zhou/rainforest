package rf.policyadmin.ds.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rf.foundation.model.ResponsePage;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.ds.BusinessNumberService;
import rf.policyadmin.ds.QuotationService;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.QueryCondition;
import rf.policyadmin.model.Quotation;
import rf.policyadmin.model.enums.QuotationStatus;
import rf.policyadmin.repository.QuotationDao;
import rf.policyadmin.repository.pojo.TEndorsement;
import rf.policyadmin.repository.pojo.TQuotation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhengzhou on 16/8/8.
 */
@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationDao dao;

    @Autowired
    private JsonHelper jsonHelper;
    @Autowired
    private BusinessNumberService businessNumberService;

    @Override
    public String generateQuotation(Quotation quotation){

        TQuotation po = new TQuotation();
        BeanUtils.copyProperties(quotation,po);

        po.setQuotationStatusCode(QuotationStatus.QUOTATION_INPROCESS.name());
        po.setQuotationDate(new Date());
        if(quotation.getQuotationNumber() == null){
            String quotationNumber = businessNumberService.generateQuotationNumber(quotation);
            po.setQuotationNumber(quotationNumber);
        }

        String content = jsonHelper.toJSON(quotation);
        po.setContent(content);

        dao.save(po);

        return po.getQuotationNumber();
    }

    @Override
    public ResponsePage<Quotation> findQuotations(QueryCondition condition) {

        // 构造自定义查询条件
        Specification<TQuotation> queryCondition = new Specification<TQuotation>() {
            @Override
            public Predicate toPredicate(Root<TQuotation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                if (condition.getChannelCode()!=null&&!"".equals(condition.getChannelCode())){
                    predicateList.add(criteriaBuilder.equal(root.get("channelCode"),condition.getChannelCode()));
                }
                if (condition.getProductCode()!=null&&!"".equals(condition.getProductCode())){
                    predicateList.add(criteriaBuilder.equal(root.get("productCode"),condition.getProductCode()));
                }
                if (condition.getDateStart()!=null&&condition.getDateEnd()!=null){
                    predicateList.add(criteriaBuilder.between(root.get("quotationDate"),condition.getDateStart(),condition.getDateEnd()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        Page<TQuotation> page = dao.findAll(queryCondition, PageRequest.of(condition.getPageNo() - 1, condition.getPageSize(), Sort.by(Sort.Direction.DESC, "quotationDate")));

        return buildResponsePage(page);
    }

    private ResponsePage<Quotation> buildResponsePage(Page<TQuotation> page){
        List<Quotation> datas = page.getContent().stream()
                .map(po -> {
                    Quotation quotation = jsonHelper.fromJSON(po.getContent(), Quotation.class);
                    return quotation;
                })
                .collect(toList());
        return new ResponsePage<>(page, datas);
    }
}
