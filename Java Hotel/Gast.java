/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *  -  Gast.java:
 *     In deze klasse krijgen een element Gast zijn waarden.
 *
 */
 
import java.util.Scanner; 
public class Gast{
    String aNaam, vNaam, deGast;
    Datum gDatum;
    
    /* Hier worden alle gegevens van de gast ingevoerd via de Scanner methode. */
    Gast(){
        String achterNaam = inputAnaam();
        String voorNaam = inputVnaam();
        String geboorteDatum = inputGdatum();
        aNaam = achterNaam; 
        vNaam = voorNaam;
        gDatum = new Datum(geboorteDatum);
        deGast = toString();
        
    }
     
    /* Hier worden de losse strings die alle gegevens van de gast bevatten
       in 1 string gezet, zodat die in Kamer.java aan gast kan worden toegewezen. */    
    public String toString(){
        String gast = aNaam + ", " + vNaam + " " + gDatum;
        return gast;
    }
      

    
    public static String inputAnaam(){
        Scanner input = new Scanner(System.in);    
        String achterNaam = "";  
        System.out.print("Geef achternaam gast: ");
 
            if(input.hasNextInt()){
                System.out.println("Uw kunt geen getallen invullen.");
                System.exit(1);
            }
            else if(input.hasNextLine()){
                achterNaam = input.nextLine();
            }       
        return achterNaam;            
    }    
    
    public static String inputVnaam(){
        Scanner input = new Scanner(System.in);    
        String voorNaam = "";  
        System.out.print("Geef voornaam Gast: ");
        if(input.hasNextInt()){
            System.out.println("Uw kunt geen getallen invullen.");
            System.exit(1);
        }
        else if(input.hasNextLine()){
            voorNaam = input.nextLine();      
        }  
        return voorNaam;            
    }
    
    public static String inputGdatum(){
        Scanner input = new Scanner(System.in);    
        String geboorteDatum = "";  
        System.out.print("Geef geboortedatum gast [dd-mm-jjjj]: ");
        if(input.hasNextLine()){
            geboorteDatum = input.nextLine();        
        }

        return geboorteDatum;            
    }
}

