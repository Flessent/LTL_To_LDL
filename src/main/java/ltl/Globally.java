package ltl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Globally extends LTLFormula {
    private LTLFormula operand;

    Globally(LTLFormula operand) {
        this.operand = operand;
    }

    @Override
    String generate() {
        return "G " + operand.generate();
    }

	@Override
	String LTLtoLDL() {
		return "[true*]("+ this.operand.LTLtoLDL()+")" ;
	}
}