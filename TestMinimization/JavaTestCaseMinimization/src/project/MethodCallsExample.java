package project;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import java.io.File;
import java.io.IOException;

public class MethodCallsExample {
    public static void listMethodCalls(File projectDir) {
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
               
            Set<String> allProductionMethods=new HashSet<>();  
          //show productyion method call list
            
            try {
            	
//            	 new VoidVisitorAdapter<Void>() {
//                 	
//                     @Override
//             	 	public void visit(MethodDeclaration n, Void arg) {
//                         /* here you can access the attributes of the method.
//                          this method will be called for all methods in this
//                          CompilationUnit, including inner class methods */
//                     	
//                     	//method body
//                         String x =String.valueOf(n.getBody());
//                         
//                         String methodName= n.getName();
//                         System.out.println(methodName);
//                         Map<String, String> methodMap= new HashMap<String, String>();
//                         
//                         String[] Split= x.split("\\n");
//                         for(int i=0;i<Split.length;i++) {
// 								String singleStatement=Split[i];
// 							
// 								if(singleStatement.contains(";")) {
// 									methodMap.put(methodName, singleStatement);
// 									//System.out.println(n.getName()+" "+singleStatement);
// 								}
// 								
// 						
//                         }
//                        // System.out.println(methodMap.size());
//                        // System.out.println("Eshan");
//          
//                         super.visit(n, arg);
//                         //System.out.println(x);
//                     }
//                     
//                     
//                 }.visit(JavaParser.parse(file), null);
                 System.out.println(); // empty line
                 
                 
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(MethodCallExpr n, Object arg) {
                    	String pmc="";
                    	pmc=n.getName()+" "+n.getArgs();
                    	allProductionMethods.add(pmc);
                    	//System.out.println(pmc);
                    	
                    	System.out.println(pmc);
                    	Node node=n.getParentNode(); 
                    	
                    		
                    	//System.out.println(node);
                    
                        if (node instanceof Statement) {
                               List<Node> getChildNodes = node.getChildrenNodes();
                               HashMap<String, String> hm = new HashMap<String, String>();
                                    // System.out.println(getChildNodes + "----------");

                               for (int i = 0; i < getChildNodes.size(); i++) {

                                         //System.out.println(getChildNodes.get(i).toString());
                                     String line = getChildNodes.get(i).toString();
                                    // allProductionMethods.add(line);         
                                    	
                                 }
                              
                               
                        } 
                    	
                           // System.out.println(allProductionMethods.toString());
                        //System.out.println(getMethodName+" "+n.getArgs());
                      super.visit(n, arg);
                    }
                 
                }.visit(JavaParser.parse(file), null);
                 // empty line
            } catch (ParseException | IOException e) {
                new RuntimeException(e);
            }
            
            System.out.println("\t\t\t\t\tPrint all PMC and UVA of this class path");
            System.out.println("\t\t\t\t\t-------------------------------");
      
            System.out.println(allProductionMethods.toString());
            
            
            
            System.err.println("Eshan");
           
        }).explore(projectDir);
    }
//    public static void main(String[] args) {
//        File projectDir = new File("D:\\BSSE_8th_semester\\SPL_3\\TestFolder");
//        listMethodCalls(projectDir);
//    }
}