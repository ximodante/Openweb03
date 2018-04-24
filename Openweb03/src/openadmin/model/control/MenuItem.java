package openadmin.model.control;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import openadmin.annotations.NoSql;
import openadmin.model.Audit;
import openadmin.model.Base;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <desc>class that stores information of attributes to be displayed in the view part of a class. </desc>
 * <responsibility>Represents all the views for users</responsibility>
 * <coperation>Interface Base - Class ViewRole - Class ActionClass</coperation>
 * @version  0.1
 * Created 06-05-2009
 * Modifier 18-09-2009 
 * Author Eduard
 * Modified Alfred 28-11-2009
*/
@ToString @NoArgsConstructor
@Entity
@Table(name = "menuItem", schema = "control")
public class MenuItem extends Audit implements Base, Comparable<Base>, java.io.Serializable {

	private static final long serialVersionUID = 28110901L;

	/** attribute that contains the identifier*/
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Short id;
	
	/** attribute that contains the description, unique value*/
	@Getter
	@Size(max = 100)
	@NotNull
	@Column(name="item", unique=true)
	private String description;
	
	/** attribute that contains the parent (recursion)*/
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name="padre")
	@NoSql
	private MenuItem parent;
	
	@Getter @Setter
	@NotNull
	@Size(min = 1, max = 1)
	@Column(name="tipoNodo")
	private String typeNode = "c";
	
	@Getter @Setter
	@Size(max = 50)
	@Column(name = "tipoVista")
	private String viewType;
	
	@Getter @Setter
	@Size(max = 50)
	@Column(name="icono")
	private String icon;
	
	/** attribute that contains the relationship with actionClass*/
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "nomClasse", nullable= true)
	private ClassName className;
	
	@Getter @Setter
	@Column(name="ordreItem")
	private Integer orden;

	public void setId(Number pId) {
		this.id = pId.shortValue();
	}

	public void setDescription(String pDescription) {
				
		this.description = pDescription.toLowerCase().replace(".", "_");
	}
	
	@Override
	public int compareTo(Base o) {
	    
		return orden.compareTo(((MenuItem) o).getOrden());
	}
	
}
