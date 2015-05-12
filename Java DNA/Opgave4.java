/* 
 * DNAMatch v0.1 
 * 
 *  author: Dr. Quakerjack. 
 *  date: 17-09-2013 
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *  
 *  Opgave4.java:
 *  -  Dit programma voert verschillende acties uit voor ingevoerde DNA strings.
 *  -  Input: a command and depending on the command one or two parameters
 *  -  output: List of string items or best match to a string or outcome of Levenshtein algorithm.
 *
 *  -  Sources Levenshtein: http://www.let.rug.nl/kleiweg/L04/Tutorial/t08.html.nl
 *                          http://nl.wikipedia.org/wiki/Levenshteinafstand
 *  -  Sources Bubblesort: http://nl.wikipedia.org/wiki/Bubblesort
 *
 *
 * version: 0.1 
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
public class Opgave4{    
    /*This declaration wasn't finished yet.*/
    public static ArrayList<String> database = new ArrayList<String>();
    
    /*Here is the input for the user interface determined.*/    
    public static String readInput(){
        Scanner input = new Scanner(System.in);    
        String consoleInput = "";  
        if(input.hasNextLine()){
            consoleInput = input.nextLine();        
        }  
        return consoleInput;            
    }
    
    /* We can give this void, because it doesn't have to return anything. */        
    public static void helpUser(){ 
        System.out.println("COMMANDS");  
        System.out.println("list            print database");
        System.out.println("add ...         add to database");
        System.out.println("compare ... ... compare two strings");
        System.out.println("retrieve ...    find in database");
        System.out.println("remove ...      remove from database");
        System.out.println("quit            stop");
        System.out.println("help            prints this text");        
    } 
    
    public static void showList(){
        for(String listItem : database){
            System.out.println(listItem);        
        }            
    }
     
    /* The method .input doesn't work for an Array list and 
       has to be replaced by .add.*/    
    public static void addDNA(String dna){
        database.add(dna);  
    }

    /* The method .delete doesn't work for an Array list and 
       has to be replaced by .remove.*/          
    public static void removeDNA(String dna){
        database.remove(database.indexOf(dna));    
    }
        
    public static void compareDNA(String dna1, String dna2){
        System.out.println("Difference = " + levenshteinAlgorithm(dna1, dna2, true));    
    }    
       
    /* Compare two strings using Levenshtein algorithm. */   
    public static int levenshteinAlgorithm(String dna1, String dna2, boolean bool){
        int[][] d = new int[dna1.length() + 1][dna2.length() +1]; 
        int i = 0, j = 0;
    
        /* delete */ 
    
        for(; i <= dna1.length(); i++){
            d[i][0] = i;        
        }        
        
        /* insert */
        
        for(; j <= dna2.length(); j++){
            d[0][j] = j;
        }    
    
        for(j = 1; j <= dna2.length(); j++){
            for(i = 1; i <= dna1.length(); i++){
                if(dna1.charAt(i-1) == dna2.charAt(j-1)){
                    d[i][j] = d[i-1][j-1];
                }
                else{                 
                    d[i][j] = minumumValue(d[i-1][j] + 1, d[i][j-1] + 1, d[i-1][j-1] + 1);
                }                
            } 
        }
        
        /* The printing of the Levenshtein chart has to be done outside of the
           forloop.*/
        if(bool){          
            for(int row = 0; row <= dna1.length(); row++){
                for(int column = 0; column <= dna2.length(); column++){
                    System.out.print(d[row][column]+ "  ");
                }
                System.out.println();
            }  
        }             
        return d[dna1.length()][dna2.length()];
    }
    
    /* Calculating de lowest value for the Levenshtein chart 
       has to be done like this.*/   
    private static int minumumValue(int above, int diagonal, int left){
        int minimum = 0;
        if(above < diagonal){
            minimum = above;
            if(left < minimum){
                minimum = left;
            }
        }
        else{
            minimum = diagonal;
            if(left < minimum){
                minimum = left;
            }
        }
       return minimum;
    }
       
       
    public static void retrieveMatch(String retrieveDNA){
        String [] stringArray = new String [database.size()];
        database.toArray(stringArray);

        int r = 0;
        int [] differenceArray = new int[stringArray.length];
                  
        for(String databaseItem : stringArray){ 
            differenceArray[r++] = levenshteinAlgorithm(retrieveDNA, databaseItem, false); 
        }   
        /* To get the length from the string array,
           use the .length element without (). 
           To swith the elements in stringArray and differenceArray,
           we need to put value's in temporary ints and strings. */
        for(int x = 0; x < stringArray.length; x++){
            for(int y = 1; y < stringArray.length; y++){
                if(differenceArray[y - 1] > differenceArray[y]){
                    int tempInt = differenceArray[y - 1];
                    differenceArray[y - 1] = differenceArray[y];
                    differenceArray[y] = tempInt;
                    String tempString = stringArray[y - 1];
                    stringArray[y - 1] = stringArray[y];
                    stringArray[y] = tempString;
         
                }
            }
        }  

        System.out.println("Best matches: ");
             
        for(r = 0; r < Math.min(3, stringArray.length); r++){
            System.out.println(differenceArray[r] + " " + stringArray[r]);        
        }
    }    

    
    // Userinterface.
    /* The Userinterface. We can give this void 
       because it doens't have to return anything. */
    public static void executeConsole(){
        boolean quit = false;       
        do{
            System.out.println("Type help for all the commands");
            System.out.print("console>");
            String consoleInput;
            consoleInput = readInput();
            String [] P = consoleInput.split(" ");
            P[0] = P[0].toUpperCase();
            String command = P[0];

            if(command.equals("HELP")){
                helpUser();
            } 
            else if(command.equals("QUIT")){
                quit = true;
            } 
            else if(command.equals("LIST")){
                showList();            
            }
            else if(command.equals("ADD")){
                addDNA(P[1]);                               
            } 
            else if(command.equals("REMOVE")){
                removeDNA(P[1]);
            } 
            else if(command.equals("COMPARE")){
                compareDNA(P[1], P[2]);
            } 
            else if(command.equals("RETRIEVE")){
                retrieveMatch(P[1]);            
            }
            else{
                System.out.println("Skipping...");
            }
        /* The exclamation had to be removed from the while condition. */
        } while(quit == false);          
    }
   
    /* The code for the userface doesn't have to be repeated in the main method
       or elsewhere. The boolean quit is declared and initialised within
       executeConsole, so it can be removed from the main.*/
    public static void main(String[] args){
        System.out.println("Welcome to DNA Matcher v0.1\n");
        database = new ArrayList<String>();
        executeConsole();        
    }
}
            

