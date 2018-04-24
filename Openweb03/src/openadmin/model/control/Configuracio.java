package openadmin.model.control;

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
import openadmin.model.Audit;
import openadmin.model.Base;

@Entity
@Table(name = "configuracion", schema = "control")
public class Configuracio extends Audit implements Base, java.io.Serializable {

	
	private static final long serialVersionUID = 28101701L;
	
	/** attribute that contains the user identifier*/
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/** clau*/
	@Getter
	@NotNull
	@Size(min = 3, max = 40)
	@Column(unique=true)
	private String description;
	
	/** valor*/	
	@Getter
	@NotNull
	@Size(max = 40)
	@Column()
	private String valor;
	
	/**
	 * Constructor.
	 * without parameters
	 */
	public Configuracio() {
		
	}
	
	/** Getters and setters*/
	
	public void setId(Number pId) {
		this.id = pId.intValue();
	}

	public void setDescription(String pDescription) {
		
		this.description = pDescription.toUpperCase();
	}
	
	public void setValor(String valor) {
		this.valor = valor.toUpperCase();
	}

	
}
