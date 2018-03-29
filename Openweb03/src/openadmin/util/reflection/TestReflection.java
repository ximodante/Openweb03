package openadmin.util.reflection;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import openadmin.dao.operation.AnalyzerConsult;
//import openadmin.model.control.Access;
//import openadmin.model.control.Action;
//import openadmin.model.control.ActionClass;
import openadmin.model.control.User;
import openadmin.util.configuration.TypeLanguages;

public class TestReflection {	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IllegalArgumentException, IllegalAccessException {
		
		System.out.println("Begin");		
		
		User user = new User();
		user.setDescription("alfred");
		user.setFullName("Maite");
		
		//Access acc = new Access();
		//acc.setUser(user);
		
		/**
		ActionClass acl = new ActionClass("l\'User");								
		Action at = new Action();
		
		ActionClass acl1 = new ActionClass("User");								
		Action at1 = new Action();
		
		at.setActionClass(acl);
		at.setDescription("User.update");
		
		at1.setActionClass(acl1);
		at1.setDescription("User.update");
		*/
		ReflectionField rf = new ReflectionField();
		
		//rf.execute(at);
		
		/**
		for (String pPropertyField[]: rf.execute(acc)){
			
			System.out.println(pPropertyField[0].toString());
			//System.out.println(pPropertyField[1].toString());	
			System.out.println(pPropertyField[2].toString());
						
			/**
			for (String pPropertyField1[]: rf.execute(at1)){
				
				//System.out.println(pPropertyField1[0].toString());			
				//System.out.println(pPropertyField1[2].toString());
				
				if (pPropertyField[0].toString().compareTo(pPropertyField1[0].toString())== 0){
					
					System.out.println("igual atribut");
					
					if (pPropertyField[2].toString().compareTo(pPropertyField1[2].toString())== 0){
						
						System.out.println("igual valor");
						
						break;
					}
					
					break;
				}
				
			}
			
		}*/
		
		//AnalyzerConsult c = new AnalyzerConsult();
		//c.makeWhere(at);
		
		//convertString("L'Alfred");
		
		
		
	}
	
	private static String convertString(String pString){
	
		String result = "";
		
		//result = pString.replace("'", "\'");
		
		System.out.println(pString);
		return result;	
		
	}

}
