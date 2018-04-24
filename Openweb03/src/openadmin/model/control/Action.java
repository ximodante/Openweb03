package openadmin.model.control;



/**
 * <desc>class that stores the basic information of Action</desc>
 * <responsibility>Represents all the actions for users</responsibility>
 * <coperation>Interface Base - Class ActionClass</coperation>
 * @version  0.1
 * Created 10-05-2008
 * Modifier 18-08-2009 
 * Author Alfred Oliver
*/
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import openadmin.model.Audit;
import openadmin.model.Base;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@ToString @NoArgsConstructor
@Table(name = "accions", schema = "control")
public class Action extends Audit implements Base, java.io.Serializable {

	private static final long serialVersionUID = 01011001L;
	
	/** attribute that contains the identifier*/
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Short id;
	
	/** attribute that contains the description, unique value*/
	@Getter
	@Size(max = 100)
	@NotNull
	@Column(name="accio", unique=true)
	private String description;
	
	/** attribute that contains the relationship with actionClass*/
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "nomClasse", nullable= false)
	private ClassName className;
	
	@Getter @Setter
	@Size(max = 50)
	@Column(name="icono")
	private String icon;
	
	@Getter @Setter
	private Integer grup;
	
	/** Getters and setters*/

	public void setId(Number pId) {
		this.id = pId.shortValue();
	}

	
	public void setDescription(String pDescription) {
		
		this.description = pDescription.toLowerCase();
	}

}
