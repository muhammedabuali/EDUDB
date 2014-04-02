package edudb;

import gudusoft.gsqlparser.TBaseType;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.nodes.TAlterTableOption;
import gudusoft.gsqlparser.nodes.TColumnDefinition;
import gudusoft.gsqlparser.nodes.TColumnDefinitionList;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TConstraintList;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TJoin;
import gudusoft.gsqlparser.nodes.TJoinItem;
import gudusoft.gsqlparser.nodes.TMultiTarget;
import gudusoft.gsqlparser.nodes.TObjectNameList;
import gudusoft.gsqlparser.nodes.TResultColumn;
import gudusoft.gsqlparser.nodes.TViewAliasClause;
import gudusoft.gsqlparser.stmt.TAlterTableStatement;
import gudusoft.gsqlparser.stmt.TCreateIndexSqlStatement;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;
import gudusoft.gsqlparser.stmt.TCreateViewSqlStatement;
import gudusoft.gsqlparser.stmt.TDeleteSqlStatement;
import gudusoft.gsqlparser.stmt.TDropTableSqlStatement;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import gudusoft.gsqlparser.stmt.TUpdateSqlStatement;

import java.util.*;

public class AnalyzeSQLStmt {

   protected static void printConstraint(TConstraint constraint, Boolean outline){

       if (constraint.getConstraintName() != null){
           System.out.println("\t\tconstraint name:"+constraint.getConstraintName().toString());
       }

       switch(constraint.getConstraint_type()){
           case notnull:
               System.out.println("\t\tnot null");
               break;
           case primary_key:
               System.out.println("\t\tprimary key");
               if (outline){
                   String lcstr = "";
                   if (constraint.getColumnList() != null){
                       for(int k=0;k<constraint.getColumnList().size();k++){
                           if (k !=0 ){lcstr = lcstr+",";}
                           lcstr = lcstr+constraint.getColumnList().getObjectName(k).toString();
                       }
                       System.out.println("\t\tprimary key columns:"+lcstr);
                   }
               }
               break;
           case unique:
               System.out.println("\t\tunique key");
               if(outline){
                   String lcstr="";
                   if (constraint.getColumnList() != null){
                       for(int k=0;k<constraint.getColumnList().size();k++){
                           if (k !=0 ){lcstr = lcstr+",";}
                           lcstr = lcstr+constraint.getColumnList().getObjectName(k).toString();
                       }
                   }
                   System.out.println("\t\tcolumns:"+lcstr);
               }
               break;
           case check:
               System.out.println("\t\tcheck:"+constraint.getCheckCondition().toString());
               break;
           case foreign_key:
           case reference:
               System.out.println("\t\tforeign key");
               if(outline){
                   String lcstr="";
                   if (constraint.getColumnList() != null){
                       for(int k=0;k<constraint.getColumnList().size();k++){
                           if (k !=0 ){lcstr = lcstr+",";}
                           lcstr = lcstr+constraint.getColumnList().getObjectName(k).toString();
                       }
                   }
                   System.out.println("\t\tcolumns:"+lcstr);
               }
               System.out.println("\t\treferenced table:"+constraint.getReferencedObject().toString());
               if (constraint.getReferencedColumnList() != null){
                   String lcstr="";
                   for(int k=0;k<constraint.getReferencedColumnList().size();k++){
                       if (k !=0 ){lcstr = lcstr+",";}
                       lcstr = lcstr+constraint.getReferencedColumnList().getObjectName(k).toString();
                   }
                   System.out.println("\t\treferenced columns:"+lcstr);
               }
               break;
           default:
               break;
       }
   }

