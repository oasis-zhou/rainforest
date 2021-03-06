package rf.bizop.controller;


import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import rf.bizop.contract.ContractFactory;
import rf.bizop.ds.BlockChainService;
import rf.bizop.pub.Constants;
import rf.foundation.utils.JsonHelper;
import rf.rating.dt.DecisionTableService;
import rf.rating.dt.DecisionTableSpec;
import rf.rating.dt.ImportDecisionTableService;
import rf.foundation.exception.GenericException;
import rf.product.ds.ProductService;
import rf.product.model.ProductSpec;

import java.io.File;

/**
 * @ClassName ProductController
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@RestController
@RequestMapping("v0/api/product")
public class ProductController {

    @Value("${operation.mode}")
    private String operationMode;

    @Autowired
    private ImportDecisionTableService importDecisionTableService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DecisionTableService decisionTableService;
    @Autowired
    private BlockChainService blockChainService;
    @Autowired
    private JsonHelper jsonHelper;

    @Transactional
    @PostMapping(value = "/save")
    public ResponseEntity saveProduct(ProductSpec spec){
        productService.saveProduct(spec);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/upload/ratetable")
    public ResponseEntity uploadRateTable(@RequestParam("file") MultipartFile file,@RequestParam("productCode") String productCode){
        if (file.isEmpty()) {
            throw new GenericException(20002L);
        }

        CommonsMultipartFile commonsmultipartfile = (CommonsMultipartFile) file;
        DiskFileItem diskFileItem = (DiskFileItem) commonsmultipartfile.getFileItem();
        File rateFile = diskFileItem.getStoreLocation();

        DecisionTableSpec spec = importDecisionTableService.buildDecisionTableSpec(rateFile);
        decisionTableService.saveRateTable(spec);

        //保存费率表到产品信息
        ProductSpec productSpec = productService.findProduct(productCode);
        if(productSpec == null)
            throw new GenericException(20003L);

        productSpec.getSubComponents().add(spec);
        productService.saveProduct(productSpec);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/release")
    public ResponseEntity deployProduct(ProductSpec spec){
        productService.saveProduct(spec);

        if(Constants.OPERATION_MODE_DECENTRALIZED.equals(operationMode)) {
            blockChainService.releaseProduct(spec);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/query/{productCode}")
    public ResponseEntity loadProduct(@PathVariable("productCode") String productCode){
        ProductSpec spec = productService.findProduct(productCode);

        return new ResponseEntity(spec,HttpStatus.OK);
    }
}
