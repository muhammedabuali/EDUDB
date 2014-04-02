package edudb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
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

public class SelectCmd implements Command  {

	private String tableName;
	private String operator;
	private LinkedHashMap FinalResult ;

	public SelectCmd (String t  , String O ){

		tableName = t;
		operator = O;
		FinalResult = new LinkedHashMap<Integer, Object>();

	}

	public LinkedHashMap execute(LinkedHashMap htblColNameValue) {
		
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


		int counterArrayX = 0;
		int counter = -1;
		int placceOfColumn = 0;
		int[] columnsPosition = new int[ElementsName.length];
		String[] Trees = new String[ElementsName.length];

		try{
			while(counterArrayX < ElementsName.length){

				FileInputStream stream = new FileInputStream(DBFile.getDirectory()+"metadata.txt");
				DataInputStream in1 = new DataInputStream(stream);
				BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
				String stLine;

				while ((stLine = br1.readLine()) != null){

					String token[] = stLine.split(",");

					if(token[0].equals(tableName)&& token[1].equals(ElementsName[counterArrayX])){

						counter++;
						placceOfColumn = counter;
						placceOfColumn ++;
						columnsPosition[counterArrayX] = placceOfColumn;
					}else{

						if(token[0].equals(tableName)&& !token[1].equals(ElementsName[counterArrayX])){

							counter++;
						}
					}

					if(token[0].equals(tableName)&& token[1].equals(ElementsName[counterArrayX]) &&(token[3].equals("True")||token[4].equals("True"))){

						Trees[counterArrayX] = "hastree";
					}

					if(token[0].equals(tableName)&& token[1].equals(ElementsName[counterArrayX]) && token[3].equals("False") && token[4].equals("False")){

						Trees[counterArrayX] = "notree";
					}
				}
				counterArrayX++;
				counter = -1;
			}

		}catch(Exception e){

			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		// read the number of columns in a table
		int noColumns = 0;

		// this try is made to return the number of columns in a page
		try{

			FileInputStream stream = new FileInputStream(DBFile.getDirectory()+tableName+"/"+tableName+","+1);
			DataInputStream in1 = new DataInputStream(stream);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String stLine;
			while ((stLine = br1.readLine()) != null){
				String token[] = stLine.split(",");
				int h = token.length;
				if (token[h-1].equals("###")){
					h = h-1;
				}
				noColumns = h;
			}
		}catch(Exception e){
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		// to get the no of files inside a page
		int noFiles = new File(DBFile.getDirectory()+tableName).listFiles().length;
		int counterNoFiles = 1;

		int flag = 0;
		int counterArray = 0;

		try{

			if(!htblColNameValue.isEmpty()){

				while(counterNoFiles <= noFiles){
					// Open the file that is the first
					// command line parameter
					FileInputStream fstream = new FileInputStream(DBFile.getDirectory()+tableName+"/"+tableName+","+counterNoFiles );
					// Get the object of DataInputStream
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String strLine;
					//Read File Line By Line
					while ((strLine = br.readLine()) != null){// begin of the while
						String newLine = "";
						// Print the content on the console
						String tokens[] = strLine.split(",");
						if (tokens.length > 0){

							if(operator.equals("OR")){//open of condition operator == OR
								//System.out.println( Arrays.toString( Trees ) );
								//System.out.println( Arrays.toString( ElementsName ) );


								if(Trees[0].equals("notree")&& Trees[1].equals("notree")){

									if ((tokens[columnsPosition[0]].equals(Elementsvalue[0]) || tokens[columnsPosition[1]].equals(Elementsvalue[1])) && !strLine.endsWith(",###")){

										int noColumns1 = 0;
										while(noColumns1 < noColumns){
											newLine = newLine+tokens[noColumns1]+",";
											noColumns1++;
										}
										flag = 1;
										FinalResult.put(counterArray,newLine);
										counterArray++;

									}
								}

								//System.out.println(tokens[columnsPosition[0]]+"&&"+Elementsvalue[0]);
								if(Trees[0].equals("notree")&& Trees[1].equals("hastree")){
									
									if (tokens[columnsPosition[0]].equals(Elementsvalue[0])&& !strLine.endsWith(",###")){

										int noColumns1 = 0;
										while(noColumns1 < noColumns){
											newLine = newLine+tokens[noColumns1]+",";
											noColumns1++;
										}
										flag = 1;
										FinalResult.put(counterArray,newLine);
										counterArray++;
									}

									if (tokens[columnsPosition[1]].equals(Elementsvalue[1])&& !tokens[columnsPosition[0]].equals(Elementsvalue[0]) ){

										// try to reload an existing B+Tree of the key field
										String DATABASE = tableName;
										String BTREE_NAME = ElementsName[1];
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
											//System.out.println( "Reloaded existing BTree with " + tree.size() );
										}

										tree = BTree.load( recman, recid );
										Object key = Elementsvalue[1];
										//System.out.println("The value to be searched for :"  +key);
										String nameOfPage = tableName+"," + counterNoFiles; // name of the page
										//System.out.println("Name of the page is" + nameOfPage);
										String rowID = tokens[0];// the ID of the row
										//System.out.println("The row ID is "  + rowID);
										Value temp = new Value(rowID, nameOfPage);
										tree.find(key);
										Value val = (Value)tree.find(key);
										String rowNo = val.rowID;
										String y = val.nameOfPage;
										//System.out.println(rowNo+y);
										//flag = 1;
										if(tokens[0].equals(rowNo)){

											int noColumns1 = 0;
											while(noColumns1 < noColumns){
												newLine = newLine+tokens[noColumns1]+",";
												noColumns1++;
											}
											FinalResult.put(counterArray,newLine);
											counterArray++;
										}
									}

								}

								//System.out.println(tokens[columnsPosition[1]]+"&&"+Elementsvalue[1]);
								if(Trees[0].equals("hastree")&& Trees[1].equals("notree")){

									if (tokens[columnsPosition[1]].equals(Elementsvalue[1])&& !strLine.endsWith(",###")){

										int noColumns1 = 0;
										while(noColumns1 < noColumns){
											newLine = newLine+tokens[noColumns1]+",";
											noColumns1++;
										}
										flag = 1;
										FinalResult.put(counterArray,newLine);
										counterArray++;
									}

									if (tokens[columnsPosition[0]].equals(Elementsvalue[0])&& !tokens[columnsPosition[1]].equals(Elementsvalue[1])){

										// try to reload an existing B+Tree of the key field
										String DATABASE = tableName;
										String BTREE_NAME = ElementsName[0];
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

										tree = BTree.load( recman, recid );
										Object key = Elementsvalue[0];
										//System.out.println("The value to be searched for :"  +key);
										String nameOfPage = tableName+"," + counterNoFiles; // name of the page
										//System.out.println("Name of the page is" + nameOfPage);
										String rowID = tokens[0];// the ID of the row
										//System.out.println("The row ID is "  + rowID);
										Value temp = new Value(rowID, nameOfPage);
										tree.find(key);
										Value val = (Value)tree.find(key);
										String rowNo = val.rowID;
										String y = val.nameOfPage;
										//System.out.println(rowNo+y);
										flag = 1;
										if(tokens[0].equals(rowNo)){

											int noColumns1 = 0;
											while(noColumns1 < noColumns){
												newLine = newLine+tokens[noColumns1]+",";
												noColumns1++;
											}
											FinalResult.put(counterArray,newLine);
											counterArray++;
										}
									}
								}

								if(Trees[0].equals("hastree")&& Trees[1].equals("hastree")){

									if (tokens[columnsPosition[0]].equals(Elementsvalue[0])){

										// try to reload an existing B+Tree of the key field
										String DATABASE = tableName;
										String BTREE_NAME = ElementsName[0];
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
											//System.out.println( "Reloaded existing BTree with " + tree.size() );
										}

										tree = BTree.load( recman, recid );
										Object key = Elementsvalue[0];
										//System.out.println("The value to be searched for :"  +key);
										String nameOfPage = tableName+"," + counterNoFiles; // name of the page
										//System.out.println("Name of the page is" + nameOfPage);
										String rowID = tokens[0];// the ID of the row
										//System.out.println("The row ID is "  + rowID);
										Value temp = new Value(rowID, nameOfPage);
										tree.find(key);
										Value val = (Value)tree.find(key);
										String rowNo = val.rowID;
										String y = val.nameOfPage;
										//System.out.println(rowNo+y);
										flag = 1;
										if(tokens[0].equals(rowNo)){

											int noColumns1 = 0;
											while(noColumns1 < noColumns){
												newLine = newLine+tokens[noColumns1]+",";
												noColumns1++;
											}
											FinalResult.put(counterArray,newLine);
											counterArray++;
										}
									}

									if (tokens[columnsPosition[1]].equals(Elementsvalue[1])){

										// try to reload an existing B+Tree of the key field
										String DATABASE = tableName;
										String BTREE_NAME = ElementsName[1];
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
											//System.out.println( "Reloaded existing BTree with " + tree.size() );
										}

										tree = BTree.load( recman, recid );
										Object key = Elementsvalue[1];
										//System.out.println("The value to be searched for :"  +key);
										String nameOfPage = tableName+"," + counterNoFiles; // name of the page
										//System.out.println("Name of the page is" + nameOfPage);
										String rowID = tokens[0];// the ID of the row
										//System.out.println("The row ID is "  + rowID);
										Value temp = new Value(rowID, nameOfPage);
										tree.find(key);
										Value val = (Value)tree.find(key);
										String rowNo = val.rowID;
										String y = val.nameOfPage;
										//System.out.println(rowNo+y);
										flag = 1;
										if(tokens[0].equals(rowNo)){

											int noColumns1 = 0;
											while(noColumns1 < noColumns){
												newLine = newLine+tokens[noColumns1]+",";
												noColumns1++;
											}
											FinalResult.put(counterArray,newLine);
											counterArray++;
										}
									}
								}


							}// close of 1st condition operator == OR
							else{
								if(operator.equals("AND")){//open of condition operator = AND

									if(Trees[0].equals("notree")&& Trees[1].equals("notree")){
										
										if (tokens[columnsPosition[0]].equals(Elementsvalue[0])&& tokens[columnsPosition[1]].equals(Elementsvalue[1])&& !strLine.endsWith(",###")){
											int noColumns1 = 0;
											while(noColumns1 < noColumns){
												newLine = newLine+tokens[noColumns1]+",";
												noColumns1++;
											}
											flag = 1;
											FinalResult.put(counterArray,newLine);
											counterArray++;
										}
									}
									
									if(Trees[0].equals("notree")&& Trees[1].equals("hastree")){
										
										if (tokens[columnsPosition[0]].equals(Elementsvalue[0])&& tokens[columnsPosition[1]].equals(Elementsvalue[1]) && !strLine.endsWith(",###")){
											int noColumns1 = 0;
											while(noColumns1 < noColumns){
												newLine = newLine+tokens[noColumns1]+",";
												noColumns1++;
											}
											flag = 1;
											FinalResult.put(counterArray,newLine);
											counterArray++;
										}
									}
									
									if(Trees[0].equals("hastree")&& Trees[1].equals("notree")){
										
										if (tokens[columnsPosition[0]].equals(Elementsvalue[0])&& tokens[columnsPosition[1]].equals(Elementsvalue[1])&& !strLine.endsWith(",###")){
											int noColumns1 = 0;
											while(noColumns1 < noColumns){
												newLine = newLine+tokens[noColumns1]+",";
												noColumns1++;
											}
											flag = 1;
											FinalResult.put(counterArray,newLine);
											counterArray++;
										}
									}
									
									if(Trees[0].equals("hastree")&& Trees[1].equals("hastree")){
										
										if (tokens[columnsPosition[0]].equals(Elementsvalue[0])&& tokens[columnsPosition[1]].equals(Elementsvalue[1])){
											
											// try to reload an existing B+Tree of the key field
											String DATABASE = tableName;
											String BTREE_NAME = ElementsName[1];
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
												//System.out.println( "Reloaded existing BTree with " + tree.size() );
											}

											tree = BTree.load( recman, recid );
											Object key = Elementsvalue[1];
											//System.out.println("The value to be searched for :"  +key);
											String nameOfPage = tableName+"," + counterNoFiles; // name of the page
											//System.out.println("Name of the page is" + nameOfPage);
											String rowID = tokens[0];// the ID of the row
											//System.out.println("The row ID is "  + rowID);
											Value temp = new Value(rowID, nameOfPage);
											tree.find(key);
											Value val = (Value)tree.find(key);
											String rowNo = val.rowID;
											String y = val.nameOfPage;
											//System.out.println(rowNo+y);
											flag = 1;
											if(tokens[0].equals(rowNo)){

												int noColumns1 = 0;
												while(noColumns1 < noColumns){
													newLine = newLine+tokens[noColumns1]+",";
													noColumns1++;
												}
												FinalResult.put(counterArray,newLine);
												counterArray++;
											}
										}
									}
									


								}//close of 2nd condition operator == AND
								else{

									if(Trees[0].equals("notree")){
										
										if (tokens[columnsPosition[0]].equals(Elementsvalue[0]) && !strLine.endsWith(",###")){
											int noColumns1 = 0;
											while(noColumns1 < noColumns){
												newLine = newLine+tokens[noColumns1]+",";
												noColumns1++;
											}
											FinalResult.put(counterArray,newLine);
											counterArray++;
											flag = 1;
										}


									}else{

										if (tokens[columnsPosition[0]].equals(Elementsvalue[0])){

											// try to reload an existing B+Tree of the key field
											String DATABASE = tableName;
											String BTREE_NAME = ElementsName[0];
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
												//System.out.println( "Reloaded existing BTree with " + tree.size() );
											}

											tree = BTree.load( recman, recid );
											Object key = Elementsvalue[0];
											//System.out.println("The value to be searched for :"  +key);
											String nameOfPage = tableName+"," + counterNoFiles; // name of the page
											//System.out.println("Name of the page is" + nameOfPage);
											String rowID = tokens[0];// the ID of the row
											//System.out.println("The row ID is "  + rowID);
											Value temp = new Value(rowID, nameOfPage);
											tree.find(key);
											Value val = (Value)tree.find(key);
											String rowNo = val.rowID;
											//String y = val.nameOfPage;
											//System.out.println(rowNo+y);
											flag = 1;
											if(tokens[0].equals(rowNo)){

												int noColumns1 = 0;
												while(noColumns1 < noColumns){
													newLine = newLine+tokens[noColumns1]+",";
													noColumns1++;
												}
												FinalResult.put(counterArray,newLine);
												counterArray++;
											}
										}
									}




								}// close of 3rd condition if operator == null and only one value in the array
							}// close of the big else
						}//close of if (tokens.length > 0)
					}// close of the while ((strLine = br.readLine()) != null)


					//Close the input stream
					in.close();
					counterNoFiles++;
				}// close of while(counterNoFiles <= noFiles)

			}// close of if(!hash.isEmpty())
		}// close of the try
		catch(Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		if(flag == 1){
			System.out.println( FinalResult  );
		}else{
			String x = "Such row was Not found !";
			FinalResult.put(0,x);
			System.out.println( FinalResult);
		}
		return FinalResult;
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
			NoOfCSVFile = new BufferedReader(new FileReader("C:/Users/Azza/Desktop/workspace/edudb/config/DBDireApp.txt"));
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


}
