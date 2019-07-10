package rf.cohorizon.ds.impl;

import org.springframework.stereotype.Service;
import rf.cohorizon.ds.CollaborationService;
import rf.cohorizon.model.Message;
import rf.cohorizon.model.Transaction;

import java.util.List;

/**
 * @ClassName CollaborationServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
@Service
public class CollaborationServiceImpl implements CollaborationService {

    @Override
    public String sendTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction getTransaction(String transactionNumber) {
        return null;
    }

    @Override
    public String sendMessage(Message message) {
        return null;
    }

    @Override
    public List<Message> findAllMessages() {
        return null;
    }

    @Override
    public Message getMessage(String msgID) {
        return null;
    }

    @Override
    public String archiveMessage(String msgID) {
        return null;
    }
}
