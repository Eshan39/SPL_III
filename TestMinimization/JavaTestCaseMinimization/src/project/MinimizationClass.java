package project;

import java.util.ArrayList;

public class MinimizationClass {

	ArrayList<String> stmts = new ArrayList<String>();
    String name = null;
    public boolean merged = false;
	public  MinimizationClass(String name,ArrayList<String> stmts) {
		this.name=name;
		this.stmts=(ArrayList<String>) stmts.clone();
		
		
		
	}
		
}


