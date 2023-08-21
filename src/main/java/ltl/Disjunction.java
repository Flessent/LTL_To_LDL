package ltl;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disjunction extends LTLFormula{
	private LTLFormula leftOperand;
    private LTLFormula rightOperand;
	private List<String> atomicPropositions;
	private   String  symbole;



    Disjunction(LTLFormula leftOperand, LTLFormula rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        generate();
    }

    @Override
    String generate() {
    	if(this.leftOperand.equals(this.rightOperand)) {
    		return "("+ this.leftOperand.LTLtoLDL()+" )" ;
    		}
        return "(" + leftOperand.generate() + " | " + rightOperand.generate() + " )";
    }

    @Override
	String LTLtoLDL() {
		if(this.leftOperand.equals(this.rightOperand)) {
    		return "("+ this.leftOperand.LTLtoLDL()+" )" ;
    		}
		return "("+ this.leftOperand.LTLtoLDL() +" | "+ this.rightOperand.LTLtoLDL()+")" ;
	}
    
    
    
    
}
