package data_structures.linearHashTable;

import static org.junit.Assert.*;

import dataTypes.DB_Type;
import dataTypes.DataType;
import org.junit.Test;

public class LinearHashTableTest {

	@Test
	public void testPutOneGetOne() {
		LinearHashTable table = new LinearHashTable(0.75f, 2);

        DB_Type.DB_Int key = new DB_Type.DB_Int(5);
		DB_Type.DB_String value = new DB_Type.DB_String();
		value.str = "five";
		table.put(key, value);
		assertTrue(key.equals(key));
		assertEquals("get as put", value,table.get(key));
	}
	
	@Test
	public void testResize() {
		LinearHashTable table = new LinearHashTable(0.75f, 2);
		DB_Type.DB_Int key1 = new DB_Type.DB_Int(5);
		DB_Type.DB_String value1 = new DB_Type.DB_String();
		value1.str = "five";
		table.put(key1, value1);
		assertEquals("table size increased", 1, table.size());
		DB_Type.DB_Int key2 = new DB_Type.DB_Int(6);
		DB_Type.DB_String value2 = new DB_Type.DB_String();
		value2.str = "six";
		table.put(key2, value2);
		assertEquals("table size increased", 2, table.size());
	}

	@Test
	public void put100(){
		LinearHashTable table = new LinearHashTable(0.75f, 2);
		for (int i = 0; i < 100; i++) {
			DB_Type.DB_Int key1 = new DB_Type.DB_Int(i);
			DB_Type.DB_String value1 = new DB_Type.DB_String();
			value1.str = "num "+i;
			table.put(key1, value1);
		}
		assertEquals("table size 100", 100, table.size());
	}
	
	@Test
	public void remove100(){
		LinearHashTable table = new LinearHashTable(0.75f, 2);
        int count = 100;
		for (int i = 0; i < count; i++) {
			DB_Type.DB_Int key1 = new DB_Type.DB_Int(i);
			DB_Type.DB_String value1 = new DB_Type.DB_String();
			value1.str = "num "+i;
			table.put(key1, value1);
		}
		for (int i = 0; i < count; i++) {
			DB_Type.DB_Int key2 = new DB_Type.DB_Int(i);
			DB_Type.DB_String value2 = new DB_Type.DB_String();
			value2.str = "num "+i;
			DataType value = table.remove(key2);
			assertEquals("value", value2, value);
		}
		assertEquals("table empty", true, table.isEmpty());
	}
	@Test
	public void testRemove() {
		LinearHashTable table = new LinearHashTable(0.75f, 2);
		DB_Type.DB_Int key1 = new DB_Type.DB_Int(5);
		DB_Type.DB_String value1 = new DB_Type.DB_String();
		value1.str = "five";
		table.put(key1, value1);
		assertEquals("table size increased", 1, table.size());
		DB_Type.DB_Int key2 = new DB_Type.DB_Int(6);
		DB_Type.DB_String value2 = new DB_Type.DB_String();
		value2.str = "six";
		table.put(key2, value2);
		DataType value3 = table.remove(key2);
		assertEquals("remove correct value", value2, value3);
	}
	
	
	
	
}
