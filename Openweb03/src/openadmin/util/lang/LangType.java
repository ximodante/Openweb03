package openadmin.util.lang;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import lombok.Setter;
import openadmin.util.configuration.TypeLanguages;

@Named (value = "lang")
@SessionScoped
public class LangType implements Serializable {
	
	private static final long serialVersionUID = 2707201501L;
	
	private static Map<String,Object> countries;
	
	/** Field that contain the working language*/
	@Setter
	private String lang = "es";
	
	/** Field that contain the working language log*/
	private static TypeLanguages langLog = TypeLanguages.es;
	
	public String msgGenerals(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("openadmin.languages.generals_" + lang);
		return language.getString(pValue);
		
	}
	
	public String msgViewTabular(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("messagesTypeViewTabular_" + lang);
		return language.getString(pValue);
		
	}
	
	//message labels
	public String msgLabels(String pDomanin, String pValue){
		
		String result = null;
		ResourceBundle language = null;
		
		try {
			
			language = ResourceBundle.getBundle("openadmin.languages.labels_" + lang);
			
			result = language.getString(pDomanin + "_" +pValue);

			
		}catch (Exception ex) {
			
			try {
			
				//System.out.println("Etiqueta " + pValue);
			
				result = language.getString(pValue);
			
			} catch (Exception exc) {
			
				exc.printStackTrace();
			
			}
		}
		
		return result;
	
	}
	
	public String msgError(String pValue){
		
		//System.out.println("error: " + pValue);
		
		ResourceBundle language = ResourceBundle.getBundle("openadmin.languages.errors_" + lang);
		
		//System.out.println("error: " + language.getString(pValue));
		
		return language.getString(pValue);
		
	}
	
	public String msgViewError(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("messagesviewerror_" + lang);
		return language.getString(pValue);
		
	}
	
	public String msgActions(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("openadmin.languages.actions_" + lang);
		return language.getString(pValue);
		
	}
	
	//message dao
	public String msgDao(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("openadmin.languages.messagesDao_" + lang);
		return language.getString(pValue);
		
	}
	
	//Message log
	public String msgLog(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("openadmin.languages.messagesLog_" + langLog);
		return language.getString(pValue);
		
	}
	
	public void changeMessageLog(TypeLanguages pLanguage) {

		langLog = pLanguage;
	
	}
	
	public void changeLocale(String pLocale) {

		for (Map.Entry<String, Object> entry : countries.entrySet()) {

			if (entry.getValue().toString().equals(pLocale)) {

				setLang(pLocale);
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)entry.getValue());

			}
		}

	}
	
	static {
		
		countries = new LinkedHashMap<String,Object>();
		countries.put("spain", new Locale("es")); 
		countries.put("valencia", new Locale("ca"));
	}
	
}
