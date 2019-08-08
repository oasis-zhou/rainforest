package rf.cohorizon.ds;

import rf.cohorizon.model.Message;
import rf.cohorizon.model.Transaction;

import java.util.List;

public interface CollaborationService {

    String createTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    Transaction getTransaction(String transactionNumber);

    String sendMessage(Message message);
    List<Message> findAllMessages();
    Message getMessage(String msgID);
    String archiveMessage(String msgID);
}
