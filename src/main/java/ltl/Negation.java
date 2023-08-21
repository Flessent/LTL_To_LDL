package ltl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Negation extends LTLFormula {
	private LTLFormula operand;

    Negation(LTLFormula operand) {
        this.operand = operand;
    }

    @Override
    String generate() {
    	//System.out.println("Test"+operand.generate());
        return "!" + operand.generate();
    }

    @Override
	String LTLtoLDL() {
		return "!("+ this.operand.LTLtoLDL()+")" ;
	}
}