   protected static void analyzeStmt(TCustomSqlStatement stmt){

       switch(stmt.sqlstatementtype){
           case sstselect:
               analyzeSelectStmt((TSelectSqlStatement)stmt);
               break;
           case sstdelete:
               analyzedelete((TDeleteSqlStatement)stmt);
               break;
           case sstupdate:
               analyzeUpdateStmt((TUpdateSqlStatement)stmt);
               break;
           case sstinsert:
               analyzeInsertStmt((TInsertSqlStatement)stmt);
               break;
           case sstcreatetable:
               analyzeCreateTableStmt((TCreateTableSqlStatement)stmt);
               break;
           case sstaltertable:
               analyzeAlterTableStmt((TAlterTableStatement) stmt);
               break;
           case sstcreateview:
               analyzeCreateViewStmt((TCreateViewSqlStatement)stmt);
               break;
           case sstcreateindex:
        	   analyzeCreateindexStmt((TCreateIndexSqlStatement)stmt);
               break;
           case sstdroptable:
        	   analyzeDropTableStmt((TDropTableSqlStatement)stmt);
               break;
        	   
           default:
               System.out.println(stmt.sqlstatementtype.toString());
       }
   }
   
   protected static void printObjectNameList(TObjectNameList objList){
       for(int i=0;i<objList.size();i++){
           System.out.println(objList.getObjectName(i).toString());
       }

   }
   
   protected static void printColumnDefinitionList(TColumnDefinitionList cdl){
       for(int i=0;i<cdl.size();i++){
           System.out.println(cdl.getColumn(i).getColumnName());
       }
   }
   
   protected static void printConstraintList(TConstraintList cnl){
       for(int i=0;i<cnl.size();i++){
           printConstraint(cnl.getConstraint(i),true);
       }
   }

   protected static void printAlterTableOption(TAlterTableOption ato){
       System.out.println(ato.getOptionType());
       switch (ato.getOptionType()){
           case AddColumn:
               printColumnDefinitionList(ato.getColumnDefinitionList());
               break;
           case ModifyColumn:
               printColumnDefinitionList(ato.getColumnDefinitionList());
               break;
           case AlterColumn:
               System.out.println(ato.getColumnName().toString());
               break;
           case DropColumn:
               System.out.println(ato.getColumnName().toString());
               break;
           case SetUnUsedColumn:  //oracle
               printObjectNameList(ato.getColumnNameList());
               break;
           case DropUnUsedColumn:
               break;
           case DropColumnsContinue:
               break;
           case RenameColumn:
               System.out.println("rename "+ato.getColumnName().toString()+" to "+ato.getNewColumnName().toString());
               break;
           case ChangeColumn:   //MySQL
               System.out.println(ato.getColumnName().toString());
               printColumnDefinitionList(ato.getColumnDefinitionList());
               break;
           case RenameTable:   //MySQL
               System.out.println(ato.getColumnName().toString());
               break;
           case AddConstraint:
               printConstraintList(ato.getConstraintList());
               break;
           case AddConstraintIndex:    //MySQL
               if (ato.getColumnName() != null){
                   System.out.println(ato.getColumnName().toString());
               }
               printObjectNameList(ato.getColumnNameList());
               break;
           case AddConstraintPK:
           case AddConstraintUnique:
           case AddConstraintFK:
               if (ato.getConstraintName() != null){
                   System.out.println(ato.getConstraintName().toString());
               }
               printObjectNameList(ato.getColumnNameList());
               break;
           case ModifyConstraint:
               System.out.println(ato.getConstraintName().toString());
               break;
           case RenameConstraint:
               System.out.println("rename "+ato.getConstraintName().toString()+" to "+ato.getNewConstraintName().toString());
               break;
           case DropConstraint:
               System.out.println(ato.getConstraintName().toString());
               break;
           case DropConstraintPK:
               break;
           case DropConstraintFK:
               System.out.println(ato.getConstraintName().toString());
               break;
           case DropConstraintUnique:
               if (ato.getConstraintName() != null){ //db2
                   System.out.println(ato.getConstraintName());
               }

               if (ato.getColumnNameList() != null){//oracle
                   printObjectNameList(ato.getColumnNameList());
               }
               break;
           case DropConstraintCheck: //db2
               System.out.println(ato.getConstraintName());
               break;
           case DropConstraintPartitioningKey:
               break;
           case DropConstraintRestrict:
               break;
           case DropConstraintIndex:
               System.out.println(ato.getConstraintName());
               break;
           case DropConstraintKey:
               System.out.println(ato.getConstraintName());
               break;
           case AlterConstraintFK:
               System.out.println(ato.getConstraintName());
               break;
           case AlterConstraintCheck:
               System.out.println(ato.getConstraintName());
               break;
           case CheckConstraint:
               break;
           case OraclePhysicalAttrs:
           case toOracleLogClause:
           case OracleTableP:
           case MssqlEnableTrigger:
           case MySQLTableOptons:
           case Db2PartitioningKeyDef:
           case Db2RestrictOnDrop:
           case Db2Misc:
           case Unknown:
               break;
       }

   }
   
