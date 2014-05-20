package operators;

import DBStructure.DBColumn;
import DBStructure.DBConst;
import DBStructure.DBRecord;
import dataTypes.DB_Type;
import dataTypes.DataType;
import gudusoft.gsqlparser.nodes.TExpression;

/**
 * Created by mohamed on 4/19/14.
 */
public class DBCondition implements DBCond {
    /**
     * @uml.property  name="column1"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    DBColumn column1;
    /**
     * @uml.property  name="column2"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    DBParameter column2;
    /**
     * @uml.property  name="op"
     */
    char op;

    public DBCondition(DBColumn column, DBParameter column2, char op) {
        column1 = column;
        this.column2 = column2;
        this.op = op;
    }

    @Override
    public int numOfParameters() {
        return 0;
    }

    public void print() {
        System.out.print(column1.toString() + " " + op + " "
                + column2.toString());
    }

    @Override
    public String toString() {
        return column1.toString() + " " + op + " " + column2.toString();
    }

    @Override
    public boolean evaluate(DBRecord dbRecord) {
        DataType value1 = dbRecord.getValue(column1);
        if (column2 instanceof DBColumn){
            DataType value2 = dbRecord.getValue( ( (DBColumn) column2 ).order-1);
            return MathUtil.evaluateCond(value1, value2, op);
        }
        if (column2 instanceof DBConst){
            return MathUtil.evaluateCond(value1,(DB_Type.DB_Int) column2, op);
        }
        return false;
    }

    public static DBCond getCondition(TExpression expression){
        //todo differnt condition types
        /*switch (expression.getExpressionType()){
            case simple_comparison_t:
            {
                TExpression exp1 = expression.getLeftOperand();
                if (exp1.getExpressionType() == )
                DBCondition condition = new DBCondition();

            }
        }*/
        System.out.println(expression.getExpressionType());
        System.out.println(expression.getLeftOperand().getExpressionType());
        System.out.println(expression.getRightOperand().getExpressionType());
        return null;
    }
}
