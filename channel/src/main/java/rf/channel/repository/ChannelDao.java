package rf.channel.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.channel.repository.pojo.TChannel;

/**
 * Created by admin on 2017/8/22.
 */
public interface ChannelDao extends CrudRepository<TChannel, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.accessKey=:accessKey")
    TChannel findByAccessKey(@Param("accessKey") String accessKey);
}
