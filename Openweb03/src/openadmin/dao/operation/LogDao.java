package openadmin.dao.operation;


import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.csvreader.CsvWriter;

import openadmin.model.Base;
import openadmin.dao.exception.Error;
import openadmin.dao.exception.ErrorDao;
import openadmin.model.log.Detail;
import openadmin.model.log.Log;
import openadmin.util.edu.ReflectionUtils;
import openadmin.util.lang.LangType;
import openadmin.util.reflection.ReflectionField;

/**
 * <desc>LogDao are used to persist information in database about the operations of Log </desc>
 * <responsibility>registered all operation in database</responsibility>
 * <coperation>Implements LogOperationFacade</coperation>
 * @version  0.2
 * Create  18-03-2009
 * Author Alfred Oliver
 * Modified 06-11-2017 
*/
public class LogDao implements Serializable, LogOperationFacade{
	
	private static final long serialVersionUID = 06111703L;
	
	/** Field with database connection*/
	private DaoOperationFacade connectDao = null;
	
	/** Work program*/
	private String program = "control";
	
	//private List<String> listLog = new ArrayList<String>();
	
	private Base objectOriginal = null;
	
	private Base objectUpdate = null;
	
	/** State debug*/
	private boolean debugLog = true;
	
	/** State historic*/
	private boolean detailLog = false;
	
	/** State trace*/
	private boolean trace = false;
	
	/** File to registered log*/
	private CsvWriter logtxt;
	
	private LangType langType;
	
	// Length of log.person
	private int lenPerson=ReflectionUtils.sizeAnnotated(Log.class, "person"); 
	
	/**
	  * Constructor of class LogDao.
	  * @throws DataException. 
	  * @param pConnect. Connection database.  	 
	  * @param 	pProgram. Work program.	
	  */
	public LogDao(DaoOperationFacade pConnect, String pProgram, LangType pLangType){
		
		langType = pLangType;
		
		this.program = pProgram;
		this.connectDao = pConnect;
		recordLog(langType.msgLog("CONNECTION_LOG"));	
				
	}
	
	
	/**
	  * Procedure open file trace. 
	  */
	public void openTrace() {
		  
	  logtxt = new CsvWriter ("c:/log" +  DateFormat.getDateInstance().format(Calendar.getInstance().getTime()) + ".csv");
	  this.trace = true;
	}
	  
	 /**
	   * Procedure closed file trace.
	   */
	  public void closedTrace() {
		  
		  if (trace){
				logtxt.close();
				this.trace = false;
			}
	  }	
	
	 //public void detailLog(Base objectOriginal, Base objectUpdate){
	  public <T extends Base> void detailLog( T objectOriginal, T objectUpdate) {
		 
		 this.objectOriginal = objectOriginal;
		 this.objectUpdate = objectUpdate;
		 
	 }
	  
	 /**
	 * <desc> Registered action done</desc>
	 * @param pAccio, Action done	
	 * @throws DataException 
	 */
	public void recordLog(List<String> listLog) {
						
		if (!debugLog || connectDao == null || connectDao.getUser() == null) {
			
			return;
		}
		
		Log log = new Log();
					 
		try {
			
			log.setProgram(program);
			log.setPerson(StringUtils.truncate(connectDao.getUser().getDescription() + " - " + InetAddress.getLocalHost(),this.lenPerson));			
			log.setObject(listLog.get(0));
			log.setIdobject(listLog.get(1));
			log.setAction(listLog.get(2));
			
			log.setDescription(StringUtils.truncate(listLog.get(3), 500));
			
			log.setData(LocalDateTime.now());	
						
			if (detailLog & objectOriginal != null & objectUpdate != null ){
				
				log.setDetail(analyzerAttribute(log));
				objectNull();				
				
			}
			
			//Per ammagatzemar en la base de dades control							
			connectDao.getEntityManager().getTransaction().begin();
			connectDao.getEntityManager().persist(log);		
			connectDao.getEntityManager().getTransaction().commit();	
			
			
		} catch (Exception ex) {
			errorTask(ex);
		}
		
		
		
		if (trace){
			
			try {
				
				logtxt.write(log.getData()+ ";" + 
							 log.getPerson() + ";" + 
							 log.getObject() + ";" + 
							 log.getIdobject() + ";" +
							 log.getAction() + ";" + 
							 log.getDescription());
				logtxt.endRecord();
									
			} catch (Exception ex) {
				errorTask(ex);
			}
			
		}										
	
	}
	
	public void finalizeLog() {
		recordLog(langType.msgLog("END_CONNECTION"));	
		connectDao.finalize();
					
	}
	
	public void activateLog(Boolean pDebug){
		this.debugLog = pDebug;
	}
	
	public void activateDetailLog(Boolean pDetailLog){
		this.detailLog = pDetailLog;
	}
	
	public void changeProgram(String pProgram){
		program = langType.msgLog(pProgram);
	}
	
	private Set<Detail> analyzerAttribute(Log log){
		
		boolean result = false;
		
		Set<Detail> listdetail = new HashSet<Detail>(0);				
		
		ReflectionField rf = new ReflectionField();
		
		List<String[]> fieldObjectOriginal = rf.execute(objectOriginal);
						
		List<String[]> fieldObjectUpdate = rf.execute(objectUpdate);
				
		listdetail.clear();
		
		for (String pPropertyFieldUpdate[]: fieldObjectUpdate){
			
			result = false;
			
			for (String pPropertyFieldOriginal[]: fieldObjectOriginal){								
				
				System.out.println("Atribut ori " + pPropertyFieldOriginal[0] + " Valor ori " + pPropertyFieldOriginal[2]);
				System.out.println("Atribut upd " + pPropertyFieldUpdate[0] + " Valor upd " + pPropertyFieldUpdate[2]);
				System.out.println("---------------------------------------------------------------- ");
				
				if (pPropertyFieldUpdate[0].toString().compareTo(pPropertyFieldOriginal[0].toString())== 0){										
					
					result = true;
					
					if (!pPropertyFieldUpdate[2].toString().equals(pPropertyFieldOriginal[2].toString())){												
						
						Detail detail = new Detail();
						detail.setAttribute(pPropertyFieldOriginal[0].toString());						
						detail.setValue(pPropertyFieldOriginal[2].toString());
						detail.setDescription("");						
						detail.setLog(log);
						listdetail.add(detail);
					}
					
					break;
				}
				
			}
			
			if (!result){
			
				System.out.println("Atribut upd " + pPropertyFieldUpdate[0] + " Valor upd " + pPropertyFieldUpdate[2]);	
				Detail detail = new Detail();
				detail.setAttribute(pPropertyFieldUpdate[0].toString());						
				detail.setValue("null");
				detail.setDescription("");						
				detail.setLog(log);
				listdetail.add(detail);
				
			}			
		}
		
		return listdetail;
	}
	
	private void objectNull(){
		
		objectOriginal = null;
		objectUpdate = null;
	}
	
	/**
	 * Tasks to do in when an exception is thrown
	 * @param ex
	 */
	private void errorTask(Exception ex) {
		Error err = new Error();
		err.DataException(ErrorDao.LOG, 
							ex.getMessage(),					
							ex.getClass().getName(),
							connectDao.getEnvironment(),
							langType);
	}
}
