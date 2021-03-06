package dataTypes;

import DBStructure.DBConst;

/**
 * Created by mohamed on 3/23/14.
 */
public class DB_Type {

/**
 * @author   mohamed
 */
public static class DB_Int implements DataType , DBConst{
        /**
         * @uml.property  name="number"
         */
        public int number;

        public DB_Int(int num){
            number = num;
        }

        public DB_Int(char num){
            number = num - '0';
        }

    public DB_Int(String s) {
        number = Integer.parseInt(s);
    }

    public double diff(DataType key){
            if (key instanceof DB_Int){
                return number - ((DB_Int) key).number;
            }
            return -1;
        }

        public int compareTo(Object dataType){
            if (dataType instanceof DB_Int){
                return number - ((DB_Int) dataType).number;
            }
            return -1;
        }

        @Override
        public boolean equals(Object o){
            if( o instanceof DB_Int){
                if (number == ((DB_Int)o).number){
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode(){
            return number;
        }

        /**
         * @return
         * @uml.property  name="number"
         */
        public int getNumber(){
            return number;
        }

        public String toString(){
            return number+"";
        }

    @Override
    public void print() {
        System.out.print(number);
    }

    @Override
    public int numOfParameters() {
        return 0;
    }
}

    public static class DB_Char implements DataType, DBConst{
        public char c;

        public DB_Char(char c){
            this.c = c;
        }
        public double diff(DataType key){
            if (key instanceof DB_Char){
                return c - ((DB_Char) key).c;
            }
            return -1;
        }

        public int compareTo(Object dataType){
            if (dataType instanceof DB_Char){
                return c - ((DB_Char) dataType).c;
            }
            return -1;
        }

        @Override
        public boolean equals(Object o){
            if( o instanceof DB_Char){
                if (c == ((DB_Char)o).c){
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode(){
            return c;
        }

        @Override
        public void print() {
            System.out.print(c);
        }

        @Override
        public int numOfParameters() {
            return 0;
        }
    }

    public static class DB_String implements DataType{
        public String str;

        public double diff(DataType key){
            return -1;
        }

        public int compareTo(Object dataType){
            if (dataType instanceof DB_String){
                return str.compareTo(((DB_String)dataType).str);
            }
            return -1;
        }

        @Override
        public boolean equals(Object o){
            if( o instanceof DB_String){
                if (str.equals(((DB_String)o).str)){
                    return true;
                }
            }
            return false;
        }
    }
}