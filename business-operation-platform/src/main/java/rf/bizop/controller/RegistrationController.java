package rf.bizop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.bizop.ds.BlockChainService;
import rf.bizop.model.Registration;


@RestController
@RequestMapping("v0/api/channel")
public class RegistrationController {

    @Autowired
    private BlockChainService blockChainService;

    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody Registration registration){
        String response = blockChainService.register(registration);

        return new ResponseEntity(response,HttpStatus.OK);
    }
}
