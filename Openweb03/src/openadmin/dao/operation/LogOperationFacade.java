package openadmin.dao.operation;




import java.util.ArrayList;
import java.util.List;

import openadmin.model.Base;

/**
 * <desc>LogOperationFacade are used to provide information about the operations of Log </desc>
 * <responsibility>Represents the pattern Facade, registered all operation with database</responsibility>
 * <coperation>DaoOperationFacade</coperation>
 * @version  0.1
 * Create  18-03-2009
 * Author Alfred Oliver
*/
public interface LogOperationFacade {
	
	/**
	  * Procedure that open one trace for registered operations performed to database 	  
	  */
	public void openTrace();
	
	/**
	  * Procedure that closed the trace 	  
	  */
	public void closedTrace();
	
	/**
	  * Procedure to persist the action perform in database
	  * {Pre: action initialized}.
	  * {Post: persist the action}.
	  */
	public void recordLog(List<String> listLog);
	
	public default void recordLog(String pDescription) {
		 recordLog(null,null,null,pDescription);
	}
	  
	public default void recordLog(String objectName, String idObject, String action, String description) {
		List<String> lstLog=new ArrayList<String>();
		
		lstLog.add(objectName);
		lstLog.add(idObject);
		lstLog.add(action);
		lstLog.add(description);
		recordLog(lstLog);
	}
	public default void recordLog(String[] aStr) {
		List<String> lstLog=new ArrayList<String>();
		
		for(String s:aStr) lstLog.add(s);
		recordLog(lstLog);
	}
	
	
	
	public <T extends Base> void detailLog( T objectOriginal, T objectUpdate);
	public void changeProgram(String pProgram);
	public void finalizeLog();
	public void activateLog(Boolean pDebug);
	public void activateDetailLog(Boolean pDetailLog);
	
}
