package openadmin.util.edu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

	public static Properties getProperties(String path) throws FileNotFoundException, IOException {
		Properties props=new Properties();
		props.load(new FileInputStream(path));
		return props;
	}
	
	public static Properties getProperties(InputStream in) throws FileNotFoundException, IOException {
		Properties props=new Properties();
		props.load(in);
		return props;
	}
	public static Properties getPropertiesNoException(String path)  {
		Properties props=new Properties();
		try {
			props.load(new FileInputStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return props;
	}
	

	
	
}
