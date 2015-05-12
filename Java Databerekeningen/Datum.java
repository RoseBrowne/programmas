/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *     Datum.java:
 *  -  De datum bestaat uit een dag, maand en jaar en worden tenzamen met de datum
 *     weer meegegeven met de bijbehorenden constructoren.
 *
 */ 
public class Datum {
    private Dag dag;
    private Maand maand;
    private Jaar jaar;

    /* Hier worden de dag, maand en jaar objecten aangemaakt */
    Datum(int dag, int maand, int jaar) throws InvalidDateException{
        this.jaar = new Jaar(jaar, this);
        this.maand = new Maand(maand, this);
        this.dag = new Dag(dag, this);                
    }

    public Jaar getJaar(){
        return jaar;
    } 

    public Maand getMaand(){
        return maand;
    }

    public Dag getDag(){
        return dag;
    }

    /* Hier worden de waarden voor het printen van de volledige datum aan elkaar
       geconcateneerd. */
    public String toString(){
        String datumString = getDag().checkDagNummer() + " " + 
        getDag().getWaarde() + " " + getMaand().checkMaandNaam() + ", " + 
        getJaar().getWaarde();
        return datumString;
    }
    
    public int getWeekNummer(){
        return getDag().checkWeekNummer();
    }

    /* Hier wordt het verschil tussen de eerste en de tweede datum berekend. */
    public void verschil(Datum that){       
        int dagenTotJaar1 = berekenDagen(this.jaar.getWaarde(), this.maand.getWaarde(), this.dag.getWaarde());
        int dagenTotJaar2 = berekenDagen(that.jaar.getWaarde(), that.maand.getWaarde(), that.dag.getWaarde());       

        int verschilDagen;
        if (dagenTotJaar1 > dagenTotJaar2){
            verschilDagen = dagenTotJaar1 - dagenTotJaar2;
        }
        else{
            verschilDagen = dagenTotJaar2 - dagenTotJaar1;
        }
        int verschilJaren = verschilDagen / 365;
        int verschilMaanden = (verschilDagen*12)/365;
        int verschilWeken = verschilDagen/7;
        System.out.println("Totaal aantal jaren:     " + verschilJaren + " jaren");
        System.out.println("Totaal aantal maand:     " + verschilMaanden + " maanden");
        System.out.println("Totaal aantal weken:     " + verschilWeken + " weken");
        System.out.println("Totaal aantal dagen:     " + verschilDagen + " dagen");
        System.out.println();
        System.out.println("(Deze aantallen zijn naar beneden afgerond.)");
    }    

    /* Hier wordt het aantal dagen van 1754 tot de meegegeven datum berekent. */
    public int berekenDagen(int jaarX, int maandX, int dagX){
        int dagenVanafX = 0;
        for(int i = 1754; i < jaarX; i++){
            boolean schrikkelJaar = Jaar.checkSchrikkel(i);
            if(schrikkelJaar == true){
                dagenVanafX += 366;
            }
            else{
                dagenVanafX += 365;
            }
        }

        for(int i = 1; i < maandX; i++){           
            if(i == 2){
                boolean schrikkelJaar = Jaar.checkSchrikkel(jaarX);
                if(schrikkelJaar){
                    dagenVanafX += 29;
                }
                else{
                    dagenVanafX += 28;
                }
            }
            else{
                if (i == 4|i == 6|i == 9|i == 11){
                    dagenVanafX +=30;
                }
                else{
                    dagenVanafX += 31;
                }
            }
        }
        dagenVanafX += (dagX - 1);
        return dagenVanafX;
    }
}
