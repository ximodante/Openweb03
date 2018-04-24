package openadmin.dao.operation;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import openadmin.model.Base;
import openadmin.dao.exception.*;
import openadmin.dao.exception.Error;
import openadmin.model.control.User;
import openadmin.util.configuration.TypeEnvironment;
import openadmin.util.lang.LangType;
import openadmin.util.reflection.ReflectionField;

/**
 * <desc>DaoJpaHibernate implement the interface DaoOperationFacade</desc>
 * <responsibility>Implement the operations of database for framework JPA - Hibernate</responsibility>
 * <coperation>All classes that need to work with database</coperation>
 * @version  0.1
 * Create  18-03-2009
 * Author Alfred Oliver
*/
@SuppressWarnings("unchecked")
public class DaoJpa implements DaoOperationFacade, Serializable{
	
	private static final long serialVersionUID = 20171129;

	/**Field that contain one list of Base objects */
	//private List <Base> listObjects = null;
	private List <? extends Base> listObjects = null;
		
	/**Field used by the application to obtain an application-managed entity manager.*/
	private EntityManagerFactory factory = null;
	
	/**Field that contain the interface used to interact with the persistence context.*/
	private EntityManager em;
	
	/** Field that contain the result to operation*/
	private boolean resultOperation;
	
	/** Field that contain the registered User*/
	@Getter @Setter
	private User user;
	
	/** Field that contain the log to record the operations performed*/
	private LogOperationFacade log;
	
	private List<String> listLog = new ArrayList<String>();
	
	/**Field that contain the working environment*/
	@Getter @Setter
	private TypeEnvironment environment = TypeEnvironment.SWING;	
	
	private LangType langType;
	
	/**
	  * Constructor of class DaoJpaHibernate.
	  * @throws DataException. 
	  * @param PUser. Registered User.  	 
	  * @param 	pDataBase. Attribute name of node persistence-unit (file persistence.xml)
	  * @param pLog. To record the operations performed. 
	  */
	public DaoJpa(User pUser, String pDataBase, LogOperationFacade pLog, LangType pLangType){
		
		langType = pLangType;
		
		this.setUser(pUser);
						
		if (pLog != null){
			
			log = pLog;
			log(null, langType.msgLog("CONNECTION") + " " +pDataBase, null);
			
		}				
		
		connection(pDataBase);
		
	}
	
	
	/**
	  * Procedure that connect to database
	  * {Pre: There isn't an open connection to the database}.
	  * {Post: has connect to database}.
	  * @throws DataException if there isn't a connection to the database.
	  * @param 	pUnitName. Attribute name of node persistence-unit (file persistence.xml)  
	  */
	private void connection(String pUnitName){		
						
		try {
			
			factory = Persistence.createEntityManagerFactory(pUnitName);
			em = factory.createEntityManager();
			
		}catch(Exception ex) {
			//ex.printStackTrace();	errorTp1(ex, ErrorDao.CONNETION);
			errorTask1(ex, ErrorDao.CONNETION);
			
		}
	}
	
	/**
	 * Persist object. 
	 * {Pre: The object no exist}.
	 * {Post: The object is persist is not exist. 
	 */
	//public void persistObject(Base obj) {
	public <T extends Base> void persistObject(T obj) {
		
		resultOperation = false;		
		log.activateLog(obj.isDebugLog());
		log.activateDetailLog(false);
		
		obj.setChanges(this.user.getDescription());
		
		try{
			if (em.isOpen()){				
				em.persist(obj);
				resultOperation = true;
				log(obj, langType.msgLog("PERSIST"), null);				
			}
		
		} catch(Exception ex) {
			/*
			resultOperation = false;		
			ex.printStackTrace();
			log(obj, langType.msgLog("ERROR_PERSIST"), langType.msgLog("ERROR_CLASS") + ex.getLocalizedMessage());				
			rollback();
			errorTp7(ex, ErrorDao.PERSIST, "ERROR_PERSIST", obj);
			*/
			log(obj, langType.msgLog("ERROR_PERSIST"), langType.msgLog("ERROR_CLASS") + ex.getLocalizedMessage());				
			errorTask7(ex, ErrorDao.PERSIST, "ERROR_PERSIST", "ERROR_CLASS", obj);
			
		}				
		
	}
	public void persistObject2(Base obj) {
		
		resultOperation = false;		
		log.activateLog(obj.isDebugLog());
		log.activateDetailLog(false);
		System.out.println("em.isOpen="+ em.isOpen());
		try{
			if (em.isOpen()){				
				em.persist(obj);
				resultOperation = true;
				log(obj, langType.msgLog("PERSIST"), null);				
			}
		
		} catch(Exception ex) {
			/*
			resultOperation = false;		
			ex.printStackTrace();
			log(obj, langType.msgLog("ERROR_PERSIST"), langType.msgLog("ERROR_CLASS") + ex.getLocalizedMessage());				
			rollback();
			errorTp7(ex, ErrorDao.PERSIST, "ERROR_PERSIST", obj);
			*/
			log(obj, langType.msgLog("ERROR_PERSIST"), langType.msgLog("ERROR_CLASS") + ex.getLocalizedMessage());				
			errorTask7(ex, ErrorDao.PERSIST, "ERROR_PERSIST", "ERROR_CLASS", obj);
		}	
		
	}	
	
	
	
