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

public class UpdateCmd implements Command {

	private String tableName;
	private LinkedHashMap<String , Object> WHERE;
	
	public UpdateCmd (String t , LinkedHashMap<String , Object> W){
		
		tableName = t;
		WHERE = W;
	}
	
	public LinkedHashMap execute(LinkedHashMap htblColNameValue) {
		
		try
		{
			File f = new File(DBFile.getDirectory()+"\\"+tableName);
			if(!f.exists()) { 
				throw new DBAppException("");
			}
		}
		catch(DBAppException ex)
		{
			System.out.println("please enter a valid page name !!!");
			System.exit(1);
		}
		
		if(htblColNameValue.isEmpty()){

			System.out.println("Must insert data first!");
		}
		
		String ElementName;
		String ElementValue;
		int size = htblColNameValue.size();
		Set s =htblColNameValue.entrySet();
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
		
		
			String colName;
			String colValue;
			int length = WHERE.size();
			Set z = WHERE.entrySet();
			Iterator i1 = z.iterator();
			String [] colsName = new String[length];
			String [] colsValue = new String[length];
			int c = 0;
			
			// to convert hashtable WHERE into 2 arrays colsName[] & colsValue[]
			while(i1.hasNext()){
				
				Map.Entry m=(Map.Entry)i1.next();
				colName = (String)m.getKey();
				colValue = (String)m.getValue();
				colsName[c] = colName;
				colsValue[c] = colValue;
				c++;
			}
			
			
			int counterWhere = -1;
			// this variable is used to know the place of the column in the metadata file
			int placceOfColumnWhere = 0;
			// this counter is used to loop on colsName array(contain names of columns) 
			//and to match with meta data file
			int counterArrayWhere = 0;
			// this array contains the exact place of the columns in the metadata file
			int[] columnsPositionWhere = new int[colsName.length];
			
			if(!WHERE.isEmpty()){
				
				try{
					
					while(counterArrayWhere < colsName.length){
						
						FileInputStream stream = new FileInputStream(DBFile.getDirectory()+"metadata.txt");
						DataInputStream in1 = new DataInputStream(stream);
						BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
						String stLine; 
						
						while ((stLine = br1.readLine()) != null){
							
							String[] tok = stLine.split(",");
							
							if(tok[0].equals(tableName)&& tok[1].equals(colsName[counterArrayWhere])){
								
								counterWhere++;
								placceOfColumnWhere = counterWhere;
								placceOfColumnWhere ++;
								columnsPositionWhere[counterArrayWhere] = placceOfColumnWhere;
								
							}else{
								
								if(tok[0].equals(tableName)&& !tok[1].equals(colsName[counterArrayWhere])){
									counterWhere++;
								}
							}
						}
						
						counterArrayWhere ++;
						counterWhere = -1;
						stream.close();
					}
					
				}catch(Exception e){
					
					System.err.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		
				
		int counter = -1;
		
		// this variable is used to know the place of the column in the metadata file
		int placceOfColumn = 0;
		
		// this counter is used to loop on ElementsName array(contain names of columns) 
		//and to match with meta data file
		int counterArray = 0;
		
		// this array contains the exact place of the columns in the metadata file
		int[] columnsPosition = new int[ElementsName.length];
		
		String[] Trees = new String[ElementsName.length];
		
		//This try is used to get the positions of ElementsName[] columns from metadata file
		try{
			
			while(counterArray < ElementsName.length){
				
				FileInputStream stream = new FileInputStream(DBFile.getDirectory()+"metadata.txt");
				DataInputStream in1 = new DataInputStream(stream);
				BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
				String stLine;
				
				while ((stLine = br1.readLine()) != null){
					
					String token[] = stLine.split(",");

					if(token[0].equals(tableName)&& token[1].equals(ElementsName[counterArray])){
						
						counter++;
						placceOfColumn = counter;
						placceOfColumn ++;
						columnsPosition[counterArray] = placceOfColumn;
						
					}else{
						
						if(token[0].equals(tableName)&& !token[1].equals(ElementsName[counterArray])){
							
							counter++;
						}
					}
					
					if(token[0].equals(tableName)&& token[1].equals(ElementsName[counterArray]) && (token[3].equals("True")||token[4].equals("True"))){
						
						Trees[counterArray] = "hastree";
					}
					
					if(token[0].equals(tableName)&& token[1].equals(ElementsName[counterArray])&& token[3].equals("False") && token[4].equals("False")){
						
						Trees[counterArray] = "notree";
					}
				}
				
				System.out.println( Arrays.toString( Trees) );
				counterArray ++;
				counter = -1;
			}
			
		}catch(Exception e){
			
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		int counterX = 0;//SET 
		int counterXX = 0; // WHERE
		// to get the no of files inside a page
		int noFiles = new File(DBFile.getDirectory()+""+tableName).listFiles().length;
		int counterNoFiles = 1;
		int counterToken = 0;
		
		try{
			
			while(counterNoFiles <= noFiles){
				
				// Open the file that is the first
				// command line parameter
				FileInputStream fstream = new FileInputStream(DBFile.getDirectory()+"\\"+tableName+"\\"+tableName+","+counterNoFiles);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				StringBuilder fileContent = new StringBuilder();
				
				if(WHERE.isEmpty()){
					//Read File Line By Line
					while ((strLine = br.readLine()) != null){
						
						String newLine = "";
						String tokens[] = strLine.split(",");
						if (tokens.length > 0){
							
							counterX = 0;
							counterToken = 0;
							
							while(counterX < columnsPosition.length){
								
								// update the column value in the file with the new value
								tokens[columnsPosition[counterX]] = Elementsvalue[counterX];
								
								
								if(!Trees[counterX].equals("notree")){
									
									// try to reload an existing B+Tree of the key field
									String DATABASE = tableName;
									String BTREE_NAME = ElementsName[counterX];
									long recid;
									RecordManager recman;
									BTree tree;
									Properties props;
									props = new Properties();
									
									// open database and setup an object cache
									recman = RecordManagerFactory.createRecordManager( DATABASE, props );
									
									// try to reload an existing B+Tree of the key field
									recid = recman.getNamedObject(BTREE_NAME);
									if ( recid != 0 ) {
						                tree = BTree.load( recman, recid );
						                System.out.println( "Reloaded existing BTree with " + tree.size() );
						            }
									
									//update the B+tree with the new value
									tree = BTree.load( recman, recid );
									String nameOfPage = tableName+"," + counterNoFiles; // name of the page
									System.out.println("Name of the page is" + nameOfPage);
									String rowID = tokens[0];// the ID of the row
									System.out.println("The row ID is "  + rowID);
									Object value = Elementsvalue[counterX];// the value to be inserted in the tree
									System.out.println("The value to be inserted in the tree"  +value);
									Value temp = new Value(rowID, nameOfPage);
									tree.insert(value,temp,true);
									recman.commit();
								}
								
								
								counterX++;
							}
							
							while(counterToken < tokens.length){
								
								newLine = newLine+tokens[counterToken]+",";
								counterToken++;
							}
							
							fileContent.append(newLine);
							fileContent.append("\r\n");
						}
					}
				}else{
					
					//Read File Line By Line
					while ((strLine = br.readLine()) != null){
						
						String newLine = "";
						String tokens[] = strLine.split(",");
						if (tokens.length > 0){
							
							counterXX = 0;
							
							int flag = 0;
							while(counterXX < columnsPositionWhere.length){
								
								if(tokens[columnsPositionWhere[counterXX]].equals(colsValue[counterXX])){
									
									flag = 1;
								}else{
									
									flag = -1;
									break;
								}
								counterXX++;
							}
							
							// The WHERE statment is true
							if(flag == 1){
								
								counterX = 0;
								counterToken = 0;
								
								while(counterX < columnsPosition.length){
									
									// update the column value in the file with the new value
									tokens[columnsPosition[counterX]] = Elementsvalue[counterX];
									
									if(!Trees[counterX].equals("notree")){
										
										// try to reload an existing B+Tree of the key field
										String DATABASE = tableName;
										String BTREE_NAME = ElementsName[counterX];
										long recid;
										RecordManager recman;
										BTree tree;
										Properties props;
										props = new Properties();
										
										// open database and setup an object cache
										recman = RecordManagerFactory.createRecordManager( DATABASE, props );
										
										// try to reload an existing B+Tree of the key field
										recid = recman.getNamedObject(BTREE_NAME);
										if ( recid != 0 ) {
							                tree = BTree.load( recman, recid );
							                System.out.println( "Reloaded existing BTree with " + tree.size() );
							            }
										
										//update the B+tree with the new value
										tree = BTree.load( recman, recid );
										String nameOfPage = tableName+"," + counterNoFiles; // name of the page
										System.out.println("Name of the page is" + nameOfPage);
										String rowID = tokens[0];// the ID of the row
										System.out.println("The row ID is "  + rowID);
										Object value = Elementsvalue[counterX];// the value to be inserted in the tree
										System.out.println("The value to be inserted in the tree"  +value);
										Value temp = new Value(rowID, nameOfPage);
										tree.insert(value,temp,true);
										recman.commit();
									}
									
									counterX++;
								}
								
								while(counterToken < tokens.length){
									
									newLine = newLine+tokens[counterToken]+",";
									counterToken++;
								}
								
								fileContent.append(newLine);
								fileContent.append("\r\n");
							}else{
								
								counterToken = 0;
								while(counterToken < tokens.length){
									
									newLine = newLine+tokens[counterToken]+",";
									counterToken++;
								}
								fileContent.append(newLine);
								fileContent.append("\r\n");
							}
						}// close of if (tokens.length > 0)
					}// close while ((strLine = br.readLine()) != null)
				}
				
				
				// Now fileContent will have updated content , which you can override into file
				FileWriter fstreamWrite = new FileWriter(DBFile.getDirectory()+"\\"+tableName+"\\"+tableName+","+counterNoFiles);
				BufferedWriter out = new BufferedWriter(fstreamWrite);
				out.write(fileContent.toString());
				out.close();
				//Close the input stream
				in.close();
				counterNoFiles++;
			}
		}catch(Exception e){
			
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
