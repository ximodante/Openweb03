package openadmin.model.control;

import javax.persistence.Column;

/**
 * <desc>class that stores the basic information of entity</desc>
 * <responsibility>Represents entity</responsibility>
 * <coperation>Interface Base - Class Access</coperation>
 * @version  0.1
 * Created 10-05-2008
 * Modifier 06-11-2017 
 * Author Alfred Oliver
*/

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import openadmin.annotations.Default;
import openadmin.model.Audit;
import openadmin.model.Base;

@Entity
@ToString @NoArgsConstructor
@Table(name = "entitatAdm", schema = "control")
public class EntityAdm extends Audit implements Base, java.io.Serializable {

	private static final long serialVersionUID = 01011001L;
	
	/** attribute that contains the identifier*/
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Default(visible=true)
	private Short id;
	
	/** attribute that contains the description*/
	@Getter @Setter
	@NotNull
	@Size(min = 4, max = 10)
	@Column(name = "nombre", unique=true)
	private String description;
	
	@Getter @Setter
	@NotNull
	@Size(min = 4, max = 20)
	@Column(name = "conexio", unique=true)
	private String conn;
	
	@Getter @Setter
	@Size(max = 50)
	@Column(name = "icona")
	private String icon;
	
	@Getter @Setter
	@Size(max = 50)
	@Column(name = "temaCSS")
	private String theme;
	
	@Getter @Setter
	@Size(max = 25)
	@Column(name = "conexioHistoric")
	private String connhistoric;
		
	/**
	 * Constructor of the class Entity.
	 * @param pDescription, is the description, (unique value), of the Entity
	 */
	public EntityAdm(String pDescription){
		
		setDescription(pDescription.toLowerCase());
	}
	
	/** Getters and setters*/
	public void setId(Number pId) {
		this.id = pId.shortValue();
	}
		
}
