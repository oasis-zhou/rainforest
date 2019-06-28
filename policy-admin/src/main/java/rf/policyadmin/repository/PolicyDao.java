package rf.policyadmin.repository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.policyadmin.repository.pojo.TPolicy;

import javax.persistence.LockModeType;

public interface PolicyDao extends CrudRepository<TPolicy, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.policyNumber=:policyNumber")
    TPolicy findByPolicyNumber(@Param("policyNumber") String policyNumber);

    @Query("SELECT m FROM #{#entityName} m WHERE m.proposalNumber=:proposalNumber")
    TPolicy findByProposalNumber(@Param("proposalNumber") String proposalNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM #{#entityName} m WHERE m.policyNumber=:policyNumber")
    TPolicy findByPolicyNumberOnLock(@Param("policyNumber") String policyNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM #{#entityName} m WHERE m.proposalNumber=:proposalNumber")
    TPolicy findByProposalNumberOnLock(@Param("proposalNumber") String proposalNumber);
}