	/**
	 * Persist object. 
	 * {Pre: The object no exist}.
	 * {Post: The object is persist is not exist. 
	 */
	//public void persistObjectDefault(Base obj) {
	public <T extends Base> void persistObjectDefault(T obj) {
		
		resultOperation = false;		
		log.activateLog(obj.isDebugLog());
		log.activateDetailLog(false);
		
		try{
			if (em.isOpen()){				
				em.persist(obj);
				resultOperation = true;								
			}
		
		} catch(Exception ex) {
			/*
			resultOperation = false;
			ex.printStackTrace();
			log(obj, langType.msgLog("ERROR_PERSIST"), langType.msgLog("ERROR_CLASS") + ex.getLocalizedMessage());				
			rollback();
			errorTp7(ex, ErrorDao.PERSIST, "ERROR_PERSIST", obj);
			*/
			log(obj, langType.msgLog("ERROR_PERSIST"), langType.msgLog("ERROR_CLASS") + ex.getLocalizedMessage());				
			errorTask7(ex, ErrorDao.PERSIST, "ERROR_PERSIST", "ERROR_CLASS", obj);
		}				
		
	}
	
	/**
	 * Update object. 
	 * {Pre: The object exist}.
	 * {Post: The object is update if exist. 
	 */
	//public void updateObject(Base objOriginal, Base obj){
	public <T extends Base> void updateObject(T objOriginal, T obj) {
			
		resultOperation = false;
		log.activateLog(obj.isDebugLog());
		//log.activatehistoric(obj.getHistoricLog());				
		
		try{
			if (em.isOpen() && obj != null){										
											
				em.merge(obj);
				resultOperation = true;
				log(obj, langType.msgLog("UPDATE"), null);				
				log.detailLog(objOriginal, obj);				

			}
		
		}catch(Exception ex) {
			/*
			ex.printStackTrace();
			resultOperation = false;
			listLog.clear();
			listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
			listLog.add(obj.getId().toString());
			listLog.add(langType.msgLog("ERROR_UPDATE"));
			listLog.add(obj.toString()+"; " + langType.msgLog("ERROR_CLASS") + ex.getClass().getName());
			log.recordLog(listLog);			
			rollback();
			errorTp6(ex, ErrorDao.UPDATE, "ERROR_UPDATE", obj);
			*/
			log.recordLog(new String[] {
				langType.msgLog(obj.getClass().getSimpleName())	,
				obj.getId().toString(),
				langType.msgLog("ERROR_UPDATE"),
				obj.toString() +"; " + langType.msgLog("ERROR_CLASS") + ex.getClass().getName()});
			errorTask6 (ex, ErrorDao.UPDATE, "ERROR_UPDATE", obj);	
		}
	}
	
	/**
	 * Update object. 
	 * {Pre: The object exist}.
	 * {Post: The object is update if exist. 
	 */
	//public void updateObjectDefault (Base obj){
	public  <T extends Base> void updateObjectDefault(T obj) {
		
		resultOperation = false;
		log.activateLog(obj.isDebugLog());
		log.activateDetailLog(obj.isDetailLog());				
		
		try{
			if (em.isOpen() && obj != null){										
							
				em.merge(obj);
				resultOperation = true;
			}
		
		}catch(Exception ex) {
			/*
			ex.printStackTrace();
			resultOperation = false;
			listLog.clear();
			listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
			listLog.add(obj.getId().toString());
			listLog.add(langType.msgLog("ERROR_UPDATE"));
			listLog.add(obj.toString()+"; " + langType.msgLog("ERROR_CLASS") + ex.getClass().getName());
			log.recordLog(listLog);			
			rollback();
			errorTp6(ex, ErrorDao.UPDATE, "ERROR_UPDATE", obj);
			*/
			log.recordLog(new String[] {
				langType.msgLog(obj.getClass().getSimpleName())	,
				obj.getId().toString(),
				langType.msgLog("ERROR_UPDATE"),
				obj.toString() +"; " + langType.msgLog("ERROR_CLASS") + ex.getClass().getName()});
			errorTask6 (ex, ErrorDao.UPDATE, "ERROR_UPDATE", obj);
		}
		
	}
	
