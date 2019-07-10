package rf.saleshorizon.conf;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhouzheng on 2018/5/8.
 */
@Component
public class IPFSServer {

    @Value("${ipfs.host.url}")
    private String ipfsUrl;
    private static IPFS ipfsServer;

    public IPFS instance(){
        if(ipfsServer == null)
            ipfsServer = new IPFS(ipfsUrl);

        return ipfsServer;
    }

}
