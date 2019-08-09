package rf.cohorizon.contract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.core.RemoteCall;
import rf.cohorizon.ds.IdentityService;
import rf.cohorizon.model.Registration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractTest {

    @Autowired
    private ContractFactory contractFactory;
    @Autowired
    private IdentityService identityService;

//    @Test
    public void deploy() throws Exception{
        RemoteCall<Collaboration> call = contractFactory.deployContract();
        Collaboration collaboration = call.send();

        String address = collaboration.getContractAddress();

        System.out.println("Address:" + address);
    }

    @Test
    public void registration() {
        Registration registration = new Registration();
        registration.setOrgCode("001");
        registration.setPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDoQj/Smz0AEiXWs2c6MNBvlQ9zaxAF2AetLyrG33K89d6PoKxcpSD6D+i1wdPVD1d/yGSy3k7R+afhU0e9a6ZmHjXZSlfswpc1GKmMiz6ZudZbUOnKwuSGpydyOCqzduM1YYi+NclyXUfNiBzmOryhRkNSfS6L5k39EScoM0bnJQIDAQAB");
        registration.setAccountAddress("0x7eff122b94897ea5b0e2a9abf47b86337fafebdc");
        String tx = identityService.register(registration);

        String pubKey = identityService.getPubKey("001");

        System.out.println("pubKey:" + pubKey);
    }
}
