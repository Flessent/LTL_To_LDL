package ltl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Frequently extends LTLFormula {
    private LTLFormula operand;

    Frequently(LTLFormula operand) {
        this.operand = operand;
    }

    @Override
    String generate() {
        return "F" + operand.generate();
    }

    @Override
	String LTLtoLDL() {
		return "<true*>("+ this.operand.LTLtoLDL()+")" ;
	}
}