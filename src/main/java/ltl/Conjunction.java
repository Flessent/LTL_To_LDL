package ltl;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conjunction extends LTLFormula {
	private LTLFormula leftOperand;
    private LTLFormula rightOperand;
	private List<String> atomicPropositions;

    Conjunction(LTLFormula leftOperand, LTLFormula rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    
  

    
    @Override
    String generate() {
    	if(this.leftOperand.equals(this.rightOperand)) {
    		return "("+ this.leftOperand.LTLtoLDL()+" )" ;
    		}
        return "(" + leftOperand.generate() + " & " + rightOperand.generate() + " )"; // call the generate method to which the left-or rightOperand belongs. 
    }
    
    
    public String AND(LTLFormula formula) {
    
    	return formula.getSymbole();
    }





	@Override
	String LTLtoLDL() {
		if(this.leftOperand.equals(this.rightOperand)) {
		return "("+ this.leftOperand.LTLtoLDL()+" )" ;
		}
		return "("+ this.leftOperand.LTLtoLDL() +" & "+ this.rightOperand.LTLtoLDL()+" )" ;

	}

    
    
}
