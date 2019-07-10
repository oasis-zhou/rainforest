package rf.saleshorizon.ds.impl;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.exception.GenericException;
import rf.saleshorizon.conf.IPFSServer;
import rf.saleshorizon.ds.IPFSService;
import java.io.File;
import java.util.Optional;

/**
 * Created by zhouzheng on 2018/5/8.
 */
@Component
public class IPFSServiceImpl implements IPFSService {

    @Autowired
    private IPFSServer server;

    @Override
    public Optional<String> addString(String name, String content) {
        IPFS ipfs = server.instance();
        String ipfsHash;
        try {
            NamedStreamable.ByteArrayWrapper byteWrapper = new NamedStreamable.ByteArrayWrapper(name, content.getBytes("UTF-8"));
            MerkleNode addResult = ipfs.add(byteWrapper).get(0);
            ipfsHash = addResult.hash.toBase58();
        }catch (Exception e){
            throw new GenericException(e);
        }

        return Optional.ofNullable(ipfsHash);
    }

    @Override
    public String getString(String ipfsHash) {
        IPFS ipfs = server.instance();
        String content;
        try {
            Multihash filePointer = Multihash.fromBase58(ipfsHash);
            byte[] contentByes = ipfs.cat(filePointer);
            content = new String(contentByes,"UTF-8");
        }catch (Exception e){
            throw new GenericException(e);
        }
        return content;
    }

    @Override
    public Optional<String> addFile(File file) {
        IPFS ipfs = server.instance();
        String ipfsHash;
        try {
            NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
            MerkleNode addResult = ipfs.add(fileWrapper).get(0);
            ipfsHash = addResult.hash.toBase58();
        }catch (Exception e){
            throw new GenericException(e);
        }
        return Optional.ofNullable(ipfsHash);
    }

    @Override
    public Optional<String> addFile(String name,byte[] fileBytes) {
        IPFS ipfs = server.instance();
        String ipfsHash;
        try {
            NamedStreamable.ByteArrayWrapper fileByteWrapper = new NamedStreamable.ByteArrayWrapper(name,fileBytes);
            MerkleNode addResult = ipfs.add(fileByteWrapper).get(0);
            ipfsHash = addResult.hash.toBase58();
        }catch (Exception e){
            throw new GenericException(e);
        }
        return Optional.ofNullable(ipfsHash);
    }

    @Override
    public byte[] getFile(String ipfsHash) {
        IPFS ipfs = server.instance();
        byte[] fileByes;
        try {
            Multihash filePointer = Multihash.fromBase58(ipfsHash);
            fileByes = ipfs.cat(filePointer);
        }catch (Exception e){
            throw new GenericException(e);
        }

        return fileByes;
    }
}
