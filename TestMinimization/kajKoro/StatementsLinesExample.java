package kajKoro;


import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.Statement;
import com.google.common.base.Strings;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StatementsLinesExample {
    public static void statementsByLine(File projectDir) {
    	 new DirExplorer((level, path, file) -> path.endsWith(".java") && ((path.contains("Test") || path.contains("test"))), (level, path, file) -> {
            System.out.println(path);
           
            System.out.println(Strings.repeat("=", path.length()));
            try {
                new NodeIterator(new NodeIterator.NodeHandler() {
                    @Override
                    public boolean handle(Node node) {
                        if (node instanceof Statement) {
                                 List<Node> getChildNodes = node.getChildrenNodes();
                                 HashMap<String, String> hm = new HashMap<String, String>();
                                // System.out.println(getChildNodes + "----------");

                                 for (int i = 0; i < getChildNodes.size(); i++) {

                                     //System.out.println(getChildNodes.get(i).toString());
                                     String line = getChildNodes.get(i).toString();
                                     //System.out.println(line);
                                     int count=0;
                                    
                                	
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
        }).explore(projectDir);
    }
   
}