package rf.cohorizon.ds.impl;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ContractFactory contractFactory;
    @Autowired
    private JsonHelper jsonHelper;

    @Override
    public String sendTransaction(Transaction transaction) {
        String tx = null;
        try {
            Collaboration collaboration = contractFactory.loadContractWithProxy();
            String receiver = transaction.getTo();
            String transactionJson = jsonHelper.toJSON(transaction);
            logger.info("Send transaction :" + transactionJson);
            RemoteCall<TransactionReceipt> remoteCall = collaboration.sendTransaction(transaction.getTransactionNumber(), transactionJson, receiver);

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }

        return tx;
    }

    @Override
    public Transaction getTransaction(String transactionNumber) {
        logger.info("Get transaction transactionNumber :" + transactionNumber);

        Transaction transaction = null;
        try {
            Collaboration collaboration = contractFactory.loadContractWithProxy();
            RemoteCall<String> remoteCall = collaboration.findTransaction(transactionNumber);
            byte[] response = remoteCall.send().getBytes();
            if (response.length > 0)
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
            Collaboration collaboration = contractFactory.loadContractWithProxy();
            String msgJson = jsonHelper.toJSON(message);
            logger.info("Send message :" + msgJson);
            RemoteCall<TransactionReceipt> remoteCall = collaboration.sendMessage(message.getMsgID(), msgJson, message.getTo());

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

        logger.info("Query pending messages" );
        try {
            Collaboration collaboration = contractFactory.loadContractWithProxy();
            RemoteCall<String> remoteCall = collaboration.findPendingMessagesByOwner();
            byte[] response = remoteCall.send().getBytes();
            if (response.length > 0) {
                String msgIDStr = new String(response);
                String[] msgIDs = msgIDStr.split(",");

                for (String msgID : msgIDs) {
                    messages.add(getMessage(msgID));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }

        return messages;
    }

    @Override
    public Message getMessage(String msgID) {
        logger.info("Get message ID : " + msgID );

        Message message = null;
        try {
            Collaboration collaboration = contractFactory.loadContractWithProxy();
            RemoteCall<String> remoteCall = collaboration.findMessage(msgID);
            byte[] response = remoteCall.send().getBytes();
            if (response.length > 0)
                message = jsonHelper.fromJSON(new String(response),Message.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }

        return message;
    }

    @Override
    public String delPendingMessage(String msgID) {
        logger.info("Delete pending message ID : " + msgID );

        String tx = null;
        try {
            Collaboration collaboration = contractFactory.loadContractWithProxy();
            RemoteCall<TransactionReceipt> remoteCall = collaboration.withdrawPendingMessage(msgID);

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return tx;
    }
}
