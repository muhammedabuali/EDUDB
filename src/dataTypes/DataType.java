package dataTypes;

public interface DataType extends Comparable {

    double diff(DataType dataType);
    int compareTo(Object o);
    String toString();
}
