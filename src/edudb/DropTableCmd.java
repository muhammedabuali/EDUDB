package edudb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class DropTableCmd implements Command {
	
	private static String tableName;
	
	public DropTableCmd(String t){
		
		tableName = t;
	}
	
	public LinkedHashMap execute(LinkedHashMap htblColNameValue){
		
		String tmp = DBFile.getDirectory();
		String dir = tmp+"\\"+tableName;
		String tmpTree = "C:\\Users\\Azza\\Desktop\\workspace\\edudb";
		String treedb = tmpTree+"\\"+tableName+".db";
		String treelg = tmpTree+"\\"+tableName+".lg";
		
		File f = new File(dir);
		File t1 = new File(treedb);
		File t2 = new File(treelg);
		
		if(f.exists()){
			try{
				
				delete(f);
				delete(t1);
				delete(t2);
				
			}catch(IOException e){
	               e.printStackTrace();
	               System.exit(0);
	           }
			
			
		}else{
			
			System.out.println("The file you want to delete does not exist !!!");
		}
		
		try{
	 		
	 		FileInputStream stream = new FileInputStream(DBFile.getDirectory()+"metadata.txt");
			DataInputStream in1 = new DataInputStream(stream);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String stLine;
			StringBuilder fileContent = new StringBuilder();
			
			while ((stLine = br1.readLine()) != null){
				
				String token[] = stLine.split(",");
				if(token.length > 0){
					
					if(token[0].equals(tableName)){
						
						stLine = stLine.replace(tableName, "");
						
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
		
		
		return null;
	}
	
	 public static void delete(File file)
 	throws IOException{

 	if(file.isDirectory()){

 		//directory is empty, then delete it
 		if(file.list().length==0){

 		   file.delete();
 		   System.out.println("Directory is deleted : " 
                                              + file.getAbsolutePath());

 		}else{

 		   //list all the directory contents
     	   String files[] = file.list();

     	   for (String temp : files) {
     	      //construct the file structure
     	      File fileDelete = new File(file, temp);

     	      //recursive delete
     	     delete(fileDelete);
     	   }

     	   //check the directory again, if empty then delete it
     	   if(file.list().length==0){
        	     file.delete();
     	     System.out.println("Directory is deleted : " 
                                               + file.getAbsolutePath());
     	   }
 		}

 	}else{
 		//if file, then delete it
 		file.delete();
 		System.out.println("File is deleted : " + file.getAbsolutePath());
 	}
 	
 	
 }

}
