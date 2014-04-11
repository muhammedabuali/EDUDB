package edudb_2.data_structures.dataTypes;

public interface DataType extends Comparable {

    double diff(DataType dataType);
    int compareTo(Object o);
}
