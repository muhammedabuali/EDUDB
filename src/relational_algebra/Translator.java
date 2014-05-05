package relational_algebra;

import adipe.translate.TranslationException;
import adipe.translate.sql.Queries;
import adipe.translate.sql.parser.SqlParser;
import edudb_2.data_structures.DBStructure.DBColumn;
import edudb_2.statistics.Schema;
import ra.Term;

import java.util.ArrayList;
import java.util.Stack;

import operators.*;

/**
 * Created by mohamed on 4/2/14.
 */
public class Translator {

    public static Object translate(String sqlQuery){
        //TODO transelate algebra to plan
        try {
            SqlParser.SelectStatementEofContext queryTree = Queries.getQueryTree(sqlQuery);
            Term ra = Queries.getRaOf(Schema.getSchema(), sqlQuery);
            return Translator.extractOperations(ra.toString());
        } catch (TranslationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Operator extractOperations(String cur){
        Operator out = null;
        Stack<Operator> required = new Stack<Operator>();
        Operator[] given = new Operator[2];
        boolean empty = true;
        ArrayList<String> tableNames = new ArrayList<String>();
        ArrayList<Integer> tableCounts = new ArrayList<Integer>();
        Operator x = null;
        while(cur.length()>0){
            while(cur.charAt(0) ==')' && cur.length()>0){
                cur = cur.substring(0);
            }
            if(cur.startsWith("0a0")){
                cur = cur.substring(3);
            }
            if(cur.startsWith("Project")){
                cur = cur.substring(8);
                ProjectOperator project = new ProjectOperator();
                required.push(project);
            }else if(cur.startsWith("Filter")){
                cur = cur.substring(7);
                FilterOperator filter = new FilterOperator();
                required.push(filter);
            }else if(cur.charAt(0) == '"'){//filter condition
                /********************extract condition************/
                String condition = "";
                char c;
                int i =0;
                while (( c =cur.charAt(++i)) != '"'){
                    condition += c;
                }
                Operator op =extractCondition(tableNames, tableCounts, condition);
                cur = cur.substring(++i);
                x = op;
                while (!required.empty()) {
                    if (empty) {
                        if (required.peek().numOfParamaters() == 1) {
                            Operator top = required.pop();
                            top.giveParameter(x);
                            x= top;
                        }else{
                            given[0] = x;
                            empty = false;
                            break;
                        }
                    }else{
                        Operator top = required.pop();
                        top.giveParameter(x);
                        top.giveParameter(given[0]);
                        x = top;
                        empty = true;
                    }
                }
            }else if(cur.charAt(0) >='a'
                    && cur.charAt(0) <='z'){// table name lower case
                RelationOperator relation = new RelationOperator();
                /**********************extract relation ********************/
                int i =0;
                String tableName ="";
                char c;
                while ((c =cur.charAt(i++)) != '='){
                    tableName += c;
                }
                while((c =cur.charAt(i++)) != ')'){}
                if(i < cur.length()-1) {
                    cur = cur.substring(++i);
                }else{
                    cur = "";
                }
                tableNames.add(tableName);
                tableCounts.add(Schema.getCount(tableName));
                relation.setTableName(tableName);
                x = relation;
                /**********************pop operators ********************/
                while (!required.empty()) {
                    if (empty) {
                        if (required.peek().numOfParamaters() == 1) {
                            Operator top = required.pop();
                            top.giveParameter(x);
                            x= top;
                        }else{
                            given[0] = x;
                            empty = false;
                            break;
                        }
                    }else{
                        Operator top = required.pop();
                        top.giveParameter(x);
                        top.giveParameter(given[0]);
                        x = top;
                        empty = true;
                    }
                }
            }else if(cur.startsWith("CartProd")){
                cur = cur.substring(9);
                JoinOperator join = new JoinOperator();
                required.push(join);
            }else if(cur.startsWith("SortAsc")){
                cur = cur.substring(8);
                SortOperator sort = new SortOperator();
                required.push(sort);
            }else if(cur.startsWith("[")){
                cur.replaceAll(" ","");
                int i = 1;
                ArrayList<DBColumn> columns= new ArrayList<DBColumn>();
                while (i<cur.length() && cur.charAt(i) <= '9' && cur.charAt(i) >= '0') {
                    int num = cur.charAt(i) - '0';
                    int tmp = 0;
                    i += 2;
                    String tableName;
                    while (true){
                        if(num > tableCounts.get(tmp)){
                            num -= tableCounts.get(tmp++);
                        }else{
                            tableName = tableNames.get(tmp);
                            break;
                        }
                    }
                    DBColumn column = new DBColumn(num,tableName);
                    columns.add(column);
                }

                ProjectOperator operator = (ProjectOperator) required.pop();
                operator.giveParameter(columns);
                operator.giveParameter(given[0]);
                empty = true;
                if(i >= cur.length()){
                    cur = "";
                }else{
                    cur = cur.substring(--i);
                }

            }
        }
        return x;
    }

    private static Operator extractCondition(ArrayList<String> names, ArrayList<Integer> counts, String condition) {
        Operator x = null;
        Stack<Operator> required = new Stack<>();
        while (condition.length()>0){
            while(condition.charAt(0) ==')' && condition.length()>0){
                condition = condition.substring(0);
            }
            if(condition.startsWith("OR")){
                condition = condition.substring(3);
                OrOperation orOperation = new OrOperation();
                required.push(orOperation);

            }else if (condition.startsWith("AND")){
                condition = condition.substring(3);
                AndOperation andOperation = new AndOperation();
                required.push(andOperation);
            }else if(condition.startsWith("#")){
                int num = condition.charAt(1) - '0';
                //TODO num could be > 9
                int i = 0;
                String tableName =null;
                while (true){
                    if(num > counts.get(i)){
                        num -= counts.get(i++);
                    }else{
                        tableName = names.get(i);
                        break;
                    }
                }
                DBColumn column = new DBColumn(num, tableName);
                char op = condition.charAt(2);
                char right = condition.charAt(3);
                if (right == '#'){
                    num = condition.charAt(1) - '0';
                    //TODO num could be > 9
                    i = 0;
                    while (true){
                        if(num > counts.get(i)){
                            num -= counts.get(i++);
                        }else{
                            tableName = names.get(i);
                            break;
                        }
                    }
                    DBColumn column2 = new DBColumn(num, tableName);
                    ConditionOperator operator = new ConditionOperator(column, column2, op);
                    boolean empty = false;
                    x = operator;
                    Operator[] given = new Operator[2];
                    while (!required.empty()) {
                        if (empty) {
                            if (required.peek().numOfParamaters() == 1) {
                                Operator top = required.pop();
                                top.giveParameter(x);
                                x= top;
                            }else{
                                given[0] = x;
                                empty = false;
                                break;
                            }
                        }else{
                            Operator top = required.pop();
                            top.giveParameter(x);
                            top.giveParameter(given[0]);
                            x = top;
                            empty = true;
                        }
                    }
                }
            }
        }
        return x;
    }

    private static Operator extractHelper(String[] operations, int i) {
        String cur = operations[i];
        if(cur.startsWith("0a0")){
            cur = cur.substring(3);
        }
        if(cur.startsWith("Project")){
            ProjectOperator project = new ProjectOperator();
        }else if(cur.startsWith("Filter")){
            FilterOperator filter = new FilterOperator();
        }else if(cur.charAt(0) >='a'
                && cur.charAt(0) <='z'){// table name lower case
            RelationOperator relation = new RelationOperator();
            String tableName = cur.split("=")[0];
            relation.setTableName(tableName);
        }else if(cur.startsWith("CartProd")){
            JoinOperator join = new JoinOperator();
        }else if(cur.startsWith("SortAsc")){
            SortOperator sort = new SortOperator();
        }
        return null;
    }

    public static void main(String[] args){
        Operator x = (Operator) Translator.translate("SELECT numbers FROM hello3");
    }
}
