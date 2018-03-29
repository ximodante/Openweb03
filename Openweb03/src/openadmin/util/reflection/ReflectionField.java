package openadmin.util.reflection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import openadmin.annotations.NoSql;
import openadmin.annotations.Search;
import openadmin.model.Base;

/**
  * To get the type, the name and the value of the fields of the class 
  * @version  0.1
  * Created 10-05-2009  
  * Author Alfred Oliver
  *
  */
public class ReflectionField {

	/** String and the type, name and value of the field */
	private String propertyField[];
	
	/** List of fields with their properties (type, name and value) */
	private List<String[]> fields;
	
	
	public static Object getCorrectObject(Object pObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		
		Object obj = pObject;
		
		if (isProxy(obj.getClass())) {	
			
			return getProxiedObject(obj);
			
		}
		
		return obj;
	}
	
	private static boolean isProxy(Class<?> cls) {
		if (cls.getSimpleName().contains("Proxy$"))
			return true;
		else
			return false;
	}
	

	// Returns the underlying object proxied by CDI
	private static Object getProxiedObject(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	    	
		BeanInfo beanInfo;
	    Object value=null;
	    	
	    beanInfo = Introspector.getBeanInfo(obj.getClass());
	    PropertyDescriptor oProperty = Arrays.stream(beanInfo.getPropertyDescriptors())
	    		.filter(e -> e.getName().equalsIgnoreCase("targetInstance"))
	    		.findFirst().get();
	    Method getter = oProperty.getReadMethod();
		value = getter.invoke(obj);
			
	    return value;
	}
	
	
	public List<String[]> execute(Base pObj) {
		
		fields = new ArrayList<String[]>();
		
		try {
			
			Object obj = getCorrectObject(pObj);
			
			for (Field f: obj.getClass().getDeclaredFields()){
					
				propertyField = new String[3];
										
				//View of fields 
				f.setAccessible(true);	
				
				//System.out.println("Atribut:  " + obj.getClass());
				//System.out.println("Atribut:  " + obj.getClass().getSimpleName() + "." + f.getName());
				
				
				if (f.get(obj) == null || 
					f.getName().equals("debugLog") ||
					f.getName().equals("detailLog") ||
					f.getName().equals ("serialVersionUID") ||
					//f.getName().equals ("password") ||
					f.get(obj).toString().trim() == "" ||
					f.getType().getSimpleName().equals("Set")) continue;

				
				if (f.isAnnotationPresent(NoSql.class)) continue;
				
				if (obj.getClass().getSimpleName().indexOf("$") >= 0) continue;
				
				
				
				/**
				if (f.isAnnotationPresent(NoSql.class)){
					
					propertyField[0] = obj.getClass().getSimpleName() + "." + f.getName();				
					propertyField[1] = "sql";																		
					propertyField[2] = f.get(obj).toString().trim();

					fields.add(propertyField);	
					
					continue;
				}*/
				
				
				
				// Objeto (openadmin.model)			
				if (f.getType().getName().startsWith("openadmin.model")){
					
					if (((Base)f.get(obj)).getId() != null){
						
						propertyField[0] = obj.getClass().getSimpleName() + "." +f.getName();				
						propertyField[1] = "=";																		
						propertyField[2] = ((Base)f.get(obj)).getId().toString();

						fields.add(propertyField);	
						
						continue;
					}
					
					else if (((Base)f.get(obj)).getDescription() == null) continue;
					
					else{
						propertyField[0] = obj.getClass().getSimpleName() + "." +f.getName() + ".description";				
						propertyField[1] = "like";																		
						propertyField[2] = ((Base)f.get(obj)).getDescription().trim();

						fields.add(propertyField);
						continue;
					}
					
				}
				
				//Field id
				if (f.getName().equals("id")){
					
					propertyField[0] = obj.getClass().getSimpleName() + "." + f.getName();				
					propertyField[1] = "id";																		
					propertyField[2] = f.get(obj).toString().trim();

					fields.add(propertyField);	
					
					continue;
					
				}
				
				propertyField[0] = obj.getClass().getSimpleName() + "." + f.getName();
				propertyField[1] = this.operator(f.getType().getSimpleName());
				propertyField[2] = f.get(obj).toString().trim();
				
				System.out.println("cpnsult: " + propertyField[0] + " - "  + propertyField[1] + " - " + propertyField[2]);
				fields.add(propertyField);			
													
			}				
			
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
				
				e.printStackTrace();
			
			}
			
			return fields;
	}
	
