package rf.foundation.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
	private Date creationTime;
	private String modifiedBy;
	private Date modificationTime;


	@PrePersist
	protected void prePersist() {
		this.creationTime = new Date();
		this.modificationTime = new Date();
	}

	@PreUpdate
	protected void preUpdate() {
		this.modificationTime = new Date();
	}
}
