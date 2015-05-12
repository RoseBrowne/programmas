/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *  -  Opgave7.java: Dit programma berekent verschillende gegevens voor 2 data en een interval.
 *  -  Invoer: Een datum, een interval en een tweede datum.
 *  -  Uitvoer: 
 *     - De jaren binnen het interval waarop de ingevoerde dag/maand combi op een zondag valt.
 *     - De volledige datum
 *     - Het weeknummer van de datum
 *     - Het verschil in dagen, weken, maanden en jaren tussen de 1e en 2e datum.
 *
 */
import java.util.Scanner;
public class Opgave7{
    public static void main(String[] args){
        String eersteDatum = "";
        String tweedeDatum = "tweede ";
        Datum deDatum = datumInvoer(eersteDatum);   
        intervalInvoer(deDatum);
        System.out.println();
        System.out.println("De volledige datum is: " + deDatum.toString());
        System.out.println();
        System.out.println("Het weeknummer is: " + deDatum.getWeekNummer());
        Datum deDatum2 = datumInvoer(tweedeDatum);   
        deDatum.verschil(deDatum2);
    }   

    /* Hier wordt gevraagd om een datum in te voeren en gecontroleerd of dit
       volgens het juiste patroon is gedaan. Dmv Do-while wordt er net zo lang
       om een datum gevraagd tot de invoer juist is. */
    public static Datum datumInvoer(String datumNummer){
        Scanner input = new Scanner(System.in); 
        Datum deDatum = null;
        do{
            String datum = "";  
            System.out.print("Geef een " + datumNummer +  "datum (dd-mm-jjjj): ");
            if(input.hasNextLine()){
                datum = input.nextLine();      
            }

            String patroon = "\\d{2}-\\d{2}-\\d{4}"; 
            if(!datum.matches(patroon)){
                System.out.println("Invalide datum");
                continue;
            }
            
            /* Hier wordt indien de invoer juist is een datum object aangemaakt
               en de ingevoerde gegevens meegegeven aan de Datum constructor */
            String[] str = datum.split("-");    
            int dag = Integer.parseInt(str[0]);
            int maand = Integer.parseInt(str[1]);
            int jaar = Integer.parseInt(str[2]);
            try{
                deDatum = new Datum(dag, maand, jaar);     
            }   
            catch(InvalidDateException e){
                    deDatum = null;
            }
        }
        while(deDatum == null);
        return deDatum;
    }

    /* Hier wordt gevraagd om een interval in te voeren en gecontroleerd of dit
       volgens het juiste patroon is gedaan. Dmv Do-while wordt er net zo lang
       om een interval gevraagd tot de invoer juist is. */
    public static void intervalInvoer(Datum deDatum){
        Scanner input = new Scanner(System.in); 
        Interval hetInterval = null;  
        do{ 
            String interval = "";  
            System.out.print("Geef een interval (jjjj-jjjj): ");
            if(input.hasNextLine()){
                interval = input.nextLine();        
            }
            String patroon = "\\d{4}-\\d{4}"; 
            if(!interval.matches(patroon)){
                System.out.println("Onjuiste invoer van interval");
                continue;
            }
            /* Hier wordt indien de invoer juist is een interval object aangemaakt
               en de ingevoerde jaren meegegeven aan de interval instructor. */
            String[] str = interval.split("-");  
            int interval1 = Integer.parseInt(str[0]);
            int interval2 = Integer.parseInt(str[1]);
            try{
                hetInterval = new Interval(interval1, interval2, deDatum);  
            }
            catch(InvalidDateException e){
                hetInterval = null;
            }
        }
        while(hetInterval == null);
    }
}
