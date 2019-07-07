package rf.claim.ds.impl;

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
import rf.claim.ds.ClaimNumberService;
import rf.claim.ds.NoticeOfLossService;
import rf.claim.model.ClaimMaterials;
import rf.claim.model.NoticeOfLoss;
import rf.claim.model.QueryCondition;
import rf.claim.repository.NoticeOfLossDao;
import rf.claim.repository.pojo.TNoticeOfLoss;
import rf.foundation.exception.GenericException;
import rf.foundation.model.ResponsePage;
import rf.foundation.utils.JsonHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

import static java.util.stream.Collectors.toList;


@Service
public class NoticeOfLossServiceImpl implements NoticeOfLossService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NoticeOfLossDao noticeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JsonHelper jsonHelper;
    @Autowired
    private ClaimNumberService claimNumberService;

    @Override
    public NoticeOfLoss createNoticeOfLoss(NoticeOfLoss notice) {
        TNoticeOfLoss po = noticeDao.findByNoticeNumber(notice.getNoticeNumber());

        if (po == null) {
            po = new TNoticeOfLoss();
        } else {
            //报案号已存在
            throw new GenericException(40000L);
        }

        String noticeNumber = claimNumberService.generateNoticeNumber(notice);
        notice.setNoticeNumber(noticeNumber);

        BeanUtils.copyProperties(notice, po);
        String content = jsonHelper.toJSON(notice);
        po.setContent(content);
        logger.debug("准备新建理赔案件号:" + notice.getNoticeNumber());
        noticeDao.save(po);

        return notice;
    }

    @Override
    public NoticeOfLoss loadNoticeOfLoss(String noticeNumber) {
        TNoticeOfLoss po = noticeDao.findByNoticeNumber(noticeNumber);

        NoticeOfLoss notice = null;
        if (po != null) {
            notice = jsonHelper.fromJSON(po.getContent(), NoticeOfLoss.class);
        }
        return notice;
    }

    @Override
    public void upload(ClaimMaterials materials) {

    }

    @Override
    public void saveNotice(NoticeOfLoss notice) {
        TNoticeOfLoss po = noticeDao.findByNoticeNumber(notice.getNoticeNumber());
        if (po == null) {
            //报案号不存在异常
            throw new GenericException(40001L);
        }
        Date date = new Date();
        //更新修改日期
        po.setModificationTime(date);
        String content = jsonHelper.toJSON(notice);
        po.setContent(content);
        logger.debug("begin to save noticeDao");
        noticeDao.save(po);
    }


    @Override
    public ResponsePage<NoticeOfLoss> queryNoticeOfLoss(QueryCondition condition) {

        // 构造自定义查询条件
        Specification<TNoticeOfLoss> queryCondition = new Specification<TNoticeOfLoss>() {
            @Override
            public Predicate toPredicate(Root<TNoticeOfLoss> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (condition.getPolicyNumber() != null && !condition.getPolicyNumber().equals("")) {
                    predicateList.add(criteriaBuilder.equal(root.get("policyNumber"),condition.getPolicyNumber()));
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
                    predicateList.add(criteriaBuilder.between(root.get("noticeTime"),condition.getDateStart(),condition.getDateEnd()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        Page<TNoticeOfLoss> page = noticeDao.findAll(queryCondition, PageRequest.of(condition.getPageNo() - 1, condition.getPageSize(), Sort.by(Sort.Direction.DESC, "noticeTime")));

        return buildResponsePage(page);

    }

    private ResponsePage<NoticeOfLoss> buildResponsePage(Page<TNoticeOfLoss> page){
        List<NoticeOfLoss> datas = page.getContent().stream()
                .map(po -> {
                    NoticeOfLoss noticeOfLoss = jsonHelper.fromJSON(po.getContent(), NoticeOfLoss.class);
                    return noticeOfLoss;
                })
                .collect(toList());
        return new ResponsePage<>(page, datas);
    }


}
