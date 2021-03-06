package project;


import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class StatementsLinesExample {
    public static void statementsByLine(File projectDir) {
    	 new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
           
            System.out.println(Strings.repeat("=", path.length()));
            
            ArrayList<RedundantClass> minimizer= new ArrayList<RedundantClass>();
            ArrayList<String> Statements = new ArrayList<String>();
            ArrayList<String> methods = new ArrayList<String>();
            Map<String, String> methodMa= new HashMap<String, String>();
            Map<String, ArrayList<String>> methodMap= new HashMap<String, ArrayList<String>>();
            
            HashMap<Integer,String> highLight= new HashMap<Integer, String>();
            
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
            	
            	
            	
                new NodeIterator(new NodeIterator.NodeHandler() {
                    @Override
                    public boolean handle(Node node) {
                        if (node instanceof Statement) {
                        	List<Node> getChildNodes = node.getChildrenNodes();
                            HashMap<String, String> hm = new HashMap<String, String>();
                                // System.out.println(getChildNodes + "----------");

                            for (int k = 0; k < getChildNodes.size(); k++) {
                                	 
          
//                                	 
                                	 
                            	highLight.put(getChildNodes.get(k).getBegin().line,getChildNodes.get(k).toString());
                            
                            	//System.out.println(getChildNodes.get(k).getBegin().line);
                                     
                                     
                              
                            }
                          
                            return false;
                        } else {
                            return true;
                        }
                    }
                }).explore(JavaParser.parse(file));
                System.out.println(); // empty line
            } catch (ParseException | IOException e) {
                new RuntimeException(e);
            }
            
            
            for(int i=0;i<minimizer.size();i++) {
            	for(int j=i+1;j<minimizer.size();j++) {
    				for(int m=0;m<minimizer.get(i).stmts.size();m++){
    					for(int y=0;y<minimizer.get(j).stmts.size();y++) {
    						//System.out.println(minimizer.get(i).stmts.get(x));
    						if(minimizer.get(i).stmts.get(m).equals(minimizer.get(j).stmts.get(y))) {

    							//System.err.println(minimizer.get(i).name+" and "+minimizer.get(j).name+" have redundant test statements");
    							//System.err.println(minimizer.get(i).stmts.get(m));
    							
   							 	for (Map.Entry<Integer, String> set : highLight.entrySet()) {
   							 		if((set.getValue().contains(minimizer.get(i).stmts.get(m)) )) {
   							 			System.out.println("Eshan");					 			
   							 		}
   								
   							 	}
    							
    						}
    					}
    				}
    			}    	 
    							
    		}
            
            
            
            
        }).explore(projectDir);
    }
   
}

//
//String[] Split= set.getValue().split("\\n");
//	
//for(int m=0;m<Split.length;m++) {
//	String singleStatement=Split[m];
//	
//	if(singleStatement.contains(";")) {
//		
//		nowStatements.add(singleStatement);
//		
//	}	
//
// && ((path.contains("Test") || path.contains("test"))
