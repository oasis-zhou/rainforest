package rf.saleshorizon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rf.claim.ds.ClaimNumberService;
import rf.claim.model.ClaimMaterials;
import rf.claim.model.NoticeOfLoss;
import rf.claim.model.enums.NoticeStatus;
import rf.foundation.exception.GenericException;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.ContractStatus;
import rf.saleshorizon.ds.NoticeOfLossService;
import rf.saleshorizon.ds.PolicyService;

import javax.websocket.server.PathParam;
import java.util.Date;

/**
 * @ClassName ClaimController
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@RestController
@RequestMapping("v0/api/claim")
public class ClaimController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NoticeOfLossService noticeOfLossService;
    @Autowired
    private ClaimNumberService claimNumberService;
    @Autowired
    private PolicyService policyService;
    @Autowired
    private JsonHelper jsonHelper;

    @PostMapping("/loss/notice/apply/{policyNumber}")
    public ResponseEntity applyNoticeOfLoss(@PathParam("policyNumber") String policyNumber){
        Policy policy = policyService.pullFromChain(policyNumber);
        if(policy == null)
            throw new GenericException(30012L);
        if(!policy.getContractStatus().equals(ContractStatus.EFFECTIVE))
            throw new GenericException(40008L);

        NoticeOfLoss noticeOfLoss = new NoticeOfLoss();
        noticeOfLoss.setPolicyNumber(policyNumber);
        noticeOfLoss.setNoticeTime(new Date());
        noticeOfLoss.setNoticeStatus(NoticeStatus.APPLICATION);

        String noticeNumber = claimNumberService.generateNoticeNumber(noticeOfLoss);
        noticeOfLoss.setNoticeNumber(noticeNumber);

        return new ResponseEntity(noticeOfLoss,HttpStatus.OK);
    }

    @PostMapping("/loss/notice")
    public ResponseEntity createNoticeOfLoss(@RequestBody NoticeOfLoss noticeOfLoss){
        //报案数据上链
        noticeOfLossService.pushToChain(noticeOfLoss);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/upload/materials")
    public ResponseEntity uploadMaterials(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new GenericException(40007L);
        }
        ClaimMaterials materials = new ClaimMaterials();

        return new ResponseEntity(materials,HttpStatus.OK);
    }

    @GetMapping("/loss/notice/{noticeNumber}")
    public ResponseEntity getNoticeOfLoss(@PathParam("noticeNumber") String noticeNumber){
        NoticeOfLoss noticeOfLoss = noticeOfLossService.pullFromChain(noticeNumber);
        return new ResponseEntity(noticeOfLoss,HttpStatus.OK);
    }

}
