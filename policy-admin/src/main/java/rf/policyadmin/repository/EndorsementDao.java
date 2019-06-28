package rf.policyadmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import rf.policyadmin.repository.pojo.TEndorsement;

import java.util.List;

public interface EndorsementDao extends PagingAndSortingRepository<TEndorsement, String> {

	@Query("SELECT m FROM #{#entityName} m WHERE m.policyNumber =:policyNumber order by m.sequence desc")
	public TEndorsement findLastEndorsement(@Param("policyNumber") String policyNumber);
	
	@Query("SELECT MAX(m.sequence) FROM #{#entityName} m WHERE m.policyNumber =:policyNumber")
	public Integer findMaxSequence(@Param("policyNumber") String policyNumber);
	
	@Query("SELECT m FROM #{#entityName} m WHERE m.policyNumber =:policyNumber and (m.statusCode = 'QUOTATION' or m.statusCode = 'UNDERWRITING')")
	public TEndorsement findPendingEndorsement(@Param("policyNumber") String policyNumber);

	@Query("SELECT m FROM #{#entityName} m WHERE m.policyNumber =:policyNumber and m.statusCode = 'ISSUE'")
	public List<TEndorsement> findEndorsement(@Param("policyNumber") String policyNumber);

	@Query("SELECT m FROM #{#entityName} m WHERE m.endorsementNumber =:endorsementNumber")
	public TEndorsement findByNumber(@Param("endorsementNumber") String endorsementNumber);

	@Query("SELECT m FROM #{#entityName} m WHERE m.policyNumber =:policyNumber")
	public List<TEndorsement> findAllEndorsements(@Param("policyNumber") String policyNumber);

	Page<TEndorsement> findAll(Specification<TEndorsement> specification, Pageable pageable);
}
