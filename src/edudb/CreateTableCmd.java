package edudb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.btree.BTree;
import jdbm.helper.StringComparator;

public class CreateTableCmd implements Command {
	
	private String TableName;
	private LinkedHashMap<String , Object> refrences;
	private String KeyColName;
	
	public CreateTableCmd (String T , String K , LinkedHashMap<String , Object> R){
		
		TableName = T;
		refrences = R;
		KeyColName = K;
	}
	
	public LinkedHashMap execute(LinkedHashMap column) {

		
		//Creating B+tree for the key column
		RecordManager recman;
		BTree  tree ;
		String BTREE_NAME = KeyColName;
		String DATABASE = TableName;
		long recid;
		Properties    props = new Properties();
		try{
			 // open database and setup an object cache
            recman = RecordManagerFactory.createRecordManager( DATABASE, props );

            // create a new B+Tree data structure and use a StringComparator
			tree = BTree.createInstance( recman, new StringComparator() );
	        recman.setNamedObject( BTREE_NAME, tree.getRecid() );
	        System.out.println( "Created a new empty BTree" );
	        
	     // make the data persistent in the database
            recman.commit();
	        
	        
		}catch(Exception e){
			
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		if(column.isEmpty()){
			System.out.println("Must insert data first!");
		}
		
		int size = column.size();
		Set s = column.entrySet();
		Iterator i= s.iterator();
		String [] ColumnTypes = new String[size];
		String [] ColumnValues = new String[size];
		String ColumnType;
		String ColumnValue;
		int t = 0; // used to index the elements of the array
		
		// to convert 1st hashtable into 2 arrays ColumnTypes[]&ColumnValues[]
		while(i.hasNext())
		{
			Map.Entry m=(Map.Entry)i.next();
			ColumnType = (String)m.getKey();//name of the column
			ColumnValue=(String)m.getValue();// type of the column
			ColumnTypes[t] = ColumnType;
			ColumnValues[t] = ColumnValue;
			t++;
		}
		//System.out.println( Arrays.toString( ColumnTypes ) );
		//System.out.println( Arrays.toString( ColumnValues ) );
		
		int size1 = refrences.size();
		Set s1 =refrences.entrySet();
		Iterator i1 = s1.iterator();
		String [] ColumnNames = new String[size1];
		String [] ColumnRefrences = new String[size1];
		String ColumnName;
		String ColumnRefrence;
		int t1 = 0; // used to index the elements of the array

		// to convert 2st hashtable into 2 arrays ColumnNames[]&ColumnRefrences[]
		while(i1.hasNext())
		{
			Map.Entry m1 =(Map.Entry)i1.next();
			ColumnName = (String)m1.getKey();//name of the column
			ColumnRefrence =(String)m1.getValue();// reference of the column
			ColumnNames[t1] = ColumnName;
			ColumnRefrences[t1] = ColumnRefrence;
			t1++;
		}
	
		
		// creates the new table
		try{
			File table = new File(DBFile.getDirectory(),TableName);
			// if the directory does not exist, create it
			if (!table.exists())
			{
               System.out.println("creating directory: " + TableName);
				boolean result = table.mkdir();  
				if(result){    
					System.out.println("DIR created");
					File x = new File(DBFile.getDirectory()+TableName+"/"+TableName+","+1);
					FileWriter fileWriter = new FileWriter(x, true);
					BufferedWriter bw = new BufferedWriter(fileWriter);
				}

				//call the insertMetaData() function to insert in the metadata file with every table created
				insertMetaData(TableName,ColumnTypes,ColumnValues,ColumnNames,ColumnRefrences,KeyColName);

			}else{

				System.out.println("The Table you are creating already exists !!!");
			}
		}catch(Exception e){
			
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return column;
	}
	
	
	//inserts a new row in metadata for each column inside a table created
	public static void insertMetaData(String tableName , String[] columnNames ,String[] ColumnTypes ,String[] colNamRef, String[] refrences , String keyColName){

		int sizecolNameType = columnNames.length;
		//to insert into metadata a new row
		try{

			File file = new File(DBFile.getDirectory()+"metadata.txt");
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			int i = 0;
			while(i<sizecolNameType)
			{
				if(columnNames[i].equals(keyColName)){
					bw.newLine();
					bw.append(tableName);
					bw.append(",");
					bw.append(columnNames[i]);
					bw.append(",");
					bw.append(ColumnTypes[i]);
					bw.append(",");
					bw.append("True");// for the key
					bw.append(",");
					bw.append("False");// for the index
					bw.append(",");
					int flag = 0;
					for(int cnt = 0 ; cnt < colNamRef.length ; cnt ++){
						if(columnNames[i].equals(colNamRef[cnt])){
							String tmp = refrences[cnt];
							bw.append(tmp);
							flag = 1;
						}
					}
					if(flag == 0){
						bw.append("null");
					}
				}else{
					bw.newLine();
					bw.append(tableName);
					bw.append(",");
					bw.append(columnNames[i]);
					bw.append(",");
					bw.append(ColumnTypes[i]);
					bw.append(",");
					bw.append("False");// for the key
					bw.append(",");
					bw.append("False");//for the index
					bw.append(",");
					int flag = 0;
					for(int cnt = 0 ; cnt < colNamRef.length ; cnt ++){
						if(columnNames[i].equals(colNamRef[cnt])){
							String tmp = refrences[cnt];
							bw.append(tmp);
							flag = 1;
						}
					}
					if(flag == 0){
						bw.append("null");
					}
				}
				i++;
			}// end of while loop
			bw.close();
		}catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
}
