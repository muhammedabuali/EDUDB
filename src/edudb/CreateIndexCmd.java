package edudb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Properties;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.btree.BTree;
import jdbm.helper.StringComparator;


public class CreateIndexCmd implements Command , Serializable {
	
	private String tableName;
	private String colName;
	
	public CreateIndexCmd (String t , String c){
		
		tableName = t;
		colName = c;
		
	}
	
	public LinkedHashMap execute(LinkedHashMap htblInputParams) {
		
		try
		{
			File f = new File(DBFile.getDirectory()+tableName);
			if(!f.exists()) { 
				throw new DBAppException("");
			}
		}
		catch(DBAppException ex)
		{
			System.out.println("please enter a valid page name !!!");
			System.exit(1);
		}
		
		RecordManager recman;
		BTree  tree ;
		String BTREE_NAME = colName;
		String DATABASE = tableName;
		Properties    props = new Properties();
		
		//to insert into the metadata file "true" for the indexed column
		try{

			FileInputStream stream = new FileInputStream(DBFile.getDirectory()+"metadata.txt");
			DataInputStream in1 = new DataInputStream(stream);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String stLine;
			StringBuilder fileContent = new StringBuilder();
			while ((stLine = br1.readLine()) != null){
				String token[] = stLine.split(",");
				if(token.length > 0){
					if(token[0].equals(tableName)&& token[1].equals(colName)){
						token[4] = "True";
						String newLine = token[0] + "," + token[1] + "," + token[2] + "," + token[3] + "," + token[4] + "," + token[5];
						fileContent.append(newLine);
						fileContent.append("\r\n");
					}else{
						fileContent.append(stLine);
						fileContent.append("\r\n");
					}
				}
			}

			// Now fileContent will have updated content , which you can override into file
			FileWriter fstreamWrite = new FileWriter(DBFile.getDirectory()+"metadata.txt");
			BufferedWriter out = new BufferedWriter(fstreamWrite);
			out.write(fileContent.toString());
			out.close();
			//Close the input stream
			in1.close();

		}catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		
		
		// To insert the values in the B+Tree
		int colID = 0;
		int realcolID = 0;
		int counter = 1;
		int noPages = new File(DBFile.getDirectory()+""+tableName).listFiles().length;
		int noOfCols = 0;

		try{

			// this part is to know the place of the column in it's page to be able to search for it in the page
			FileInputStream stream = new FileInputStream(DBFile.getDirectory()+"metadata.txt");
			BufferedReader buff = new BufferedReader(new InputStreamReader(stream));
			String stLine;
			while ((stLine = buff.readLine()) != null){

				String token[] = stLine.split(",");
				if(token.length > 0){
					
					if(token[0].equals(tableName)){
						
						noOfCols++;
					}

					if(token[0].equals(tableName)&& token[1].equals(colName)){
						colID++;
						realcolID = colID;
					}else{
						if(token[0].equals(tableName)&&! token[1].equals(colName)){
							colID++;
						}
					}
				}
			}
			///////////////////////////////////////////////////////////////////////////////////////
			noOfCols = noOfCols + 2;
			
			 // open database and setup an object cache
			 recman = RecordManagerFactory.createRecordManager( DATABASE, props );
			// create a new B+Tree data structure and use a StringComparator
			 tree = BTree.createInstance( recman, new StringComparator() );
			 recman.setNamedObject( BTREE_NAME, tree.getRecid() );
			 System.out.println( "Created a new empty BTree" );
			 
			  // make the data persistent in the database
	          recman.commit();

			while(counter <= noPages){
				FileInputStream st = new FileInputStream(DBFile.getDirectory()+""+tableName+"\\"+tableName+","+counter);
				BufferedReader br = new BufferedReader(new InputStreamReader(st));
				String line;
				while ((line = br.readLine()) != null){
					String array[] = line.split(",");
					if(array.length > 0 && (array.length != noOfCols)){
						
						String nameOfPage = tableName+","+counter; // name of the page
						System.out.println("Name of the page is" + nameOfPage);
						String rowID = array[0];// the ID of the row
						System.out.println("The row ID is "  + rowID);
						Object key = array[realcolID];// the value to be inserted in the tree
						System.out.println("The value to be inserted in the tree"  +key);
						Value temp = new Value(rowID, nameOfPage);
						tree.insert(key,temp,false);
						recman.commit();
					}
				}
				counter++;
			}

		}catch(Exception e){
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return htblInputParams;
	}

}