   protected static void analyzeDropTableStmt(TDropTableSqlStatement pStmt){
	   
	   String TableName = null;
	   LinkedHashMap<String , Object> nothing = new LinkedHashMap<String, Object>();
	   
	   if (pStmt.getTableName() != null){
		   
		   System.out.println("Table name:"+pStmt.getTableName().toString());
           TableName = pStmt.getTableName().toString();
	   }
	   
	   DropTableCmd x = new DropTableCmd(TableName);
       x.execute(nothing);
	   
   }
   
   protected static void analyzeCreateindexStmt(TCreateIndexSqlStatement pStmt){
	   
	   String TableName = null;
	   String ColName = null;
	   LinkedHashMap<String , Object> index = new LinkedHashMap<String, Object>();
	   
	   if (pStmt.getTableName() != null){
		   
		   System.out.println("Table name:"+pStmt.getTableName().toString());
           TableName = pStmt.getTableName().toString();
	   }
	   
	   if(pStmt.getIndexName() != null){
		   
		   System.out.println("Index Name:"+pStmt.getIndexName().toString());
		   ColName = pStmt.getIndexName().toString();
	   }
	   
	   if(pStmt.getColumnNameList() != null){
		   
		   System.out.println("Column Name:"+pStmt.getColumnNameList().toString());
	   }
	   
	   CreateIndexCmd x = new CreateIndexCmd(TableName, ColName);
       x.execute(index);
	   
	   
   }

   
   protected static void analyzedelete(TDeleteSqlStatement pStmt)
   {
	   LinkedHashMap<String , Object> WHERE = new LinkedHashMap<String, Object>();
	   String TableName = null;
	   String strOperator = null;
	   
       System.out.println("Delete statement:");

       //System.out.println(DeleteStmtInfo(pStmt));
       
       if (pStmt.getTargetTable() != null){
           System.out.println("Table name:"+pStmt.getTargetTable().toString());
           TableName = pStmt.getTargetTable().toString();
       }
       
       if(pStmt.getWhereClause() != null){
           System.out.println("where clause:\n"+pStmt.getWhereClause().getCondition().toString());
           
           String line = pStmt.getWhereClause().getCondition().toString();
           String[] arrayLine = line.split(" ");
           System.out.println( Arrays.toString( arrayLine ) );
           String condition = null;
           for(int z=0 ; z<arrayLine.length ; z++){
        	   
        	   if(arrayLine[z].equals("AND")){
        		   
        		   condition = "AND";
        		   break;
        	   }
        	   
        	   if(arrayLine[z].equals("OR")){
        		   
        		   condition = "OR";
        		   break;
        	   }
        	   
        	   condition = "Empty";
           }
           strOperator = condition;
           System.out.println(condition);
           
           if(condition.equals("OR")){
        	   
        	   String regex = "\\bOR\\b";
        	   String lineModified = line.replaceAll(regex, "=");
        	   System.out.println(lineModified );
        	   String[] arrayLine2 = lineModified .split(" = ");
        	   System.out.println( Arrays.toString( arrayLine2 ) );
        	   System.out.println(arrayLine2.length);
        	   for(int g=0; g<arrayLine2.length; g=g+2){
        		   
        		   WHERE.put(arrayLine2[g], arrayLine2[g+1]);
        	   }
           }
           
           if(condition.equals("AND")){
        	   
        	   String regex = "\\bAND\\b";
        	   String lineModified = line.replaceAll(regex, "=");
        	   System.out.println(lineModified );
        	   String[] arrayLine2 = lineModified .split(" = ");
        	   System.out.println( Arrays.toString( arrayLine2 ) );
        	   System.out.println(arrayLine2.length);
        	   for(int g=0; g<arrayLine2.length; g=g+2){
        		   
        		   WHERE.put(arrayLine2[g], arrayLine2[g+1]);
        	   }
           }
           
           if(condition.equals("Empty")){
        	   
        	   String[] arrayLine2 = line.split(" = ");
        	   System.out.println( Arrays.toString( arrayLine2 ) );
        	   WHERE.put(arrayLine2[0], arrayLine2[1]);
           }
       }
       
       DeleteCmd x = new DeleteCmd(TableName, strOperator);
       x.execute(WHERE);
       
   }
   
   
   protected static void analyzeCreateViewStmt(TCreateViewSqlStatement pStmt){
       TCreateViewSqlStatement createView = pStmt;
       System.out.println("View name:"+createView.getViewName().toString());
       TViewAliasClause aliasClause = createView.getViewAliasClause();
       for(int i=0;i<aliasClause.getViewAliasItemList().size();i++){
           System.out.println("View alias:"+aliasClause.getViewAliasItemList().getViewAliasItem(i).toString());
       }

       System.out.println("View subquery: \n"+ createView.getSubquery().toString() );
   }

