package edudb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.btree.BTree;

public class DeleteCmd implements Command {
	
	private String TableName;
	private String strOperator;
	
	public DeleteCmd (String t , String O ){
		
		TableName = t;
		strOperator = O;
	}

	
	public LinkedHashMap execute(LinkedHashMap hash) {
		
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
						placeOfKey ++;
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
						placeOfIndexCol++;
					}
					
				}
			}
		}catch(Exception e){
			
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		
		// read the number of columns in a table
		int noColumns = 1;

		// this try is made to return the number of columns in a page
		try{
			FileInputStream stream = new FileInputStream(DBFile.getDirectory()+"metadata.txt");
			DataInputStream in1 = new DataInputStream(stream);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String stLine;
			
			while ((stLine = br1.readLine()) != null){
				
				String token[] = stLine.split(",");
				
				if(token[0].equals(TableName)){
					
					noColumns++;
				}
			}
			//System.out.println(noColumns);
		}catch(Exception e){}

		String ElementName;
		String ElementValue;
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
		//System.out.println( Arrays.toString( ElementsName ) );
		
		
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

		// to get the no of files inside a page
		int noFiles = new File(DBFile.getDirectory()+""+TableName).listFiles().length;
		int counterNoFiles = 1;

		int noColumns1 = 0; // counter for the number of columns
		int tokenn = 1; // counter for tokens for the number of columns
		int flag = 0;

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
			
			if(!hash.isEmpty()){

				while(counterNoFiles <= noFiles){
					// Open the file that is the first
					// command line parameter
					FileInputStream fstream = new FileInputStream(DBFile.getDirectory()+"\\"+TableName+"\\"+TableName+","+counterNoFiles);
					// Get the object of DataInputStream
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String strLine;
					StringBuilder fileContent = new StringBuilder();
					//Read File Line By Line
					while ((strLine = br.readLine()) != null){// begin of the while
						String newLine = "";
						String tokens[] = strLine.split(",");
						if (tokens.length > 0){
							tokenn = 1;
							flag = 0;
							while(tokenn < tokens.length){// open of while loop while(tokenn < tokens.length)
								if(strOperator.equals("OR")){//open of condition operator == OR

									if ((tokens[tokenn].equals(Elementsvalue[0]) || tokens[tokenn].equals(Elementsvalue[1])) && tokens.length == noColumns){
										noColumns1 = 0;
										while(noColumns1 < noColumns){
											newLine = newLine+tokens[noColumns1]+",";
											noColumns1++;
											System.out.println(newLine);
										}
										newLine = newLine+"###" ;
										fileContent.append(newLine);
										System.out.println(newLine);
										fileContent.append("\r\n");
										flag = 1;
										tokenn = tokens.length;//####################
										
										
										//Delete the key column from the B+tree
										tree = BTree.load( recman, recid );
										Object key = tokens[placeOfKey];
										System.out.println("the value is:"+key);
										String valueOfRowID = tokens[0];
										System.out.println("the row id:"+valueOfRowID);
										String nameOFPage = TableName+","+counterNoFiles;
										System.out.println("the name of the page:"+nameOFPage);
										Value k2 = new Value(valueOfRowID,nameOFPage);
										tree.remove(key);
										System.out.println("The value has been removed from the tree");
										recman.commit();
										
										
										//Delete the indexed column from the B+tree
										if(flagIndex == 1){
											
											tree1 = BTree.load( recman, recid1 );
											Object key1 = tokens[placeOfIndexCol];
											System.out.println("the value of index is:"+key1);
											String valueOfRowID1 = tokens[0];
											System.out.println("the row id:"+valueOfRowID1);
											String nameOFPage1 = TableName+","+counterNoFiles;
											System.out.println("the name of the page:"+nameOFPage1);
											Value k1 = new Value(valueOfRowID1,nameOFPage1);
											tree1.remove(key1);
											System.out.println("The value has been removed from the indexed tree");
											recman.commit();
										}
										
									}else {tokenn++;}
									if(tokenn == tokens.length && flag != 1 ){
										//update content as it is
										fileContent.append(strLine);
										fileContent.append("\r\n");
									}
									
								}// close of 1st condition operator == OR
								else{
				
									if(strOperator.equals("AND")){
										
										int limit = tokens.length;
										int countLimit = 1;
										while(countLimit < limit){
											if (tokens[tokenn].equals(Elementsvalue[0])&& tokens[countLimit].equals(Elementsvalue[1]) 
												&& tokens.length == noColumns){
												noColumns1 = 0;
												while(noColumns1 < noColumns){
													
													newLine = newLine+tokens[noColumns1]+",";
													noColumns1++;
												}
												newLine = newLine+"###" ;
												fileContent.append(newLine);
												fileContent.append("\r\n");
												flag = 1;
												countLimit = limit;
												tokenn = tokens.length;	
												
												//Delete the key column from the B+tree
												tree = BTree.load( recman, recid );
												Object key = tokens[placeOfKey];
												System.out.println("the value is:"+key);
												String valueOfRowID = tokens[0];
												System.out.println("the row id:"+valueOfRowID);
												String nameOFPage = TableName+","+counterNoFiles;
												System.out.println("the name of the page:"+nameOFPage);
												Value k2 = new Value(valueOfRowID,nameOFPage);
												tree.remove(key);
												System.out.println("The value has been removed from the tree");
												recman.commit();
												
												
												//Delete the indexed column from the B+tree
												if(flagIndex == 1){
													
													tree1 = BTree.load( recman, recid1 );
													Object key1 = tokens[placeOfIndexCol];
													System.out.println("the value of index is:"+key1);
													String valueOfRowID1 = tokens[0];
													System.out.println("the row id:"+valueOfRowID1);
													String nameOFPage1 = TableName+","+counterNoFiles;
													System.out.println("the name of the page:"+nameOFPage1);
													Value k1 = new Value(valueOfRowID1,nameOFPage1);
													tree1.remove(key1);
													System.out.println("The value has been removed from the indexed tree");
													recman.commit();
												}
											}
											else {System.out.println("t");}
											countLimit++;
										}// close of while(countLimit < limit)
										tokenn++;
										if(tokenn == tokens.length && flag != 1 ){
											//update content as it is
											fileContent.append(strLine);
											fileContent.append("\r\n");
										}
										
									}//close of 2nd condition operator == AND
									else{
										
										if (tokens[tokenn].equals(Elementsvalue[0]) && tokens.length == noColumns){
											noColumns1 = 0;
											while(noColumns1 < noColumns){
												newLine = newLine+tokens[noColumns1]+",";
												noColumns1++;
											}
											newLine = newLine+"###" ;
											fileContent.append(newLine);
											fileContent.append("\r\n");
											flag = 1;
											tokenn = tokens.length;
											
											//Delete the key column from the B+tree
											tree = BTree.load( recman, recid );
											Object key = tokens[placeOfKey];
											System.out.println("the value is:"+key);
											String valueOfRowID = tokens[0];
											System.out.println("the row id:"+valueOfRowID);
											String nameOFPage = TableName+","+counterNoFiles;
											System.out.println("the name of the page:"+nameOFPage);
											Value k2 = new Value(valueOfRowID,nameOFPage);
											tree.remove(key);
											System.out.println("The value has been removed from the tree");
											recman.commit();
											
											
											//Delete the indexed column from the B+tree
											if(flagIndex == 1){
												
												tree1 = BTree.load( recman, recid1 );
												Object key1 = tokens[placeOfIndexCol];
												System.out.println("the value of index is:"+key1);
												String valueOfRowID1 = tokens[0];
												System.out.println("the row id:"+valueOfRowID1);
												String nameOFPage1 = TableName+","+counterNoFiles;
												System.out.println("the name of the page:"+nameOFPage1);
												Value k1 = new Value(valueOfRowID1,nameOFPage1);
												tree1.remove(key1);
												System.out.println("The value has been removed from the indexed tree");
												recman.commit();
											}
										}
										else {tokenn++;}
										if(tokenn == tokens.length && flag != 1 ){
											//update content as it is
											fileContent.append(strLine);
											fileContent.append("\r\n");
										}
										
									}// close of 3rd condition if operator == null and only one value in the array
								}// close of the big else
							}//close of while(tokenn < tokens.length)
						}//close of if (tokens.length > 0)
					}// close of the while ((strLine = br.readLine()) != null)

					// Now fileContent will have updated content , which you can override into file
					FileWriter fstreamWrite = new FileWriter(DBFile.getDirectory()+"\\"+TableName+"\\"+TableName+","+counterNoFiles);
					BufferedWriter out = new BufferedWriter(fstreamWrite);
					out.write(fileContent.toString());
					out.close();
					//Close the input stream
					in.close();
					counterNoFiles ++;
				}//while(counterNoFiles <= noFiles)
			}// close of if(!hash.isEmpty())
		}// close of the try
		catch(Exception e){//Catch exception if any
			
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
			
			}
		
		
		return hash;
	}

}
