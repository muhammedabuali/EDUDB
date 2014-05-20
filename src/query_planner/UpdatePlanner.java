package query_planner;

import DBStructure.DBColumn;
import dataTypes.DB_Type;
import gudusoft.gsqlparser.EExpressionType;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TParseTreeNode;
import gudusoft.gsqlparser.stmt.TUpdateSqlStatement;
import operators.*;

import java.util.ArrayList;

/**
 * Created by mohamed on 5/20/14.
 */
public class UpdatePlanner implements Planer{
    @Override
    public Operator makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TUpdateSqlStatement statement = (TUpdateSqlStatement) tCustomSqlStatement;
        ArrayList<DBAssignment> assignments = new ArrayList<>();

        String tableName = statement.getTargetTable().toString();

        for(int i=0;i<statement.getResultColumnList().size();i++){
            TParseTreeNode assignment = statement.getResultColumnList().elementAt(i);
            DBAssignment assignment1 = new DBAssignment(assignment.getStartToken().toString(),
                    assignment.getEndToken().toString(),tableName );
            assignments.add(assignment1);
        }
        TExpression expression = statement.getWhereClause().getCondition();
        if (expression.getExpressionType() == EExpressionType.simple_comparison_t){
            String leftString = expression.getLeftOperand().toString();
            DBColumn column1 = new DBColumn(leftString, tableName);
            TExpression right = expression.getRightOperand();
            String rightString = right.toString();
            if (right.getExpressionType()  == EExpressionType.simple_constant_t){
                DB_Type.DB_Int constant = new DB_Type.DB_Int(rightString);
                DBCondition condition = new DBCondition(column1,constant,
                        expression.getOperatorToken().toString().charAt(0));
                UpdateOperator update = new UpdateOperator(tableName, assignments, condition);
                return update;
            }else{
                DBColumn column2 = new DBColumn(rightString, tableName);
                DBCondition condition = new DBCondition(column1,column2,
                        expression.getOperatorToken().toString().charAt(0));
                UpdateOperator update = new UpdateOperator(tableName, assignments, condition);
                return update;
            }
        }
        return null;
    }
}
