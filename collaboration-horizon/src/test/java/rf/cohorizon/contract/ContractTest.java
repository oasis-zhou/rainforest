package rf.cohorizon.contract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.core.RemoteCall;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractTest {

    @Autowired
    private ContractFactory contractFactory;

    @Test
    public void deploy() throws Exception{
        RemoteCall<Collaboration> call = contractFactory.deployContract();
        Collaboration collaboration = call.send();

        String address = collaboration.getContractAddress();

        System.out.println("Address:" + address);
    }
}
