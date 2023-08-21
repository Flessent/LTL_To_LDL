package ltl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.module.ModuleDescriptor.Builder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IllegalFormatConversionException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import lombok.Data;
@Data
@ShellComponent
public class RandomLTLFormulaGenerator {
   // private static final String[] ATOMIC_PROPOSITIONS = {"p", "q", "a", "b", "c"};
    private static final String[] OPERATORS = {"!", "&", "|", "U", "X", "->","F","G"};
    private static  long EXPIRATION_DURATION=1 *60*1000;; // 1 minute
    private static final String AP_FILE = "atomic_proposiions.txt";
    private static final String LOG_FILE = "log.txt";
    private static final String LTL_FILE = "ltl.txt";
    private static final String LDL_FILE = "ldl.txt";

    private static final Random random = new Random();
    private static List<LTLFormula> generatedFormulasInOrder = new ArrayList<LTLFormula>();
   public static StringBuilder resultBuilder = new StringBuilder();
   public static Set<String> ATOMICS_PROPOSITIONS = new HashSet<>();
public  static String generatedFormula;
    private int depth;
    
    
    public static void savedGeneratedLtlAndLdlFormula(String ltl, String ldl) {
        try {
            BufferedWriter logBufferWriter = new BufferedWriter(new FileWriter(LOG_FILE, true));
            logBufferWriter.write(ltl);
            logBufferWriter.newLine();
            logBufferWriter.write(ldl);
            logBufferWriter.newLine();
            logBufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public static void savedGeneratedLdlFormula(String ldlFormula) {
    	
    	if(OWL.savedInFile) {
        try {
            BufferedWriter logBufferWriter = new BufferedWriter(new FileWriter(LDL_FILE, true));

            int lastNumber = extractLastNumberFromFile(LDL_FILE);
            int nextLineNumber = lastNumber + 1;

            logBufferWriter.write(nextLineNumber + "-" + ldlFormula);
            logBufferWriter.newLine();
            logBufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    	}
    }
    
    
    public static void savedGeneratedLtlFormula(String ltlFormula) {
    	if(OWL.savedInFile) {
        try {
            BufferedWriter logBufferWriter = new BufferedWriter(new FileWriter(LTL_FILE, true));

            int lastNumber = extractLastNumberFromFile(LTL_FILE);
            int nextLineNumber = lastNumber + 1;

            logBufferWriter.write(nextLineNumber + "-" + ltlFormula);
            logBufferWriter.newLine();
            logBufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	}
    }
    
    public static int extractLastNumberFromFile(String filePath) {
        int lastNumber = -1; // Default value if no number is found
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String previousLine = null;
            while ((line = br.readLine()) != null) {
                if (previousLine != null) {
                    previousLine = previousLine.concat(line);
                    line = previousLine;
                    previousLine = null;
                }
                String[] parts = line.split("-");
                if (parts.length > 0) {
                    String lastPart = parts[0].trim();
                    if (lastPart.matches("\\d+")) {
                        lastNumber = Integer.parseInt(lastPart);
                    }
                } else {
                    previousLine = line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastNumber;
    }
    
    
    
    
    
    
    public static List<String> orderedListSymbole = new ArrayList<String>();
    
    
    
    @ShellMethod(key="info",value = "Infos about this  tool")
    public static void  Infos() {
		System.out.println("************************************* Information *************************************************");

		System.out.println("In this sofware, there are reserved words(Operators) that can not be used as AP. Their meaning in this context is : ");
		System.out.println(
				"X : Next\n "
				+ "G : Globally\n "
				+ "U : Until\n "
				+ "F : Future\n "
				+ "| : OR\n "
				+ "& : AND\n "
				+ "-> : Implication\n "
				+ "! : Negation");

    	
    }
    
 @ShellMethod(key = "setting", value = "AP :Max number of AP to use. Defautl=2;choice: write 'Y' for changing AP. Default=N; AP:Specify your AP ")
    public static Set<String> settingAtomicProposition(
    		@ShellOption(defaultValue = "2") String maxNumAP,
 @ShellOption(arity = 1) Set<String> AP){
	 
	 if(AP.size()==0) {
		 throw new IndexOutOfBoundsException("You list of AP is empty. You propably forget to use the option '--AP' of the command 'setting'. Enter:  setting --help ");
	 }
	
	ATOMICS_PROPOSITIONS.clear(); // we clear the existing AP at each call of the setting command 
     System.out.println("*****Number of AP :****");

		System.out.println(AP.size());



        AP.forEach(ap->{
        	if(ap.isEmpty() || ap.equals("F") || ap.equals("G") || ap.equals("U")||  ap.equalsIgnoreCase("|")
                    || ap.equals("X") ||  ap.equals(" ")) {

        	    throw new IllegalArgumentException("An AP can neither  be empty nor : (F, G , U , |, |, X). Use command 'info' for more informations.");
        	} else {
                ATOMICS_PROPOSITIONS.add(ap);

        	}
        	
        });
        System.out.println("Your list of AP is : " + ATOMICS_PROPOSITIONS.toString());

		
      //  int parsedNumAP=Integer.parseInt(maxNumAP);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(AP_FILE))) {
        	

               
                                AP.forEach(ap->{
                                    try {
										bufferedWriter.write(ap);
									    bufferedWriter.newLine();
		                                bufferedWriter.flush();
									} catch (NumberFormatException e) {
										e.printStackTrace();
									} catch (IOException e) {
										e.printStackTrace();
									}
                                });
                            
                                System.out.println("Atomic Propositions saved successfully.");
                            
                        
                    


               

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ATOMICS_PROPOSITIONS;
    }    
    
		@ShellMethod(key = "generate", value = "The greater the option 'expansionGrad' of the command 'generate' the complexer the formula : Generation of LTL and LDL pairs")
	public  void launch(@ShellOption(defaultValue = "3") String expansionGrad, @ShellOption(defaultValue = "1") String nberOfFormula  ) throws NumberFormatException, IncorrectFormulaException, IOException, InterruptedException {
		int nberOfForm=Integer.parseInt(nberOfFormula);
	for(int j=0;j<nberOfForm;j++) {
		int parsedExpansionGrad=Integer.parseInt(expansionGrad);

		 String ltlformula="";
	           List<LTLFormula> listeF= new ArrayList<LTLFormula>();

				listeF = RandomLTLFormulaGenerator.getAllGeneratedFormulas(parsedExpansionGrad);

	             
	            	 if(listeF.size()==0) {
	            		 throw new IndexOutOfBoundsException("Your list of AP is empty. You propably forget to use the option '--AP' of the command 'setting' to set your list of AP. Enter for example :  setting --AP a,b ");
	            	 } 
	            	 
	            	 
			
		      		//System.out.println("Please ensure yourself that you set parameters properly using the 'setting' command");

      		System.out.println("Number of AP and Operators : "+listeF.size());

	             for (int i=0; i<listeF.size();i++) {
	            		if(i==listeF.size()-1) {
	            			//System.out.println("The original LTL Formula is  : "+listeF.get(i).generate());

	            		System.out.println("*********************Reduced LTL/LDL Formula can be the same as the original depending on the operators and the reduced form can also be different from what you expected."
	            				+ " All depend on your requirements and specifications.");
          	          System.out.println("**************************************************Reduced LTL Formula : **********************************************************************************************");

	            			System.out.println(" Reduced LTL Formula : "+RandomLTLFormulaGenerator.reduce(listeF.get(i).generate()));
	            			ltlformula=RandomLTLFormulaGenerator.reduce(listeF.get(i).generate());
	        	            RandomLTLFormulaGenerator.savedGeneratedLtlFormula(RandomLTLFormulaGenerator.reduce(listeF.get(i).generate()));

	            		}
	            	} 
   	          System.out.println("**************************************************The Original LDL Formula : **********************************************************************************************");

	             System.err.println(RandomLTLFormulaGenerator.resultBuilder.toString());
   	          System.out.println("**************************************************Reduced LDL Formula : **********************************************************************************************");

	             System.err.println(RandomLTLFormulaGenerator.reduce(RandomLTLFormulaGenerator.resultBuilder.toString()));

	            RandomLTLFormulaGenerator.savedGeneratedLdlFormula(RandomLTLFormulaGenerator.reduce(RandomLTLFormulaGenerator.resultBuilder.toString()));
	            
	        
              RandomLTLFormulaGenerator.readInFileAP();
	}
	 }

    
   public  static boolean areAtomicProposiionsSaved() {

        File file = new File(AP_FILE);
        return file.exists();
    }


   public static void clearFile() {
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(AP_FILE))) {
           writer.write("");
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public static boolean isAtomicProposiionsFileEmpty() {
	    try (BufferedReader reader = new BufferedReader(new FileReader(AP_FILE))) {
	        return reader.readLine() == null; // Check if the file has no content (no lines)
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return true; // Treat file read error as empty
	}




   public static void readInFileAP() {
       try (BufferedReader reader = new BufferedReader(new FileReader(AP_FILE))) {
           String line;
          // List<String> lines = new ArrayList<>();

          

           if (ATOMICS_PROPOSITIONS.size() > 0) {
               System.out.println("AP are :");

               for (String AP : ATOMICS_PROPOSITIONS) {
                   System.out.println(AP);
               }
               System.out.println("Used Operators and AP  are :");

           } else {
               System.out.println("Any AP found.");
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    
   
   
    
    
    

    public static String  reduce(String formula) {
    	 if(Pattern.compile("GG+|XGG+|FF+").matcher(formula).find()) {
     	  	  System.err.println("Reduction Laws :");
     	  	formula=formula.replaceAll("GG+", "G");
     	  	formula= formula.replaceAll("FF+", "F");

    	 } else if (Pattern.compile("FGF").matcher(formula).find()) {
     	  	  	  System.err.println("Absorption Laws :");
     	  	  	 // absorption law
     	  	  	formula=formula.replaceAll("FGF", "GF");
     	  	  	  }else if (Pattern.compile("FGF").matcher(formula).find()) {
     	  	  	  	  System.err.println("Absorption Laws :");
     	  	    	 // absorption law
     	  	  	 formula=formula.replaceAll("FGF", "GF");
     	  	  	 
     	    	 }   else if(Pattern.compile("\\[true\\*\\]\\((\\[true\\*\\])+").matcher(formula).find()) {
     	      		//String regex = "\\[true\\*\\]\\((\\[true\\*\\])+";  
        	  	  	  System.out.println("Globally LDL Reduction :");

     	    		formula = formula.replaceAll("\\[true\\*\\](?=.*\\[true\\*\\])", "");

     	    	 }
    	 
     	    	else if(Pattern.compile("\\<true\\*\\>\\((\\<true\\*\\>)+").matcher(formula).find()) {
     	      		//String regex = "\\[true\\*\\]\\((\\[true\\*\\])+";  
        	  	  	  System.out.println("Frequently LDL Reduction :");

     	    		formula = formula.replaceAll("\\<true\\*\\>(?=.*\\<true\\*\\>)", "");

    	    	 }   else if(Pattern.compile("([a-zA-Z]+)\\s+&\\s+!\\1").matcher(formula).find()) {
    	    		 System.out.println("Pattern like :(a & !a) is just reduced to 'false'");
      	    		formula = formula.replaceAll("([a-zA-Z]+)\\s+&\\s+!\\1", "false");

    	    		 
    	    	 }
	return formula;
    
    }
      
    
    public  int countAtomicProps(LTLFormula formula) {
    	 String regex = "[a-z]{1}[^FGXU]";
    	 int countAtomicProposition=0;

         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(formula.getSymbole());

         Set<String> atomicProps = new HashSet<>();

         while (matcher.find()) {
             String atomicProp = matcher.group();
             atomicProps.add(atomicProp);
             countAtomicProposition=atomicProps.size();
         }
//         atomicProps.forEach(AP->{System.out.println("AP "+AP);});
         
         if(atomicProps.size()==0) {
        	 countAtomicProposition=atomicProps.size()+1;
         }

         return countAtomicProposition;
     
    }
     
    
    public  static  LTLFormula generateRandomFormula(int depth) throws IncorrectFormulaException {
    	resultBuilder.setLength(0); 
        double r = random.nextDouble();
        List<String> treeString= new ArrayList<String>();
        if (depth <= 0 || r < 0.3) {
            String proposition = getRandomAtomicProposition();
            
            treeString.add(proposition);
            AtomicProposition atomicProposition = new AtomicProposition(proposition);
            //atomicProposition.LTLtoLDL();
            atomicProposition.setSymbole(proposition);
            //resultBuilder.append(proposition);
            generatedFormulasInOrder.add(atomicProposition); 
            treeString.forEach(f->{System.err.println(f);});

            return atomicProposition;
        } else {
            String operator = getRandomOperator();
            treeString.add(operator);
            treeString.forEach(f->{System.err.println(f);});

            switch (operator) {
                case "!":
                    Negation negation = new Negation(generateRandomFormula(depth - 1));
                    negation.setSymbole("!");
                    
                    //resultBuilder.append(negation.LTLtoLDL());
                    generatedFormulasInOrder.add(negation); 
                    return negation;
                case "&":
                    Conjunction conjunction = new Conjunction(generateRandomFormula(depth - 1), generateRandomFormula(depth - 1));
                    conjunction.setSymbole("&");
                   
                    //resultBuilder.append(conjunction.LTLtoLDL());
                    generatedFormulasInOrder.add(conjunction);  
                    return conjunction;
                case "|":
                    Disjunction disjunction = new Disjunction(generateRandomFormula(depth - 1), generateRandomFormula(depth - 1));
                    disjunction.setSymbole("|");
                   
                    //resultBuilder.append(disjunction.LTLtoLDL());
                    generatedFormulasInOrder.add(disjunction); 
                    return disjunction;
                case "U":
                    Until until = new Until(generateRandomFormula(depth - 1), generateRandomFormula(depth - 1));
                    until.setSymbole("U");
                    //until.LTLtoLDL();
                    //resultBuilder.append(until.LTLtoLDL());
                    generatedFormulasInOrder.add(until); 
                      return until;
                case "->":
                    Implication implication = new Implication (generateRandomFormula(depth - 1), generateRandomFormula(depth - 1));
                    implication.setSymbole("->");
                    //implication.LTLtoLDL();
                    //resultBuilder.append(implication.LTLtoLDL());
                    generatedFormulasInOrder.add(implication);  
                    return implication;
                case "X":
                    Next next = new Next(generateRandomFormula(depth - 1));
                    next.setSymbole("X");
                    //next.LTLtoLDL();
                    //resultBuilder.append(next.LTLtoLDL());
                    generatedFormulasInOrder.add(next); 
                    return next;
                case "F":
                    Frequently frequently = new Frequently(generateRandomFormula(depth - 1));
                    frequently.setSymbole("F");
                    //frequently.LTLtoLDL();
                    //resultBuilder.append(frequently.LTLtoLDL());
                    generatedFormulasInOrder.add(frequently);  
                    return frequently;
                case "G":
                    Globally globally = new Globally(generateRandomFormula(depth - 1));
                    globally.setSymbole("G");
                   // globally.LTLtoLDL();
                    //resultBuilder.append(globally.LTLtoLDL());

                    generatedFormulasInOrder.add(globally);  
                    return globally;
             
                default:
                    throw new IncorrectFormulaException("Invalid operator: " + operator);
            }
        }
        
    }
    
    
       public static  List<LTLFormula> getAllGeneratedFormulas(int depth) throws IncorrectFormulaException, IOException, InterruptedException {
           generatedFormulasInOrder.clear(); // Clear the list before generating new formulas
           
          LTLFormula formula= generateRandomFormula(depth); // Generate the formulas (they will be added to generatedFormulasInOrder list)

          System.out.println("**************************************************The Original LTL Formula : **********************************************************************************************");
           generatedFormula=formula.generate();
          System.out.println(generatedFormula);

 
           RandomLTLFormulaGenerator.resultBuilder.append(formula.LTLtoLDL());
           new OWL().fromOWL(generatedFormula);
           return generatedFormulasInOrder;
       } 


    private static String getRandomAtomicProposition() {
 		 if(ATOMICS_PROPOSITIONS.size()==0) {
    		 throw new IndexOutOfBoundsException("Your list of AP is empty. You propably forget to use the option '--AP' of the command 'setting' to set your list of AP. Enter for example :  setting --AP a,b ");
    	 } 

        int index = random.nextInt(ATOMICS_PROPOSITIONS.size());

        List<String> listOfAP = new ArrayList<>(ATOMICS_PROPOSITIONS);

        return listOfAP.get(index);
    }

    private static String getRandomOperator() {
        int index = random.nextInt(OPERATORS.length);
        orderedListSymbole.add(OPERATORS[index]);
        return OPERATORS[index];
    }

    


  









    
}