   protected static void analyzeSelectStmt(TSelectSqlStatement pStmt){
	   
	   String TableName = null;
	   String strOperator  = null;
	   LinkedHashMap<String , Object> WHERE = new LinkedHashMap<String, Object>();
	   
	   
       System.out.println("\nSelect:");
       if (pStmt.isCombinedQuery()){
           String setstr="";
           switch (pStmt.getSetOperator()){
               case 1: setstr = "union";break;
               case 2: setstr = "union all";break;
               case 3: setstr = "intersect";break;
               case 4: setstr = "intersect all";break;
               case 5: setstr = "minus";break;
               case 6: setstr = "minus all";break;
               case 7: setstr = "except";break;
               case 8: setstr = "except all";break;
           }
           System.out.printf("set type: %s\n",setstr);
           System.out.println("left select:");
           analyzeSelectStmt(pStmt.getLeftStmt());
           System.out.println("right select:");
           analyzeSelectStmt(pStmt.getRightStmt());
           if (pStmt.getOrderbyClause() != null){
               System.out.printf("order by clause %s\n",pStmt.getOrderbyClause().toString());
           }
       }else{
           //select list
           for(int i=0; i < pStmt.getResultColumnList().size();i++){
               TResultColumn resultColumn = pStmt.getResultColumnList().getResultColumn(i);
               System.out.printf("Column: %s, Alias: %s\n",resultColumn.getExpr().toString(), (resultColumn.getAliasClause() == null)?"":resultColumn.getAliasClause().toString());
           }

           //from clause, check this document for detailed information
           //http://www.sqlparser.com/sql-parser-query-join-table.php
           for(int i=0;i<pStmt.joins.size();i++){
               TJoin join = pStmt.joins.getJoin(i);
               switch (join.getKind()){
                   case TBaseType.join_source_fake:
                       System.out.printf("table: %s, alias: %s\n",join.getTable().toString(),(join.getTable().getAliasClause() !=null)?join.getTable().getAliasClause().toString():"");
                       TableName = join.getTable().toString();
                       break;
                   case TBaseType.join_source_table:
                       System.out.printf("table: %s, alias: %s\n",join.getTable().toString(),(join.getTable().getAliasClause() !=null)?join.getTable().getAliasClause().toString():"");
                       for(int j=0;j<join.getJoinItems().size();j++){
                           TJoinItem joinItem = join.getJoinItems().getJoinItem(j);
                           System.out.printf("Join type: %s\n",joinItem.getJoinType().toString());
                           System.out.printf("table: %s, alias: %s\n",joinItem.getTable().toString(),(joinItem.getTable().getAliasClause() !=null)?joinItem.getTable().getAliasClause().toString():"");
                           if (joinItem.getOnCondition() != null){
                               System.out.printf("On: %s\n",joinItem.getOnCondition().toString());
                           }else  if (joinItem.getUsingColumns() != null){
                               System.out.printf("using: %s\n",joinItem.getUsingColumns().toString());
                           }
                       }
                       break;
                   case TBaseType.join_source_join:
                       TJoin source_join = join.getJoin();
                       System.out.printf("table: %s, alias: %s\n",source_join.getTable().toString(),(source_join.getTable().getAliasClause() !=null)?source_join.getTable().getAliasClause().toString():"");

                       for(int j=0;j<source_join.getJoinItems().size();j++){
                           TJoinItem joinItem = source_join.getJoinItems().getJoinItem(j);
                           System.out.printf("source_join type: %s\n",joinItem.getJoinType().toString());
                           System.out.printf("table: %s, alias: %s\n",joinItem.getTable().toString(),(joinItem.getTable().getAliasClause() !=null)?joinItem.getTable().getAliasClause().toString():"");
                           if (joinItem.getOnCondition() != null){
                               System.out.printf("On: %s\n",joinItem.getOnCondition().toString());
                           }else  if (joinItem.getUsingColumns() != null){
                               System.out.printf("using: %s\n",joinItem.getUsingColumns().toString());
                           }
                       }

                       for(int j=0;j<join.getJoinItems().size();j++){
                           TJoinItem joinItem = join.getJoinItems().getJoinItem(j);
                           System.out.printf("Join type: %s\n",joinItem.getJoinType().toString());
                           System.out.printf("table: %s, alias: %s\n",joinItem.getTable().toString(),(joinItem.getTable().getAliasClause() !=null)?joinItem.getTable().getAliasClause().toString():"");
                           if (joinItem.getOnCondition() != null){
                               System.out.printf("On: %s\n",joinItem.getOnCondition().toString());
                           }else  if (joinItem.getUsingColumns() != null){
                               System.out.printf("using: %s\n",joinItem.getUsingColumns().toString());
                           }
                       }

                       break;
                   default:
                       System.out.println("unknown type in join!");
                       break;
               }
           }

           //where clause
           if (pStmt.getWhereClause() != null){
               System.out.printf("where clause: \n%s\n", pStmt.getWhereClause().toString());
               String line = pStmt.getWhereClause().getCondition().toString();
               String[] arrayLine = line.split(" ");
               //System.out.println( Arrays.toString( arrayLine ) );
               String condition = null;
               for(int z=0 ; z<arrayLine.length ; z++){
            	   
            	   if(arrayLine[z].equals("AND")){
            		   
            		   condition = "AND";
            		   break;
            	   }
            	   
            	   if(arrayLine[z].equals("OR")){
            		   
            		   condition = "OR";
            		   break;
            	   }
            	   
            	   condition = "Empty";
               }
               strOperator = condition;
               //System.out.println(condition);
               if(condition.equals("OR")){
            	   
            	   String regex = "\\bOR\\b";
            	   String lineModified = line.replaceAll(regex, "=");
            	   System.out.println(lineModified );
            	   String[] arrayLine2 = lineModified .split(" = ");
            	   //System.out.println( Arrays.toString( arrayLine2 ) );
            	   //System.out.println(arrayLine2.length);
            	   for(int g=0; g<arrayLine2.length; g=g+2){
            		   
            		   WHERE.put(arrayLine2[g], arrayLine2[g+1]);
            	   }
               }
               
               if(condition.equals("AND")){
            	   
            	   String regex = "\\bAND\\b";
            	   String lineModified = line.replaceAll(regex, "=");
            	   //System.out.println(lineModified );
            	   String[] arrayLine2 = lineModified .split(" = ");
            	   //System.out.println( Arrays.toString( arrayLine2 ) );
            	   //System.out.println(arrayLine2.length);
            	   for(int g=0; g<arrayLine2.length; g=g+2){
            		   
            		   WHERE.put(arrayLine2[g], arrayLine2[g+1]);
            	   }
               }
               
               if(condition.equals("Empty")){
            	   
            	   String[] arrayLine2 = line.split(" = ");
            	   //System.out.println( Arrays.toString( arrayLine2 ) );
            	   WHERE.put(arrayLine2[0], arrayLine2[1]);
               }
           }

           // group by
           if (pStmt.getGroupByClause() != null){
               System.out.printf("group by: \n%s\n",pStmt.getGroupByClause().toString());
           }

           // order by
           if (pStmt.getOrderbyClause() != null){
             System.out.printf("order by: \n%s\n",pStmt.getOrderbyClause().toString());
           }

           // for update
           if (pStmt.getForUpdateClause() != null){
               System.out.printf("for update: \n%s\n",pStmt.getForUpdateClause().toString());
           }

           // top clause
           if (pStmt.getTopClause() != null){
               System.out.printf("top clause: \n%s\n",pStmt.getTopClause().toString());
           }

           // limit clause
           if (pStmt.getLimitClause() != null){
               System.out.printf("top clause: \n%s\n",pStmt.getLimitClause().toString());
           }
       }
       
       SelectCmd x = new SelectCmd(TableName, strOperator);
	   x.execute(WHERE);
   }

