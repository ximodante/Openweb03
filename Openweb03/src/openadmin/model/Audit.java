package openadmin.model;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class Audit {

	@Getter @Setter
	@Size(max = 15)
	private String lastUser;
	
	@Getter @Setter
	private LocalDateTime auditData;
	
	public void setChanges(String pUser) {
		
		this.setLastUser(pUser);
		this.setAuditData(LocalDateTime.now());
	}
	
}
