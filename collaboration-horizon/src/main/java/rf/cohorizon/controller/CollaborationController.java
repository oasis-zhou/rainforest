package rf.cohorizon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.cohorizon.ds.CollaborationService;
import rf.cohorizon.ds.IdentityService;
import rf.cohorizon.model.Message;
import rf.cohorizon.model.Transaction;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.Guid;
import rf.foundation.utils.AESUtils;
import rf.foundation.utils.JsonHelper;
import rf.foundation.utils.RSAUtils;

import javax.websocket.server.PathParam;
import java.util.List;

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

    @Value("${crypto.public.key}")
    private String pubKey;
    @Value("${crypto.private.key}")
    private String privateKey;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private CollaborationService collaborationService;
    @Autowired
    private JsonHelper jsonHelper;

    @PostMapping("/transaction")
    public ResponseEntity sendTransaction(@RequestBody Transaction transaction){

        try {
            String randomKey = Guid.random(10);
            String cryptoData = AESUtils.encrypt(transaction.getBusinessData(), randomKey);
            String fromCryptoKey = RSAUtils.encryptByPublicKey(randomKey,pubKey);
            String toPubKey = identityService.getPubKey(transaction.getTo());
            String toCryptoKey = RSAUtils.encryptByPublicKey(randomKey,toPubKey);

            transaction.setBusinessData(cryptoData);
            transaction.getCryptoKeys().put(pubKey,fromCryptoKey);
            transaction.getCryptoKeys().put(toPubKey,toCryptoKey);

            collaborationService.sendTransaction(transaction);

        } catch (Exception e) {
            throw new GenericException(10003L);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/transaction/{transactionNumber}")
    public ResponseEntity getTransaction(@PathParam("transactionNumber") String transactionNumber) {
        Transaction transaction = collaborationService.getTransaction(transactionNumber);
        try {
            String cryptoKey = transaction.getCryptoKeys().get(pubKey);
            String randomKey = RSAUtils.decryptByPrivateKey(cryptoKey, privateKey);
            String businessData = AESUtils.decrypt(transaction.getBusinessData(),randomKey);
            transaction.setBusinessData(businessData);
            transaction.getCryptoKeys().clear();

        }catch (Exception e) {
            throw new GenericException(10004L);
        }
        return new ResponseEntity(transaction,HttpStatus.OK);
    }

    @PostMapping("/message")
    public ResponseEntity sendMessage(@RequestBody Message message) {
        try {
            String randomKey = Guid.random(10);
            String cryptoData = AESUtils.encrypt(message.getContent(), randomKey);
            String toPubKey = identityService.getPubKey(message.getTo());
            String toCryptoKey = RSAUtils.encryptByPublicKey(randomKey,toPubKey);
            message.setContent(cryptoData);
            message.setPubKey(toPubKey);
            message.setCryptoKey(toCryptoKey);

            collaborationService.sendMessage(message);
        } catch (Exception e) {
            throw new GenericException(10003L);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/message/list")
    public ResponseEntity findAllMessages(){
        List<Message> messages = collaborationService.findAllMessages();

        messages.forEach(msg -> {
            try {
                String cryptoKey = msg.getCryptoKey();
                String randomKey = RSAUtils.decryptByPrivateKey(cryptoKey, privateKey);
                String data = AESUtils.decrypt(msg.getContent(),randomKey);
                msg.setContent(data);
                msg.setPubKey(null);
                msg.setCryptoKey(null);
            }catch (Exception e) {
                throw new GenericException(10004L);
            }
        });
        return new ResponseEntity(messages,HttpStatus.OK);
    }

    @GetMapping("/message/{msgID}")
    public ResponseEntity getMessage(@PathParam("msgID") String msgID){
        Message message = collaborationService.getMessage(msgID);
        try {
            String cryptoKey = message.getCryptoKey();
            String randomKey = RSAUtils.decryptByPrivateKey(cryptoKey, privateKey);
            String data = AESUtils.decrypt(message.getContent(),randomKey);
            message.setContent(data);
            message.setPubKey(null);
            message.setCryptoKey(null);

        }catch (Exception e) {
            throw new GenericException(10004L);
        }
        return new ResponseEntity(message,HttpStatus.OK);
    }

    @PostMapping("/message/archive/{msgID}")
    public ResponseEntity archiveMessage(@PathParam("msgID") String msgID){
        collaborationService.archiveMessage(msgID);
        return new ResponseEntity(HttpStatus.OK);
    }
}
