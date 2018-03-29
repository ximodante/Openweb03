package openadmin.util.edu;

import java.util.List;

public class CollectionUtils {
	
	public static <T extends Object> T get (List<T> lst, int index) {
		if (null != lst && lst.size()>index) return lst.get(index);
		else return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
