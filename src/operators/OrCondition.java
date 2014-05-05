package operators;

/**
 * Created by mohamed on 4/19/14.
 */
public class OrCondition implements DBMulCondition{

    public int numOfParameters() {
        return 2;
    }

    public void giveParameter(DBCondition condition) {
        ;
    }

	@Override
	public void print() {
		
	}

    @Override
    public void giveParameter(DBParameter param) {
        // TODO Auto-generated method stub
        
    }
}