   protected static void analyzeInsertStmt(TInsertSqlStatement pStmt){
	   
	   String TableName = null;
	   LinkedHashMap<String, Object> Row = new LinkedHashMap<String, Object>();
	   String[] colNames = new String[pStmt.getColumnList().size()];
	   Object[] colValues = new String[pStmt.getColumnList().size()];
	   
       if (pStmt.getTargetTable() != null){
           System.out.println("Table name:"+pStmt.getTargetTable().toString());
           TableName = pStmt.getTargetTable().toString();
       }

       System.out.println("insert value type:"+pStmt.getValueType());

       if (pStmt.getColumnList() != null){
           System.out.println("columns:");
           for(int i=0;i<pStmt.getColumnList().size();i++){
               System.out.println("\t"+pStmt.getColumnList().getObjectName(i).toString());
               colNames[i] = pStmt.getColumnList().getObjectName(i).toString();
           }
       }

       if (pStmt.getValues() != null){
           System.out.println("values:");
           for(int i=0;i<pStmt.getValues().size();i++){
               TMultiTarget mt = pStmt.getValues().getMultiTarget(i);
               for(int j=0;j<mt.getColumnList().size();j++){
                   System.out.println("\t"+mt.getColumnList().getResultColumn(j).toString());
                   colValues[j] = mt.getColumnList().getResultColumn(j).toString();
               }
           }
       }

       if (pStmt.getSubQuery() != null){
           analyzeSelectStmt(pStmt.getSubQuery());
       }
       
       for(int z=0 ; z<colNames.length ; z++){
    	   
    	   Row.put(colNames[z], colValues[z]);
       }
       
       InsertCmd x = new InsertCmd(TableName);
		x.execute(Row);
   }
   
