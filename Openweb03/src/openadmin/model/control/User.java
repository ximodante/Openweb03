package openadmin.model.control;

/**
 * <desc>class that stores the basic information of user</desc>
 * <responsibility>Represents the users</responsibility>
 * <coperation>Interface Base - Class Role - Class Access</coperation>
 * @version  0.2
 * Created 10-05-2008
 * Modifier 06-11-2017 
 * Author Alfred Oliver
*/

import java.time.LocalDate;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import openadmin.model.Audit;
import openadmin.model.Base;
import openadmin.util.cripto.Encriptacion;


@Named
@RequestScoped
@Entity
@ToString @NoArgsConstructor
@Table(name = "usuari", schema = "control")
public class User extends Audit implements Base, java.io.Serializable {

	private static final long serialVersionUID = 01011001L;
	
	/** attribute that contains the user identifier*/
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/** attribute that contains the user name*/	
	@NotNull
	@Size(max = 15)
	@Column(name = "usuari", unique=true, length=15)
	@Getter @Setter
	private String description;
	
	/** attribute that contains the password*/	
	/**
	 * 
	 */
	@NotNull
	@Column(name = "clau", length=50)
	@Getter
	private String password;
	
	/** attribute that contains the full name user*/	
	/**
	 * 
	 */
	@Size(min = 4, max = 40)
	@NotNull
	@Column(name="nom")
	@Getter
	private String fullName;
	
	/** attribute that contains the identification card user*/	
	/**
	 * 
	 */
	@Size(min = 8, max = 15)
	@NotNull
	@Column(name="identificador", unique=true)
	@Getter
	private String identifier;

	/** attribute that contains the date begin*/	
	/**
	 * 
	 */	
	@NotNull
	@Getter @Setter
	@Column(name="dataAlta")
	private LocalDate dateBegin;
	
	/** attribute that contains the date low */	
	/**
	 * 
	 */
	@Getter @Setter
	@Column(name="dataBaixa")
	private LocalDate dateEnd;
	
	/** attribute that contains the language for application
	 * example ca; es; .....*/	
	/**
	 * 
	 */
	@Getter @Setter
	@Size(min = 2, max = 2)
	@NotNull
	@Column(name="idioma")
	private String language;		

	/** attribute that contains if user is active*/	
	/**
	 * 
	 */
	@Getter @Setter
	private boolean active = true;
		
	/**
	 * 
	 */
	@Getter @Setter
	private boolean firma = false;
	
	public User(String pUser,String pPassword, String pFullname){
		
		setDescription(pUser);
		setPassword(pPassword);
		setFullName(pFullname);
		
	}
	
	/** Getters and setters*/	
	public void setId(Number pId) {
		this.id = pId.intValue();
	}

	/**
	 * <desc> Accessor of writing which gives us the encrypted password</desc>
	 * <pre> x is an instance of user</pre>
	 * <post> the encrypted password is stored</post>
	 * @param pPassword, is the password*/
	 	
	public void setPassword(String pPassword){
		
		
		if (pPassword != ""){
				
			try {
				
				this.password = Encriptacion.md5(pPassword);
			
			} catch (Exception e) {
				
				e.printStackTrace();
			
			}
		
		}
		
	}

	public void setFullName(String pFullName) {

		this.fullName = pFullName.toUpperCase();
	
	}

	public void setIdentifier(String pIdentifier) {
	
		this.identifier = pIdentifier.toUpperCase();
	
	}
	
}
