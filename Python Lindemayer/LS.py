#
#   Student: Rose Browne
#   UVA-ID: 10492674
#   Studie: Informatica
#
#   templateLS.py: This program creates a word based on an axiom and a set of 
#   rules. With this word and the given alphabet the program can draw objects 
#   such as trees.
#
from graphics import *
from math import sin, cos, pi

class LS:
    """The Lindenmayer System"""
    def __init__(self, defstep, defangle):
        """Initialize all LS attributes"""
        self.rules = {}
        self.axiom = ''
        self.nRepetitions = 0
        self.defstep = defstep
        self.defangle = defangle
        self.theword = ''

    def setAxiom(self, ax):
        """Set the axiom"""
        self.axiom = ax     

    def addRule(self, letter, word):
        """Add a rule to the set of rules"""
        self.rules[letter] = word

    def generate(self, n):
        """Generate the word by applying the rules n times. 
           Return this word."""
        self.nRepetitions = n 
        self.theword = self.axiom      
        for j in range(0, n):  
            result = ''
            for i in range(0, len(self.theword)):
                if self.theword[i] in self.rules:
                    result = result + self.rules[self.theword[i]]
                else:
                    result = result + self.theword[i]
            self.theword = result

        return self.theword

    def __repr__(self):
        """Generate a string representing the LS"""
        #
        # for this class just printing the constructor is not
        # enough:
        # the axiom and rules are added later. These have to 
        # be printed as well. You could return as a string the
        # Python code that would reconstruct the LS class
        # instantiation WITH the axiom and the rules.
        #
        # This method is not obligatory for this course but 
        # you are strongly encouraged to give it a try. It 
        # also helps when you need to debug your code.
        #
        LSrepresenting = (str(self.defstep) + ", " + str(self.defangle) + ", " + self.axiom + ", " + str(self.rules))
        return LSrepresenting

class TurtleState:
    """The state of the turtle. This is needed so we can push 
        one object on the stack that contains: the position 
        of the turtle, its orientation, the actual stepsize 
        and the actual width of the lines to be drawn."""
    def __init__(self, pos, step, angle, width):
        self.position = pos
        self.step = step
        self.angle = angle
        self.width = width
        self.state = (pos, step, angle, width)

    def clone(self):
        """return a clone of the state. When pushing something 
           on the stack be aware that it doesn't contain
           references that are also used in other parts of your
           program"""
        self.stateClone = self.state
        return self.stateClone

    def __repr__(self):
        """really easy when debugging"""
        return self.state

class Stack:
    def __init__(self):
        """A new list is created in which the state of the Turtle can be saved"""
        self.stackList = []

    def push(self, item):
        self.stackList.append(item)

    def pop(self):
        """ the item that is popped from the stacklist is returned so the Turtle
        can get it's former state back. """
        item = self.stackList.pop()
        return item

def parseWord(word, startIndex):
    c = word[startIndex]
    """Check if the supplied startIndex is smaller than length of word minus one,
    since that index can never hold a parameter. """
    if startIndex < len(word) - 1:
        if word[startIndex + 1] == '(':
            endPar = word.find(')', startIndex + 1)
            par = ''
            for i in range(startIndex + 2, endPar):
                par = par + word[i] 
        else:
            par = None

        if par == None:
            pastIndex = startIndex + 1
        else:
            pastIndex = endPar + 1;
    else:
        par = None
        pastIndex = startIndex + 1

    parser = c, par, pastIndex
    return parser 