	/**
	 * Remove object. 
	 * {Pre: The object exist}.
	 * {Post: The object is remove. 
	 */
	//public void removeObject (Base obj) {
	public <T extends Base> void removeObject(T obj) {	
		resultOperation = false;
		log.activateLog(obj.isDebugLog());
		log.activateDetailLog(false);
		
		try{
			
			obj = findObjectPK(obj);
			
			if (em.isOpen() && (obj) != null){
				
				em.remove(obj);				
				
				resultOperation = true;
				log(obj, langType.msgLog("REMOVE"), null);
							
			}
			
		} catch(Exception ex) {
			/*
			ex.printStackTrace();
			resultOperation = false;
			listLog.clear();
			listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
			listLog.add(obj.getId().toString());
			listLog.add(langType.msgLog("ERROR_REMOVE"));
			listLog.add(obj.toString()+"; " + langType.msgLog("ERROR_CLASS") + ex.getClass().getName());
			log.recordLog(listLog);				
			rollback();
			errorTp6(ex, ErrorDao.REMOVE, "REMOVE", obj);
			*/
			log.recordLog(new String[] {
					langType.msgLog(obj.getClass().getSimpleName())	,
					obj.getId().toString(),
					langType.msgLog("ERROR_REMOVE"),
					obj.toString() +"; " + langType.msgLog("ERROR_CLASS") + ex.getClass().getName()});
				
			errorTask6(ex, ErrorDao.REMOVE, "ERROR_REMOVE", obj);			
		}		
	}	
	
	/**
	 * Remove object. 
	 * {Pre: The object exist}.
	 * {Post: The object is remove. 
	 */
	//public void removeObjectDefault (Base obj) {
	public <T extends Base> void removeObjectDefault(T obj) {	
		resultOperation = false;
		log.activateLog(obj.isDebugLog());
		log.activateDetailLog(false);
		
		try{
			
			obj = findObjectPK(obj);
			
			if (em.isOpen() && (obj) != null){
				
				em.remove(obj);								
				resultOperation = true;
				log(obj, langType.msgLog("REMOVE"), null);
				
			}
			
		} catch(Exception ex) {
			/*
			ex.printStackTrace();
			resultOperation = false;
			listLog.clear();
			listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
			listLog.add(obj.getId().toString());
			listLog.add(langType.msgLog("ERROR_REMOVE"));
			listLog.add(obj.toString()+"; " + langType.msgLog("ERROR_CLASS") + ex.getClass().getName());
			log.recordLog(listLog);				
			rollback();
			errorTp6(ex, ErrorDao.REMOVE, "ERROR_REMOVE", obj);
			*/
			log.recordLog(new String[] {
				langType.msgLog(obj.getClass().getSimpleName())	,
				obj.getId().toString(),
				langType.msgLog("ERROR_REMOVE"),
				obj.toString() +"; " + langType.msgLog("ERROR_CLASS") + ex.getClass().getName()});
			errorTask6( ex, ErrorDao.REMOVE, "ERROR_REMOVE", obj);
		}		
	}	
	
	/**
	 * Find object of type Base by primary key. 
	 * {Pre: Primary key of object}.
	 * {Post: return object if exist}. 
	 */
	//public Base findObjectPK (Base obj) {
	public <T extends Base> T findObjectPK (T obj) {	
		resultOperation = false;
		
		//Base new_obj = null;
		T new_obj = null;
		
		try{
			if (em.isOpen()){
																							
				//new_obj = em.find(obj.getClass(), obj.getId());												
				new_obj = (T) em.find(obj.getClass(), obj.getId());
				
				resultOperation = true;
				
				log(obj, langType.msgLog("FIND_PK"), null);											
			}
			
		}catch(Exception ex) {
			/*
			ex.printStackTrace();
			resultOperation = false;
			listLog.clear();
			listLog.add(langType.msgDao(obj.getClass().getSimpleName()));
			listLog.add(obj.getId().toString());
			listLog.add(langType.msgLog("ERROR_FIND_PK"));
			listLog.add(langType.msgLog("ERROR_CLASS") + ex.getClass().getName());
			log.recordLog(listLog);				
			errorTp5(ex, ErrorDao.FIND_PK, "ERROR_FIND_PK", obj.getClass().getSimpleName());
			*/
			log.recordLog(new String[] {
					langType.msgLog(obj.getClass().getSimpleName())	,
					obj.getId().toString(),
					langType.msgLog("ERROR_FIND_PK"),
					langType.msgLog("ERROR_CLASS") + ex.getClass().getName()});
			errorTask5(ex, ErrorDao.FIND_PK, "ERROR_FIND_PK", obj.getClass().getSimpleName());
		}
		
		return new_obj;
	}
	