   protected static void analyzeUpdateStmt(TUpdateSqlStatement pStmt){
       System.out.println("Table Name:"+pStmt.getTargetTable().toString());
       String tableName = pStmt.getTargetTable().toString();
       LinkedHashMap<String, Object> SET = new LinkedHashMap<String, Object>();
       LinkedHashMap<String, Object> WHERE = new LinkedHashMap<String, Object>();
       System.out.println("set clause:");
       for(int i=0;i<pStmt.getResultColumnList().size();i++){
           TResultColumn resultColumn = pStmt.getResultColumnList().getResultColumn(i);
           TExpression expression = resultColumn.getExpr();
           System.out.println("\tcolumn:"+expression.getLeftOperand().toString()+"\tvalue:"+expression.getRightOperand().toString());
           String colName = expression.getLeftOperand().toString();
           String value = expression.getRightOperand().toString();
           SET.put(colName,value);
       }
       if(pStmt.getWhereClause() != null){
           System.out.println("where clause:\n"+pStmt.getWhereClause().getCondition().toString());
           String tmp = pStmt.getWhereClause().getCondition().toString();
           String[] where = tmp.split(" = ");
           System.out.println( Arrays.toString( where ) );
           for(int z=0 ; z<where.length ; z = z+2){
        	   
        	   String ColName = where[z];
        	   String ColVal = where[z+1];
        	   WHERE.put(ColName,ColVal);
           }
       }
       UpdateCmd x  = new UpdateCmd(tableName,WHERE);
       x.execute(SET);
   }

