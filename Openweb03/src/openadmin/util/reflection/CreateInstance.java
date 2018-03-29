package openadmin.util.reflection;

public class CreateInstance {

	/**
	public static Object instanceObject (String pObj){
		
		Object obj = null;
		
		//Instance object
		try {
			
			//System.out.println("Instance class: " + pObj);
			Class<?> cls = Class.forName(pObj);
			
			obj = cls.newInstance();
			
			//System.out.println("Objete instanciat: " + pObj);
				
		} catch (ClassNotFoundException e) {
				
			e.printStackTrace();
			
		} catch (InstantiationException e) {
				
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
				
			e.printStackTrace();
		}
		
		return obj;
		
	}*/
}
