package openadmin.util.configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;

import openadmin.dao.operation.DaoJpa;
import openadmin.dao.operation.DaoOperationFacade;
import openadmin.dao.operation.LogDao;
import openadmin.dao.operation.LogOperationFacade;

import openadmin.model.control.Access;
import openadmin.model.control.Action;
import openadmin.model.control.ActionViewRole;
import openadmin.model.control.EntityAdm;
import openadmin.model.control.MenuItem;
import openadmin.model.control.ClassName;
import openadmin.model.control.Program;
import openadmin.model.control.Role;
import openadmin.model.control.User;
import openadmin.util.configuration.TypeLanguages;
import openadmin.util.lang.LangType;

/**
 * To make the first load of the class, and maybe it can be useful
 * for new updates
 * @author edu
 *
 */
public class FirstControlLoad {
	
	/** Name of the files to read */
	private static final String path="./resources/data/";
	
	private static final String programDataFile="ProgramData.txt";
	private static final String rolDataFile="RoleData.txt";
	private static final String userDataFile="UserData.txt";
	private static final String entityAdmDataFile="EntityAdmData.txt";
	private static final String classNameDataFile="ClassNameData.txt";
	private static final String actionDataFile="MenuItemRoleActionData.txt";
	private static final String actionRoleDataFile=actionDataFile;
	private static final String viewDataFile="ViewRoleData.txt";
	private static final String viewRoleDataFile=viewDataFile;
	private static final String AccessDataFile="AccessData.txt";
	
	private static User firstLoadUser = new User("FirstLoader","123456","First Load User");
	private static DaoOperationFacade connectionLog = null;	
	private static LogOperationFacade log =null; 
	private static DaoOperationFacade connection = null; 	
	
	/**
	 * Read delimited data from a delimited file  
	 * @param pFileName the name of the file
	 * @param pDelimiter  The delimiter of the data
	 * @return 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static List<String[]> ReadData(String pFileName, String pDelimiter) throws FileNotFoundException, IOException{
		
		InputStream in = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(pFileName);
		
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(in));
		
		Pattern p = Pattern.compile(pDelimiter);
		List<String[]> lst=new ArrayList<String[]>();
		while(true) {
			String strLine= inputStream.readLine();	  		// Input a line from the file
			if (strLine == null) break;  			 		// We have reached eof
			System.out.println(strLine);
			strLine=strLine.trim();
			if (strLine.length()>1) if (strLine.charAt(0)!='#')lst.add(p.split(strLine)); // Split the line into strings delimited by delimiters
		}
		
		return lst;
	} 
	
	/**
	 * Gets the Program records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 */
	private static void programDef() throws FileNotFoundException, IOException {
		
		int i=1;
		
		for (String[] s: ReadData(path + programDataFile,"\\|")) {
			
			Program program=new Program(s[0].trim());
			program.setIcon(s[1].trim());
			//System.out.println(s[0]+"-->PROGRAM:"+program.getDescription()+" "+program.getDebugLog());
			
			if (connection.findObjectDescription(program) == null){
				connection.persistObject(program);
				System.out.println(i+".- Programa: " + s[0]+ " donat d'alta");	
			}
			i++;
		}
	}
	
	/**
	 * Gets the Role records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 */
	private static void RolDef() throws FileNotFoundException, IOException {
		int i=1;
		
		for (String[] s: ReadData(path + rolDataFile,"\\|")) {
			
			Role role=new Role();
			role.setDescription(s[0].trim());
			
			System.out.println(s[0]+"-->ROLE:"+role.getDescription());
			
			if (connection.findObjectDescription(role) == null){
				connection.persistObject(role);
				System.out.println(i+".- Role: " + s[0]+ " donat d'alta");	
			}
			i++;
		}
	}
	
