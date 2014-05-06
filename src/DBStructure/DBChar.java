package DBStructure;

public class DBChar implements DBConst {
    public char c;

    public DBChar(char c) {
        this.c = c;
    }

    @Override
    public void print() {
        System.out.print(c);
    }

    public String toString(){
        return c+"";
    }

    @Override
    public int numOfParameters() {
        return 0;
    }
}
