package rf.cohorizon.ds;

import rf.cohorizon.model.Message;
import rf.cohorizon.model.Transaction;

import java.util.List;

public interface CollaborationService {
    String sendTransaction(Transaction transaction);
    Transaction getTransaction(String transactionNumber);

    String sendMessage(Message message);
    List<Message> queryPendingMessages();
    Message getMessage(String msgID);
    String delPendingMessage(String msgID);
}
