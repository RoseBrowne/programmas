/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *     TestPolynoom.java:
 *  -  Hier worden de methoden aangeroepen
 *  -  Hier wordt aangegeven welke polynoom waar in de methode ingevuld moet worden.
 *
 */

class TestPolynoom {
    public static void main (String[] args) {
        Polynoom pol1, pol2, som, verschil, product, diff1, integr, orig;
        String polynoom1 = "polynoom1";
        String polynoom2 = "polynoom2";
        pol1 = inlezen(polynoom1);
        pol2 = inlezen(polynoom2);

        System.out.println("Polynoom 1: " + pol1);
        System.out.println("Polynoom 2: " + pol2);
        som = pol1.telop(pol2);
        System.out.println("som: " + som);
        
        pol1 = inlezen(polynoom1);
        pol2 = inlezen(polynoom2);
        verschil = pol1.trekaf(pol2);
        System.out.println("verschil: " + verschil);

        pol1 = inlezen(polynoom1);
        pol2 = inlezen(polynoom2);
        product = pol1.vermenigvuldig(pol2);
        System.out.println("product: " + product);

        diff1 = pol1.differentieer();
        System.out.println("differentieer pol1: " + diff1);
        diff1 = pol2.differentieer();
        System.out.println("differentieer pol2: " + diff1);
        
        integr = pol1.integreer();
        System.out.println("integreren pol 1: " + integr);
        integr = pol2.integreer();
        System.out.println("integreren pol 2: " + integr);
        
        //testen van differentieer- en integreer-methode
        orig = pol1.differentieer().integreer();
        System.out.println("differentieer dan integreer pol 1: " + orig);
        if (orig.equals(pol1)) {
            System.out.println(" (is gelijk aan origineel)");
        }
        else {
            System.out.println(" (is niet gelijk aan origineel)");
        }
        orig = pol1.integreer().differentieer();
        System.out.println("integreer dan differentieren pol 1: " + orig);
        System.out.println("orig is: " + orig);
        System.out.println("pol1 is: " + pol1);
        if (orig.equals(pol1)) {
            System.out.println(" (is gelijk aan origineel)");
        }
        else {
            System.out.println(" (is niet gelijk aan origineel)");
        }
        
        orig = pol2.differentieer().integreer();
        System.out.println("differentieer dan integreer pol 2: " + orig);
        if (orig.equals(pol1)) {
            System.out.println(" (is gelijk aan origineel)");
        }
        else {
            System.out.println(" (is niet gelijk aan origineel)");
        }
        orig = pol2.integreer().differentieer();
        System.out.println("integreer dan differentieren pol 2: " + orig);
        System.out.println("orig is: " + orig);
        System.out.println("pol1 is: " + pol1);
        if (orig.equals(pol1)) {
            System.out.println(" (is gelijk aan origineel)");
        }
        else {
            System.out.println(" (is niet gelijk aan origineel)");
        }
    }

    /* Hier worden de polynomen aangemaakt. De orginele waarde van de polynoom
       veranderde steeds na berekeningen, daarom wordt ie hiermee steeds opnieuw
       ingelezen. */
    
    public static Polynoom inlezen(String filenaam){
        Polynoom pol = new Polynoom(filenaam);
        return pol;
    }
}
