package openadmin.model;

import java.time.LocalDateTime;

/**
  * <desc>interface that stores the unique information for all classes dao</desc>
  * <responsibility>Represents a unique object</responsibility>
  * <coperation>All classes dao</coperation>
  *	@version  0.1
  *	Create  18-03-2009
  * Author Alfred Oliver
*/
public interface Base extends Comparable<Base>{
	
	/**
	 * <desc> Accessor reading which gives us the identifier</desc>
	 * <pre> x is an instance of Base</pre>
	 * <post> the return is the identifier of x </post>
	 * @return Identifier of object
	 */	
	public Number getId();
	
	public void setId(Number pId);
	
	
	/**
	 * <desc> Accessor reading which gives us the description</desc>
	 * <pre> x is an instance of Base</pre>
	 * <post> the return is the description of x </post>
	 * @return Description of object
	 */	
	public String getDescription();
	
	public void setDescription(String pDescription);
	
	/**
	 * <desc> Accessor reading that means that the system should make a log on any JPA operation of this class</desc>
	 * <pre> x is an instance of Base</pre>
	 * <post> the return is the debugLog of x </post>
	 * @return Debug log mode of object
	 */	
	public default boolean isDebugLog() {return true;}
		
	public default boolean isDetailLog() {return false;}
	
	@Override
	public default int compareTo(Base o) {
		  
		return getDescription().compareTo(o.getDescription());
	
	}
	
	public String getLastUser();
	
	public void setLastUser(String pUser);
	
	public LocalDateTime getAuditData();
	
	public void setAuditData(LocalDateTime pDate);
	
	public void setChanges(String User);
		
}