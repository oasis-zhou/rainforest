package rf.cohorizon.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import rf.cohorizon.ds.CollaborationService;
import rf.cohorizon.ds.IdentityService;
import rf.cohorizon.model.Message;
import rf.cohorizon.model.Transaction;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.Guid;
import rf.foundation.utils.AESUtils;
import rf.foundation.utils.RSAUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName TransactionController
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
@RestController
@RequestMapping("v0/api/collaboration")
public class CollaborationController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${crypto.public.key}")
    private String pubKey;
    @Value("${crypto.private.key}")
    private String privateKey;
    @Value("${guid.org.code}")
    private String orgCode;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private CollaborationService collaborationService;

    @PostMapping("/transaction")
    public ResponseEntity createTransaction(@RequestBody Transaction transaction){
        try {
            if (!orgCode.equals(transaction.getFrom()))
                throw new GenericException(10010L);
            String randomKey = Guid.random(10);
            String cryptoData = AESUtils.encrypt(transaction.getBusinessData(), randomKey);
            transaction.setBusinessData(cryptoData);
            transaction.getParticipants().add(transaction.getFrom());
            transaction.getParticipants().add(transaction.getTo());
            Set<String> participants = transaction.getParticipants();
            for (String participant : participants) {
                String pubKey = identityService.getPubKey(participant);
                String cryptoKey = RSAUtils.encryptByPublicKey(randomKey,pubKey);
                transaction.getCryptoKeys().put(pubKey,cryptoKey);
            }

            String tx = collaborationService.sendTransaction(transaction);
            Map<String, Object> response = Maps.newHashMap();
            response.put("tx", tx);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (GenericException e){
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(10003L);
        }
    }

    @PostMapping("/transaction/update")
    public ResponseEntity updateTransaction(@RequestBody Transaction transaction){
        if (!orgCode.equals(transaction.getFrom()))
            throw new GenericException(10010L);

        Transaction tran = collaborationService.getTransaction(transaction.getTransactionNumber());
        if (tran == null)
            throw new GenericException(10009L);
        try {
            String cryptoKey = tran.getCryptoKeys().get(pubKey);
            if(StringUtils.isEmpty(cryptoKey))
                throw new GenericException(10007L);
            String randomKey = RSAUtils.decryptByPrivateKey(cryptoKey, privateKey);
            String cryptoData = AESUtils.encrypt(transaction.getBusinessData(), randomKey);
            tran.setBusinessData(cryptoData);
            tran.setFrom(transaction.getFrom());
            tran.setTo(transaction.getTo());
            transaction.getParticipants().add(transaction.getFrom());
            transaction.getParticipants().add(transaction.getTo());
            Set<String> participants = transaction.getParticipants();
            tran.getParticipants().addAll(participants);
            for (String participant : participants) {
                String pubKey = identityService.getPubKey(participant);
                cryptoKey = RSAUtils.encryptByPublicKey(randomKey,pubKey);
                tran.getCryptoKeys().put(pubKey,cryptoKey);
            }

            String tx = collaborationService.sendTransaction(tran);
            Map<String, Object> response = Maps.newHashMap();
            response.put("tx", tx);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (GenericException e){
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(10003L);
        }
    }

    @GetMapping("/transaction/{transactionNumber}")
    public ResponseEntity getTransaction(@PathVariable("transactionNumber") String transactionNumber) {
        Transaction transaction = collaborationService.getTransaction(transactionNumber);
        if (transaction == null)
            throw new GenericException(10009L);
        try {
            String cryptoKey = transaction.getCryptoKeys().get(pubKey);
            if(StringUtils.isEmpty(cryptoKey))
                throw new GenericException(10007L);
            String randomKey = RSAUtils.decryptByPrivateKey(cryptoKey, privateKey);
            String businessData = AESUtils.decrypt(transaction.getBusinessData(),randomKey);
            transaction.setBusinessData(businessData);
            transaction.getCryptoKeys().clear();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(10004L);
        }
        return new ResponseEntity(transaction, HttpStatus.OK);
    }

    @PostMapping("/message")
    public ResponseEntity sendMessage(@RequestBody Message message) {
        try {
            if (!orgCode.equals(message.getFrom()))
                throw new GenericException(10010L);
            String randomKey = Guid.random(10);
            String cryptoData = AESUtils.encrypt(message.getPayload(), randomKey);
            String toPubKey = identityService.getPubKey(message.getTo());
            String toCryptoKey = RSAUtils.encryptByPublicKey(randomKey,toPubKey);
            message.setPayload(cryptoData);
            message.setPubKey(toPubKey);
            message.setCryptoKey(toCryptoKey);

            String tx = collaborationService.sendMessage(message);
            Map<String, Object> response = Maps.newHashMap();
            response.put("tx", tx);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (GenericException e){
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(10003L);
        }
    }

    @GetMapping("/pending/messages")
    public ResponseEntity queryPendingMessages(){
        List<Message> messages = collaborationService.queryPendingMessages();
        try {
            for (Message msg : messages) {
                String cryptoKey = msg.getCryptoKey();
                String randomKey = RSAUtils.decryptByPrivateKey(cryptoKey, privateKey);
                String data = AESUtils.decrypt(msg.getPayload(), randomKey);
                msg.setPayload(data);
                msg.setPubKey(null);
                msg.setCryptoKey(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(10004L);
        }
        return new ResponseEntity(messages, HttpStatus.OK);
    }

    @GetMapping("/message/{msgID}")
    public ResponseEntity getMessage(@PathVariable("msgID") String msgID){
        Message message = collaborationService.getMessage(msgID);
        if (message == null)
            throw new GenericException(10008L);
        try {
            String cryptoKey = message.getCryptoKey();
            String randomKey = RSAUtils.decryptByPrivateKey(cryptoKey, privateKey);
            String data = AESUtils.decrypt(message.getPayload(),randomKey);
            message.setPayload(data);
            message.setPubKey(null);
            message.setCryptoKey(null);

            return new ResponseEntity(message, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(10004L);
        }
    }

    @PostMapping("/message/archive/{msgID}")
    public ResponseEntity archiveMessage(@PathVariable("msgID") String msgID){
        String tx = collaborationService.delPendingMessage(msgID);
        Map<String, Object> response = Maps.newHashMap();
        response.put("tx", tx);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
