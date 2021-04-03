package project;

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


public class RedundantFinder {
    public static void listOfRedundant(File projectDir) {
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            
            ArrayList<RedundantClass> minimizer= new ArrayList<RedundantClass>();
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
                        //System.out.println(n.getBeginLine());
                        methodMa.put(methodName, x);
                        methods.add(methodName);
                       
                        
                        String[] Split= x.split("\\n");
                        for(int i=0;i<Split.length;i++) {
								String singleStatement=Split[i];
							
								if(singleStatement.contains(";")) {
								
									outputMethodSet.add(singleStatement);
									Statements.add(singleStatement);
									
								}								
                        }
                             
                       methodMap.put(methodName, Statements);
                        
                       RedundantClass mc= new  RedundantClass(methodName,Statements);    
                       minimizer.add(mc);
                       Statements.removeAll(Statements);
                       
                      
                       super.visit(n, arg);
                    }
               
                }.visit(JavaParser.parse(file), null);
                System.out.println(); // empty line
            } catch (ParseException | IOException e) {
                new RuntimeException(e);
            }
            
     	
            for (Map.Entry<String, String> set : methodMa.entrySet()) {	
					System.out.println(set.getKey() + " " + set.getValue());
			 }
            
            
            Set<String> redundant= new HashSet<>();

            //
            for(int i=0;i<minimizer.size();i++) {
    			for(int j=i+1;j<minimizer.size();j++) {
    				for(int m=0;m<minimizer.get(i).stmts.size();m++){
    					for(int y=0;y<minimizer.get(j).stmts.size();y++) {
    						//System.out.println(minimizer.get(i).stmts.get(x));
    						if(minimizer.get(i).stmts.get(m).equals(minimizer.get(j).stmts.get(y))) {
    							
    							System.out.println(minimizer.get(i).name+" and "+minimizer.get(j).name+" have redundant test statements");
        						//redundant.add(minimizer.get(i).name+" and "+minimizer.get(j).name+" have redundant test statements");
    							System.out.println(minimizer.get(i).stmts.get(m));
    					
//    							Map<String, String> methodMapTemp1 = new HashMap<String, String>();
//    							Map<String, String> methodMapTemp2 = new HashMap<String, String>();
//    				            Set<String> nowStatements =  new HashSet<String>();
    							
   							 	for (Map.Entry<String, String> set : methodMa.entrySet()) {
   							 		if((set.getValue().contains(minimizer.get(i).stmts.get(m)) )) {
   							 									 			
   							 		}
   								
   							 	}
//   							 	for (Map.Entry<String, String> set : methodMa.entrySet()) {
//							 		if((set.getKey().contains(minimizer.get(j).name)) ) {
//							 			
//							 			String[] Split= set.getValue().split("\\n");
//							 			for(int n=0;n<Split.length;n++) {
//   											String singleStatement=Split[n];
//   										
//   											if(singleStatement.contains(";")) {
//   											
//   												nowStatements.add(singleStatement);
//   											
//   											}	
//   											
//							 			}
//							 			
//							 			//nowStatements.add(set.getValue());
//							 			//methodMapTemp1.computeIfPresent(minimizer.get(i).name,(k,v)->v);
//							 			//methodMapTemp1.put(set.getKey(), set.getValue());
//										//System.out.println(set.getKey() + "" + set.getValue());
//							 		}
//								
//							 	}
   							 	//methodMapTemp1.putAll(methodMapTemp2);
   							 	
//   							 	for(String merge : nowStatements){
//								 	System.out.println(merge);
//								}
   							 	//System.err.println(methodMapTemp1);
    							//output=output.concat(minimizer.get(i).stmts.get(x));
    							
    							//output+=minimizer.get(i).stmts.get(x);
   							 	
    								
    						}
    						
    					}
    				
    					
    				}
    			}
    		}
            //
     

            

              
        }).explore(projectDir);
    }


}