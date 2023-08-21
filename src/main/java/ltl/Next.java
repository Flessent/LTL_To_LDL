package ltl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Next extends LTLFormula {
	private LTLFormula operand;
	private   String  symbole;


    Next(LTLFormula operand) {
        this.operand = operand;
    }

    @Override
    String generate() {
        return "X" + operand.generate();
    }

	@Override
	String LTLtoLDL() {
		return "<true>("+ this.operand.LTLtoLDL()+")";
	}
}
