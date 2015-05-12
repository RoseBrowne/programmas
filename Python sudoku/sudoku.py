#
#   Naam: Rose Browne
#   UvA-ID: 10492674
#   Studie: informatica
#
#   Dit programma berekent voor een opgegeven sudoku de juiste oplossing.
#   De gebruiker geeft onderaan het programma een file met daarin de gewenste
#   sudoku op en deze wordt vervolgens opgelost.
#
#   Bronnen: 
#   Voor de necessityOplossingen() functie:
#   http://killersudokuonline.com/tips.html
#   Voor het berekenen van de mogelijke combinaties in de Brute Force oplossing:
#   https://docs.python.org/2/library/itertools.html#itertools.combinations_with_replacement
#
from numpy import *
import numpy
import copy
def checkLegeCellen(array):
    legeCellen = True
    # Hier wordt gekeken of de sudoku lege cellen bevat
    for rij in range(0, len(array)):
        for kolom in range(0, len(array)):
            if array[rij][kolom] == 0:
                legeCellen = False
    return legeCellen

def checkSudokuWaardes(array):   
    correcteWaardes = True 
    # Hier wordt gekeken of rijen in de sudoku dezelfde getallen bevatten
    for rij in range(0, len(array)):
        for i in range(0, len(array)):
            for j in range(0, len(array)):
                if i != j:
                    if array[rij][i] != 0 and array[rij][j] != 0:
                        if array[rij][i] == array[rij][j]:
                            correcteWaardes = False

    # Hier wordt gekeken of kolommen in de sudoku dezelfde getallen bevatten
    for kolom in range(0, len(array)):
        for i in range(0, len(array)):
            for j in range(0, len(array)):
                if i != j:
                    if array[i][kolom] != 0 and array[j][kolom] != 0:
                       if array[i][kolom] == array[j][kolom]:
                           correcteWaardes = False                           

    # Hier wordt gekeken of de ingenestelde blokken in de sudoku dezelfde getallen
    # bevatten. We maken hier gebruik v/d waardes links(i) en rechts(i), omdat
    # de sudoku rijen en kolommen opgesplits moeten worden en afzonderlijk
    # bekeken moeten worden. 
    linksi = 0
    rechtsi = vakjesGrote
    for kolom in range(0, vakjesGrote):
        links = 0
        rechts = vakjesGrote
        vakje = []
        for rij in range(0, vakjesGrote):    
            vakje = []
            for i in range(linksi, rechtsi):
                for j in range(links, rechts): 
                    vakje.append(int(array[i][j]))
                for m in range(0, len(vakje)):
                    for n in range(0, len(vakje)):
                        if m != n: 
                            if vakje[m] != 0 and vakje[n] != 0:
                                if vakje[m] == vakje[n]:
                                    correcteWaardes = False
            links = rechts
            rechts = rechts + vakjesGrote
        linksi = rechtsi
        rechtsi = rechtsi + vakjesGrote
    return correcteWaardes

# Hier worden alle mogelijke opties voor de lege cellen berekent door middel van
# het aantal lege cellen en de mogelijke getallen.
def product(*args, **kwds):
    pools = map(tuple, args) * kwds.get('herhaling', 1)
    result = [[]]
    for pool in pools:
        result = [x+[y] for x in result for y in pool]
    for prod in result:
        yield tuple(prod)

    pass

# Hier wordt gekeken of een rij, kolom of vak in de sudoku exact 1 lege cel bevat.
# De som van alle mogelijke getallen in een rij(totaal) min de som van alle aanwezige 
# getallen in een rij(somX) geeft dan de waarde van de lege cel. 
def necessityOplossingen(array): 
    # We berekenen de som van alle mogelijkheden afhankelijk van de grote van 
    # de sudoku.
    totaal = 0
    for i in range(1, aantalVakjes + 1):
        totaal += i

    # Als we een lege cel ingevuld hebben tellen we 1 op bij succes, zodat we 
    # de necessityOplossingen functie opnieuw aan kunnen roepen als succes groter
    # is dan 0. Dit doen we omdat we voor lege cellen die we eerst niet in konden 
    # vullen, nu wel de juiste waarde kunnen vinden.
    succes = 0     
    for rij in range(0, len(array)):
        somRij = 0
        legeCellenRij = []
        for kolom in range(0, len(array)):
            somRij += array[rij][kolom]
            if array[rij][kolom] == 0:
                legeCellenRij.append((rij, kolom))
        if len(legeCellenRij) == 1:
            ontbrekendeGetalRij = 0
            ontbrekendeGetalRij = totaal - somRij
            array[legeCellenRij[0]] = ontbrekendeGetalRij
            succes += 1

    #Lege cellen in een kolom
    for kolom in range(0, len(array)):
        somKolom = 0
        legeCellenKolom = []
        for rij in range(0, len(array)):
            somKolom += array[rij][kolom]
            if array[rij][kolom] == 0:
                legeCellenKolom.append((rij, kolom))
        if len(legeCellenKolom) == 1:
            ontbrekendeGetalKolom = 0;
            ontbrekendeGetalKolom = totaal - somKolom
            array[legeCellenKolom[0]] = ontbrekendeGetalKolom 
            succes += 1

    #Lege cellen in vakjes
    linksi = 0
    rechtsi = vakjesGrote
    for kolom in range(0, vakjesGrote):
        links = 0
        rechts = vakjesGrote
        for rij in range(0, vakjesGrote):
            legeCellenVakje = []
            somVakje = 0
            for i in range(linksi, rechtsi):
                for j in range(links, rechts):
                    somVakje += array[i][j]
                    if array[i][j] == 0:
                        legeCellenVakje.append((i, j)) 
            if len(legeCellenVakje) == 1:
                ontbrekendeGetalVakje = 0
                ontbrekendeGetalVakje = totaal - somVakje
                array[legeCellenVakje[0]] = ontbrekendeGetalVakje 
                succes += 1  
            links = rechts
            rechts = rechts + vakjesGrote
        linksi = rechtsi
        rechtsi = rechtsi + vakjesGrote  
    return succes

