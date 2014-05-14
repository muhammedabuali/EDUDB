package operators;

import dataTypes.DataType;

/**
 * Created by mohamed on 5/13/14.
 */
public class MathUtil {

    public static boolean evaluateCond(DataType value1,
                                       DataType value2, char op){
        switch (op){
            case '=': return value1.equals(value2);
            case '<': return ( value1.compareTo(value2) < 0 );
            case '>': return ( value1.compareTo(value2) > 0 );
        }
        return false;
    }
}
