package ltl;


import java.util.ArrayList;
import java.util.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@ToString
public abstract class LTLFormula {
    abstract String generate();
    abstract String LTLtoLDL();
    private boolean visited;
    private  LTLFormula LTLformula;
	private   String symbole;
    @ToString.Exclude
    private List<LTLFormula> neighbors=new ArrayList<>() ;
	   //private String rightFormula;
	   //private Operators operator;




	LTLFormula(String symbole){
		this.symbole=symbole;
	      }
	
	
	

}

