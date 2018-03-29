package openadmin.dao.operation;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.persistence.EntityManager;

import openadmin.model.Base;
import openadmin.model.control.User;
import openadmin.util.configuration.TypeEnvironment;

/**
 * <desc>DaoOperationFacade are used to provide information about the operations of database </desc>
 * <responsibility>Represents the pattern DAO</responsibility>
 * <coperation>All classes that need to work with database</coperation>
 * @version  0.1
 * Create  18-03-2009
 * Author Alfred Oliver
*/
public interface DaoOperationFacade {
	
	/**
	  * Procedure that starts the transaction
	  * {Pre: There is an open connection to the database}.
	  * {Post: has initiated the transaction}.
	  * @throws DataException if there isn't a connection to the database.
	  * @throws DataException if there is an error to start the transaction.
	  */
	public void begin();
	
	/**
	  * Procedure that end the transaction accepting updates.
	  * {Pre: There is an open connection to the database}.
	  * {Post: End the transaction}.
	  */
	public void commit();
	 
	/**
	  * Procedure that end the transaction not accepting updates.
	  * {Pre: There is an open connection to the database}.
	  * {Post: End the transaction}.
	  */
	public void rollback();  
	
	/**
	 * Procedure that closed the connection to database. 
	 * {Pre: there isn't more references to object in memory}.
	 * {Post: Closed the connection to database}. 
	 */
	public void finalize();

	/**
	 * Result of the operation. 
	
	 */
	public boolean isResultOperation();
	 
	/**
	 * Persist object. 
	 * {Pre: The object no exist}.
	 * {Post: The object is persist. 
	 */
	//public void persistObject(Base obj);
	public <T extends Base> void persistObject(T obj);
	public void persistObject2(Base obj);
	
	//public void persistObjectDefault(Base obj);
	public <T extends Base> void persistObjectDefault(T obj);
	/**
	 * Update object. 
	 * {Pre: The object exist}.
	 * {Post: The object is update. 
	 */
	//public void updateObject(Base objOriginal, Base obj);
	public <T extends Base> void updateObject(T objOriginal, T obj);
	
	//public void updateObjectDefault(Base obj);
	public  <T extends Base> void updateObjectDefault(T obj);
	
	/**
	 * Remove object. 
	 * {Pre: The object exist}.
	 * {Post: The object is remove. 
	 */
	//public void removeObject(Base obj);
	public <T extends Base> void removeObject(T obj);
	
	//public void removeObjectDefault(Base obj);
	public <T extends Base> void removeObjectDefault(T obj);
	
	//public void deleteObjects (Base obj);
	public <T extends Base> void deleteObjects (T obj);
	
	/**
	 * Find object by primary key. 
	 * {Pre: Primary key of object}.
	 * {Post: return object if exist}. 
	 */
	//public Base findObjectPK (Base obj);
	public <T extends Base> T findObjectPK (T obj);
	
	
	/**
	 * Find object by field unique description. 
	 * {Pre: Field description}.
	 * {Post: return object if exist}. 
	 */
	//public Base findObjectDescription (Base obj);
	public <T extends Base> T  findObjectDescription (T obj);
	
	/**
	 * Find objects by some criterion. 
	 * {Pre: Some criterion, if there aren't some criterion, List all object}.
	 * {Post: return List objects if exist}. 
	 */
	//public List<Base> findObjects (Base obj);		
	public <T extends Base> List<T> findObjects (T obj);
	
	//public List<Object[]> findObjectPerson (String pSentencia);
	public <T extends Object> List<T[]> findObjectPerson (String pSentencia);
	
	//public List<Base> findObjectPerson2 (String pSentencia);
	public <T extends Base> List<T> findObjectPerson2 (String pSentencia);
	
	//public void executeSQL (String pSentencia);
	public void executeSQL (String pSentencia);
	
	/** Getters and setters*/
	public User getUser();
	
	/** Getters and setters*/
	public void setUser(User user);	
	
	public TypeEnvironment getEnvironment();
	
	public void setEnvironment(TypeEnvironment pEnvironment);
		
	public EntityManager getEntityManager();
	
	
	/** Edu 5/12/2017
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException **/
	public <T extends Base> boolean isEmpty(T obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException;
	public  boolean isEmpty(Class<?> c);
	
}
