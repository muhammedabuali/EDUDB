package edudb;

import java.io.Serializable;

public class Value implements  Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String rowID;
	String nameOfPage;
	
	public Value (String x , String y){
		
		rowID = x;
		nameOfPage = y;
	}
	
	
}

