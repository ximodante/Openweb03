package openadmin.util.edu;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.Size;

import openadmin.model.Base;
import openadmin.model.log.Log;

public class ReflectionUtils {
	
	public static int maxStringSize=500;
	
	/**
	 * Create an instance of a class whose type is klass using the no args contructor.
	 * @param klass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public static Object createObject(Class<?> klass) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		return klass.getConstructor().newInstance();
	}
	
	
	/**
	 * Create an instance of a class whose name is className using the no args contructor.
	 * @param className
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public static Object createObject(String className) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		return createObject(Class.forName(className));
	}
	
	/**
	 * A class whose name contanins "$Proxy$", is a class injected and proxied by CDI
	 * @param cls
	 * @return
	 */
	public static boolean isProxy(Class<?> cls) {
		return cls.getSimpleName().contains("Proxy$");
	}
	
	/**
	 * Return the underlaying object incrustated in a CDI Weld proxy class wrapper
	 * @param proxy
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object getProxiedObject(Object proxy)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			
		return getProperty(proxy,"targetInstance");
	}
	
	/**
	 * If an object is a Weld Proxy then return the underlaying object, else return itself
	 * @param bean
	 * @return
	 * @throws ReflectiveOperationException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public static Object getRealObject(Object bean) 
			throws ReflectiveOperationException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		
		if (!isProxy(bean.getClass())) return bean;
		else return getProxiedObject(bean);
	}
	
	/**
	 * Get the property descriptors (from BeanInfo)
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor[] getPropertiesDescriptor(Class klass) 
			throws IntrospectionException {
		
		BeanInfo beanInfo = Introspector.getBeanInfo(klass);
		return beanInfo.getPropertyDescriptors();
	}
	
	/**
	 * Get the property descriptors (from BeanInfo)
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor[] getPropertiesDescriptor(Object bean) 
			throws IntrospectionException {
		/**
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		return beanInfo.getPropertyDescriptors();
		*/
		return getPropertiesDescriptor(bean.getClass());
	}
	
	
	
	
	
	/**
	 * Return a concrete property descriptor from an array of properties descriptors
	 * @param propsDesc
	 * @param propertyName
	 * @return
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] propsDesc, String propertyName) 
			throws IntrospectionException {
		//System.out.println("propertyName=" + propertyName);	
		return Arrays.stream(propsDesc)
				.filter(e -> e.getName().equalsIgnoreCase(propertyName))
				.findFirst().get();
	}
	
	/**
	 * return a property descriptor from a bean
	 * @param bean
	 * @param propertyName
	 * @return
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName) 
			throws IntrospectionException {
		
		return getPropertyDescriptor(getPropertiesDescriptor(bean), propertyName);
	}
	
	/**
	 * getter of a property from its property descriptor
	 * @param bean
	 * @param propDesc
	 * @return
	 * @throws IllegalAccessException
	 * @throws RuntimeException
	 * @throws InvocationTargetException
	 */
	public static Object getProperty(Object bean, PropertyDescriptor propDesc) 
			throws IllegalAccessException, RuntimeException, InvocationTargetException {
		Method getter = propDesc.getReadMethod();
		return getter.invoke(bean);
	}
	
	/**
	 * Getter of a property from the array of property descriptors and its name (quick)
	 * @param bean
	 * @param propsDesc
	 * @param propertyName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws RuntimeException
	 * @throws IntrospectionException
	 */
	public static Object getProperty(Object bean, PropertyDescriptor[] propsDesc, String propertyName) 
			throws IllegalAccessException, InvocationTargetException, RuntimeException, IntrospectionException {
		return getProperty(bean, getPropertyDescriptor(propsDesc, propertyName));
	}
	
	/**
	 * Getter of a property (slow)
	 * @param bean
	 * @param propertyName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws RuntimeException
	 * @throws IntrospectionException
	 */
	public static Object getProperty(Object bean, String propertyName) 
			throws IllegalAccessException, InvocationTargetException, RuntimeException, IntrospectionException {
		return getProperty(bean, getPropertiesDescriptor(bean), propertyName);
	}

	/**
	 * Setter of a property knowing its property descriptor (very quick)
	 * @param bean
	 * @param propDesc
	 * @param propertyValue
	 * @throws IllegalAccessException
	 * @throws RuntimeException
	 * @throws InvocationTargetException
	 */
	public static void setProperty(Object bean, PropertyDescriptor propDesc, Object propertyValue) 
			throws IllegalAccessException, RuntimeException, InvocationTargetException {
		if (propertyValue==null) 
			System.out.println("OJO:-------------------->asignando propiedad nula a "+ bean.getClass().getName() + "\r" +
					"propiedad:"+propDesc.toString());
		Method setter = propDesc.getWriteMethod();
		setter.invoke(bean,propertyValue);
		
	}
		
	/**
	 * Setter of a property from the array of properties descriptors (quick)
	 * @param bean
	 * @param propsDesc
	 * @param propertyName
	 * @param propertyValue
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws RuntimeException
	 * @throws IntrospectionException
	 */
	public static void setProperty(Object bean, PropertyDescriptor[] propsDesc, String propertyName, Object propertyValue) 
			throws IllegalAccessException, InvocationTargetException, RuntimeException, IntrospectionException {
		setProperty(bean, getPropertyDescriptor(propsDesc, propertyName),propertyValue);
	}
	
    /**
     * Set property of an object (slow)
     * @param bean
     * @param propertyName
     * @param propertyValue
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws RuntimeException
     * @throws IntrospectionException
     */
	public static void setProperty(Object bean, String propertyName, Object propertyValue) 
			throws IllegalAccessException, InvocationTargetException, RuntimeException, IntrospectionException {
		setProperty(bean, getPropertiesDescriptor(bean), propertyName, propertyValue);
	}
	
	/**
	 * Set property of an object that may be a CDI Weld prowy wrapper class (very slow) 
	 * @param bean
	 * @param propertyName
	 * @param propertyValue
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws RuntimeException
	 * @throws IntrospectionException
	 * @throws ReflectiveOperationException
	 */
	public static void setProperty_ProxyOrNot(Object bean, String propertyName, Object propertyValue) 
			throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, RuntimeException, IntrospectionException, ReflectiveOperationException {
		setProperty(getRealObject(bean), propertyName, propertyValue);
	}
	
	/**
	 * Set a property of an object that may be a CDI Weld prowy wrapper class (very slow) 
	 * @param bean
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws RuntimeException
	 * @throws IntrospectionException
	 * @throws ReflectiveOperationException
	 */
	public static Object getProperty_ProxyOrNot(Object bean, String propertyName, Object propertyValue) 
			throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, RuntimeException, IntrospectionException, ReflectiveOperationException {
		return getProperty(getRealObject(bean), propertyName);
	}

	
	// Gets information from a class bypassing proxies
	public static List<String[]> getObjectInfo(Object obj) {
		List<String[]> fields = new ArrayList<String[]>();

		Object myObj = obj;
		Class<?> cls = myObj.getClass();

		try {
			if (isProxy(cls)) {
				myObj = getProxiedObject(myObj);
				cls = myObj.getClass();
			}
			//System.out.println("CLASS=" + cls.getName());
			int i = 0;
			for (Field f : cls.getDeclaredFields()) {
				f.setAccessible(true);
				String[] propertyField = new String[4];

				if (f != null) {

					//System.out.println("FIELD" + i + "=" + f.getName() + "----" + f.getType());
					// View of fields
					propertyField[0] = "" + i;
					propertyField[1] = myObj.getClass().getSimpleName() + "." + f.getName();
					propertyField[2] = operator(f.getType().getSimpleName());
					propertyField[3] = f.get(myObj).toString().trim();

					System.out.println("consult: " + propertyField[0] + " - " + propertyField[1] + " - "
							+ propertyField[2] + " - " + propertyField[3]);
					fields.add(propertyField);
					i++;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fields;

	}

	// public List<String[]> execute(Base obj) {
	// Gets information from a class but cannot bypass proxies!!!!!
	public static List<String[]> execute(Object obj) {

		List<String[]> fields = new ArrayList<String[]>();
		int i = 0;
		try {

			System.out.println("CLASS=" + obj.getClass().getName());
			for (Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				String[] propertyField = new String[4];

				if (f != null) {

					System.out.println("FIELD" + i + "=" + f.getName() + "----" + f.getType());
					// View of fields
					propertyField[0] = "" + i;
					propertyField[1] = obj.getClass().getSimpleName() + "." + f.getName();
					propertyField[2] = operator(f.getType().getSimpleName());

					if (f.get(obj) != null) {
						propertyField[3] = f.get(obj).toString().trim();
					}

					// System.out.println("consult: " + propertyField[0] + " - " + propertyField[1]
					// + " - " + propertyField[2]);
					fields.add(propertyField);
					i++;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return fields;
	}

	/**
	 * Get all the fields initialized
	 * 
	 * @param String
	 *            Identifies the declared type for the field
	 * @return Type logical operator
	 */
	public static String operator(String pType) {

		if (pType.equals("String"))
			return "like";

		else if (pType.equals("boolean"))
			return "=";

		else if (pType.equals("Date"))
			return "=";

		else if (pType.equals("Integer"))
			return "n";

		else if (pType.equals("Double"))
			return "n";

		else if (pType.equals("Boolean"))
			return "=";

		return null;

	}
	
	public static Object newObjectByDescription(Class<?> pClass, String pDescription) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, RuntimeException, IntrospectionException {
		Object obj=createObject(pClass);
		setProperty(obj,"description",pDescription);
		return obj;
	}
	
	/**
	 * Return the size value of a size annotation
	 * @param klass
	 * @return
	 */
	public static int sizeAnnotated(Class<?> klass, String fieldname)  {
		int size=maxStringSize;
		try {
			Field f=klass.getDeclaredField(fieldname);
			if (f.isAnnotationPresent(Size.class)) 	size= f.getAnnotation(Size.class).max();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
		
		
	}
	
	
	
	public static void main(String[] args) {
		Object myObj;
		try {
			
			int a=sizeAnnotated(openadmin.model.log.Log.class,"person");
			System.out.println("Size=" + a);
			myObj = ReflectionUtils.createObject("openadmin.model.control.User");
			ReflectionUtils.setProperty(myObj, "description", "tatia");
			LocalDate localDate=LocalDate.now();
			//ReflectionUtilsEdu.setProperty(myObj, "dateBegin",localDate.now());
			
			System.out.println(myObj.toString());
			
			Class<? extends Base> myClass = (Class<? extends Base>) Class.forName("openadmin.model.control.User");
			PropertyDescriptor[] propsDesc=ReflectionUtils.getPropertiesDescriptor(myClass);
			ReflectionUtils.setProperty(myObj, propsDesc, "dateBegin", localDate.now());
			
			
			System.out.println(myObj.toString());
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException | ClassNotFoundException | RuntimeException | IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

}