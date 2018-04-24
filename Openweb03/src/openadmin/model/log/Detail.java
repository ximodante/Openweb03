package openadmin.model.log;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import openadmin.model.Audit;
import openadmin.model.Base;

@Entity
@Table(name = "detalleLog", schema = "log")
public class Detail extends Audit implements Base, Serializable {

	private static final long serialVersionUID = 06111702L;
	
	/** attribute that contains the identifier*/
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Getter
	@Size(max = 200)
	@Column(name = "descripcio")
	private String description;
	
	@Getter @Setter
	@Size(max = 50)
	@Column(name = "atribut")
	private String attribute;
	
	@Getter @Setter
	@Size(max = 250)
	@Column(name = "valor")
	private String value;
	
	/** attribute that contains the relationship with Log*/
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "log", nullable= false)
	private Log log;		
	
	/** Transient attribute that means that the system should make a log on any JPA operation of this class*/
	@Transient
	@Getter
	private boolean debugLog = true;

	@Transient
	@Getter
	private boolean detailLog = false;
	
	public Detail() {
		
	}
	
	public void setId(Number id) {
		this.id = id.intValue();
	}
	
	public void setDescription(String pDescription) {
		
		this.description = attribute + " :" + value;
	
	}
	
	@Override
	public String toString(){
		 
		 return description;
	}

}
