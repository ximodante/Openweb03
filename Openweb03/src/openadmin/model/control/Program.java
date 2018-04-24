package openadmin.model.control;



/**
 * <desc>class that stores the basic information of program</desc>
 * <responsibility>Represents the programs</responsibility>
 * <cooperation>Interface Base - Class Role</cooperation>
 * @version  0.1
 * Created 10-05-2008
 * Modifier 06-11-2017 
 * Author Alfred Oliver
*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import openadmin.annotations.Default;
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
@Table(name = "programa", schema = "control")
public class Program extends Audit implements Base, java.io.Serializable {

	private static final long serialVersionUID = 01011001L;
	
	/** attribute that contains the identifier*/
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Default(visible=true)
	private Short id;
	
	/** attribute that contains the program name (description), unique value*/
	@Getter
	@Size(min =3, max = 25)
	@NotNull
	@Column(name = "programa", unique=true)
	private String description;
	
	@Getter @Setter
	@Size(max = 50)
	@Column(name = "icona")
	private String icon;
	
	/**
	 * Constructor of the class Program.
	 * @param pDescription, is the description, (unique value), of the Program
	 */
	public Program(String pDescription) {
		
		setDescription(pDescription);
		
	}

	/** Getters and setters*/
	public void setId(Number id) {
		
		this.id = id.shortValue();
	}
	
	public void setDescription(String pDescription) {

		this.description = pDescription.toLowerCase();
	
	}

}
