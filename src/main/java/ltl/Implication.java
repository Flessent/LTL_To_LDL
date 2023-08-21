package ltl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Implication extends LTLFormula{

	private LTLFormula leftOperand;
    private LTLFormula rightOperand;

    Implication(LTLFormula leftOperand, LTLFormula rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    String generate() {
        return "(" + leftOperand.generate() + " -> " + rightOperand.generate() + " )";
    }

    @Override
	String LTLtoLDL() {
		
		return "("+ this.leftOperand.LTLtoLDL() +" -> "+ this.rightOperand.LTLtoLDL()+")" ;
	}
}
