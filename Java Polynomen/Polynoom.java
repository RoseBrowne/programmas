/*
 *  Naam: Rose Browne
 *  Studentnummer: 10492674
 *  Studie: Informatica
 *
 *     Polynoom.java:
 *  -  Dit programma leest Polynomen uit files.
 *  -  Vervolgens voert het de berekenen: optellen, aftrekken, vermenigvuldigen, 
 *     differentieren en integreren uit met de ingelezen polynomen.
 *
 *     Bron: http://stackoverflow.com/questions/1694751/java-array-sort-descending
 */

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.Collections;
class Polynoom implements PolynoomInterface {
    ArrayList<Paar> termen;
    Polynoom pol1;
    
    /* Hier wordt de ArrayList met termen van een polynoom aangemaakt. */
    Polynoom(String filenaam) {
        this.termen = new ArrayList <Paar>();
        leesPolynoom(filenaam, termen);
    }

    /* Hier wordt weer een nieuwe ArrayList aangemaakt voor de uitkomsten van de
       berekeningen. */
    Polynoom(){
        termen = new ArrayList <Paar>();
    }  

    /* Hier wordt de inhoud van de file gelezen en in paren van 2 meegegeven 
       aan de constructor voor het aanmaken van paren. */
    static void leesPolynoom(String filenaam, ArrayList <Paar> termen){
        int aantalGetallen = 0;
        int getal1 = 0;
        int getal2 = 0;

        try{
            Scanner input = new Scanner(new File(filenaam));
            while (input.hasNext()){
                int first = input.nextInt();
                aantalGetallen += 1;
                if(aantalGetallen == 1){
                    getal1 = first;
                }
                else if(aantalGetallen == 2){
                    getal2 = first;
                    Paar paar = new Paar(getal1, getal2);
                    termen.add(paar);
                    aantalGetallen = 0;
                }
            }
            input.close();
        }

        catch(IOException e){
            System.out.println("iets fout");
        }   

        Collections.sort(termen, Collections.reverseOrder());     
    }

    public String toString(){ 
        String dePolynoom = termen.get(0) + "";
        String plus = "";
        String hetPaar = ""; 

        for(int i = 1; i < termen.size(); i++){
            if(termen.get(i).coef < 0){
                plus = " ";
            }
            else if(termen.get(i).coef > 0){
                plus = " + ";
            }
            dePolynoom += plus + termen.get(i);
        }
        return dePolynoom;   
    }

    public Polynoom telop(Polynoom pol2){
        Polynoom som = new Polynoom();    

        /* Alle termen uit de 1e polynoom worden aan de polynoom van de som toegevoegd. */
        for(int i = 0; i < termen.size(); i++){
            som.termen.add(termen.get(i));
        }

        /* Alle termen uit de 1e polynoom worden aan de polynoom van de som toegevoegd. */
        for(int i = 0; i < pol2.termen.size(); i++){
            som.termen.add(pol2.termen.get(i));
        }

        /* Hier woorden alle termen uit de polynoom som met elkaar vergeleken en
           indien de machten van twee termen gelijk zijn worden ze bij elkaar opgeteld.
           Start is 1 en word na elke loop opgehoogd met 1,
           omdat je termen niet dubbel wilt vergelijken. */
        int start = 1;
        for(int i = 0; i < som.termen.size(); i++){
            for(int j = start; j < som.termen.size(); j++){
                if(som.termen.get(i).macht == som.termen.get(j).macht){
                    som.termen.get(i).coef = (som.termen.get(i).coef + som.termen.get(j).coef);
                    som.termen.remove(j);
                }
            }
            start += 1;
        }

        Collections.sort(som.termen, Collections.reverseOrder());
        return som;
    }

