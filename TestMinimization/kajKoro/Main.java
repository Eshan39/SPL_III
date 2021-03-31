package kajKoro;


import java.io.File;

public class Main{

	public static void main(String[] args) {
      
		File projectDir = new File("D:\\BSSE_8th_Semester\\SPL_3\\TestFolder");
		
		StatementsLinesExample javaFiles = new StatementsLinesExample();
		//javaFiles.statementsByLine(projectDir);
	
		
	       
		RedundantFinder redundant= new RedundantFinder();
		//redundant.listOfRedundant(projectDir);   
		
		MethodParser mathCall= new MethodParser();
		mathCall.listOfMethod(projectDir);
		
	}

}
