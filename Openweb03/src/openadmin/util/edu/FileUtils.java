package openadmin.util.edu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

public class FileUtils {

	/**
	 * Read delimited data from a delimited file  
	 * @param pFileName the name of the file
	 * @param pDelimiter  The delimiter of the data
	 * @return 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]> ReadData(String pFileName, String pDelimiter, String commentStr) throws FileNotFoundException, IOException{
		
		BufferedReader inputStream = Files.newBufferedReader(Paths.get(pFileName));
		
		//Pattern p = Pattern.compile(pDelimiter);
		List<String[]> lst=new ArrayList<String[]>();
		while(true) {
			String strLine= inputStream.readLine();	  		// Input a line from the file
			if (strLine == null) break;  			 		// We have reached eof
			System.out.println(strLine);
			strLine=strLine.trim();
			strLine=StringUtils.substringBefore(strLine,commentStr);
			//if (strLine.length()>1) lst.add(p.split(strLine)); // Split the line into strings delimited by delimiters
			//if (strLine.length()>1) lst.add(strLine.split(pDelimiter)); // Split the line into strings delimited by delimiters
			if (strLine.length()>1) lst.add(StringUtilsEdu.splitAndTrim(strLine,pDelimiter)); // Split the line into strings delimited by delimiters
		}
		
		return lst;
	} 
	
	
	/**
	 * Get an input stream from a relative path from WebContent folder in NO Maven Project
	 * 
	 * If there is a file in the folder WebContent/resources/data/AccessData.txt we should call it as:
	 * 
	 *   Inputstream in=getStreamFromWebContentFolder("./resources/data/AccessData.text")
	 * 
	 * @param filePath
	 * @return
	 */
	public static InputStream getStreamFromWebContentFolder(String filePath) {
		return FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(filePath);
	}
	
	/**
	 * Get the path from a relative path from WebContent folder in NO Maven Project
	 * 
	 * If there is a file in the folder WebContent/resources/data/AccessData.txt we should call it as:
	 * 
	 *   InputStream in=getPathFromWebContentFolder("./resources/data/AccessData.text")
	 * 
	 * @param filePath
	 * @return
	 * @throws MalformedURLException 
	 */
	public static String getPathFromWebContentFolder(String filePath) throws MalformedURLException {
		return FacesContext.getCurrentInstance().getExternalContext().getResource(filePath).getPath();
	}
	
	/**
	 * Get an input stream from a relative path from resources folder in Maven Project
	 * 
	 * If there is a file in the folder resources/data/AccessData.txt we should call it as:
	 * 
	 *   InputStream in=getStreamFromResourcesFolder("data/AccessData.text")
	 * 
	 * @param filePath
	 * @return
	 */
	public static InputStream getStreamFromResourcesFolder(String filePath) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
	}
	
	
	/**
	 * Get the path from a relative path from resources folder in Maven Project
	 * 
	 * If there is a file in the folder resources/data/AccessData.txt we should call it as:
	 * 
	 *   String path=getPathFromWebContentFolder("data/AccessData.text")
	 * 
	 * @param filePath
	 * @return
	 * @throws MalformedURLException 
	 */
	public static String getPathFromResourcesFolder(String filePath) {
		return Thread.currentThread().getContextClassLoader().getResource(filePath).getPath();
	}
	
	//Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
