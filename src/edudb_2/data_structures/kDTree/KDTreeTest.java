package edudb_2.data_structures.kDTree;

import edudb_2.data_structures.dataTypes.DB_Type;
import edudb_2.data_structures.dataTypes.DataType;
import org.junit.Test;

/**
 * Created by mohamed on 3/23/14.
 */
public class KDTreeTest {

    @Test
    public void testConstructor(){
        KDTree tree = new KDTree(2);
    }

    @Test
    public void test10Insert(){
        KDTree tree = new KDTree(2);
        DataType[] key = new DataType[2];
        for (int i=0; i< 10; i++){
            key[0] = new DB_Type.DB_Int(i);
            key[1] = new DB_Type.DB_Char((char)('a'+i));
            DataType value = new DB_Type.DB_Int(100);
            tree.insert(key,value);
        }
    }

    @Test
    public void testSearch(){
        KDTree tree = new KDTree(2);
        DataType[] key = new DataType[2];
        for (int i=0; i< 10; i++){
            key[0] = new DB_Type.DB_Int(i);
            key[1] = new DB_Type.DB_Char((char)('a'+i));
            DataType value = new DB_Type.DB_Int(i+5);
            tree.insert(key,value);
        }
        for (int i=0; i< 10; i++){
            key[0] = new DB_Type.DB_Int(i);
            key[1] = new DB_Type.DB_Char((char)('a'+i));
            Object value = tree.search(key);
            assert value != null;
            DB_Type.DB_Int intValue = (DB_Type.DB_Int) value;
            assert intValue.number == i+5;
        }
    }

    @Test
    public void testDelete(){
        KDTree tree = new KDTree(2);
        DataType[] key = new DataType[2];
        for (int i=0; i< 10; i++){
            key[0] = new DB_Type.DB_Int(i);
            key[1] = new DB_Type.DB_Char((char)('a'+i));
            DataType value = new DB_Type.DB_Int(i+5);
            tree.insert(key,value);
        }
        for (int i=0; i< 10; i++){
            key[0] = new DB_Type.DB_Int(i);
            key[1] = new DB_Type.DB_Char((char)('a'+i));
            tree.delete(key);
        }
    }

    @Test
    public void testRange(){
        KDTree tree = new KDTree(2);
        DataType[] key = new DataType[2];
        for (int i=0; i< 10; i++){
            key[0] = new DB_Type.DB_Int(i);
            key[1] = new DB_Type.DB_Char((char)('a'+i));
            DataType value = new DB_Type.DB_Int(i+5);
            tree.insert(key,value);
        }
        DataType[] lowk = new DataType[2];
        lowk[0] = new DB_Type.DB_Int(3);
        lowk[1] = new DB_Type.DB_Char('d');
        DataType[] uppk = new DataType[2];
        uppk[0] = new DB_Type.DB_Int(6);
        uppk[1] = new DB_Type.DB_Char('g');
        Object[] values = tree.range(lowk, uppk);
        assert values.length == 4;
    }

}