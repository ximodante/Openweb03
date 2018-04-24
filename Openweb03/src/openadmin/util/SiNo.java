package openadmin.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import openadmin.model.Audit;
import openadmin.model.Base;

@Entity
@Table(name = "sino", schema = "util")
public class SiNo extends Audit implements Base, java.io.Serializable {
	
	private static final long serialVersionUID = 29061201L;
	
	@Getter
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Short id;	
	
	/** attribute that contains the description, unique value*/
	@Size(max = 2)
	@NotNull ()
	@Column(unique = true)
	@Getter @Setter
	private String description;
	
	/** Transient attribute that means that the system should make a log on any JPA operation of this class*/
	@Transient
	@Getter
	private boolean debugLog = true;

	@Transient
	@Getter
	private boolean detailLog = false;
	
	public SiNo(){
		
	}
	
	/** Getters and setters*/	
	
	public void setId(Number id) {
		
		this.id = id.shortValue();
		
	}

}
