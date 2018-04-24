package openadmin.model.log;

/**
*	Classe que emmagatzema les dades b�siques de les accions
*	@version  0.2
*	Creada  18-04-2008
*   Modifier 06-11-2017 
*/


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import openadmin.model.Audit;
import openadmin.model.Base;

@Entity
@Table(name = "log", schema = "log")

public class Log extends Audit implements Base, Serializable {	
	
	private static final long serialVersionUID = 06111701L;
	
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Getter @Setter
	@Size(max = 50)
	@Column(name = "programa")
	private String program;		

	@Getter 
	@Size(max = 50)
	@Column(name = "usuari")
	private String person;
	
	@Getter @Setter
	@Size(max = 40)
	@Column(name = "classe")
	private String object;
	
	@Getter @Setter
	@Size(max = 20)
	@Column(name = "idObjecte")
	private String idobject;
	
	@Getter @Setter
	@Size(max = 50)
	@Column(name = "accio")
	private String action;
	
	@Getter @Setter
	@Size(max = 500)
	@Column(name = "operacio")
	private String description;
	
	@Getter @Setter
	private LocalDateTime data;
	
	/** attribute that contains the relationship with Province*/
	@Getter @Setter
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "log")
	private Set<Detail> detail = new HashSet<Detail>(0);
	
	/** Transient attribute that means that the system should make a log on any JPA operation of this class*/
	@Transient
	@Getter
	private boolean debugLog = true;

	@Transient
	@Getter
	private boolean detailLog = false;
	
	public Log() {
		
	}
	
	public void setId(Number id) {
		this.id = id.intValue();
	}
	
	public void setPerson(String pPerson) {
		this.person=StringUtils.truncate(pPerson,50);
	}
	/*
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String pDescription) {
		this.description = pDescription;
	}
	
    */
	@Override
	public String toString(){
		 
		 return description;
	}
	
}
