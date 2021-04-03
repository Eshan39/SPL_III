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


public class MethodParser {
    public void listOfMethod(File projectDir) {
    	
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
                        
                        String methName= n.getDeclarationAsString();
                        String methodName=methName.replace("void ","");
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
           
            String output="";
            int count=0;
            //find redundant Test Statements
            
            Set<String> redundant= new HashSet<>();
            Set<String> outputSet = new HashSet<>();

            
            for(int i=0;i<minimizer.size();i++) {
            	if (minimizer.get(i).merged) continue;
    			for(int j = i+1; j < minimizer.size(); j++) {
    				if (minimizer.get(j).merged) continue;
    				if (!shouldMerge(minimizer.get(i), minimizer.get(j))) continue;
    				MinimizationClass newMethod = merge(minimizer.get(i), minimizer.get(j));
    				minimizer.get(i).merged = true;
    				minimizer.get(j).merged = true;
    				minimizer.add(newMethod);
    				break;
    			}
    		}
            
            System.out.println("Show assertion of minimized class--------------------------------------");
            for (String line : minimizer.get(minimizer.size()-1).stmts) 
            { 
                if(line.contains("assert")) {
                	String newLine=line;
                	System.out.println(newLine);
                }
            }
            
              
        }).explore(projectDir);
    }
    
    public static MinimizationClass merge(MinimizationClass one, MinimizationClass two) {
    	
    	String output="";
       
        //find redundant Test Statements
    	ArrayList<MinimizationClass> minimizer = null;
    	
        Set<String> redundant= new HashSet<>();
        ArrayList<String> outputSet = new ArrayList<>();

               
				output = "";
				int mIndex = 0;
				boolean matched = false;
				for(int x=0;x<one.stmts.size();x++){
					String l1 = one.stmts.get(x);
					for(int y = mIndex; y < two.stmts.size();y++) {
						String l2 = two.stmts.get(y);
						if (l1.equals(l2)) matched = true;
					}
					if (matched && !outputSet.contains(l1)) {
						outputSet.add(l1);
						output += l1;
						for(int y = mIndex; y < two.stmts.size(); y++) {
							mIndex++;
							String l2 = two.stmts.get(y);
							if (l1.equals(l2)) {
								break;
							}
							outputSet.add(l2);
							output += l2;
						}
					} else if (!outputSet.contains(l1)) {
						outputSet.add(l1);
						output += l1;
					}
					matched = false;
				}
				for(int y = 0; y < two.stmts.size(); y++) {
					String l2 = two.stmts.get(y);
					if (!outputSet.contains(l2)) {
						outputSet.add(l2);
						output += l2;
					}
				}
				String mergedMethodName=one.name+"_"+two.name;
				
//				System.out.println("--------------------------------");
		
//				System.out.println(one.name);
//				System.err.println(two.name);
					
				
				System.out.println(mergedMethodName);
				System.out.println(output);
				MinimizationClass mc = new MinimizationClass(mergedMethodName, outputSet);
//				System.out.println("--------------------------------");
				return mc;
			}
		
    public static boolean shouldMerge(MinimizationClass one, MinimizationClass two) {             
				for(int x=0;x<one.stmts.size();x++){
					String l1 = one.stmts.get(x);
					for(int y = 0; y < two.stmts.size();y++) {
						String l2 = two.stmts.get(y);
						if (l1.equals(l2)) return true;
					}
				}
				return false; 
    }
   
}