package ltl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AtomicProposition extends LTLFormula {
private String proposition;
    AtomicProposition(String proposition) {
    super(proposition);
    this.proposition=proposition;
    }
    @Override
    String generate() {
      //  System.out.println("AP is called");

        return proposition;
    }
	@Override
	String LTLtoLDL() {
		return proposition;
	}
}