	/**
	 * Find object by field unique description. 
	 * {Pre: Field description}.
	 * {Post: return object if exist}. 
	 */
	//public Base findObjectDescription (Base obj) {
	public <T extends Base> T  findObjectDescription (T obj) {	
		resultOperation = false;
		
		List <T> lstObjects = null;
		
		T new_base = null;
		
		
		try {
								
			lstObjects = em.createQuery("select o from " + obj.getClass().getSimpleName() + " o where o.description like :pDescription")
						.setParameter("pDescription", obj.getDescription())
						.getResultList();
			
			if (lstObjects.size() == 1){
			
				new_base = lstObjects.get(0);
				resultOperation = true;
				/*
				listLog.clear();
				listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
				listLog.add(null);
				listLog.add(langType.msgLog("FIND_DESCRIPTION"));
				listLog.add(obj.getDescription());
				log.recordLog(listLog);
				*/
				log.recordLog(new String[] {
						langType.msgLog(obj.getClass().getSimpleName())	,
						null,
						langType.msgLog("FIND_DESCRIPTION"),
						obj.getDescription()});
			}
		
		}
		catch (NoResultException Nex){
						
			resultOperation = false;
			
			return null;
		
		}
		
		catch(Exception ex) {
			/*
			resultOperation = false;
			ex.printStackTrace();
			listLog.clear();
			listLog.add(langType.msgDao(obj.getClass().getSimpleName()));
			listLog.add(null);
			listLog.add(langType.msgLog("ERROR_FIND_DESCRIPTION"));
			listLog.add(langType.msgLog("ERROR_CLASS") + ex.getClass().getName());
			log.recordLog(listLog);				
			errorTp5(ex, ErrorDao.FIND_DESCRIPTION, "ERROR_FIND_DESCRIPTION", obj.getClass().getSimpleName());			
			*/
			log.recordLog(new String[] {
					langType.msgLog(obj.getClass().getSimpleName())	,
					null,
					langType.msgLog("ERROR_FIND_DESCRIPTION"),
					langType.msgLog("ERROR_CLASS") + ex.getClass().getName()});
			
			errorTask5(ex, ErrorDao.FIND_DESCRIPTION, "ERROR_FIND_DESCRIPTION", obj.getClass().getSimpleName());			
		}
		
		return new_base;
		
	}
	
	//public void deleteObjects (Base obj) {
	public <T extends Base> void deleteObjects (T obj) {
						
		resultOperation = false;
		String className = obj.getClass().getSimpleName();		
		AnalyzerConsult c = new AnalyzerConsult();
		String whereClause = c.makeWhere(obj);										
		
		try{
			if (em.isOpen()){										
				
				//Integer i = em.createQuery("delete from " + className + " " + className +  " " + whereClause)
				em.createQuery("delete from " + className + " " + className +  " " + whereClause)
				  .executeUpdate();																				
			}
			
		}catch(Exception ex) {
			/*
			ex.printStackTrace();
			listLog.clear();
			listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
			listLog.add(null);
			listLog.add(langType.msgLog("ERROR_FIND_OBJECTS"));
			listLog.add(langType.msgLog("ERROR_CLASS") + ex.getClass().getName());
			log.recordLog(listLog);				
			errorTp5(ex, ErrorDao.FIND_OBJECTS, "ERROR_FIND_OBJECTS", obj.getClass().getSimpleName());
			*/
			log.recordLog(new String[] {
					langType.msgLog(obj.getClass().getSimpleName())	,
					null,
					langType.msgLog("ERROR_FIND_DESCRIPTION"),
					langType.msgLog("ERROR_CLASS") + ex.getClass().getName()});
			
			errorTask5(ex, ErrorDao.FIND_DESCRIPTION, "ERROR_FIND_DESCRIPTION", obj.getClass().getSimpleName());			
		}
												
	}
	
