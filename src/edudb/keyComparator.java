package edudb;

import java.io.Serializable;
import java.util.Comparator;

public class keyComparator implements Serializable,Comparator {
	
	//

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		Value Lkey, Rkey;
		Lkey = (Value) arg0;
		Rkey = (Value) arg1;
		String strLCombined, strRCombined;
		strLCombined = Lkey.rowID + Lkey.nameOfPage;
		strRCombined = Rkey.rowID + Rkey.nameOfPage;
		
		return strLCombined.compareTo( strRCombined );
		
	}

}
