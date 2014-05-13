package operators;

import java.util.ArrayList;

import DBStructure.DBColumn;

public class SelectColumns implements DBParameter {

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
}