    public Polynoom trekaf(Polynoom pol2){
        Polynoom verschil = new Polynoom();

        for(int i = 0; i < this.termen.size(); i++){
            verschil.termen.add(this.termen.get(i));
        }

        for(int i = 0; i < pol2.termen.size(); i++){
            pol2.termen.get(i).coef = (-1 * pol2.termen.get(i).coef);
            verschil.termen.add(pol2.termen.get(i));
        }

        int start = 1;
        for(int i = 0; i < verschil.termen.size(); i++){
            for(int j = start; j < verschil.termen.size(); j++){
                if(verschil.termen.get(i).macht == verschil.termen.get(j).macht){
                    verschil.termen.get(i).coef = (verschil.termen.get(i).coef + verschil.termen.get(j).coef);
                    verschil.termen.remove(j);
                }
            }
            start += 1;
        }

        Collections.sort(verschil.termen, Collections.reverseOrder());
        return verschil;
    } 

    public Polynoom vermenigvuldig(Polynoom pol2){
        Polynoom product = new Polynoom();   
        
        /* Elke coefficient van de 1e polynoom wordt vermenigvuldigd met 
           die van de 2e polynoom en de bijbehorende machten worden bij elkaar opgeteld.
           Het paar wat hieruit ontstaan wordt toegevoegd aan de product polynoom. */
        for(int i = 0; i < termen.size(); i++){
            for(int j = 0; j < pol2.termen.size(); j++){
                double hetCoef = (termen.get(i).coef * pol2.termen.get(j).coef);
                int deMacht = (termen.get(i).macht + pol2.termen.get(j).macht);
                Paar hetProduct = new Paar(hetCoef, deMacht);
                product.termen.add(hetProduct);              
            }     
        }  

        /* Hier worden termen met een zelfde macht bij elkaar opgeteld. */
        int start = 1;
        for(int i = 0; i < product.termen.size(); i++){
            for(int j = start; j < product.termen.size(); j++){
                if(product.termen.get(i).macht == product.termen.get(j).macht){
                    product.termen.get(i).coef = (product.termen.get(i).coef + product.termen.get(j).coef);
                    product.termen.remove(j);
                }
            }
            start += 1;
        }

        Collections.sort(product.termen, Collections.reverseOrder());
        return product;
    }

    public Polynoom differentieer(){
        Polynoom diff = new Polynoom();

        /* Alle coefficienten uit de polynoom worden vermenigvuldigd met de 
           bijbehorende macht en die macht wordt vervolgens verlaagd met 1. Het 
           paar dat daaruit ontstaat wordt toegevoegd aan de differentatie polynoom. */
        for(int i = 0; i < termen.size(); i++){

            /* Dit geldt alleen als de macht van een term niet gelijk is aan nul. */
            if(termen.get(i).macht != 0){
                double hetCoef = (termen.get(i).coef * termen.get(i).macht);
                int deMacht = (termen.get(i).macht - 1);
                Paar deDiff = new Paar(hetCoef, deMacht);
                diff.termen.add(deDiff);
            }
        }

        Collections.sort(diff.termen, Collections.reverseOrder());
        return diff;
    } 

    public Polynoom integreer(){
        Polynoom integr = new Polynoom();

        /* Alle machten uit de polynomen worden verhoogd met één en de 
           bijbehorende coefficient worden gevonden door de oude coefficient 
           te delen door de nieuwe macht. Het paar wat hieruit ontstaat 
           wordt toegevoegd aan de integratie Polynoom. */
        for(int i = 0; i < termen.size(); i++){
            int deMacht = (termen.get(i).macht + 1);
            double hetCoef = (termen.get(i).coef / deMacht);
            Paar deDiff = new Paar(hetCoef, deMacht);
            integr.termen.add(deDiff);
        }

        /* De integratie constante 1, wordt hier toegevoegd aan de integratie polynoom. */
        Paar nul = new Paar(1.0, 0);
        integr.termen.add(nul);

        Collections.sort(integr.termen, Collections.reverseOrder());
        return integr;
    }
}