	/**
	 * Gets the User records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 */
	private static void UserDef() throws FileNotFoundException, IOException {
		int i=1;
		LocalDate data = LocalDate.now();
		
		for (String[] s: ReadData(path + userDataFile,"\\|")) {
			
			User user=new User(s[0].trim(), s[1].trim(),s[2].trim());
			user.setDateBegin(data);   user.setLanguage(s[3].trim());  user.setIdentifier(s[4].trim());
			user.setActive(true)   ;  
			
			System.out.println(s[0]+"-->USER:"+user.getDescription()+"--"+user.getPassword()+ "--" + user.getFullName() + "--" + user.getIdentifier() + "--" + user.getLanguage());
			
			if (connection.findObjectDescription(user) == null){
				connection.persistObject(user);
				System.out.println(i+".- Usuari: " + s[0]+ " donat d'alta");	
			}
			i++;
		}
	}
	
	/**
	 * Gets the EntityAdm records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 */
	private static void EntityAdmDef() throws FileNotFoundException, IOException {
		int i=1;
		
		for (String[] s: ReadData(path + entityAdmDataFile,"\\|")) {
			
			EntityAdm entity=new EntityAdm(s[0].trim());
			entity.setIcon(s[1].trim());
			entity.setConn(s[2].trim());
			
			System.out.println(s[0]+"-->ENTITY ADM:"+entity.getDescription()+"--"+entity.getConn());
			
			if (connection.findObjectDescription(entity) == null){
				connection.persistObject(entity);
				System.out.println(i+".- Entitat: " + s[0]+ " donat d'alta");	
			}
			i++;
		}
	}
	
	/**
	 * Gets the Actionclass records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 */
	private static void ClassNameDef() throws FileNotFoundException, IOException {
		int i=1;
		
		for (String[] s: ReadData(path + classNameDataFile,"\\|")) {
			
			ClassName aClass=new ClassName(s[0].trim());
			 
			
			System.out.println(s[0]+"-->CLASS NAME: "+aClass.getDescription());
			
			if (connection.findObjectDescription(aClass) == null){
				connection.persistObject(aClass);
				System.out.println(i+".- Classe amb accions: " + s[0]+ " donat d'alta");	
			}
			i++;
		}
	}

	/**
	 * Gets the Action records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 */
	private static void ActionDef() throws FileNotFoundException, IOException {
		int i=1;
		
		for (String[] s: ReadData(path + actionDataFile,"\\|")) {
			// Description is made of class.MainAction by example:User.New 
			Action action=new Action(); 
			action.setClassName((ClassName) connection.findObjectDescription(new ClassName(s[1].trim())));
			action.setDescription(s[1].trim() + "_" + s[0].trim());
			action.setIcon(s[2].trim());
			action.setGrup(Integer.parseInt(s[3].trim()));
			System.out.println(s[1].trim()+"."+s[0].trim());
			System.out.println(s[0]+"-->ACTION:"+action.getDescription()+" ->"+action.getClassName().getDescription());
			
			if (connection.findObjectDescription(action) == null){
				connection.persistObject(action);
				System.out.println(i+".- Action: " + s[0]+ " donat d'alta");	
			}
			i++;
		}
	}
	
	/**
	 * Gets the View records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 */
	private static void menuItemDef() throws FileNotFoundException, IOException {
		int i=1;
		
		for (String[] s: ReadData(path + viewDataFile,"\\|")) {
			
			MenuItem menuItem=new MenuItem();
			menuItem.setClassName((ClassName) connection.findObjectDescription(new ClassName(s[1].trim())));
			menuItem.setDescription(s[1].trim() + "_" + s[0].trim());
			menuItem.setIcon(s[2].trim());
			menuItem.setTypeNode((s[3].trim()));
			menuItem.setViewType(s[4].trim());
			menuItem.setOrden(Integer.parseInt(s[5].trim()));
			
			System.out.println(menuItem.getDescription());
			System.out.println(menuItem.getClassName().getDescription());
			System.out.println(s[0]+"-->Menuitem:"+menuItem.getDescription()+" ->"+menuItem.getClassName().getDescription());
			
			if (connection.findObjectDescription(menuItem) == null){
				connection.persistObject(menuItem);
				System.out.println(i+".- Menuitem: " + s[0]+ " donat d'alta");	
			}
			i++;
		}
	}
	
