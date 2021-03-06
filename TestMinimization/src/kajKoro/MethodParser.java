package kajKoro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import javassist.bytecode.Descriptor.Iterator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class MethodParser {
    public static void listOfMethod(File projectDir) {
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            
            ArrayList<MinimizationClass> minimizer= new ArrayList<MinimizationClass>();
            ArrayList<String> Statements = new ArrayList<String>();
            ArrayList<String> methods = new ArrayList<String>();
            Map<String, String> methodMa= new HashMap<String, String>();
            Map<String, ArrayList<String>> methodMap= new HashMap<String, ArrayList<String>>();
            
            Set<String> outputMethodSet= new HashSet<>();
            try {
                new VoidVisitorAdapter<Void>() {
                	
                    @Override
            	 	public void visit(MethodDeclaration n, Void arg) {
                    	//method body
                        String x =String.valueOf(n.getBody());
                        
                        String methodName= n.getDeclarationAsString();
                        //System.out.println(methodName);
                        //System.out.println(x);
                        methodMa.put(methodName, x);
                        methods.add(methodName);
                       
                        String[] Split= x.split("\\n");
                        for(int i=0;i<Split.length;i++) {
								String singleStatement=Split[i];
							
								if(singleStatement.contains(";")) {
								
									outputMethodSet.add(singleStatement);
									Statements.add(singleStatement);
									
									//System.out.println(n.getName()+" "+singleStatement);
								}
								
								
                        }
                       
                        
                       methodMap.put(methodName, Statements);
                        
                       MinimizationClass mc= new  MinimizationClass(methodName,Statements);
                        //System.out.println(methodName);
                        //System.out.println(Statements);
                        
                        
                       minimizer.add(mc);
                       Statements.removeAll(Statements);
                  
         
                        super.visit(n, arg);
                        //System.out.println(x);
                    }
                    
                   
                    
                }.visit(JavaParser.parse(file), null);
                System.out.println(); // empty line
            } catch (ParseException | IOException e) {
                new RuntimeException(e);
            }
            
     	
            //System.out.println(outputMethodSet.toString());
            
            
//            for(int i=0;i<minimizer.size();i++) {
//            	//System.out.println(minimizer.get(i).stmts.toString());
//            }
           
            String output="";
            int count=0;
            //find redundant Test Statements
            
            for(int i=0;i<minimizer.size();i++) {
    			for(int j=i+1;j<minimizer.size();j++) {
    				for(int x=0;x<minimizer.get(i).stmts.size();x++){
    					for(int y=0;y<minimizer.get(j).stmts.size();y++) {
    						//System.out.println(minimizer.get(i).stmts.get(x));
    						if(minimizer.get(i).stmts.get(x).equals(minimizer.get(j).stmts.get(y))) {
    							
    							System.err.println(minimizer.get(i).name+" and "+minimizer.get(j).name+" have redundant test statements");
        						
    							System.err.println(minimizer.get(i).stmts.get(x));
    							
    							//
    							Map<String, String> methodMapTemp1 = new HashMap<String, String>();
    							Map<String, String> methodMapTemp2 = new HashMap<String, String>();
    							Set<String> nowStatements =  new HashSet<String>();
    							
   							 	for (Map.Entry<String, String> set : methodMa.entrySet()) {
   							 		if((set.getKey().contains(minimizer.get(i).name)) ) {
   							 			
   							 		String[] Split= set.getValue().split("\\n");
   							 		
   			                        for(int m=0;m<Split.length;m++) {
   											String singleStatement=Split[m];
   										
   											if(singleStatement.contains(";")) {
   											
   												nowStatements.add(singleStatement);
   											
   											}	
   											
   			                         }
   							 			
   							 			//nowStatements.add(set.getValue());
   							 			methodMapTemp1.put(set.getKey(), set.getValue());
										//System.out.println(set.getKey() + "" + set.getValue());
   							 		}
   								
   							 	}
   							 	for (Map.Entry<String, String> set : methodMa.entrySet()) {
							 		if((set.getKey().contains(minimizer.get(j).name)) ) {
							 			
							 			String[] Split= set.getValue().split("\\n");
							 			for(int n=0;n<Split.length;n++) {
   											String singleStatement=Split[n];
   										
   											if(singleStatement.contains(";")) {
   											
   												nowStatements.add(singleStatement);
   											
   											}	
   											
   			                         }
							 			
							 			//nowStatements.add(set.getValue());
							 			methodMapTemp1.put(set.getKey(), set.getValue());
										//System.out.println(set.getKey() + "" + set.getValue());
							 		}
								
							 	}
   							 	//methodMapTemp1.putAll(methodMapTemp2);
   							 	System.out.println(nowStatements);
   							 	//System.err.println(methodMapTemp1);
    							output=output.concat(minimizer.get(i).stmts.get(x));
    							
    							//output+=minimizer.get(i).stmts.get(x);
    								
    						}
    						
    					}
    				}
    			}
    		}
            System.out.println(output);
            System.err.println("Eshan");
            
            
               
           
            
//            
//            Map<String, String> methodTemp= new HashMap<String, String>();
//           
            for (Map.Entry<String,String> me : methodMa.entrySet()) { 
               // System.out.print(me.getKey() + ":"); 
                //System.out.println(me.getValue()); 
               
            } 
            // redundant test statements
            
            for(int i=0;i<Statements.size();i++) {
            	//System.out.println(Statements.get(i));	
            	for(int j=0;j<Statements.size();j++) {
            		if( (Statements.get(i).equals(Statements.get(j))&& (i!=j))) {
            			//System.out.println(Statements.get(i));
            		}
            	}
            }
              
        }).explore(projectDir);
    }

}