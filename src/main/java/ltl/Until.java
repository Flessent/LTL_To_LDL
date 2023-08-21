package ltl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Until extends LTLFormula{
	private LTLFormula leftOperand;
    private LTLFormula rightOperand;
	private   String  symbole;

    Until(LTLFormula leftOperand, LTLFormula rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    String generate() {
    	if(this.leftOperand.equals(this.rightOperand)) {
    		return "("+ this.leftOperand.LTLtoLDL()+" )" ;
    		}
        return "(" + leftOperand.generate() + " U " + rightOperand.generate() + " )";
    }

	@Override
	String LTLtoLDL() {
		if(this.leftOperand.equals(this.rightOperand)) {
			return "("+ this.leftOperand.LTLtoLDL()+" )" ;
			}
		return "<("+ this.leftOperand.LTLtoLDL() +")*>"+ this.rightOperand.LTLtoLDL();
	}
}
