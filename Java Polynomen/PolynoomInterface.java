/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *     PolynoomInterface.java:
 *  -  Hier wordt bepaald hoe de gebruikte methoden worden aangeroepen.
 *
 */

interface PolynoomInterface {
    Polynoom vermenigvuldig(Polynoom that);
    Polynoom trekaf(Polynoom that);
    Polynoom telop(Polynoom that);
    Polynoom differentieer();
    Polynoom integreer();
    boolean equals(Object object);
    String toString();
}

