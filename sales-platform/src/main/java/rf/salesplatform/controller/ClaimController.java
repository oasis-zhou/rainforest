package rf.salesplatform.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rf.claim.ds.ClaimService;
import rf.claim.ds.NoticeOfLossService;
import rf.claim.model.Claim;
import rf.claim.model.ClaimMaterials;
import rf.claim.model.NoticeOfLoss;
import rf.claim.model.QueryCondition;
import rf.claim.model.enums.NoticeStatus;
import rf.foundation.exception.GenericException;
import rf.foundation.model.ResponsePage;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.ContractStatus;
import java.util.Date;
import java.util.Map;

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
    private ClaimService claimService;
    @Autowired
    private PolicyService policyService;
    @Autowired
    private JsonHelper jsonHelper;

    @PostMapping("/loss/notice/apply/{policyNumber}")
    public ResponseEntity applyNoticeOfLoss(@PathVariable("policyNumber") String policyNumber){
        Policy policy = policyService.loadPolicyByPolicyNumber(policyNumber);
        if(policy == null)
            throw new GenericException(30012L);
        if(!policy.getContractStatus().equals(ContractStatus.EFFECTIVE))
            throw new GenericException(40008L);

        NoticeOfLoss noticeOfLoss = new NoticeOfLoss();
        noticeOfLoss.setPolicyNumber(policyNumber);
        noticeOfLoss.setNoticeTime(new Date());
        noticeOfLoss.setNoticeStatus(NoticeStatus.APPLICATION);

        noticeOfLoss = noticeOfLossService.createNoticeOfLoss(noticeOfLoss);

        Map<String, Object> response = Maps.newHashMap();
        response.put("noticeNumber", noticeOfLoss.getNoticeNumber());
        return new ResponseEntity(response,HttpStatus.OK);
    }

    @PostMapping("/loss/notice")
    public ResponseEntity saveNoticeOfLoss(@RequestBody NoticeOfLoss noticeOfLoss){
        noticeOfLossService.saveNotice(noticeOfLoss);
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
    public ResponseEntity getNoticeOfLoss(@PathVariable("noticeNumber") String noticeNumber){
        NoticeOfLoss noticeOfLoss = noticeOfLossService.loadNoticeOfLoss(noticeNumber);
        return new ResponseEntity(noticeOfLoss,HttpStatus.OK);
    }

    @PostMapping(value = "/loss/notice/query")
    public ResponseEntity queryNoticeOfLoss(@RequestBody QueryCondition condition) {

        logger.debug("noticeOfLoss query condition:" + jsonHelper.toJSON(condition));
        ResponsePage<NoticeOfLoss> notices = noticeOfLossService.queryNoticeOfLoss(condition);

        return new ResponseEntity(notices, HttpStatus.OK);
    }

    @PostMapping(value = "/query")
    public ResponseEntity queryClaim(@RequestBody QueryCondition condition) {

        logger.debug("claim query condition:" + jsonHelper.toJSON(condition));
        ResponsePage<Claim> claims = claimService.queryClaim(condition);

        return new ResponseEntity(claims, HttpStatus.OK);
    }
}
