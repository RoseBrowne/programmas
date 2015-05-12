--  Name          : Rose
--  Student ID    : 10492674


--  bron: http://www.love2d.org/
--  The Cell table is used for every individual square on 
--  the gameboard, comparable to how a class/object works in Java.    
--  Within this table, define any global values used in a Cell object.
Cell = {
-- De globale cell heeft de zelfde eigenschappen als de cellen die worden aangemaakt.
    x;
    y;
    index;
    mijn;
    klik;
    omliggendeMijnen;
}



--  The function new belongs to Cell and spawns a new object (a table)
--  with the same attributes as Cell. It requires an index on the
--  board, and returns an object which is a Cell-object.
function Cell:new(i, j)
    -- Hier wordt elke keer als deze functie wordt aangeroepen een nieuwe cel 
    -- met onderstaande eigenschappen aangemaakt.

    local vakje = {}     
    vakje.x = (j - 1) * CELL_SIZE
    vakje.y = STATS_HEIGHT + (i - 1) * CELL_SIZE
    vakje.index = (i - 1) * 10 + j
    vakje.mijn = false
    vakje.klik = false
    vakje.omliggendeMijnen = 10;
    -- 'self' verwijst hier naar de globale Cell {}
    setmetatable(vakje, self) 
    return vakje
end

--  The function checkNeighbors belongs to Cell. It sets its own status
--  to 'checked' and checks how many neighbors are mines. If none are
--  mines, it will cascade.
function Cell:checkNeighbors(i, j)
    -- Hier krijgt elke aangeklikte cell mee dat ie is aangeklikt
    vakjes[i][j].klik = true
    omringendeMijnen = 0;
    bovenI = i - 1
    middenI = i
    onderI = i + 1
    middenJ = j
    linksJ = j - 1
    rechtsJ = j + 1
    
    -- Hier wordt gekeken of de coordinaten van een omliggend vakje niet
    -- buiten het bord liggen en zo nee dan wordt er gekeken of het een mijn is.
    -- Als het een mijn is dan wordt het aantal omringende mijnen van het 
    -- aangeklikte vakjes opgehoogd met 1.
    
    if (bovenI > 0 and bovenI < 15 and linksJ > 0 and linksJ < 10) then
        if vakjes[bovenI][linksJ].mijn == true then
            omringendeMijnen = omringendeMijnen + 1 
        end
    end
    
    if (bovenI > 0 and bovenI < 15 and middenJ > 0 and middenJ < 10) then
        if vakjes[bovenI][middenJ].mijn == true then
                omringendeMijnen = omringendeMijnen + 1 
        end
    end
    
    if (bovenI > 0 and bovenI < 15 and rechtsJ > 0 and rechtsJ < 10) then
        if vakjes[bovenI][rechtsJ].mijn == true then
                omringendeMijnen = omringendeMijnen + 1 
        end
    end
    
    if (middenI > 0 and middenI < 15 and linksJ > 0 and linksJ < 10) then
        if vakjes[middenI][linksJ].mijn == true then
                omringendeMijnen = omringendeMijnen + 1 
        end
    end
    
    if (middenI > 0 and middenI < 15 and rechtsJ > 0 and rechtsJ < 10) then
        if vakjes[middenI][rechtsJ].mijn == true then
                omringendeMijnen = omringendeMijnen + 1 
        end
    end
    
    if (onderI > 0 and onderI < 15 and linksJ > 0 and linksJ < 10) then
        if vakjes[onderI][linksJ].mijn == true then
                omringendeMijnen = omringendeMijnen + 1 
        end
    end
    
    if (onderI > 0 and onderI < 15 and middenJ > 0 and middenJ < 10) then
        if vakjes[onderI][middenJ].mijn == true then
                omringendeMijnen = omringendeMijnen + 1 
        end
    end
    
    if (onderI > 0 and onderI < 15 and rechtsJ > 0 and rechtsJ < 10) then
        if vakjes[onderI][rechtsJ].mijn == true then
                omringendeMijnen = omringendeMijnen + 1 
        end
    end
    
    -- Hier wordt indien het aantal omliggende mijnen 0 is, die omliggende cellen
    -- ook naar checkNeighbors gestuurd en ontruimd.    
    vakjes[i][j].omliggendeMijnen = omringendeMijnen
    if vakjes[i][j].omliggendeMijnen == 0 then
        if (bovenI > 0 and bovenI < 15 and linksJ > 0 and linksJ < 10 and vakjes[bovenI][linksJ].klik == false) then
            Cell:checkNeighbors(bovenI, linksJ)
        end
        
        if (bovenI > 0 and bovenI < 15 and middenJ > 0 and middenJ < 10 and vakjes[bovenI][middenJ].klik == false) then
            Cell:checkNeighbors(bovenI, middenJ)
        end
        
        if (bovenI > 0 and bovenI < 15 and rechtsJ > 0 and rechtsJ < 10 and vakjes[bovenI][rechtsJ].klik == false) then
            Cell:checkNeighbors(bovenI, rechtsJ)
        end
        
        if (middenI > 0 and middenI < 15 and linksJ > 0 and linksJ < 10 and vakjes[middenI][linksJ].klik == false) then
            Cell:checkNeighbors(middenI, linksJ)
        end
        
        if (middenI > 0 and middenI < 15 and rechtsJ > 0 and rechtsJ < 10 and vakjes[middenI][rechtsJ].klik == false) then
            Cell:checkNeighbors(middenI, rechtsJ)
        end
        
        if (onderI > 0 and onderI < 15 and linksJ > 0 and linksJ < 10  and linksJ < 10 and vakjes[onderI][linksJ].klik == false) then
            Cell:checkNeighbors(onderI, linksJ)
        end
        
        if (onderI > 0 and onderI < 15 and middenJ > 0 and middenJ < 10 and vakjes[onderI][middenJ].klik == false) then
            Cell:checkNeighbors(onderI, middenJ)
        end
        
        if (onderI > 0 and onderI < 15 and rechtsJ > 0 and rechtsJ < 10 and vakjes[onderI][rechtsJ].klik == false) then
            Cell:checkNeighbors(onderI, rechtsJ)
        end
        
    end
    
