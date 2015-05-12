/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *  -  Hotel.java:
 *     Deze klasse beheert het aantal gasten en (beschikbare) kamers 
 *     en regelt het inchecken, uitchecken en status overzicht.
 *
 */
 
import java.util.Scanner; 
public class Hotel{
    int aantalKamers;
    Kamer[] kamers;
    int beschikbareKamers;
    Gast nieuweGast;
    int aantalGasten;
    
    /* Hier wordt een string met alle kamers geinitsaliseerd,
       afhankelijk van het meegegeven aantal kamers. */
    Hotel(int aantalKamers){   
        this.aantalKamers = aantalKamers;
        beschikbareKamers = aantalKamers;   
        kamers = new Kamer[aantalKamers];
        
        for(int i = 0; i < aantalKamers; i++){
            kamers[i] = new Kamer();
        }
    
    }  
                        
    public int statusOverzicht(){
        int k = 0;
        
        /* Hier wordt de inhoud van een kamer geprint dmv kamers[i]. */
        for (int i = 0; i < aantalKamers; i++) {
            System.out.print("Kamer " + (i + 1) + ": " + kamers[i]);
        }  
        System.out.println("Aantal gasten: " + aantalGasten);
        return k;
    }
    
    public int inchecken(){
        if(beschikbareKamers > 0){
            System.out.println("Er  is een kamer vrij.");
            for(int i = 0; i < kamers.length; i++){
                Kamer kamer = kamers[i];
                
                /* Hier wordt gekeken welke kamer leeg is,
                   zodat het systeem weet in welke kamer ingecheckt kan worden. */
                if(kamer.gast == null){
                    kamers[i] = new Kamer(nieuweGast);
                    beschikbareKamers -= 1;
                    aantalGasten += 1;
                    break;
                }
            }
        }
        else{
            System.out.println("Er is helaas geen kamer meer vrij.");
        }
        
        return beschikbareKamers;
    }
    
    public int uitchecken(){
        System.out.println("In welke kamer verbleef de gast?");
        Scanner inputNum = new Scanner(System.in);
        int kamerNummer = 0;
        if(inputNum.hasNextInt()){
            kamerNummer = inputNum.nextInt();
        }
        
        kamers[kamerNummer - 1] = new Kamer();
        beschikbareKamers += 1;
        aantalGasten -= 1;
        return beschikbareKamers;
    }
    

          
}
