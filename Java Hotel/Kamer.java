/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *  -  Kamer.java:
 *     In deze klasse worden de elementen van de array kamers aangemaakt.
 *
 */


public class Kamer{
    Gast gast;
      
    /* Hier wordt het element Kamer in de array kamers leeg gemaakt
       als ie nog leeg is of als er uitgecheckt wordt. */    
    Kamer(){
        gast = null;
    }
    
    /* Als er wordt ingecheckt wordt hier een nieuwe gast aangemaakt 
       en toegewezen aan het bijbehorende element Kamer in de array kamers. */     
    Kamer(Gast nieuweGast){
        nieuweGast = new Gast();
        gast = nieuweGast;
    }
             
    /* Hier wordt het element Gast omgezet naar een string,
       waarmee in Hotel.java de bezetting van een kamer geprint kan worden. */
    public String toString() {
        String bezetting = "";

        if (this.gast == null) {
            bezetting = "Vrij\n";
        } 
        else {
            bezetting = gast + "\n";
        }
        return bezetting;
    }
    
                
}    

    
    

