package ltl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
@SpringBootApplication
public class Application {
	
	
	public static void main(String[] args) throws IncorrectFormulaException, IOException {
		
		SpringApplication.run(Application.class, args);

		
	}
	
}