# Hier wordt gekeken of er exact 2 lege cellen in een rij zijn. Door alle
# aanwezige getallen in een rij van de mogelijke getallen in een rij(opties) af
# te trekken, weten we de 2 getallen die nog in deze rij moeten komen.
# Door ze in te vullen en vervolgens te checken kunnen we de juiste plaats bepalen.
# We doen dit enkel voor een rij omdat dit afdoende is.    
def optiesOplossingen(array):   

    # Hier wordt indien er 2 lege cellen in een rij zijn de oplossingen berekend.
    for rij in range(0, len(array)):
        legeCellenRij = []
        # We maken een lijst van de mogelijke opties aan afhankelijk van de grote 
        # van de sudoku.
        opties = []
        for i in range(1, aantalVakjes + 1):
            opties.append(i)
        for kolom in range(0, len(array)):
            if array[rij][kolom] == 0:
                legeCellenRij.append((rij, kolom))
            elif array[rij][kolom] != 0:
                opties.remove(array[rij][kolom])
        if len(legeCellenRij) == 2:
            kopieSudoku = copy.copy(array)
            for i in range(0, len(legeCellenRij)):
                kopieSudoku[legeCellenRij[i]] = opties[i]
            if checkSudokuWaardes(kopieSudoku) == True:
                array = kopieSudoku
            else:
                for i in range(0, len(legeCellenRij)):
                    kopieSudoku[legeCellenRij[i]] = opties[(i - 1) * -1]
                    array = kopieSudoku
    return array    

# Hier worden de waardes voor de laatste lege cellen in de sudoku berekend.
# Daarvoor wordt dmv de product methode alle mogelijke combinaties berekend.      
def genereerPermutaties(array):

    # Om de mogelijke combinaties te vinden, berekenen we hier eerst het aantal lege
    # cellen en localiseren die.   
    legeCellen = []
    for i in range(0, len(sudokuArray)):
        for j in range(0, len(sudokuArray)):
            if sudokuArray[i][j] == 0:
                legeCellen.append((i, j)) 

    mogelijkheden = list(product(range(1, aantalVakjes + 1), herhaling=len(legeCellen)))

    # Hier wordt een kopie van de sudoku gemaakt, omdat we anders de orginele 
    # sudoku ook aan gaan passen met waarde waarvan we eerst nog moeten kijken
    # of ze correct zijn op de aangewezen plek.
    kopieSudoku = copy.copy(sudokuArray) 

    # Hier wordt gekeken of een getal N op een plek X in de sudoku correct is. 
    # Als dat niet zo is verwijderen we alle mogelijkheden uit de mogelijkheden
    # lijst waarvan waarde N op plek X staat.
    for j in range(0, len(mogelijkheden)):
        for i in range(0, len(legeCellen)):
            if j < len(mogelijkheden):
                kopieSudoku = copy.copy(sudokuArray)
                kopieSudoku[legeCellen[i]] = mogelijkheden[j][i]
                testWaarde = mogelijkheden[j][i]
                if checkSudokuWaardes(kopieSudoku) == False:
                    k = 0
                    while k < len(mogelijkheden):
                        if mogelijkheden[k][i] == testWaarde:
                            mogelijkheden.remove(mogelijkheden[k])  
                            kopieSudoku = copy.copy(sudokuArray)               
                        else:
                            k = k + 1

    # Hier gaan we voor de overgebleven mogelijkheden kijken of ze gezamelijk
    # in de sudoku passen. Zo ja dan hebben we de waarde gevonden en krijgt
    # de orginele sudoku deze waardes toegekend.
    for j in range(0, len(mogelijkheden)):
        for i in range(0, len(legeCellen)):  
            kopieSudoku[legeCellen[i]] = mogelijkheden[j][i]        

        if checkSudokuWaardes(kopieSudoku) == True:
            array = kopieSudoku

    return array

# Hier wordt een tekstbestand dat de gewenste sudoku bevat ingelezen.
# Vervolgens kennen we dat tekstbestand toe aan de sudokuArray.
tekstBestand =  numpy.loadtxt("21_open_spots_9_grid.txt")
sudokuArray = numpy.array(tekstBestand)
# Hier berekenen we het aantal ingenestelde vakjes en de grote ervan.
aantalVakjes = len(sudokuArray)
vakjesGrote = int(sqrt(aantalVakjes))

if checkLegeCellen(sudokuArray) == False or checkSudokuWaardes(sudokuArray) == False:
    while necessityOplossingen(sudokuArray) > 0:
        necessityOplossingen(sudokuArray)

if checkLegeCellen(sudokuArray) == False or checkSudokuWaardes(sudokuArray) == False:
    sudokuArray = optiesOplossingen(sudokuArray)

if checkLegeCellen(sudokuArray) == False or checkSudokuWaardes(sudokuArray) == False:
    sudokuArray = genereerPermutaties(sudokuArray)   

if checkLegeCellen(sudokuArray) == True and checkSudokuWaardes(sudokuArray) == True:
    print "de juiste oplossing is: "
    print sudokuArray
