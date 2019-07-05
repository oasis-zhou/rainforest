package rf.channel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.channel.repository.pojo.TChannel;

import java.util.List;

/**
 * Created by admin on 2017/8/22.
 */
public interface ChannelDao extends JpaRepository<TChannel, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.idType=:idType and idNumber=:idNumber")
    TChannel findByIdNumber(@Param("idType") String idType, @Param("idNumber") String idNumber);

    @Query("SELECT m FROM #{#entityName} m WHERE m.code=:code")
    TChannel findByCode(@Param("code") String code);

    @Query("SELECT m FROM #{#entityName} m WHERE m.accessKey=:accessKey")
    TChannel findByAccessKey(@Param("accessKey") String accessKey);

    Long count(Specification<TChannel> specification);

    Page<TChannel> findAll(Specification<TChannel> specification, Pageable pageable);

    List<TChannel> findAll(Specification<TChannel> specification);}
