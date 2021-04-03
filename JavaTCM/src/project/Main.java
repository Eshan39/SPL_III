package project;


import java.io.File;

public class Main{

	public static void main(String[] args) {
      
		File projectDir = new File("D:\\BSSE_8th_Semester\\SPL_3\\JUnit_Testing-master\\JUnit\\accountTest");
		
	
		StatementsLinesExample javaFiles = new StatementsLinesExample();
		//javaFiles.statementsByLine(projectDir);
		
		RedundantFinder mathCall= new RedundantFinder();
		mathCall.listOfRedundant(projectDir);
		
		
	       
		MethodParser mPars= new MethodParser();
		mPars.listOfMethod(projectDir);   
		
	}

}
