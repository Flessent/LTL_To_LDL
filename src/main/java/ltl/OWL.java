
package ltl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OWL {
	
	public static  boolean savedInFile;
    private static final String OWL_FILE = "owl.txt";

	
   public static void savedGeneratedOwllFormula(String ldlFormula) {
    	
    	if(OWL.savedInFile) {
        try {
            BufferedWriter logBufferWriter = new BufferedWriter(new FileWriter(OWL_FILE, true));

            int lastNumber =   RandomLTLFormulaGenerator.extractLastNumberFromFile(OWL_FILE);
            int nextLineNumber = lastNumber + 1;
            logBufferWriter.write(nextLineNumber + "-" + ldlFormula);
            logBufferWriter.newLine();
            logBufferWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        
    	}
    }
    
    
	public String fromOWL(String ltlFormula) throws IOException, InterruptedException {
		  String OWL_LTL=RandomLTLFormulaGenerator.generatedFormula;
		
		  // Create a ProcessBuilder instance
		String ltl_formula_string = "Ga & Xa & XX!a";
		String serverIpAddress = "192.168.1.106";
		String command =ltl_formula_string + serverIpAddress;
		ProcessBuilder processBuilder = new ProcessBuilder(
				"/home/flessent/owl/owl-linux-musl-amd64-21.0/bin/owl",
		        "ltl-utilities",
		        "--rewriter=SYNTACTIC",
		        "-f",
		        ltlFormula
		        );

		// Start the external program
		Process process = processBuilder.start();

		// Get the program's output stream
		InputStream inputStream = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		// Read and print the program's output
		String line;
		System.out.println("************************************************************* OWL OUTPUT *********************************************************************************************");
		String all_output = "";
		while ((line = reader.readLine()) != null) {
		    all_output = all_output + line;
		    savedGeneratedOwllFormula(all_output);
		    
		    System.out.println(all_output);
		}

		// ----------- final check if the formula is interesting or boring ------------------
		if (all_output.strip().equals("false") || all_output.strip().equals("true")) {
		    System.out.println("the formula is NOT interesting");
		    savedInFile=false;

		// Wait for the program to complete and get the exit code
		int exitCode = process.waitFor();
		System.out.println("Exit code: " + exitCode);
		} else {
			savedInFile=true;
		}
		return all_output;
	}
    
}
