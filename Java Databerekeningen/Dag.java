/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *     Dag.java:
 *  -  Dag bevat ook de maand en jaar waarden, omdat het deze gegevens nodig heeft
 *     bij het checken of de dag waarde correct is, aangezien de maximale dag waarde
 *     afhankelijk is van de maand en het jaar(voor februari).
 *
 *  -  Bron: Voor het berekenen van het week http://nl.wikipedia.org/wiki/Weeknummer
 *
 */
public class Dag {
    private int deDag;
    int dagNummer;
    Jaar jaar;
    Maand maand;
    Datum deDatum;
    String naamDag;
    /* Het 1e element in de String Array is leeg, omdat het eerste dagnummer 1 is. */
    String[] dagNamen = new String[] { "","Maandag","Dinsdag","Woensdag"
    ,"Donderdag","Vrijdag","Zaterdag","Zondag"  };

    Dag(int dag, Datum datum) throws InvalidDateException{
        deDag = dag;
        jaar = datum.getJaar();
        maand = datum.getMaand();
        deDatum = datum;
        checkSemantisch();
    }
    
    public int getWaarde(){
        return deDag;
    }     
    
    /* Hier wordt gekeken of de ingevoerde dag waarde correct is. */
    public void checkSemantisch() throws InvalidDateException{ 
        if(deDag > 31 | deDag < 1){
            System.out.println("Onjuiste datum: De dag moet minimaal 1 en maximaal 31 zijn.");
            throw new InvalidDateException();
   
        }
        if(deDag > 28 && maand.getWaarde() == 2){ 
            if(deDag == 29 && jaar.checkSchrikkel(deDatum.getJaar().getWaarde())){ 
                return;
            }
            else{
                System.out.println("Onjuiste datum: Dit jaar heeft geen 29 februari, want het is geen schrikkeljaar.");
                throw new InvalidDateException(); 
            }          
        }
        if(deDag > 30){
            if(maand.getWaarde() == 4 | maand.getWaarde() == 6 | maand.getWaarde() == 9 | maand.getWaarde() == 11){
                System.out.println("Onjuiste datum: " + maand.checkMaandNaam() + " heeft maar 30 dagen");
                throw new InvalidDateException();    
            }
        }
             
    }

    /* Hier wordt het dag nummer bepaald. */
    public String checkDagNummer(){        
        int dagenVanaf1754 = deDatum.berekenDagen(jaar.getWaarde(), maand.getWaarde(), deDag);
        int rest = dagenVanaf1754 % 7;
        if(rest == 0){
            dagNummer = 2;
        }
        else{
            dagNummer = 2 + rest;
            if(dagNummer > 7){
                dagNummer -= 7;
            }
        }
        
        naamDag = dagNamen[dagNummer];             
        return naamDag; 
    }
    
    /* Hier wordt het week nummer berekend. */
    public int checkWeekNummer(){
        int dagenVanaf1754 = deDatum.berekenDagen(jaar.getWaarde(), maand.getWaarde(), deDag);
        int dagen1JanTotDatum = dagenVanaf1754 - deDatum.berekenDagen(jaar.getWaarde(), 1, 1) + 1;
        int weekJaar = 0;
        int weekDonderdag = 4 - dagNummer;
        
        /* Hier wordt gekeken of de week donderdag in het jaar vóór, ná of in
           hetzelfde jaar ligt als de datum. */            
        int dagen1JanTotDond = dagen1JanTotDatum + weekDonderdag;
        if (dagen1JanTotDond < 1){
            weekJaar = jaar.getWaarde() - 1;
        }
        else if (dagen1JanTotDond > 366){
            weekJaar = jaar.getWaarde() + 1;
        }
        else if(dagen1JanTotDond > 365 && !Jaar.checkSchrikkel(jaar.getWaarde())){
                weekJaar = jaar.getWaarde() + 1;
        }
        else{
                weekJaar = jaar.getWaarde();
        }

        /* "hetVerschilInDagen" en "dagen1JanTotDond" lijken hetzelfde, maar
           "hetVerschilInDagen" houdt rekening met het weekjaar en "dagen1JanTotDond"
           gebruikt het jaar van de ingevoerde datum. */
        int hetVerschilInDagen = dagenVanaf1754 - deDatum.berekenDagen(weekJaar, 1, 1);
        
        if (hetVerschilInDagen < 0){
            hetVerschilInDagen = (hetVerschilInDagen * -1);
        }
        hetVerschilInDagen += weekDonderdag;
        
        int weekNummer = hetVerschilInDagen/7+1;  
        return weekNummer; 
    }        
}
