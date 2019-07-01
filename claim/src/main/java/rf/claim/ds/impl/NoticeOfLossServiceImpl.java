package rf.claim.ds.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rf.claim.ds.NoticeOfLossService;
import rf.claim.model.ClaimMaterials;
import rf.claim.model.NoticeOfLoss;
import rf.claim.model.NoticeOfLossIndex;
import rf.claim.model.NoticeOfLossQueryCondition;
import rf.claim.repository.NoticeOfLossDao;
import rf.claim.repository.NoticeOfLossIndexDao;
import rf.claim.repository.pojo.TNoticeOfLoss;
import rf.claim.repository.pojo.TNoticeOfLossIndex;
import rf.foundation.exception.GenericException;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;
import rf.foundation.utils.JsonHelper;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class NoticeOfLossServiceImpl implements NoticeOfLossService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NoticeOfLossDao noticeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NumberingService numberingService;

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private NoticeOfLossIndexDao noticeOfLossIndexDao;

    @Override
    public NoticeOfLoss createNoticeOfLoss(NoticeOfLoss notice) {
        TNoticeOfLoss po = noticeDao.findByNoticeNumber(notice.getNoticeNumber());

        if (po == null) {
            po = new TNoticeOfLoss();
        } else {
            //报案号已存在
            throw new GenericException(40000L);
        }
        Date date = new Date();
        //生成新的理赔号
        if (po.getNoticeNumber() == null) {
            Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
            factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));
            String noticeNumber = numberingService.generateNumber(NumberingType.NOTICE_NUMBER, factors);
            notice.setNoticeNumber(noticeNumber);
        } else {
            throw new GenericException(40000L);
        }
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

    @Transactional
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
    public List<NoticeOfLossIndex> queryNoticeOfLoss(NoticeOfLossQueryCondition condition) {
        //PREPARE属于中间状态，还未生成报案，故不允许在查询中展示
        String sql = "select * from t_notice_of_loss_index as p  where 1=1 ";

        List<NoticeOfLossIndex> list = Lists.newArrayList();

        List<Object> queryConditions = Lists.newArrayList();
        if (condition.getMobile() != null && !"".equals(condition.getMobile())) {
            sql += " and p.mobile = ? ";
            queryConditions.add(condition.getMobile());
        }
        if (condition.getClaimant() != null && !"".equals(condition.getClaimant())) {
            sql += " and p.claimant = ? ";
            queryConditions.add(condition.getClaimant());
        }
        if (condition.getNoticeNumber() != null && !"".equals(condition.getNoticeNumber())) {
            sql += " and p.noticeNumber = ? ";
            queryConditions.add(condition.getNoticeNumber());
        }

        if (condition.getNoticeStatus() != null && !"".equals(condition.getNoticeStatus())) {
            sql += " and p.noticeStatus = ? ";
            queryConditions.add(condition.getNoticeStatus());
        }
        sql += " order by p.noticeTime desc ";
        list = jdbcTemplate.query(sql, queryConditions.toArray(), new BeanPropertyRowMapper(NoticeOfLossIndex.class));

        return list;
    }


    @Override
    public void generateNoticeOfLossIndex(NoticeOfLoss notice) {
        TNoticeOfLossIndex po = noticeOfLossIndexDao.findById(notice.getUuid()).get();
        if (po == null) {
            po = new TNoticeOfLossIndex();
        }
        BeanUtils.copyProperties(notice, po);
        //设置报案人姓名、手机号
        if (notice.getClaimant() != null) {
            po.setClaimant(notice.getClaimant().getName());
            po.setMobile(notice.getClaimant().getMobile());
        }
        if (notice.getNoticeStatus() != null) {
            po.setNoticeStatus(notice.getNoticeStatus().name());
        }
        noticeOfLossIndexDao.save(po);
    }


}
