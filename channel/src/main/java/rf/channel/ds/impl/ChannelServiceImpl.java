package rf.channel.ds.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rf.channel.ds.ChannelNumberService;
import rf.channel.ds.ChannelService;
import rf.channel.model.ChannelSpec;
import rf.channel.model.QueryCondition;
import rf.channel.model.SalesAgreementSpec;
import rf.channel.repository.ChannelDao;
import rf.channel.repository.pojo.TChannel;
import rf.foundation.model.ResponsePage;
import rf.foundation.utils.JsonHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by admin on 2017/8/22.
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private JsonHelper jsonHelper;
    @Autowired
    private ChannelNumberService channelNumberService;

    @Override
    public void saveChannel(ChannelSpec channelSpec) {
        TChannel po = channelDao.findByIdNumber(channelSpec.getIdType(),channelSpec.getIdNumber());
        if(po == null) {
            String code = channelNumberService.generateChannelCode(channelSpec);
            channelSpec.setCode(code);

            po = new TChannel();
        }else {
            channelSpec.setUuid(po.getUuid());
        }

        BeanUtils.copyProperties(channelSpec, po);
        String json = jsonHelper.toJSON(channelSpec);
        po.setContent(json);
        po.setTypeCode(channelSpec.getType().name());
        channelDao.save(po);
    }

    @Override
    public ChannelSpec loadChannel(String code) {

        TChannel po = channelDao.findByCode(code);
        ChannelSpec channelSpec = jsonHelper.fromJSON(po.getContent(), ChannelSpec.class);

        return channelSpec;
    }

    @Override
    @Cacheable(value="channel" , key = "#accessKey")
    public ChannelSpec findByAccessKey(String accessKey) {
        TChannel po = channelDao.findByAccessKey(accessKey);
        ChannelSpec channelSpec = jsonHelper.fromJSON(po.getContent(), ChannelSpec.class);
        return channelSpec;
    }

    @Override
    public void saveSalesAgreement(SalesAgreementSpec agreementSpec) {
        ChannelSpec channelSpec = this.loadChannel(agreementSpec.getChannelCode());

        if(channelSpec != null)
            channelSpec.getSubComponents().add(agreementSpec);

        this.saveChannel(channelSpec);
    }

    @Override
    public ResponsePage<ChannelSpec> findChannels(QueryCondition condition) {

        // 构造自定义查询条件
        Specification<TChannel> queryCondition = new Specification<TChannel>() {
            @Override
            public Predicate toPredicate(Root<TChannel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                if (condition.getIdType()!=null&&!"".equals(condition.getIdType())){
                    predicateList.add(criteriaBuilder.equal(root.get("idType"),condition.getIdType()));
                }
                if (condition.getIdNumber()!=null&&!"".equals(condition.getIdNumber())){
                    predicateList.add(criteriaBuilder.equal(root.get("idNumber"),condition.getIdNumber()));
                }
                if (condition.getCode()!=null&&!"".equals(condition.getCode())){
                    predicateList.add(criteriaBuilder.equal(root.get("code"),condition.getCode()));
                }
                if (condition.getName()!=null&&!"".equals(condition.getName())){
                    predicateList.add(criteriaBuilder.equal(root.get("name"),"%" + condition.getName() + "%"));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        Page<TChannel> page = channelDao.findAll(queryCondition, PageRequest.of(condition.getPageNo() - 1, condition.getPageSize(), Sort.by(Sort.Direction.DESC, "creationTime")));

        return buildResponsePage(page);
    }

    private ResponsePage<ChannelSpec> buildResponsePage(Page<TChannel> page){
        List<ChannelSpec> datas = page.getContent().stream()
                .map(po -> {
                    ChannelSpec channelSpec = jsonHelper.fromJSON(po.getContent(), ChannelSpec.class);
                    return channelSpec;
                })
                .collect(toList());
        return new ResponsePage<>(page, datas);
    }
}