	/**
	 * Get all the fields initialized
	 * @param obj  object type Base
	 * @return List of fields with their properties (type, name and value)
	 * */
	public List<String[]> executeOld(Base obj) {
						
		fields = new ArrayList<String[]>(0);				
				
		try {
			
			
		for (Field f: obj.getClass().getDeclaredFields()){
				
			propertyField = new String[3];
									
			//View of fields 
			f.setAccessible(true);	
			
			System.out.println("Atribut:  " + obj.getClass());
			System.out.println("Atribut:  " + obj.getClass().getSimpleName() + "." + f.getName());
			
			
			if (f.get(obj) == null || 
				f.getName().equals("debuglog") ||
				f.getName().equals("historiclog") ||
				f.getName().equals ("serialVersionUID") ||
				//f.getName().equals ("password") ||
				f.get(obj).toString().trim() == "" ||
				f.getType().getSimpleName().equals("Set")) continue;

			
			if (f.isAnnotationPresent(NoSql.class)) continue;
			
			if (obj.getClass().getSimpleName().indexOf("$") >= 0) continue;
			
			
			
			/**
			if (f.isAnnotationPresent(NoSql.class)){
				
				propertyField[0] = obj.getClass().getSimpleName() + "." + f.getName();				
				propertyField[1] = "sql";																		
				propertyField[2] = f.get(obj).toString().trim();

				fields.add(propertyField);	
				
				continue;
			}*/
			
			
			
			// Objeto (openadmin.model)			
			if (f.getType().getName().startsWith("openadmin.model")){
				
				if (((Base)f.get(obj)).getId() != null){
					
					propertyField[0] = obj.getClass().getSimpleName() + "." +f.getName();				
					propertyField[1] = "=";																		
					propertyField[2] = ((Base)f.get(obj)).getId().toString();

					fields.add(propertyField);	
					
					continue;
				}
				
				else if (((Base)f.get(obj)).getDescription() == null) continue;
				
				else{
					propertyField[0] = obj.getClass().getSimpleName() + "." +f.getName() + ".description";				
					propertyField[1] = "like";																		
					propertyField[2] = ((Base)f.get(obj)).getDescription().trim();

					fields.add(propertyField);
					continue;
				}
				
			}
			
			//Field id
			if (f.getName().equals("id")){
				
				propertyField[0] = obj.getClass().getSimpleName() + "." + f.getName();				
				propertyField[1] = "id";																		
				propertyField[2] = f.get(obj).toString().trim();

				fields.add(propertyField);	
				
				continue;
				
			}
			
			propertyField[0] = obj.getClass().getSimpleName() + "." + f.getName();
			propertyField[1] = this.operator(f.getType().getSimpleName());
			propertyField[2] = f.get(obj).toString().trim();
			
			System.out.println("cpnsult: " + propertyField[0] + " - "  + propertyField[1] + " - " + propertyField[2]);
			fields.add(propertyField);			
												
		}				
		
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		
		return fields;
	}
	
	
	/**
	 * Get all the fields initialized
	 * @param String  Identifies the declared type for the field
	 * @return Type logical operator
	 * */
	private String operator (String pType) {
		
		if (pType.equals("String")) return "like";
		
		else if (pType.equals("boolean")) return "=";
		
		else if (pType.equals("Date")) return "=";
		
		else if (pType.equals("Integer")) return "n";
		
		else if (pType.equals("Double")) return "n";
		
		else if (pType.equals("Boolean")) return "=";
		
		return null;
		
	}
	
	
	public static Base searchSelect(Base obj, Base base){
		
		Base baseResult = null;
		
		String classp = "";
		
		String classc = "";
		
		//Search value annotation
		for (Field f: base.getClass().getDeclaredFields()){
			
			if ((obj.getClass().getSimpleName()).equals(f.getType().getSimpleName())){
				
				int val = f.getAnnotation(Search.class).nameObjects().indexOf(":");
				
				classp = "get" + f.getAnnotation(Search.class).nameObjects().substring(0, val);
				
				classc = f.getAnnotation(Search.class).nameObjects().substring(val + 1);
				
				continue;
			}
			
		}

		
		//Search 
		for (Method me: base.getClass().getDeclaredMethods()){
			
			if (classp.equals(me.getName())){
				
				try {
					Base ba = (Base) me.invoke(base);
					
					if (ba == null) {
						
						obj = null;
						continue;
					}
					
					for (Method me2: ba.getClass().getDeclaredMethods()){
						
						if (("get" + classc).equals(me2.getName())){
							
							baseResult = (Base) me2.invoke(ba);
							
						}
						
					}
					
					
				} catch (IllegalArgumentException e) {
					
					e.printStackTrace();
					
				} catch (IllegalAccessException e) {
					
					e.printStackTrace();
					
				} catch (InvocationTargetException e) {
					
					e.printStackTrace();
				}			
					
				continue;
			}
				
		}		
		
		if (obj != null) {
			
			for (Method me3: obj.getClass().getDeclaredMethods()){
				
				if (("set" + classc).equals(me3.getName())){																																																										
					
					try {
					
						me3.invoke(obj, baseResult);						
						continue;
					
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
																						
				}
				
			}
			
		}
		
		return obj;

		
	}
	
	public static Base copyObject(Base objCopy, Base basePaste, String metodo){		
		
		for (Method me: basePaste.getClass().getDeclaredMethods()){
			
			if (("set" + metodo).equals(me.getName())){	
					
			try {
				
				if (objCopy.getId() == null || objCopy.getDescription() == null)
					objCopy = null;
				
				//System.out.println("OBJETO INVOCADO " + me.getName());
				me.invoke(basePaste, objCopy);						
				continue;
					
				} catch (IllegalArgumentException e) {
						
					e.printStackTrace();
				} catch (IllegalAccessException e) {
						
					e.printStackTrace();
				} catch (InvocationTargetException e) {
						
					//e.printStackTrace();
				} 
																			
			}
				
		}	
		
		return basePaste;
	}
	
	public static Base copyObject2(Base objCopy, Base basePaste){
		
		for (Method me: objCopy.getClass().getDeclaredMethods()){
			
			//System.out.println(basePaste.getClass().getSimpleName() + " --  " + me.getName());
			
			if (("get" + basePaste.getClass().getSimpleName()).equals(me.getName())){																																			
					
			try {
					
				if (me.invoke(objCopy, (Object)null) != null){
					
					basePaste = (Base) me.invoke(objCopy, (Object)null);
					
				}
										
				continue;
					
				} catch (IllegalArgumentException e) {
						
					e.printStackTrace();
				} catch (IllegalAccessException e) {
						
					e.printStackTrace();
				} catch (InvocationTargetException e) {
						
					e.printStackTrace();
				}
																			
			}
				
		}	
		
		return basePaste;
	}
	
}
