package rf.cohorizon.ds.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import rf.cohorizon.contract.Collaboration;
import rf.cohorizon.contract.ContractFactory;
import rf.cohorizon.ds.CollaborationService;
import rf.cohorizon.model.Message;
import rf.cohorizon.model.Transaction;
import rf.foundation.exception.GenericException;
import rf.foundation.utils.JsonHelper;

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

    @Autowired
    private ContractFactory contractFactory;
    @Autowired
    private JsonHelper jsonHelper;

    @Override
    public String sendTransaction(Transaction transaction) {
        String tx = null;
        try {
            Collaboration collaboration = contractFactory.loadContract();
            String receiver = transaction.getTo();
            RemoteCall<TransactionReceipt> remoteCall = collaboration.sendTransaction(transaction.getTransactionNumber(), jsonHelper.toJSON(transaction), receiver);

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }

        return tx;
    }

    @Override
    public Transaction getTransaction(String transactionNumber) {
        Transaction transaction = null;
        try {
            Collaboration collaboration = contractFactory.loadContract();
            RemoteCall<String> remoteCall = collaboration.findTransaction(transactionNumber);
            byte[] response = remoteCall.send().getBytes();

            transaction = jsonHelper.fromJSON(new String(response),Transaction.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }

        return transaction;
    }

    @Override
    public String sendMessage(Message message) {
        String tx = null;
        try {
            Collaboration collaboration = contractFactory.loadContract();
            RemoteCall<TransactionReceipt> remoteCall = collaboration.sendMessage(message.getMsgID(), jsonHelper.toJSON(message), message.getTo());

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }

        return tx;
    }

    @Override
    public List<Message> queryPendingMessages() {
        List<Message> messages = Lists.newArrayList();
        try {
            Collaboration collaboration = contractFactory.loadContract();
            RemoteCall<String> remoteCall = collaboration.findPendingMessagesByOwner();
            byte[] response = remoteCall.send().getBytes();
            String msgIDStr = new String(response);
            String[] msgIDs = msgIDStr.split(",");

            for (String msgID : msgIDs) {
                messages.add(getMessage(msgID));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }

        return messages;
    }

    @Override
    public Message getMessage(String msgID) {
        Message message = null;
        try {
            Collaboration collaboration = contractFactory.loadContract();
            RemoteCall<String> remoteCall = collaboration.findMessage(msgID);
            byte[] response = remoteCall.send().getBytes();

            message = jsonHelper.fromJSON(new String(response),Message.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }

        return message;
    }

    @Override
    public String delPendingMessage(String msgID) {
        String tx = null;
        try {
            Collaboration collaboration = contractFactory.loadContract();
            RemoteCall<TransactionReceipt> remoteCall = collaboration.withdrawPendingMessage(msgID);

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return tx;
    }
}
