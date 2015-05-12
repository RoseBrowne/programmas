/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *     Paar.java:
 *  -  Een polynoom bestaat uit een som van termen 
 *     die bestaan uit een coefficient en een macht.
 *  -  Paar.java bevat die term.
 *
 *     Bron: http://docs.oracle.com/javase/tutorial/collections/interfaces/order.html
 */
import java.util.ArrayList;
class Paar implements Comparable <Paar> {
    double coef;
    int macht;

    Paar(double a, int b){
        coef = a;
        macht = b;
    }

    /* Hier worden de machten met elkaar vergeleken en wordt er een waarde 
       gereturned om de termen in een polynoom aflopende op macht te kunnen sorteren */
    public int compareTo(Paar that) {
        int grootste = 0;
        if(macht < that.macht){
            grootste = -1;
        }
        else if(macht == that.macht){
            grootste = 0;
        }
        else if(macht > that.macht){
            grootste = 1;
        }
        return grootste;
    }

    public boolean equals(Object object) {
        return false;
    }

    public String toString() {
        String hetPaar;

        if(coef == 0){
            hetPaar = "";
        }

        else if(coef == 1){
            if(macht == 0){
                hetPaar = coef + "";
            }
            else{
                hetPaar = "x^" + macht;
            }
        }

        else{
            if(macht == 0){
                hetPaar = coef + "";
            }
            else{
                hetPaar = coef + "x^" + macht;
            }
        }
        return hetPaar;
    }
}