	/**
	 * Find objects by some criterion. 
	 * {Pre: Some criterion}.
	 * {Post: return List objects if exist}. 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	
	//public List<Base> findObjects (Base pObj) {
	public <T extends Base> List<T> findObjects (T pObj)	{
		T obj = null;
		
		try {
			
			obj = (T) ReflectionField.getCorrectObject(pObj);
					
			listObjects = new ArrayList <T>();
		
			resultOperation = false;
			String className = obj.getClass().getSimpleName();		
			AnalyzerConsult c = new AnalyzerConsult();
			String whereClause = c.makeWhere((Base) obj);								
			//listObjects.clear();
				
			if (em.isOpen()){										
																												
				listObjects = em.createQuery("select " +  className + " from " + className + " " + className +  " " + whereClause)
							.getResultList();								
				/*
				listLog.clear();
				listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
				listLog.add(null);
				listLog.add(langType.msgLog("FIND_OBJECTS"));
				//listLog.add("select " +  className + " from " + className + " " + className + " " + whereClause);
				
				if (whereClause.length() > 200) whereClause = whereClause.substring(0, 200);
				listLog.add("select " +  className + " - " + whereClause);
				log.recordLog(listLog);	
				*/
				log.recordLog(new String[] {
						langType.msgLog(obj.getClass().getSimpleName())	,
						null,
						langType.msgLog("FIND_DESCRIPTION"),
						"select " +  className + " - " + StringUtils.truncate(whereClause, 200)});
			}
			
		} catch(Exception ex) {
			/*
			ex.printStackTrace();
			listLog.clear();
			listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
			listLog.add(null);
			listLog.add(langType.msgLog("ERROR_FIND_OBJECTS"));
			listLog.add(langType.msgLog("ERROR_CLASS") + ex.getClass().getName());
			log.recordLog(listLog);				
			errorTp5(ex, ErrorDao.FIND_OBJECTS, "ERROR_FIND_OBJECTS", obj.getClass().getSimpleName());
			*/
			log.recordLog(new String[] {
					langType.msgLog(obj.getClass().getSimpleName())	,
					null,
					langType.msgLog("ERROR_FIND_OBJECTS"),
					langType.msgLog("ERROR_CLASS") + ex.getClass().getName()});
			
			errorTask5(ex, ErrorDao.FIND_OBJECTS, "ERROR_FIND_OBJECTS", obj.getClass().getSimpleName());			
		}
				
		if (listObjects.size() > 0) resultOperation = true;
		
		return (List<T>) listObjects;
	}		
	
	/**
	 * Find object by field unique description. 
	 * {Pre: Field description}.
	 * {Post: return object if exist}. 
	 */
	//public List<Object[]> findObjectPerson (String pSentencia) {
	public <T extends Object> List<T[]> findObjectPerson (String pSentencia) {						
		resultOperation = false;
		
		try{
			if (em.isOpen()){										
																								
				//System.out.println("select " +  className + " from " + className + " " + className + " " + whereClause + " order by " + className + ".getDescription");				
				
				List<T> lstObjects = em.createQuery(pSentencia).getResultList();																
				
				if (lstObjects.size() > 0) resultOperation = true;
												
				return (List<T[]>) lstObjects;
			}
			
		}catch(Exception ex) {
			/*
			ex.printStackTrace();
			errorTp4(ex, ErrorDao.FIND_OBJECTS, "ERROR_FIND_OBJECTS");	
			*/		
			errorTask4(ex, ErrorDao.FIND_OBJECTS, "ERROR_FIND_OBJECTS");
		}
						
		return null;		
	}
	
	//public List<Base> findObjectPerson2 (String pSentencia) {
	public <T extends Base> List<T> findObjectPerson2 (String pSentencia) {
		resultOperation = false;
		
		try{
			if (em.isOpen()){										
																								
				//System.out.println("select " +  className + " from " + className + " " + className + " " + whereClause + " order by " + className + ".getDescription");				
				
				List<T> lstObjects = em.createQuery(pSentencia).getResultList();																
				
				if (lstObjects.size() > 0) resultOperation = true;
												
				return lstObjects;
			}
			
		}catch(Exception ex) {
			/*
			ex.printStackTrace();
			errorTp4(ex, ErrorDao.FIND_OBJECTS, "ERROR_FIND_OBJECTS");
			*/	
			errorTask4(ex, ErrorDao.FIND_OBJECTS, "ERROR_FIND_OBJECTS");
		}
						
		return null;		
	}
	
	public void executeSQL (String pSentencia){
		
		resultOperation = false;
		
		try{
			if (em.isOpen()){										
																											
				int result = em.createQuery(pSentencia).executeUpdate();																
				
				System.out.println(" RESULT " + result);
			}
			
		}catch(Exception ex) {
			/*
			ex.printStackTrace();
			errorTp4(ex, ErrorDao.FIND_OBJECTS, "ERROR_FIND_OBJECTS"); 
			*/
			errorTask4(ex, ErrorDao.FIND_OBJECTS, "ERROR_FIND_OBJECTS");
			
		}
		
		
	}
	
	/**
	  * Procedure that starts the transaction
	  * {Pre: There is an open connection to the database}.
	  * {Post: has initiated the transaction}.
	  * @throws DataException if there isn't a connection to the database.
	  * @throws DataException if there is an error to start the transaction.
	  */
	  public void begin() {
	    
		
		try {
			
			if (em.getTransaction().isActive()) return;
						
			em.getTransaction().begin();
			/*
			listLog.clear();
			listLog.add(null);
			listLog.add(null);
			listLog.add(null);
			listLog.add(langType.msgLog("TRANSACTION_BEGIN"));
			log.recordLog(listLog);		
			*/
			log.recordLog(langType.msgLog("TRANSACTION_BEGIN"));
			
		} catch (Exception ex) {
			/*
			ex.printStackTrace();
			listLog.clear();
			listLog.add(null);
			listLog.add(null);
			listLog.add(null);
			listLog.add(langType.msgLog("ERROR_TRANSACTION_BEGIN"));
			log.recordLog(listLog);
			errorTp1(ex, ErrorDao.TRANSACTION);
			*/
			log.recordLog(langType.msgLog("ERROR_TRANSACTION_BEGIN"));
			errorTask1(ex, ErrorDao.TRANSACTION);
		}
		
		return;
	  }	
	
	  /**
		* Result of the operation. 
		*/
	  public boolean isResultOperation() {
		
		  return resultOperation;
	  
	  }	
	  
	  
	  /**
		* Procedure that end the transaction accepting updates.
		* {Pre: There is an open connection to the database}.
		* {Post: End the transaction}.
		* @throws DataException if there isn't a connection to the database.
		* @throws DataException if there is an error to end the transaction.
		*/
	  public void commit() {
		
		
		try{  
				
			if ( ! factory.isOpen() && ! em.isOpen()); 				
				
		} catch (Exception ex){
			/*
			ex.printStackTrace();
			listLog.clear();  
			listLog.add(null);
			listLog.add(null);
			listLog.add(null);
			listLog.add(langType.msgLog("ERROR_CONNECTION_COMMIT"));
			log.recordLog(listLog);
			errorTp3(ex, ErrorDao.TRANSACTION);
			*/
			log.recordLog(langType.msgLog("ERROR_CONNECTION_COMMIT"));
			errorTask3(ex, ErrorDao.TRANSACTION);
		}
		  
		if ( ! em.getTransaction().isActive() )
			  
			return;
		  
		try {
			/*
			listLog.clear();  
			listLog.add(null);
			listLog.add(null);
			listLog.add(null);
			listLog.add(langType.msgLog("TRANSACTION_END"));
			log.recordLog(listLog);	
			*/
			log.recordLog(langType.msgLog("TRANSACTION_BEGIN"));
			em.getTransaction().commit();						  	
		
		}catch (Exception ex) {
			/*
			ex.printStackTrace();
			System.out.println("Error " + ex.getLocalizedMessage() + ex.getCause().getClass());
			listLog.clear();  
			listLog.add(null);
			listLog.add(null);
			listLog.add(null);
			listLog.add(langType.msgLog("ERROR_TRANSACTION_COMMIT"));
			log.recordLog(listLog);			
			resultOperation = false;			
			errorTp2(ex, ErrorDao.COMMIT, "ERROR_TRANSACTION");
			*/
			log.recordLog(langType.msgLog("ERROR_CONNECTION_COMMIT"));
			errorTask2(ex, ErrorDao.COMMIT, "ERROR_TRANSACTION");
		}
		  
		return;
	  }
	  

	  /**
		* Procedure that end the transaction not accepting updates.
		* {Pre: There is an open connection to the database}.
		* {Post: End the transaction}.
		* @throws DataException if there isn't a connection to the database.
		* @throws DataException if there is an error to end the transaction.
		*/
	  public void rollback() {
		 
		 
		 try{  
				
			  if ( ! factory.isOpen() && ! em.isOpen()); 
				
		} catch (Exception ex){
			/*
			ex.printStackTrace();
			listLog.clear();
			listLog.add(null);
			listLog.add(null);
			listLog.add(null);
			listLog.add(langType.msgLog("ERROR_CONNECTION_ROLLBACK"));
			log.recordLog(listLog);	
			errorTp1(ex, ErrorDao.CONNETION);
			*/
			log.recordLog(langType.msgLog("ERROR_CONNECTION_ROLLBACK"));	
			errorTask1(ex, ErrorDao.CONNETION);
		}

		if ( ! em.getTransaction().isActive() )
		  return;
		
		try {
			
			em.getTransaction().rollback();
			/*
			listLog.clear();
			listLog.add(null);
			listLog.add(null);
			listLog.add(null);
			listLog.add(langType.msgLog("TRANSACTION_ROLLBACK"));
			log.recordLog(listLog);	
			*/
			log.recordLog(langType.msgLog("TRANSACTION_ROLLBACK"));	
			
			
		} catch (Exception ex) {
			/*
			ex.printStackTrace();
			listLog.clear();
			listLog.add(null);
			listLog.add(null);
			listLog.add(null);
			listLog.add(langType.msgLog("ERROR_TRANSACTION_ROLLBACK"));			
			resultOperation = false;
			errorTp1(ex, ErrorDao.TRANSACTION);
			*/
			log.recordLog(langType.msgLog("ERROR_CONNECTION_COMMIT"));
			errorTask1(ex, ErrorDao.TRANSACTION);
		}
		
		return;
	  }
	  
	  /**
		* Procedure that closed the connection to database. 
		* {Pre: there isn't more references to object in memory}.
		* {Post: Closed the connection to database}. 
		*/
	  public void finalize() {								
		
		em.close();
			
		factory.close();				
		
	  }	  	  	
	  
	
	/**Instance associated with a persistence context*/
	public EntityManager getEntityManager(){
		
		return em;
	}
		
	
	private void log(Base obj, String pmensaje, String pError){
		
		listLog.clear();
		
		if (obj == null){
			
			listLog.add(null);		//Object
			listLog.add(null);		//idObject
			listLog.add(null);		//Action
			listLog.add(pmensaje);	//Description
			
		}
		
		else if (pError != null){
			
			listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
			listLog.add("0");
			listLog.add(pmensaje);
			listLog.add(obj.toString()+"; " + pError);

		}
								
		else {
			
			listLog.add(langType.msgLog(obj.getClass().getSimpleName()));
			listLog.add(obj.getId().toString());
			listLog.add(pmensaje);
			listLog.add(obj.toString());
			
				
		}
		
		
		log.recordLog(listLog);
	
		
	}
	
	/** Edu 5/12/2017
	 * to know if a table is empty or not using the entity
	 * 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException **/
	public <T extends Base> boolean isEmpty(T pObj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		Object	obj = ReflectionField.getCorrectObject(pObj);
		return isEmpty(obj.getClass());
		
	}
	/**
	 * To know if a table is empty knowing the class of the entity
	 */
	public  boolean isEmpty(Class<?> klass) {
		return
				em.createQuery("select 1 from " + klass.getSimpleName())
					.setMaxResults(1)
					.getResultList()
					.size() == 0;
	}
	
	/*************************  ERRORS ************************************/
	private void errorDataException(Exception ex ,ErrorDao pErrDao, String p1,  String p2) {
		ex.printStackTrace();               
		new Error().DataException(pErrDao, p1, p2, environment, langType);  
	}
	
	/**
	 * Task to do when an exception raises
	 * @param ex
	 * @param pErrDao
	 */
	private void errorTask1(Exception ex, ErrorDao pErrDao) {
		errorDataException(ex ,pErrDao, 
				ex.getMessage(),	
				ex.getClass().getName());
	}
	/**
	 * Task to do when an exception raises
	 * @param ex
	 * @param pErrDao
	 * @param pMsgLog
	 */
	private void errorTask2(Exception ex, ErrorDao pErrDao, String pMsgLog) {
		resultOperation = false;
		errorDataException(ex ,pErrDao, 
				ex.getMessage(),
				ex.getLocalizedMessage() + ex.getCause());
	}
	
	/**
	 * Task to do when an exception raises
	 * @param ex
	 * @param pErrDao
	 */
	private void errorTask3(Exception ex, ErrorDao pErrDao) {
		resultOperation = false;
		errorDataException(ex ,pErrDao, 
				ex.getMessage(),
				ex.getLocalizedMessage() + ex.getCause());
	}
	
	/**
	 * Task to do when an exception raises
	 * @param ex
	 * @param pErrDao
	 * @param pMsgError
	 */
	private void errorTask4(Exception ex, ErrorDao pErrDao, String pMsgError) {
		resultOperation = false;
		errorDataException(ex ,pErrDao, 
				langType.msgError(pMsgError) + "\n SENTENCIA PERSONALIZADA", 
				ex.getClass().getName());
	}
	
	/**
	 * Task to do when an exception raises
	 * @param ex
	 * @param pErrDao
	 * @param pMsgError
	 * @param pMsgDao
	 */
	private void errorTask5(Exception ex, ErrorDao pErrDao, String pMsgError, String pMsgDao) {
		resultOperation = false;
		errorDataException(ex ,pErrDao, 
				langType.msgError(pMsgError) + "\n"  + langType.msgDao(pMsgDao), 
				ex.getClass().getName());
	}
	
	/**
	 * Task to do when an exception raises
	 * @param ex
	 * @param pErrDao
	 * @param pMsgError
	 * @param obj
	 */
	private <T extends Base> void errorTask6(Exception ex, ErrorDao pErrDao, String pMsgError, T obj) {
		resultOperation = false;
		rollback();
		errorDataException(ex ,pErrDao, 
				langType.msgError(pMsgError) + "\n"  + 
					langType.msgDao(obj.getClass().getSimpleName()) + " " + obj.getDescription(), 					
				ex.getClass().getName());
	}
	
	/**
	 * Task to do when an exception raises
	 * @param ex
	 * @param pErrDao
	 * @param pMsgError
	 * @param pError
	 * @param obj
	 */
	private <T extends Base> void errorTask7(Exception ex, ErrorDao pErrDao, String pMsgError, String pError, T obj) {
		resultOperation = false;
		rollback();
		errorDataException(ex ,pErrDao, 
				langType.msgError(pMsgError) + "\n "  + 
					langType.msgDao(obj.getClass().getSimpleName()) + " " + obj.getDescription(),					
				ex.getLocalizedMessage().substring(0, ex.getLocalizedMessage().indexOf(":")));
	}	
	
	
	/*
	private void errorTp1(Exception ex, ErrorDao pErrDao) {
		
		Error err = new Error();
		err.DataException(pErrDao,   
				ex.getMessage(),	
				ex.getClass().getName(),
				environment, 
				langType);
		
	}
	
	private void errorTp2(Exception ex, ErrorDao pErrDao, String pMsgLog) {
		
		Error err = new Error();
		err.DataException(pErrDao,   
				langType.msgLog(pMsgLog),	
				ex.getCause().getClass().getName(),
				environment, 
				langType);
		
	}
	
	private void errorTp3(Exception ex, ErrorDao pErrDao) {
		
		Error err = new Error();
		err.DataException(pErrDao,   
				ex.getMessage(),
				ex.getLocalizedMessage() + ex.getCause(),
				environment, 
				langType);
	}

	private void errorTp4(Exception ex, ErrorDao pErrDao, String pMsgError) {
		
		Error err = new Error();
		err.DataException(pErrDao,   
				langType.msgError(pMsgError) + "\n"  + 
				" SENTENCIA PERSONALIZADA", 
				ex.getClass().getName(),
				environment, 
				langType);			
	}
	
	private void errorTp5(Exception ex, ErrorDao pErrDao, String pMsgError, String pMsgDao) {
		
		Error err = new Error();	
		err.DataException(pErrDao,   
				langType.msgError(pMsgError) + "\n"  + 
				langType.msgDao(pMsgDao), 
				ex.getClass().getName(),
				environment,
				langType);
	}
	
	private void errorTp6(Exception ex, ErrorDao pErrDao, String pMsgError, Base obj) {
		
		Error err = new Error();
		err.DataException(pErrDao, 
				langType.msgError(pMsgError) + "\n"  + 
				langType.msgDao(obj.getClass().getSimpleName()) + " " + obj.getDescription(), 					
				ex.getClass().getName(),
				environment,
				langType);
		
	}
	
	private void errorTp7(Exception ex, ErrorDao pErrDao, String pMsgError, Base obj) {
		
		Error err = new Error();
		err.DataException(pErrDao, 
				langType.msgError(pMsgError) + ": "  + 
				langType.msgDao(obj.getClass().getSimpleName()) + " " + obj.getDescription(),					
				ex.getLocalizedMessage().substring(0, ex.getLocalizedMessage().indexOf(":")),
				environment,
				langType);
		
	}
	*/

}
