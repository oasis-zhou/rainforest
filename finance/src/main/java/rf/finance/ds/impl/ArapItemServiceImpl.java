package rf.finance.ds.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.finance.ds.ArapItemService;
import rf.finance.model.ArapItem;
import rf.finance.repository.ArapItemDao;
import rf.finance.repository.pojo.TArapItem;
import rf.foundation.utils.JsonHelper;


@Service
public class ArapItemServiceImpl implements ArapItemService {

    @Autowired
    private ArapItemDao arapItemDao;
    @Autowired
    private JsonHelper jsonHelper;

    @Override
    public void keepAccounts(ArapItem record) {
        TArapItem po = arapItemDao.findByRefNumber(record.getTransType().name(),record.getRefBizNumber());

        if(po == null){
            po = new TArapItem();
        }else{
            return;
        }

        BeanUtils.copyProperties(record,po);
        po.setTransType(record.getTransType().name());

        String content = jsonHelper.toJSON(record);
        po.setContent(content);

        arapItemDao.save(po);
    }
}
