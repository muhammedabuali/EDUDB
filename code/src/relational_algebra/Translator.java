package relational_algebra;

import java.util.ArrayList;
import java.util.Stack;

import dataTypes.DB_Type;
import operators.AndCondition;
import operators.DBCond;
import operators.DBCondition;
import operators.DBMulCondition;
import operators.DBParameter;
import operators.FilterOperator;
import operators.JoinOperator;
import operators.Operator;
import operators.OrCondition;
import operators.ProjectOperator;
import operators.RelationOperator;
import operators.SelectColumns;
import operators.SortOperator;
import ra.Term;
import DBStructure.DBColumn;
import adipe.translate.TranslationException;
import adipe.translate.sql.Queries;
import adipe.translate.sql.parser.SqlParser;
import statistics.Schema;

/**
 * Created by mohamed on 4/2/14.
 */
public class Translator {

    public static Operator translate(String sqlQuery) {
        // TODO transelate algebra to plan
        try {
            SqlParser.SelectStatementEofContext queryTree = Queries
                    .getQueryTree(sqlQuery);
           /* Map<String, ArrayList<String>> schema = new HashMap<String, ArrayList<String>>();
            schema.put("p", new ArrayList<String>(Arrays.asList("a", "b", "p")));
            schema.put("l", new ArrayList<String>(Arrays.asList("a")));
            schema.put("xy", new ArrayList<String>(Arrays.asList("x", "y")));
            schema.put(
                    "r4",
                    new ArrayList<String>(Arrays.asList("ra", "rb", "rc", "rd")));
            schema.put("t", new ArrayList<String>(Arrays.asList("t2", "t3")));*/

            Term ra = Queries.getRaOf(Schema.getSchema(), sqlQuery);
            return Translator.extractOperations(ra.toString());
        } catch (TranslationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * required stack accumulates operations that need parameters and
     * conditions, fields , relations pop operators from the stack
     */
    public static Operator extractOperations(String cur) {
        boolean first = true;
        Operator out = null;
        Stack<Operator> required = new Stack<Operator>();
        Operator[] given = new Operator[2];
        boolean empty = true;
        ArrayList<String> tableNames = new ArrayList<String>();
        ArrayList<Integer> tableCounts = new ArrayList<Integer>();
        Operator x = null;
        while (cur.length() > 0) {
            while (cur.length() > 0) {
                if(cur.charAt(0) == ')' || cur.charAt(0) == ',') 
                    cur = cur.substring(1);
                else
                    break;
            }
            if (cur.startsWith("0a0=")) {
                cur = cur.substring(4);
            }
            if (cur.startsWith("Project")) {
                cur = cur.substring(8);
                ProjectOperator project = new ProjectOperator();
                required.push(project);
                if (first) {
                    out = project;
                    first = false;
                }
            } else if (cur.startsWith("Filter")) {
                cur = cur.substring(7);
                FilterOperator filter = new FilterOperator();
                required.push(filter);
                if (first) {
                    out = filter;
                    first = false;
                }
            } else if (cur.charAt(0) == '"') {// filter condition
                /******************** extract condition ************/
                String condition = "";
                char c;
                int i = 0;
                while ((c = cur.charAt(++i)) != '"') {
                    condition += c;
                }
                DBParameter cond = extractCondition(tableNames, tableCounts,
                        condition);
                cur = cur.substring(i + 2);
                try {// pop filter operator
                    FilterOperator top = (FilterOperator) required.pop();
                    top.giveParameter(given[0]);
                    top.giveParameter(cond);
                    empty = false;
                    given[0] = top;
                    x = top;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (cur.charAt(0) >= 'a' && cur.charAt(0) <= 'z') {// table
                                                                      // name
                RelationOperator relation = new RelationOperator();
                /********************** extract relation ********************/
                int i = 0;
                String tableName = "";
                char c;
                while ((c = cur.charAt(i++)) != '=') {
                    tableName += c;
                }
                while ((c = cur.charAt(i++)) != '(') {
                }
                int count = 0;
                while ((c = cur.charAt(i++)) != ')') {
                    if (cur.charAt(i - 1) != ',') {
                        count++;
                    }
                }
                // System.out.println("count " + count);
                if (i < cur.length() - 1) {
                    cur = cur.substring(++i);
                } else {
                    cur = "";
                }
                tableNames.add(tableName);
                tableCounts.add(count);
                relation.setTableName(tableName);
                if (first) {
                    out = relation;
                    first = false;
                }
                x = relation;
                /********************** pop operators ********************/
                while (!required.empty()) {
                    if (empty) {
                        if (required.peek().numOfParameters() == 1) {
                            Operator top = required.pop();
                            top.giveParameter(x);
                            x = top;
                        } else {
                            given[0] = x;
                            empty = false;
                            break;
                        }
                    } else {
                        Operator top = required.pop();
                        top.giveParameter(x);
                        top.giveParameter(given[0]);
                        x = top;
                        empty = true;
                    }
                }
            } else if (cur.startsWith("CartProd")) {
                cur = cur.substring(9);
                JoinOperator join = new JoinOperator();
                required.push(join);
                if (first) {
                    out = join;
                    first = false;
                }
            } else if (cur.startsWith("SortAsc")) {
                cur = cur.substring(8);
                SortOperator sort = new SortOperator();
                required.push(sort);
            } else if (cur.startsWith("[")) {// column list
                cur = cur.replaceAll("\\s", "");
                int i = 1;
                ArrayList<DBColumn> columns = new ArrayList<DBColumn>();
                while (i < cur.length() && cur.charAt(i) <= '9'
                        && cur.charAt(i) >= '0') {
                    int num = cur.charAt(i) - '0';
                    int tmp = 0;
                    i += 2;
                    String tableName;
                    while (true) {
                        if (num > tableCounts.get(tmp)) {
                            num -= tableCounts.get(tmp++);
                        } else {
                            tableName = tableNames.get(tmp);
                            break;
                        }
                    }
                    DBColumn column = new DBColumn(num, tableName);
                    columns.add(column);
                }

                ProjectOperator operator = (ProjectOperator) required.pop();
                SelectColumns columns2 = new SelectColumns(columns);
                operator.giveParameter(columns2);
                operator.giveParameter(given[0]);
                given[0] = operator;
                empty = false;
                if (i >= cur.length()) {
                    cur = "";
                } else {
                    cur = cur.substring(--i);
                }
                cur = cur.substring(2);
            }
        }
        return out;
    }

    // TODO constants in cond
    private static DBParameter extractCondition(ArrayList<String> names,
            ArrayList<Integer> counts, String condition) {
        DBCond x = null;
        DBCond given = null;
        Stack<DBParameter> required = new Stack<>();
        while (condition.length() > 0) {
            while (condition.length() > 0) {
                if(condition.charAt(0) == ')'||condition.charAt(0) == ',')
                    condition = condition.substring(1);
                else
                    break;
            }
            if (condition.startsWith("OR")) {
                condition = condition.substring(3);
                OrCondition orOperation = new OrCondition();
                required.push(orOperation);
            } else if (condition.startsWith("AND")) {
                condition = condition.substring(4);
                AndCondition andOperation = new AndCondition();
                required.push(andOperation);
            } else if (condition.startsWith("#")) {// parameter
                int num = condition.charAt(1) - '0';
                // TODO num could be > 9
                int i = 0;
                String tableName = null;
                while (true) {
                    if (num > counts.get(i)) {
                        num -= counts.get(i++);
                    } else {
                        tableName = names.get(i);
                        break;
                    }
                }
                DBColumn column = new DBColumn(num, tableName);
                char op = condition.charAt(2);
                char right = condition.charAt(3);
                DBCondition condition2;
                if (right == '#') {
                    num = condition.charAt(4) - '0';
                    condition = condition.substring(5);
                    // TODO num could be > 9
                    i = 0;
                    while (true) {
                        if (num > counts.get(i)) {
                            num -= counts.get(i++);
                        } else {
                            tableName = names.get(i);
                            break;
                        }
                    }
                    DBColumn column2 = new DBColumn(num, tableName);
                    condition2 = new DBCondition(column, column2,
                            op);
                }else{
                    int index = 3;
                    char ch = condition.charAt(index);
                    int param =0;
                    while (ch >= '0' && ch <= '9'){
                        param *= 10;
                        param += ch - '0';
                        index++;
                        if(index == condition.length())
                            break;
                        ch = condition.charAt(index);
                    }
                    DB_Type.DB_Int c = new DB_Type.DB_Int( param );
                    condition2 = new DBCondition(column, c, op);
                    if(index == condition.length())
                        condition = "";
                    else
                        condition = condition.substring(index + 1);
                }
                x = condition2;
                if (given == null) {
                    if (!required.empty()) {
                        if (required.peek().numOfParameters() == 1) {
                            DBParameter top = required.pop();
                            ((DBMulCondition) top).giveParameter(x);
                            x = (DBCond) top;
                        } else {
                            given = x;
                        }
                    }
                } else {
                    DBParameter top = required.pop();
                    ((DBMulCondition) top).giveParameter(x);
                    ((DBMulCondition) top).giveParameter(given);
                    x = (DBCond) top;
                    given = x;
                }
            }
        }
        return x;
    }

    public static void main(String[] args) {
        Operator x = (Operator) Translator
                .translate("SELECT a, x FROM p, xy WHERE (y = b AND y = a) OR"
                        + " x = y");
        ArrayList<DBParameter> upper = new ArrayList<>();
        ArrayList<DBParameter> lower = new ArrayList<>();
        upper.add(x);
        while(!upper.isEmpty() ){
            DBParameter cur = upper.get(0);
            if(cur instanceof Operator){
                DBParameter[] children = ((Operator) cur).getChildren();
                for (int i = 0; i < children.length; i++) {
                    Operator child = (Operator)children[i];
                    if(child != null)
                        lower.add( child );
                }
            }
            System.out.print(cur.toString() + " ");
            upper.remove(0);
            if(upper.isEmpty()){
                System.out.print("\n");
                upper = lower;
                lower = new ArrayList<>();
            }
        }
    }
}
