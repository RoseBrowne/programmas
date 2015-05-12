/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *     Interval.java:
 *  -  Het interval bestaat uit 2 jaren en de eerste datum, zodat adhv die twee 
 *     jaren en de 1e datum het aantal jaren waarop de ingevoerd datum een zondag 
 *     is, berekend kan worden.
 *
 */ 
public class Interval{
    private Jaar min;
    private Jaar max;
    private Datum deDatum;

    /* Hier wordt van het interval 2 jaar objecten gemaakt. */
    public Interval(int min, int max, Datum datum) throws InvalidDateException{
        this.min = new Jaar(min);
        this.max = new Jaar(max);
        deDatum = datum;
        if(checkInterval()){
            checkZondagen();
        }   
    }
    
    /* Hier wordt gekeken of het interval correct is. */
    public boolean checkInterval() throws InvalidDateException{
        int interval1 = min.getWaarde();
        int interval2 = max.getWaarde();

        if(interval1 > interval2){
            System.out.println("Onjuist interval: het eerste jaartal mag niet " 
            + "groter zijn dan het tweede.");
            throw new InvalidDateException();
        }
        return true;
    }    

    /* Hier vindt het berekenen van de jaren met een zondag op de 
       ingevoerde datum plaats. */
    public void checkZondagen(){      
        int interval1 = min.getWaarde();
        int interval2 = max.getWaarde();

        System.out.println();
        System.out.println(deDatum.getDag().getWaarde() + " " + 
        deDatum.getMaand().checkMaandNaam() + 
        " is een zondag in de volgende jaren:");  

        for(int i = interval1; i <= interval2; i++){
            int dagenVanaf1754 = deDatum.berekenDagen(i, deDatum.getMaand().getWaarde(),
             deDatum.getDag().getWaarde());
            int rest = dagenVanaf1754 % 7;
            /* Omdat we 1 jan 1754 een dinsdag is en we dit als eerste dag 
               beschouwen, moet de restwaarde 5 zijn wil het een zondag zijn. */
            if (rest == 5){
                System.out.print(i + " ");
            }  
        }
        System.out.println();
    }
}
