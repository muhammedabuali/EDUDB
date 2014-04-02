package edudb;

import java.util.LinkedHashMap;

public interface Command {
	
	public abstract LinkedHashMap execute(LinkedHashMap htblInputParams);

}
