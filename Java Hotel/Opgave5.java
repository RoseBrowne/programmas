/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *  -  Opgave5.java:
 *     Dit programma beheert de administratie van een hotel
 *     Input: actie die uitgevoerd moet worden dmv 1, 2, 3 of 4 
 *     en afhankelijk van de keuze gast gegevens of kamer nummer
 *     Output: Status overzicht van de hotel bezetting.
 *     Gebruik: java Opgave5 [geheel getal]
 *
 *
 */
 
import java.util.Scanner;
public class Opgave5 {

    public static void main(String[] args){
        int aantalKamers = 0;
        boolean stoppen = false;
    
        if(args.length == 0){
            System.out.println("U moet het aantal kamers invoeren");
            System.exit(0);
        } 
        
        /* Hier wordt een hotel aangemaakt en het aantal kamers meegegeven
           via de command line. */
        aantalKamers = Integer.parseInt(args[0]);
        Hotel hotel = new Hotel(aantalKamers);
     
            do{
                System.out.println();
                System.out.print("Kies uit ");
                System.out.print("[1] Statusoverzicht ");
                System.out.print("[2] Gasten inchecken ");
                System.out.print("[3] Gasten uitchecken");
                System.out.print("[4] Einde");
                System.out.println();
                int keuze = menuKeuze();

                if(keuze == 1){
                    hotel.statusOverzicht();
                }
                else if(keuze == 2){
                    hotel.inchecken();
                }
                else if(keuze == 3){
                    hotel.uitchecken();
                }
                else if(keuze == 4){
                    stoppen = true;
                } 
            } while(stoppen == false);    
    
    
    
    }
    
    
    public static int menuKeuze(){
        Scanner input = new Scanner(System.in); 
        int keuze = 0;   
        System.out.print("Uw keuze: ");
        if(input.hasNextInt()){
            keuze = input.nextInt();        
        }
        return keuze;            
    }

}

