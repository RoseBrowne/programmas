/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *     Maand.java:
 *  -  Hier wordt de maand gecontroleerd en de "naam" van de maand bepaald.
 *
 */
 public class Maand {
    private int deMaand;
    private Datum deDatum;
    String[] maandNamen = new String[] {"","januari","februari","maart", 
    "april","mei","juni","juli", "augustus", "september", 
    "oktober", "november", "december"};
    String maandNaam;

    Maand(int maand, Datum datum) throws InvalidDateException{
        deMaand = maand;
        deDatum = datum;
        checkSemantisch();
    }

    public int getWaarde(){
        return deMaand;
    }

    /* Hier wordt de waarde van de maand gecontroleerd. */
    public void checkSemantisch() throws InvalidDateException{
        if(deMaand > 12 | deMaand < 1){
            System.out.println("Onjuiste datum: De maand moet minimaal 1 en maximaal 12 zijn.");
            throw new InvalidDateException();
        }
    }

    /* Hier wordt gekeken wat de naam van een maand is. */
    public String checkMaandNaam(){
        maandNaam = maandNamen[deMaand];
        return maandNaam;
    }
}
