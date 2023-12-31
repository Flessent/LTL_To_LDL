# LTL To LDL
# Prerequisites
## OWL
- Before diging into the installation part, the project requires some external components like OWL.
Download and unzip the OWL (The Linux version  is using here ) file according to your operating system. The zipped file can be found : [OWL](https://owl.model.in.tum.de/).
The path towards the  OWL-file unziped previously should be alter   in the OWL.java class. It's the first parameter of the ProcessBuilder object.
This should set like following : ***/your_path/owl-linux-musl-amd64-21.0/bin/owl***


##  Java version
     The version should be from 11. 
     Jdk 17 is using.
# Project Structure
  The project has 14 java-classes. Certain among them are classes for operators and other are implementation using these class-operators.
  The main class is ***RandomLTLFormulaGenerator.java***. The huge part of the implementation is there.



# What is  happening  behind the scenes ?
This project generates a LTL-Formula then converts it  into a LDL-Formula through a CLI.
 After generating a LTL-Formula, this formula is sent to OWL-Program (running in Debian-Linux). Please feel free to get information about  OWL : [Here](https://owl.model.in.tum.de/) or 
 [OWL-GitHub](https://github.com/owl-toolkit/owl).
OWL transforms or rewrites or interpretes  the generated LTL-Formula into another LTL-Formula.
-If the returned OWL-LTL-Formula is 'true' or 'false'  then 
the original generated LTL-Formula isn't going to be saved neither onto the LTL.txt nor onto the LDL.txt files.
-Else (the rewritten LTL-Formula isn't interpreted as 'true' or 'false' but rather translated into another equivalent LTL-Formula) then
the original LTL-Formula and its conversion into LDL-Formula are both saved onto LTL.txt and onto LDL.txt files respectively.

# Launch the project
The project uses the Spring-Shell Framework making possible to use the project as a command line tool. Get more infos about this framework here : [Spring Shell](https://spring.io/projects/spring-shell).
**Maven** is used as  management tools for dependencies. 
You can use the following command to unzip : ***unzip LTL_LDL_Generation.zip -d /path_to_folder_where_unziped_folder_will_be***
Use the ***cd** Linux command so as to be in the folder where there's the **pom.xml** file and run following commands :
***mvn compile***
***mvn clean install***
Afterwards, you can now execute the project with the command :     ***mvn exec:java -Dexec.mainClass="ltl.Application"***

You can now enter  commands.

# Command Line Features (CLI)
          LTL-Formulas are generated by typing some command in the CLI.
# Liste of commands and its explanation

0. The zero command is the command ***info***. It allows to see Operators used within a formula. Its ouptut is:
   
 * X : **Next**
 * G : **Globally**
 * U : **Until**
 * F : **Future**
 * | : **OR**
 * & : **AND**
 * -> : **Implication**
 * ! : **Negation**

   
1. ***generate expansionGrad nberOfFormula***
Although this session starts with this command, it must be used after the ***setting*** command. If you try to use it before, you w'll get the following error message :
***Your list of AP is empty. You propably forget to use the option '--AP' of the command 'setting' to set your list of AP. Enter for example :  setting --AP a,b***.
   
This command is the main command. It allows to generate LTL-Formula(behind the scenes, theses generated LTL-Formulas are converted into LDL-Formula).
The command accepts two parameters :
- ***expansionGrad***
It is an integer and specifies the depth of the formula. The greater it is, the longer and complexer would be the formula.
The default value is 3.
- ***nberOfFormula***
It is also  an integer and specifies the number of formulas to be generated.
The default value is 1 for generating just one formula.

  **Usage :**
  
Therefore this command can be used following 3 manner :
- ***generate***    (this generates one formula having depth equals 3)
- ***generate 2***  (this generates one formula having depth 2)
- ***generate 3 5*** (this generates for example  5 formula each one having depth 3 )

For getting help about this command, use the following help-command : ***help generate***

2. ***setting --AP ap1,ap2,ap3,...,apN***
This command must be called before using the 'generate' command,because it allows to specify the list of Atomic Propositions(APs).
-It has one parameter : AP
This paramter is for specifying the list of APs.
There's no default value for this parameter. That means if you enter just 'setting' without the 'AP' parameter, you'll get the following error : **2000E:(pos 0): Missing mandatory option, longnames='AP',                          shortnames=''**

**Usage :**

This command must be written as follow :
***setting --AP a,b,c***
Here we have 3 AP : a, b and c. ***These APs will be save onto the atomic_proposiions.txt file***.


**Attention !!!**


Don't put space between APs when specifying the list of APs.

For getting help about this command, use the following help-command : ***help setting***

# Resultat

Please take a look at the ***Resulat.png*** file which depicts a result of the command: ***generate 4 3***.
![Resulat of generate 4 3](Resultat.png)

# Reference

The idea of implementation comes from the paper [Linear Temporal Logic and Linear Dynamic Logic on Finite Traces](https://www.cs.rice.edu/~vardi/papers/ijcai13.pdf) Theorem 9, P 5
   
   
