package openadmin.dao.operation;

import java.util.List;

import openadmin.model.Base;
import openadmin.util.configuration.TypeLanguages;

public class LogFile implements LogOperationFacade {
	
	public void openTrace(){
		
	}
	public void closedTrace(){
		
	}
	
	public void setLanguageLog(TypeLanguages pLanguage){
		
	}
	public String language(String pKey){
		
		return null;
	}
	public void finalizeLog() {
		
	}

	public void activateLog(Boolean pDebug){
				
	}
	
	public void changeProgram(String pProgram){
		
	}
	
	public void activateDetailLog(Boolean pDetailLog){
		
		
	}
	
	public <T extends Base> void detailLog( T objectOriginal, T objectUpdate) {
		
		
	}
	
	public void recordLog(List<String> listLog){
		
		
	}

}
