/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *  -  Datum.java:
 *     In deze klasse wordt gekeken of de datum in de correcte vorm is ingevoerd.
 *     Zo ja, dan wordt die in de vorm dd.mm.jj gezet.
 *     Zo nee, dan wordt het programma afgebroken.
 *
 */
 
public class Datum {
    String geboortedatum, deDatum, ster;
    String dag, maand, jaar;
    int meerderjarigDatum = 19961001;
    
    Datum(String gDatum){
        geboortedatum = gDatum;
        checkDatum();    
        ster = check18();
        deDatum = toString();
    }
    
    /* Hier wordt gekeken of de gast jonger dan 18 is door van de geboortedatum 
       van de gast een geheel getal te maken en te kijken of die kleiner is dan 
       de datum waarop iemand "vandaag" 18 zou zijn. */
    public String check18(){
        String datumCheck = jaar + maand + dag;
        int datumCheckint = Integer.parseInt(datumCheck.toString());
        if(meerderjarigDatum < datumCheckint){
            ster = "*";
        } 
        else{
            ster = "";
        }
        return ster;
    }
    
    /* Hier worden de losse strings uit de datum omgezet in 1 string,
       zodat die in Gast.java aan gDatum toegewezen kan worden. */
    public String toString(){
        String Datum = "(" + dag + "." + maand + "." + jaar + ")" + ster;
        return Datum;
    }
    
    /* Hier wordt gekeken of de ingevoerde datum 
       volgens het gewenste patroon is ingevoerd.
       Indien dit zo is wordt b true, zo niet false. */      
    public String checkDatum(){
    String datom = "";
    String patroon = "\\d{2}-\\d{2}-\\d{4}"; 
    boolean b = geboortedatum.matches(patroon);
    
    if(b) {
        /* Hier wordt de datum opgesplits in 3 elementen
           en toegewezen aan dag, maand en jaar. */
        String[] str = geboortedatum.split("-");        
            dag = str[0];
            maand = str[1];
            jaar = str[2];
  
        }
        else{
            System.out.println("Geboortedatum in onjuiste formaat");
            System.exit(1);
        }
        return datom;
    }
        
        

}
