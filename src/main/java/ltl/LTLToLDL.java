package ltl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LTLToLDL {

    public String convertLTLToLDLs(List<LTLFormula> listOfObjectsOfFormula) throws IncorrectFormulaException {
        StringBuilder resultBuilder = new StringBuilder();
        for (int index = 0; index < listOfObjectsOfFormula.size(); index++) {
            String symbol = listOfObjectsOfFormula.get(index).getSymbole();

            switch (symbol) {
                case "U":
                     {
                   resultBuilder.append(new Until().LTLtoLDL());
                    
                    }
                    break;
                case "V":
                {
                    resultBuilder.append(new Disjunction().LTLtoLDL());

                    }
                    break;
                case "&&":
                {
                    resultBuilder.append(new Conjunction().LTLtoLDL());

                    }
                    break;
                case "¬":
                    {
                        resultBuilder.append(new Negation().LTLtoLDL());
	
                    }
                    break;
                case "X":
                {
                    resultBuilder.append(new Next().LTLtoLDL());

                	
                    }
                    break;
                case "->":
                    {
                        resultBuilder.append(new Implication().LTLtoLDL());

                    }
                    break;
                case "G":
                    {
                        resultBuilder.append(new Globally().LTLtoLDL());

                    }
                    break;
                case "F":
                {
                    resultBuilder.append(new Frequently().LTLtoLDL());

                    }
                    break;
                default:
                    resultBuilder.append(symbol);
                    break;
            }
        }
        return resultBuilder.toString();

    }

    
    public String convertLTLToLDL(List<LTLFormula> listOfObjectsOfFormula) throws IncorrectFormulaException {
        StringBuilder resultBuilder = new StringBuilder();

        for (int index = 0; index < listOfObjectsOfFormula.size(); index++) {
            String symbol = listOfObjectsOfFormula.get(index).getSymbole();
  //if(listOfObjectsOfFormula.get(index) instanceof Conjunction || listOfObjectsOfFormula.get(index) instanceof Disjunction || listOfObjectsOfFormula.get(index) instanceof Until||
	//	  listOfObjectsOfFormula.get(index) instanceof Implication) {
            switch (symbol) {
                case "U":
                    if (index + 2 < listOfObjectsOfFormula.size() && listOfObjectsOfFormula.get(index+1) instanceof AtomicProposition && listOfObjectsOfFormula.get(index+2) instanceof AtomicProposition) {
                        String leftPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 1)));
                        String rightPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 2)));

                        resultBuilder.append("<(")
                                .append(leftPart)
                                .append(")*>")
                                .append(rightPart);

                        index += 2;
                    }
                    break;
                case "V":
                    if (index + 2 < listOfObjectsOfFormula.size() && listOfObjectsOfFormula.get(index+1) instanceof AtomicProposition && listOfObjectsOfFormula.get(index+2) instanceof AtomicProposition) {
                        String leftPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 1)));
                        String rightPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 2)));

                        resultBuilder.append("(")
                                .append(leftPart)
                                .append("V")
                                .append(rightPart)
                                .append(")");

                        index += 2;
                    }
                    break;
                case "∧":
                    if (index + 2 < listOfObjectsOfFormula.size() && listOfObjectsOfFormula.get(index+1) instanceof AtomicProposition && listOfObjectsOfFormula.get(index+2) instanceof AtomicProposition) {
                        String leftPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 1)));
                        String rightPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 2)));

                        resultBuilder.append("(")
                                .append(leftPart)
                                .append("∧")
                                .append(rightPart)
                                .append(")");

                        index += 2;
                    }
                    break;
                case "¬":
                    if (index + 1 < listOfObjectsOfFormula.size() && listOfObjectsOfFormula.get(index+1) instanceof AtomicProposition) {
                        String formula = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 1)));
                        resultBuilder.append("¬(")
                                .append(formula)
                                .append(")");

                        index += 1;
                    }
                    break;
                case "X":
                    if (index + 1 < listOfObjectsOfFormula.size() && listOfObjectsOfFormula.get(index+1) instanceof AtomicProposition) {
                        String nextPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 1)));

                        resultBuilder.append("<true>")
                                .append("(")
                                .append(nextPart)
                                .append(")");

                        index++;
                    }
                    break;
                case "->":
                    if (index + 2 < listOfObjectsOfFormula.size() && listOfObjectsOfFormula.get(index+1) instanceof AtomicProposition && listOfObjectsOfFormula.get(index+2) instanceof AtomicProposition) {
                        String leftPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 1)));
                        String rightPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 2)));

                        resultBuilder.append("(")
                                .append(leftPart)
                                .append("->")
                                .append(rightPart)
                                .append(")");

                        index += 2;
                    }
                    break;
                case "G":
                    if (index + 1 < listOfObjectsOfFormula.size() && listOfObjectsOfFormula.get(index+1) instanceof AtomicProposition) {
                        String nextPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 1)));

                        resultBuilder.append("[true*](")
                                .append(nextPart)
                                .append(")");

                        index ++;
                    }
                    break;
                case "F":
                    if (index + 1 < listOfObjectsOfFormula.size() && listOfObjectsOfFormula.get(index+1) instanceof AtomicProposition) {
                        String innerPart = convertLTLToLDL(Arrays.asList(listOfObjectsOfFormula.get(index + 1)));
                        if (!innerPart.isEmpty()) { // in case there's any formula inside the brackets
                            resultBuilder.append("<true*>(")
                                    .append(innerPart)
                                    .append(")");
                        }
                        index += 1;
                    }
                    break;
                default:
                    resultBuilder.append(symbol);
                    break;
            }
        }

        return resultBuilder.toString();
    }
    
    
    
   /* public LTLFormula conversion(List<LTLFormula> listOfObjectsOfFormula) {
    	
    	for (int index=0; index<listOfObjectsOfFormula.size(); index++) {
    		conversionLTLtoLDL(listOfObjectsOfFormula.get(index));
    	}
    	//String symbole=formula.getSymbole();
    	
    	
       	
    }
    
    
public void conversionLTLtoLDL(LTLFormula formula) {}*/
   




}