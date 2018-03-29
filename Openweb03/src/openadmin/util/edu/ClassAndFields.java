package openadmin.util.edu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* Gets the class name and fields to be retieved in a csv file
* @author eduard
*
*/
@AllArgsConstructor
public class ClassAndFields {
	@Getter private String name=null;             // Simple class name
	@Getter private String packageName=null;      // Package name
	@Getter private String readFields=null;       // comma separated field names to read from csv file
	@Getter private String expressions=null;      // comma separated expressions   
	@Getter private String groupFields=null;      // comma separate fields
	@Getter private String groupExpressions=null; // comma separate group expressions
	@Getter private int groupBeginPos=-1;         // The first value position where group begins
	@Getter private int groupLength=-1;           // Number of values used to form a group
	@Getter private String fileName=null;         // FileName
}