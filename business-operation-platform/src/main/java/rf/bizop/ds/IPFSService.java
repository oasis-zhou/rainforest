package rf.bizop.ds;

import java.io.File;
import java.util.Optional;

/**
 * Created by zhouzheng on 2018/5/8.
 */
public interface IPFSService {

    Optional<String> addString(String name, String content);

    String getString(String ipfsHash);

    Optional<String> addFile(File file);

    Optional<String> addFile(String name, byte[] fileBytes);

    byte[] getFile(String ipfsHash);
}
