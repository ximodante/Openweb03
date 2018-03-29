package openadmin.dao.exception;

import openadmin.util.configuration.TypeEnvironment;
import openadmin.util.lang.LangType;
import openadmin.util.lang.WebMessages;

public class Error {

    private TypeEnvironment environment;
    private String message;
	      
    public void DataException(ErrorDao pError, String pText, String pClassError, TypeEnvironment pEnvironment, LangType pLangType) {    	    	
    	
    	LangType langType = pLangType;
    	
    	environment = pEnvironment;
    	
    	if (pError.equals(ErrorDao.PERSIST)&& pClassError.equals("org.hibernate.exception.ConstraintViolationException")){    		
    		message= pText + ".\n " +  langType.msgError("ERROR_PK");    		
    	}
    	
    	else if (pError.equals(ErrorDao.PERSIST)&& pClassError.equals("org.hibernate.validator.InvalidStateException")){    		
    		message = pText + ", " +  langType.msgError("ERROR_UNIQUE");    		
    	}
    	
    	else if (pError.equals(ErrorDao.UPDATE)&& pClassError.equals("openadmin.dao.exception.DataException")){    		
    		message = pText + "\n" +  langType.msgError("ERROR_UNIQUE");    		
    	} 
    	
    	else if (pError.equals(ErrorDao.COMMIT)&& pClassError.equals("org.hibernate.exception.ConstraintViolationException")){    		
    		message = pText + "\n" + langType.msgError("ERROR_CONSTRAINT");    		
    	}
    	
    	else if (pError.equals(ErrorDao.COMMIT)&& pClassError.equals("org.hibernate.validator.InvalidStateException")){    		
    		message = pText + "\n" +  langType.msgError("ERROR_UNIQUE");    		
    	}    	
    	
    	else if (pError.equals(ErrorDao.FIND_PK)&& pClassError.equals("java.lang.IllegalArgumentException")){    		
    		message = pText + "\n" +  langType.msgError("ERROR_NO_PK");    		
    	}
    	
    	else if (pError.equals(ErrorDao.UPDATE)&& pClassError.equals("java.lang.IllegalStateException")){    		
    		message = pText + "\n" +  langType.msgError("ERROR_UNIQUE");    		
    	}    	
    	
    	else message ="Error no tipificat" + "\n " + pText + "\n " + pClassError;
    	
    	DisplayedError();
    	
    }   
    
    private void DisplayedError(){
    	    	
    	if (environment.equals(TypeEnvironment.SWING))displayedSwing();
    	else if (environment.equals(TypeEnvironment.WEB))displayedWeb();
    	
    }
    
    public void displayedSwing(){
		    	
    	System.out.println("Control ERROR: " + message);
		
	}
    
    public void displayedWeb(){
    	
    	WebMessages.messageErrorDao(message);
    	
	}
	
}