    protected static void analyzeAlterTableStmt(TAlterTableStatement pStmt){
        System.out.println("Table Name:"+pStmt.getTableName().toString());
        System.out.println("Alter table options:");
        for(int i=0;i<pStmt.getAlterTableOptionList().size();i++){
            printAlterTableOption(pStmt.getAlterTableOptionList().getAlterTableOption(i));
        }
    }

   protected static void analyzeCreateTableStmt(TCreateTableSqlStatement pStmt){
       System.out.println("Table Name:"+pStmt.getTargetTable().toString());
       String TableName = pStmt.getTargetTable().toString();
       LinkedHashMap<String, Object> table = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> reference = new LinkedHashMap<String, Object>();
       String KeyColName = null;
       System.out.println("Columns:");
       TColumnDefinition column;
       if (RA.db_schema == null){
           RA.db_schema = new HashMap<String, ArrayList<String>>();
       }

       ArrayList<String> ColumnNames = new ArrayList<String>();
       for(int i=0;i<pStmt.getColumnList().size();i++){
           column = pStmt.getColumnList().getColumn(i);
           System.out.println("\tname:"+column.getColumnName().toString());
           ColumnNames.add(column.getColumnName().toString());
           System.out.println("\tdatetype:"+column.getDatatype().toString());
           table.put(column.getColumnName().toString(), column.getDatatype().toString());
           if (column.getDefaultExpression() != null){
               System.out.println("\tdefault:"+column.getDefaultExpression().toString());
           }
           if (column.isNull()){
               System.out.println("\tnull: yes");
           }
           if (column.getConstraints() != null){
               System.out.println("\tinline constraints:");
               for(int j=0;j<column.getConstraints().size();j++){
                   printConstraint(column.getConstraints().getConstraint(j),false);
                   
                   
                   switch(column.getConstraints().getConstraint(j).getConstraint_type()){
                   
                   case primary_key:
                	   KeyColName = column.getColumnName().toString();
                	   System.out.println("KeyColName:"+KeyColName);
                	   break;
                	   
                   default: break;
                   }
               }
               
           }
           System.out.println("");
       }

       RA.db_schema.put(TableName,ColumnNames);
       if(pStmt.getTableConstraints().size() > 0){
           System.out.println("\toutline constraints:");
           for(int i=0;i<pStmt.getTableConstraints().size();i++){
        	   
             printConstraint(pStmt.getTableConstraints().getConstraint(i), true);
             System.out.println(""); 
             String refTable = pStmt.getTableConstraints().getConstraint(i).getReferencedObject().toString();
             String refCols = pStmt.getTableConstraints().getConstraint(i).getReferencedColumnList().toString();
             String columns = pStmt.getTableConstraints().getConstraint(i).getColumnList().toString();
             String[] RefCols = refCols.split(",");
             String[] cols = columns.split(",");
             //System.out.println(Arrays.toString( RefCols ));
             for(int ninja=0; ninja<cols.length; ninja++){
            	 
            	 String tmp = refTable+"."+RefCols[ninja];
            	 String tmp0 = cols[ninja];
            	 reference.put(tmp0,tmp);
             }
             
           }
       }
       
       CreateTableCmd x = new CreateTableCmd(TableName,KeyColName,reference);
       x.execute(table);
   }
      
}//close of class