end

--  love.load is a love-function that is called once when the game
--  starts. It can be used to initialise variables.
function love.load() 
    verloren = false;
    normalfont = love.graphics.newFont(12)
    bigfont = love.graphics.newFont(30) 
    hugefont = love.graphics.newFont(50)
    
    -- Hier wordt een tabel met daarin alle cellen(150) aangemaakt. 
    vakjes = {}
    for i = 1, 15 do  
    vakjes[i] = {}      
        for j = 1, 10 do
            vakjes[i][j] = Cell:new(i, j)
        end
    end  

    -- Hier worden 22 random vakjes aangewijzen die mijnen zijn.
    aantMijnen = 0
    while aantMijnen < 22 do
        nummer = math.random(150)
        for i = 1, 15 do
            for j = 1, 10 do
                if(vakjes[i][j].index == nummer and vakjes[i][j].mijn == false) then
                    vakjes[i][j].mijn = true
                    aantMijnen = aantMijnen + 1
                end
            end
        end
    end    
   
    printx = 0
    printy = 0   
    return vakjes; 
end


--  love.draw is a love-function that is called continuously during the
--  game. Each time it is called the screen is refreshed, meaning the
--  screen will be cleared of everything on it but the background
--  color. This means that, for something to appear and stay on the
--  screen, it will have to be drawn each time this function is called.
function love.draw()
    love.graphics.setFont(normalfont)  
    -- Hier wordt de grid getekend.
    for verticaal = 100, 660, 40 do
        for horizontaal = 0, 360, 40 do
            love.graphics.setColor(255,255,255)
            love.graphics.rectangle("line", horizontaal, verticaal, 40, 40)
        end
    end   
    love.graphics.print(" ", printx, printy) 
    openVakjes = 0;
    mijnVakjes = 0;
    -- Hier krijgen de aangeklikte vakjes een kleur bepaald door of het wel of geen mijnen zijn.
    for i = 1, 15 do
        for j = 1, 10 do
        love.graphics.setFont(normalfont)  
        if vakjes[i][j].klik == true then
            if vakjes[i][j].mijn == true then
                love.graphics.setColor(255,0,0)
            else
                love.graphics.setColor(255,255,255)
            end
            love.graphics.rectangle("fill", vakjes[i][j].x, vakjes[i][j].y, 40, 40)
            love.graphics.setColor(0,0,0)
            love.graphics.rectangle("line", vakjes[i][j].x, vakjes[i][j].y, 40, 40)
        end
            
            -- Hier wordt het aantal omliggende mijnen van een aangeklikt vakje geprint.
            if (vakjes[i][j].klik == true and vakjes[i][j].omliggendeMijnen > 0 and vakjes[i][j].mijn == false) then
                love.graphics.setColor(0,0,0)
                love.graphics.print(vakjes[i][j].omliggendeMijnen, (vakjes[i][j].x + 20), (vakjes[i][j].y + 15), 0, 1, 1, 0, 0, 0, 0)
            end
            
            -- Hier wordt het aantal te openen vakjes bepaald.
            if vakjes[i][j].klik == true then
                openVakjes = openVakjes + 1
            end
            if vakjes[i][j].mijn == true then
                mijnVakjes = mijnVakjes + 1
            end
            -- Hier wordt er indien het aangeklikte vakje een mijn is VERLOREN geprint.
            if (vakjes[i][j].mijn == true and printx >= vakjes[i][j].x and printx < (vakjes[i][j].x + 40) 
            and printy >= vakjes[i][j].y and printy < (vakjes[i][j].y + 40)) then
                love.graphics.setColor(255,0,0)
                love.graphics.setFont(hugefont)
                love.graphics.print("VERLOREN", 50, 350, 0, 1, 1, 0, 0, 1, 0)
            end
           
        end
    end
    geslotenVakjes = 150 - openVakjes - mijnVakjes
    
    -- Hier wordt het aantal nog te openen vakjes en de verstreken tijd geprint.
    t = love.timer.getMicroTime()
    local starttijd = math.floor(love.timer.getTime())
    
    love.graphics.setFont(bigfont)  
    love.graphics.setColor(255,0,0)
    love.graphics.print(geslotenVakjes, 10, 50, 0, 1, 1, 0, 0, 0, 0) 
    if eindtijd ~= nil then
        love.graphics.print(eindtijd, 350, 50, 0, 1, 1, 0, 0, 0, 0) 
    else
        love.graphics.print(starttijd, 350, 50, 0, 1, 1, 0, 0, 0, 0) 
    end
    
    -- Hier wordt GEWONNEN geprint als het nog te openen vakjes 0 is.
    if geslotenVakjes == 0 then
        love.graphics.setColor(255,0,0)
        love.graphics.setFont(hugefont)
        love.graphics.print("GEWONNEN", 50, 350, 0, 1, 1, 0, 0, 0, 0) 
    end       
end


--  love.mousepresed is a love-function which is called every time a
--  mousebutton is pressed. Its parameters include the location of the
--  cursor and which mouse button was pressed.
function love.mousepressed(x, y, button)
    -- Hier worden de coordinaten van een muisklik geregistreerd.
    if button == "l" then
        printx = x
        printy = y
    end
    
    -- Hier wordt de bijbehorende cel gezocht en naar checkNeighbors gestuurd.
    if verloren == false then
        for i = 1, 15 do
            for j = 1, 10 do
                if (printx >= vakjes[i][j].x and printx < (vakjes[i][j].x + 40) 
                and printy >= vakjes[i][j].y and printy < (vakjes[i][j].y + 40)) then
                    Cell:checkNeighbors(i, j)
                    -- Hier wordt indien het aangeklikt vakje een mijn is, de muis disabled.
                    -- en verloren wordt true.
                    if vakjes[i][j].mijn == true then
                        love.mouse.setVisible(false)
                        verloren = true
                        eindtijd = math.floor(love.timer.getTime());
                    end
                end
            end
        end 
    end       
    
end