	/**
	 * Gets the ActionRole records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 
	private static void menuItemRoleDef() throws FileNotFoundException, IOException {
		int i=1;
		
		for (String[] s: ReadData(path + viewRoleDataFile,"\\|")) {
			// To simplify we have included in the table all available roles. So we should see all roles 
			for (int j=2; j<10; j++){
				int iRole=2*(j+1)-1;
				int iProgram=2*(j+1);
				
				if (s[iRole].trim().length()>1 && s[iProgram].trim().length()>1) {
					// Description is made of role.class.action by example:ADMIN.User.New
					 
					MenuItem menuItem = new MenuItem();
					menuItem.setClassName((ClassName)connection.findObjectDescription(new ClassName(s[1].trim())));
					menuItem.setDescription(s[1].trim() + "_" + s[0].trim());
					
					Role role = new Role();
					//role.setProgram((Program)connection.findObjectDescription(new Program(s[iProgram].trim())));
					role.setDescription(s[iRole].trim());
					i++;
				}
			}
		}
	}
	*/
	/**
	 * Gets the Access records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 */
	private static void AccessDef() throws FileNotFoundException, IOException {
		int i=1;
		
		for (String[] s: ReadData(path + AccessDataFile,"\\|")) {
			// To simplify we have included in the table all available roles. So we should see all roles 
			for (int j=0; j<10; j++){
				int iRole=2*(j+1);
				int iProgram=2*(j+1)+1;
				if (s[iRole].trim().length()>1 && s[iProgram].trim().length()>1) {
					// Description is made of id EntityAdm + "_" + id user + "_" id role
					Access access=new Access(); 
					access.setUser((User)        connection.findObjectDescription(new User     (s[1].trim()," "," ")));						
					access.setRole((Role)        connection.findObjectDescription(new Role (s[iRole].trim())));
					access.setProgram((Program)  connection.findObjectDescription(new Program (s[iProgram].trim())));
					access.setEntityAdm((EntityAdm) connection.findObjectDescription(new EntityAdm(s[0].trim())));	
					access.setDescription("");
					
					System.out.println(s[0]+"-->ACCESS:"+access.getDescription());
							
					if (connection.findObjectDescription(access) == null){
						connection.persistObject(access);
						System.out.println(i+".- Access: " + access.getDescription()+ " donat d'alta");	
					}
					i++;
				}
			}
		}
	}
	
