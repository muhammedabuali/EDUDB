package edudb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.btree.BTree;
import jdbm.helper.Tuple;
import jdbm.helper.TupleBrowser;

public class InsertCmd implements Command{
	
	private String TableName;
	
	public InsertCmd (String t ){
		
		TableName = t;
	}
	
public LinkedHashMap execute(LinkedHashMap hash) {
	
	System.out.println(hash);
		
		String colKey = "";
		String colIndex = "";
		int counter = -1;
		int placeOfKey = 0;
		int placeOfIndexCol = 0;
		int flagIndex = 0;
		
		//This try is used to get the name of the key column from metadata file
		try{
			
			FileInputStream stream = new FileInputStream(DBFile.getDirectory()+"metadata.txt");
			DataInputStream in1 = new DataInputStream(stream);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String stLine;
			while ((stLine = br1.readLine()) != null){
				
				String token[] = stLine.split(",");
				if(token.length > 0){
					
					if(token[0].equals(TableName)&& token[3].equals("True")){
						
						counter++;
						placeOfKey = counter;
						colKey = token[1];
						//break;
						
					}else{
						
						if(token[0].equals(TableName)&& !token[3].equals("True")){
							
							counter++;
						}
					}
					
					if(token[0].equals(TableName) && token[4].equals("True")){
						
						flagIndex = 1;
						placeOfIndexCol = counter;
						colIndex = token[1];
					}
					
				}
			}
		}catch(Exception e){
			
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		
	
		try
		{
			File f = new File(DBFile.getDirectory()+"\\"+TableName);
			if(!f.exists()) { 
				throw new DBAppException("");
			}
		}
		catch(DBAppException ex)
		{
			System.out.println("please enter a valid page name !!!");
			System.exit(1);
		}

		if(hash.isEmpty()){

			System.out.println("Must insert data first!");
		}
		
		String ElementName;
		String ElementValue;
		int NumberRowsCountinPage = MaximumRowsCountinPage();
		int size = hash.size();
		Set s =hash.entrySet();
		Iterator i=s.iterator();
		String [] ElementsName = new String[size];
		String [] Elementsvalue = new String[size];
		int t =0; // used to index the elements of the array

		// to convert hashtable into 2 arrays ElementsName[]&Elementsvalue[]
		while(i.hasNext())
		{
			Map.Entry m=(Map.Entry)i.next();
			ElementName = (String)m.getKey();
			ElementValue=(String)m.getValue();
			ElementsName[t] = ElementName;
			Elementsvalue[t] = ElementValue;
			t++;
		}
		
		// to get the no of files inside a page
		int noFiles = new File(DBFile.getDirectory()+""+TableName).listFiles().length;

		// to get the correct id of the inserted row in the page
		String y = DBFile.getDirectory()+"\\"+TableName+"\\"+TableName+","+noFiles;
		int id = 0;
		String id1 = "";
		try {
			id = countLines(y);
			id1 = Integer.toString(id);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// try to reload an existing B+Tree of the key field
		String DATABASE = TableName;
		String BTREE_NAME = colKey;
		long recid;
		RecordManager recman;
		BTree tree;
		Properties props;
		props = new Properties();
		
		// try to reload an existing B+Tree of the Index field
		String BTREE_NAME1 = colIndex;
		long recid1 = 0;
		BTree tree1;
		
		//to write in the page(table) the inserted data
		try{
			
			// open database and setup an object cache
			recman = RecordManagerFactory.createRecordManager( DATABASE, props );
			
			// try to reload an existing B+Tree of the key field
			recid = recman.getNamedObject(BTREE_NAME);
			if ( recid != 0 ) {
                tree = BTree.load( recman, recid );
                System.out.println( "Reloaded existing BTree with " + tree.size() );
            }
			
			
			if(flagIndex == 1){
				
				// try to reload an existing B+Tree of the Index field
				recid1 = recman.getNamedObject(BTREE_NAME1);
				if ( recid1 != 0 ) {
	                tree1 = BTree.load( recman, recid1 );
	                System.out.println( "Reloaded existing BTree with " + tree1.size() );
	            }
			}

			if(noFiles == 1){

				if(id<NumberRowsCountinPage){
					BufferedWriter bw = new BufferedWriter( new FileWriter(DBFile.getDirectory()+"\\"+TableName+"\\"+TableName+","+noFiles,true));
					int i1 = 0;
					bw.append(id1);
					bw.append(",");
					for(;Elementsvalue.length>0 && i1<Elementsvalue.length;i1++){

						bw.append(Elementsvalue[i1]);
						bw.append(",");
					}
					bw.newLine();
					bw.close();
					
					//Insert in the B+tree the key column
					tree = BTree.load( recman, recid );
					String nameOfPage = TableName+","+noFiles; // name of the page
					System.out.println("Name of the page is" + nameOfPage);
					String rowID = id1;// the ID of the row
					System.out.println("The row ID is "  + rowID);
					Object key = Elementsvalue[placeOfKey];// the value to be inserted in the tree
					System.out.println("place Of Key : " + placeOfKey);
					System.out.println("The value to be inserted in the tree"  +key);
					Value temp = new Value(rowID, nameOfPage);
					tree.insert(key,temp,false);
					recman.commit();
					
					//Insert in the B+tree the Index column
					if(flagIndex == 1){
						
						//Insert in the B+tree the key column
						tree1 = BTree.load( recman, recid1 );
						String nameOfPage1 = TableName+","+noFiles; // name of the page
						System.out.println("Name of the page is" + nameOfPage1);
						String rowID1 = id1;// the ID of the row
						System.out.println("The row ID is "  + rowID1);
						Object key1 = Elementsvalue[placeOfIndexCol];// the value to be inserted in the tree
						System.out.println("The value to be inserted in the tree"  +key1);
						Value temp1 = new Value(rowID1, nameOfPage1);
						tree1.insert(key1,temp1,false);
						recman.commit();
					}
					
				}else{

					createNewFile(noFiles,TableName);

					noFiles++;
					int newID = NumberRowsCountinPage;
					for(int cnt = 2 ; cnt < noFiles ; cnt++){
						newID = newID + NumberRowsCountinPage ;
					}
					BufferedWriter bw = new BufferedWriter( new FileWriter(DBFile.getDirectory()+"\\"+TableName+"\\"+TableName+","+noFiles,true));
					int i1 = 0;
					String xx = Integer.toString(newID);
					bw.append(xx);
					bw.append(",");
					for(;Elementsvalue.length>0 && i1<Elementsvalue.length;i1++){

						bw.append(Elementsvalue[i1]);
						bw.append(",");
					}
					bw.newLine();
					bw.close();
					
					
					//Insert in the B+tree the key column
					tree = BTree.load( recman, recid );
					String nameOfPage = TableName+","+noFiles; // name of the page
					System.out.println("Name of the page is" + nameOfPage);
					String rowID = xx;// the ID of the row
					System.out.println("The row ID is "  + rowID);
					Object key = Elementsvalue[placeOfKey];// the value to be inserted in the tree
					System.out.println("The value to be inserted in the tree"  +key);
					Value temp = new Value(rowID, nameOfPage);
					tree.insert(key,temp,false);
					recman.commit();
					
					
					//Insert in the B+tree the Index column
					if(flagIndex == 1){
						
						//Insert in the B+tree the key column
						tree1 = BTree.load( recman, recid1 );
						String nameOfPage1 = TableName+","+noFiles; // name of the page
						System.out.println("Name of the page is" + nameOfPage1);
						String rowID1 = xx;// the ID of the row
						System.out.println("The row ID is "  + rowID1);
						Object key1 = Elementsvalue[placeOfIndexCol];// the value to be inserted in the tree
						System.out.println("The value to be inserted in the tree"  +key1);
						Value temp1 = new Value(rowID1, nameOfPage1);
						tree1.insert(key1,temp1,false);
						recman.commit();
						
					}
				}
			}else{

				int newID = NumberRowsCountinPage;
				for(int cnt = 2 ; cnt < noFiles ; cnt++){
					newID = newID + NumberRowsCountinPage ;
				}
				if(id<NumberRowsCountinPage){

					BufferedWriter bw = new BufferedWriter( new FileWriter(DBFile.getDirectory()+"\\"+TableName+"\\"+TableName+","+noFiles,true));
					int i1 = 0;
					int x = Integer.parseInt(id1);
					newID = newID + x;
					String xx = Integer.toString(newID);
					bw.append(xx);
					bw.append(",");
					for(;Elementsvalue.length>0 && i1<Elementsvalue.length;i1++){

						bw.append(Elementsvalue[i1]);
						bw.append(",");
					}
					bw.newLine();
					bw.close();
					
					
					//Insert in the B+tree the key column
					tree = BTree.load( recman, recid );
					String nameOfPage = TableName+","+noFiles; // name of the page
					System.out.println("Name of the page is" + nameOfPage);
					String rowID = xx;// the ID of the row
					System.out.println("The row ID is "  + rowID);
					Object key = Elementsvalue[placeOfKey];// the value to be inserted in the tree
					System.out.println("The value to be inserted in the tree"  +key);
					Value temp = new Value(rowID, nameOfPage);
					tree.insert(key,temp,false);
					recman.commit();
					
					
					//Insert in the B+tree the Index column
					if(flagIndex == 1){
						
						//Insert in the B+tree the key column
						tree1 = BTree.load( recman, recid1 );
						String nameOfPage1 = TableName+","+noFiles; // name of the page
						System.out.println("Name of the page is" + nameOfPage1);
						String rowID1 = xx;// the ID of the row
						System.out.println("The row ID is "  + rowID1);
						Object key1 = Elementsvalue[placeOfIndexCol];// the value to be inserted in the tree
						System.out.println("The value to be inserted in the tree"  +key1);
						Value temp1 = new Value(rowID1, nameOfPage1);
						tree1.insert(key1,temp1,false);
						recman.commit();
						
					}
				}else{

					createNewFile(noFiles,TableName);

					noFiles++;
					int newId = NumberRowsCountinPage;
					for(int cnt = 2 ; cnt < noFiles ; cnt++){
						newId = newId + NumberRowsCountinPage ;
					}
					BufferedWriter bw = new BufferedWriter( new FileWriter(DBFile.getDirectory()+"\\"+TableName+"\\"+TableName+","+noFiles,true));
					int i1 = 0;
					String xx = Integer.toString(newId);
					bw.append(xx);
					bw.append(",");
					for(;Elementsvalue.length>0 && i1<Elementsvalue.length;i1++){

						bw.append(Elementsvalue[i1]);
						bw.append(",");
					}
					bw.newLine();
					bw.close();
					
					//Insert in the B+tree the key column
					tree = BTree.load( recman, recid );
					String nameOfPage = TableName+","+noFiles; // name of the page
					System.out.println("Name of the page is" + nameOfPage);
					String rowID = xx;// the ID of the row
					System.out.println("The row ID is "  + rowID);
					Object key = Elementsvalue[placeOfKey];// the value to be inserted in the tree
					System.out.println("The value to be inserted in the tree"  +key);
					Value temp = new Value(rowID, nameOfPage);
					tree.insert(key,temp,false);
					recman.commit();
					
					
					//Insert in the B+tree the Index column
					if(flagIndex == 1){
						
						//Insert in the B+tree the key column
						tree1 = BTree.load( recman, recid1 );
						String nameOfPage1 = TableName+","+noFiles; // name of the page
						System.out.println("Name of the page is" + nameOfPage1);
						String rowID1 = xx;// the ID of the row
						System.out.println("The row ID is "  + rowID1);
						Object key1 = Elementsvalue[placeOfIndexCol];// the value to be inserted in the tree
						System.out.println("The value to be inserted in the tree"  +key1);
						Value temp1 = new Value(rowID1, nameOfPage1);
						tree1.insert(key1,temp1,false);
						recman.commit();
						
					}

				}
			}

		}catch(Exception e){//Catch exception if any

			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return hash;
	}
	
	// returns the MaximumRowsCountinPage
	public static int MaximumRowsCountinPage(){

		String temp = numberOfCsvFiles();
		StringTokenizer st = new StringTokenizer(temp, "%MaximumRowsCountinPage%");
		String MaximumRowsCountinPage = st.nextToken();//MaximumRowsCountinPage
		int foo1 = Integer.parseInt(MaximumRowsCountinPage);
		return foo1;
	}
	
	//returns the csv/page text file as a string.
	public static String numberOfCsvFiles(){

		StringTokenizer st;
		String x = "";
		String key = "";
		String val = "";

		try{

			BufferedReader NoOfCSVFile;
			NoOfCSVFile = new BufferedReader(new FileReader("C:\\Users\\Azza\\Desktop\\workspace\\edudb\\config\\DBApp.txt"));
			String line = NoOfCSVFile.readLine();

			while(line != null){
				st = new StringTokenizer(line , "=;");
				while(st.hasMoreTokens()) {
					key = st.nextToken(); //maximum rows countingpage
					val = st.nextToken(); // 200
				}

				x = "%" + x + key + val + "%";
				line = NoOfCSVFile.readLine();
			}
			NoOfCSVFile.close();
		}
		catch(Exception e){
			System.out.println("Error!");
		}
		return x;
	}
	
	// helper method used by insertIntoTable() to make a different id for each line in the page
	public static int countLines(String filename) throws IOException {
		LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
		int cnt = 0;
		String lineRead = "";
		while ((lineRead = reader.readLine()) != null) {}
		cnt = reader.getLineNumber(); 
		reader.close();
		return cnt;
	}
	
	// This method is called internally by insertIntoTable it is used when the page is full and need to create a new page
	public static void createNewFile(int numberOfFiles , String Name){

		numberOfFiles ++;

		try{

			File x = new File(DBFile.getDirectory()+"\\"+Name+"\\"+Name+","+numberOfFiles);
			FileWriter fileWriter = new FileWriter(x, true);
			BufferedWriter bw = new BufferedWriter(fileWriter);

		}catch(Exception e){//Catch exception if any

			System.err.println("Error: " + e.getMessage());
		}
	}

}
