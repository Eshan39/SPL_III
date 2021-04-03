package project;

import java.util.ArrayList;

public class RedundantClass {

	ArrayList<String> stmts = new ArrayList<String>();
    String name = null;
    
	public  RedundantClass(String name,ArrayList<String> stmts) {
		this.name=name;
		this.stmts=(ArrayList<String>) stmts.clone();	
		
	}
		
}