	/**
	 * Gets the ActionRole records from a file and persist them in the database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DataException
	 */
	private static void menuItemRoleActionDef() throws FileNotFoundException, IOException{
		int i=1;
		
		for (String[] s: ReadData(path + actionRoleDataFile,"\\|")) {
			// To simplify we have included in the table all available roles. So we should see all roles 
			for (int j=1; j<10; j++){
				int iRole=2*(j+1)+1;
				int iProgram=2*(j+1)+2;
				if (s[iRole].trim().length()>1 && s[iProgram].trim().length()>1) {
					// Description is made of role.class.action by example:ADMIN.User.New
					ActionViewRole actionViewRole = new ActionViewRole();
					MenuItem menuItem = new MenuItem();
					menuItem.setDescription(s[1].trim()+"_"+s[4].trim());
					
					Action action = new Action();
					action.setDescription(s[1].trim()+"_"+s[0].trim());
					
					Role role = new Role();
					//role.setProgram((Program) connection.findObjectDescription(new Program(s[iProgram].trim())));
					role.setDescription(s[iRole].trim());
					
					actionViewRole.setMenuItem((MenuItem) connection.findObjectDescription(menuItem));
					actionViewRole.setAction((Action) connection.findObjectDescription(action));
					actionViewRole.setRole((Role) connection.findObjectDescription(role));
					actionViewRole.setDescription("");
							
					System.out.println(s[0]+"-->ACTION ROLE:"+actionViewRole.getDescription());
							
					if (connection.findObjectDescription(actionViewRole) == null){
						connection.persistObject(actionViewRole);
						System.out.println(i+".- Action View Role: " + s[0]+ " donat d'alta");	
					}
					i++;
				}
			}
		}
	}
	
	
	public static void dataLoad() throws NoSuchAlgorithmException, IOException {
		
		LangType langType = new LangType();
		
		langType.changeMessageLog(TypeLanguages.es);
		
		//1.0- Open connections
		connectionLog = new DaoJpa(firstLoadUser, "log_post", null, langType);	
		log = new LogDao(connectionLog, "control", langType);
		connection = new DaoJpa(firstLoadUser, "control_post", log, langType);
		
		connection.begin();		//2.1.-	Begin transaction  
		programDef();		  	//2.2- 	Load and persist program definitions
		connection.commit(); 	//2.3-	Commit connection
		if (connection.isResultOperation()) System.out.println("1.-NEW PROGRAMS SAVED -----------------------");
		
		connection.begin();		//3.1.-	Begin transaction  
		RolDef();			  	//3.- 	Load and persist role    definitions
		connection.commit(); 	//3.3-	Commit connection
		if (connection.isResultOperation()) System.out.println("2.-NEW ROLES SAVED -----------------------");
		
		connection.begin();		//4.1.-	Begin transaction  
		UserDef();				//4.2-  Load and persist user    definitions
		connection.commit(); 	//4.3-	Commit connection
		if (connection.isResultOperation()) System.out.println("3.-NEW USERS SAVED -----------------------");
		
		
		connection.begin();		//5.1.-	Begin transaction  
		EntityAdmDef();			//5.2-  Load and persist entity  definitions
		connection.commit(); 	//5.3-	Commit connection
		if (connection.isResultOperation()) System.out.println("4.-NEW ENTITIES SAVED -----------------------");
		
		
		connection.begin();		//6.1.-	Begin transaction  
		ClassNameDef();		//6.2-  Load and persist action classes definitions
		connection.commit(); 	//6.3-	Commit connection
		if (connection.isResultOperation()) System.out.println("5.-NEW ACTION CLASSES SAVED -----------------------");
		
		connection.begin();		//7.1.-	Begin transaction  
		ActionDef();			//7.2-  Load and persist action  definitions
		connection.commit(); 	//7.3-	Commit connection
		if (connection.isResultOperation()) System.out.println("6.-NEW ACTIONS SAVED -----------------------");
			
		connection.begin();		//9.1.-	Begin transaction  
		menuItemDef();				//9.2-  Load and persist view    definitions
		connection.commit(); 	//9.3-	Commit connection
		if (connection.isResultOperation()) System.out.println("8.-NEW VIEWS SAVED -----------------------");
		
		/**
		connection.begin();		//10.1.- Begin transaction  
		menuItemRoleDef();			//10.2-  Load and persist view Role   definitions
		connection.commit(); 	//10.3-	 Commit connection
		if (connection.isResultOperation()) System.out.println("9.-NEW VIEW ROLES SAVED -----------------------");
		*/
		
		connection.begin();		//11.1.- Begin transaction  
		AccessDef();			//11.2-  Load and persist access      definitions
		connection.commit(); 	//11.3-	 Commit connection
		if (connection.isResultOperation()) System.out.println("10.-NEW ACCESS SAVED -----------------------");
		
		
		connection.begin();		//8.1.-	Begin transaction  
		menuItemRoleActionDef();//8.2-  Load and persist action Role  definitions
		connection.commit(); 	//8.3-	Commit connection
		if (connection.isResultOperation()) System.out.println("7.-NEW ACTIONS AND VIEW SAVED -----------------------");
		
		
		// Close log connection
		log.finalizeLog();		//20.2.- close connections
		connection.finalize();

	}

}

