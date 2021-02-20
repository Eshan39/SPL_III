package kajKoro;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
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
               
              
          //show productyion method call list
            
            try {
            	
            	 new VoidVisitorAdapter<Void>() {
                 	
                     @Override
             	 	public void visit(MethodDeclaration n, Void arg) {
                         /* here you can access the attributes of the method.
                          this method will be called for all methods in this
                          CompilationUnit, including inner class methods */
                     	
                     	//method body
                         String x =String.valueOf(n.getBody());
                         
                         String methodName= n.getName();
                         System.out.println(methodName);
                         Map<String, String> methodMap= new HashMap<String, String>();
                         
                         String[] Split= x.split("\\n");
                         for(int i=0;i<Split.length;i++) {
 								String singleStatement=Split[i];
 							
 								if(singleStatement.contains(";")) {
 									methodMap.put(methodName, singleStatement);
 									//System.out.println(n.getName()+" "+singleStatement);
 								}
 								
 						
                         }
                        // System.out.println(methodMap.size());
                        // System.out.println("Eshan");
          
                         super.visit(n, arg);
                         //System.out.println(x);
                     }
                     
                     
                 }.visit(JavaParser.parse(file), null);
                 System.out.println(); // empty line
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(MethodCallExpr n, Object arg) {
                        super.visit(n, arg);
                        String getMethodName=n.getName();
                        System.out.println(getMethodName);
                        System.out.println(getMethodName+" "+n.getArgs());
                        //String parent=n.getArgs().toString();
                        //System.out.println(parent);
                       // System.out.println(" [L " + n.getBeginLine() + "] " + n);
                        //System.out.println("Eshan");
                    }
                 
                }.visit(JavaParser.parse(file), null);
                 // empty line
            } catch (ParseException | IOException e) {
                new RuntimeException(e);
            }
            System.err.println("EShan");
           
        }).explore(projectDir);
    }
//    public static void main(String[] args) {
//        File projectDir = new File("D:\\BSSE_8th_semester\\SPL_3\\TestFolder");
//        listMethodCalls(projectDir);
//    }
}