class Turtle:
    def __init__(self, win, defwidth):
        self.window = win
        self.defWidth = defwidth  
        self.theStack = Stack()

    def stepPenUp(self, theParser):
        self.step(False, theParser)

    def stepPenDown(self, theParser):
        self.step(True, theParser)

    def step(self, isPenDown, theParser):
        """action associated with F or f:
        change the state of the turtle and draw
        in case isPenDown==True"""

        if theParser[1] != None:
            self.state.step = theParser[1]

        x1 = self.state.position[0]
        y1 = self.state.position[1]
        x2 = x1 + cos(self.state.angle) * self.state.step
        y2 = y1 + sin(self.state.angle) * self.state.step

        line = Line(Point(x1, y1), Point(x2, y2))
        self.state.position = (x2, y2)

        """The color for a line gets determined here, 
        depending of the width of the line."""
        if self.state.width < 0.7 and self.state.width > 0: #roze
            color = '#FF00B7'
        elif self.state.width < 1.4 and self.state.width > 0.7: #paars
            color = '#AF00FF'
        elif self.state.width < 2.1 and self.state.width > 1.4: #blauw
            color = '#2EA8F9'
        elif self.state.width < 2.8 and self.state.width > 2.1: #groen
            color = '#6EF92E'
        elif self.state.width < 3.5 and self.state.width > 2.8: #geel
            color = '#F9F22E'
        elif self.state.width < 4.2 and self.state.width > 3.5: #oranje
            color = '#ff9900'
        else:
            color = '#F64940' #rood

        if isPenDown == True:     
            line.setFill(color)
            line.setWidth(self.state.width)
            line.draw(self.window)

    def left(self, theParser):
        """action associated with +"""
        if theParser[1] == None:
            self.state.angle = self.state.angle + self.lsys.defangle
        else:
            self.state.angle = self.state.angle + theParser[1]

    def right(self, theParser):
        """action associated with -"""
        if theParser[1] == None:
            self.state.angle = self.state.angle - self.lsys.defangle
        else:
            self.state.angle = self.state.angle - theParser[1]

    def scale(self, scale):
        """action associated with \"(scale) """
        if scale != None:
            self.state.width = self.state.width * float(scale)
            self.state.step = self.state.step * float(scale)

    def push(self):
        """action associated with [""" 
        clone = TurtleState(self.state.position, self.state.step, self.state.angle, self.state.width).clone()
        self.theStack.push(clone)

    def pop(self):
        """action associated with ]"""
        if self.theStack.stackList:
            popped = self.theStack.pop()

        self.state.position = popped[0]
        self.state.step = popped[1]
        self.state.angle = popped[2]
        self.state.width = popped[3]

    def drawLS(self, lsys, n, startx, starty, startangle):
        """Draw the Lindenmayer system (lsys) after n iterations
           startx, starty are the starting position on the window
           startangle is the starting angle.

           This function does the interpretation of the LS string
           (you are given the LS object and the required number 
           of iterations).

           Loop over characters in the generated string using 
           the parseWord function. Every call to parseWord will
           return three values c,par,pastindex.

           Decide what to do for every character c (and use the
           right argument if an argument was given and found in
           the string).

           This is also the function where you need the Stack
           class. When you encounter the '[' character you have 
           to push the state and when you encounter the ']'
           character you have to pop a state from the stack.

           Please be aware not to push a reference to the state 
           on the stack (you will be overwriting it). 
           I advise to have a method clone in the State class 
           that really makes a new state object that you can
           safely push on the stack."""

        """The Word is generated and iterated through. Depending on the characters
        functions are called. """   
        self.lsys = lsys
        self.state = TurtleState((startx, starty), self.lsys.defstep, startangle, self.defWidth)            
        theWord = self.lsys.generate(n)  

        startIndex = 0
        while startIndex < len(theWord):
            theParser = parseWord(theWord, startIndex)
            c = theParser[0]
            par = theParser[1]
            pastIndex = theParser[2]
            if c == 'F':
                self.stepPenDown(theParser)
            elif c == 'f':
                self.stepPenUp(theParser)
            elif c == '+':
                self.left(theParser)
            elif c == '-':
                self.right(theParser)
            elif c == '[':
                self.push()
            elif c == ']':
                self.pop()
            elif c == '"':
                self.scale(par)
            elif c == "'":
                self.scale(par)
            startIndex = pastIndex         

if __name__=='__main__':
    #
    # The following code is what is used to test the default
    # implementation. 
    # Running this code shows two Lindemayer systems in one window
    #
    win = GraphWin('Lindenmayer System', 400, 400)
    win.yUp()

    ls = LS(3,pi/2)
    ls.setAxiom('F-F-F-F')
    ls.addRule('F','F-F+F+FF-F-F+F')

    print ls
    print ls.generate(1)  

    t = Turtle(win, 1)
    t.drawLS(ls, 3, 100, 100, pi/2)

    tree = LS(80,pi/2)
    tree.setAxiom('"(1.5)FFA')
    tree.addRule('A', '"(0.687)[+FA][-FA]')

    t.defWidth = 12
    t.drawLS(tree, 10, 200, 30, pi/2)

    """ This will draw a colored spiral. """
    t.defWidth = 0.2
    spiral = LS(15, pi/2)
    spiral.setAxiom('FG')
    spiral.addRule('G', '"(1.05)+FG')
    t.drawLS(spiral, 80, 200, 200, pi/2)

    win.promptClose(win.getWidth()/2,20)
