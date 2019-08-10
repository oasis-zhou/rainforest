package rf.saleshorizon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.saleshorizon.ds.BlockChainService;


@RestController
@RequestMapping("v0/api/channel")
public class RegistrationController {

    @Autowired
    private BlockChainService blockChainService;

    @PostMapping("/registration")
    public ResponseEntity register(@RequestParam String accountAddress){
        String response = blockChainService.register(accountAddress);

        return new ResponseEntity(response,HttpStatus.OK);
    }
}
