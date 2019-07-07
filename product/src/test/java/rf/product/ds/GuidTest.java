package rf.product.ds;


import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rf.foundation.pub.Guid;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuidTest {

    @Test
    public void testGuid(){

        String randomId = Guid.random(10);
        System.out.println("random id ==" + randomId);

        String uuid = Guid.generateUuid();
        System.out.println("uuid ==" + uuid);

        int randomNumber = Guid.randomNumber();
        System.out.println("randomNumber ==" + randomNumber);

        LocalTime time = LocalTime.now();
        String hourMinute = time.toString("HHmm");
        System.out.println("hourMinute ==" + hourMinute);
    }
}
