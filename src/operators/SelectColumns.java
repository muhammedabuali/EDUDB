package operators;

import java.util.ArrayList;

import DBStructure.DBColumn;

/**
 * @author  mohamed
 */
public class SelectColumns implements DBParameter {

    /**
     * @uml.property  name="columns"
     * @uml.associationEnd  multiplicity="(0 -1)" elementType="DBStructure.DBColumn"
     */
    ArrayList<DBColumn> columns;

    public SelectColumns(ArrayList<DBColumn> columns) {
        this.columns = columns;
    }

    @Override
    public void print() {
        String outString = " ";
        for (int i = 0; i < columns.size(); i++) {
            outString+= columns.get(i).toString() + ",";
        }
    }

    public ArrayList<Integer> getColumns(){
        int prev = -1;
        ArrayList<Integer> out = new ArrayList<>();
        for (int i=0; i< columns.size(); i++){
                out.add(columns.get(i).order);
        }
        return out;
    }
    @Override
    public int numOfParameters() {
        return 0;
    }

    @Override
    public String toString(){
        String outString = " ";
        for (int i = 0; i < columns.size(); i++) {
            outString+= columns.get(i).toString() + ",";
        }
        return outString;
    }

    public void union(SelectColumns selectColumns) {
        ArrayList<DBColumn> dbColumns = selectColumns.getDBColumns();
        for(int i=0; i<dbColumns.size(); i++){
            if ( columns.indexOf( dbColumns.get(i) ) == -1 ){
                columns.add(dbColumns.get(i));
            }
        }
    }

    private ArrayList<DBColumn> getDBColumns() {
        return columns;
    }
}
