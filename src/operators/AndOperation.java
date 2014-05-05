package operators;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/19/14.
 */
public class AndOperation implements Operator{
	
	ArrayList<Operator> parameters;
	
	public AndOperation(){
		parameters = new ArrayList<Operator>();
	}
	 
    @Override
    public void execute() {

    }

    @Override
    public int numOfParamaters() {
        return 2;
    }

    @Override
    public void giveParameter(Operator relation) {
    	
    }

	@Override
	public void print() {
		System.out.println("AND");
		
	}
}
