/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *     Jaar.java:
 *  -  Hier worden de jaar objecten gecontroleerd en gekeken of het 
 *     schrikkel jaren zijn.
 *  -  Dat "hetJaar" van beide constructoren de jaar waarde krijgt vormt geen
 *     problemen, omdat de jaar waarde van de datum na het aanroepen van de Jaar
 *     constructor voor het interval alleen nog maar opgevraagd wordt via Datum.java.
 *
 */
 public class Jaar {
    int hetJaar;
    boolean boolJaar;
    private Datum deDatum;

    Jaar(int jaar, Datum datum) throws InvalidDateException{
        deDatum = datum;
        hetJaar = jaar;
        checkSemantisch();
    }

    /* Deze Jaar constructor is voor de interval objecten en bevat geen datum. */
    Jaar(int jaar) throws InvalidDateException{
        hetJaar = jaar;
        checkSemantisch();
    } 

    public int getWaarde(){
        return hetJaar;
    }

    /* Hier wordt voor de meegegeven waarde(testWaarde) gekeken of het een
       schrikkel jaar is. */
    public static boolean checkSchrikkel(int testWaarde){
        boolean schrikkelJaar = false;
        if(testWaarde % 4 == 0){
            schrikkelJaar = true;
            if(testWaarde % 100 == 0){
                schrikkelJaar = false;
                if(testWaarde % 400 == 0){
                    schrikkelJaar = true;
                }
            }
        }
        return schrikkelJaar;
    }

    /* Hier wordt gekeken of de ingevoerde jaartallen juist zijn. */
    public void checkSemantisch() throws InvalidDateException{
        if(hetJaar < 1754){
            System.out.println("Onjuiste datum: Jaartallen vóór 1754 zijn niet toegstaan.");
            throw new InvalidDateException();
        }
    }
}
