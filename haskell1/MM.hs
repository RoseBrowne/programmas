module MM
where
import Data.List
import Data.Function(on)
import Control.Monad(replicateM)

data Color = Red | Yellow | Blue | Green | Orange | Purple
            deriving ( Eq, Show, Bounded, Enum)
type Pattern = [ Color ]

-- (# Black , # White )
type Feedback = (Int , Int)
reaction :: Pattern -> Pattern -> Feedback
reaction p1 p2 = (black p1 p2, white p1 p2 - black p1 p2)

black :: Pattern -> Pattern -> Int
black p1 p2 = foldr (\x y -> x + y) 0 [if p1 !! x == p2 !! x then 1 else 0 |x <- [0,1,2,3]]

white :: [Color] -> [Color] -> Int
white [x] p2
        | elem x p2 == True = 1
        | otherwise = 0
white (x:xs) p2
        | elem x p2 == True = 1 + white xs (match x p2)
        | otherwise = 0 + white xs p2

match :: Color -> [Color] -> [Color]
match p1 p2 = delete p1 p2

colors :: [ Color ]
colors = [ minBound .. maxBound]
store = replicateM 4 colors

naive_algorithm :: Pattern -> [Pattern] -> Pattern -> [Pattern]
naive_algorithm secret options guess
        | reaction secret (head options) == (4,0) = [head options]
        | otherwise = naive_algorithm secret (filterOptions options secret (head options)) guess  ++ [head options]

startGuess = [Red,Red,Yellow,Yellow]
allReactions = [(0,0), (0,1), (0,2), (0,3), (0,4), (1,0), (1,1), (1,2), (1,3), (2,0), (2,1), (2,2), (3,0), (4,0)]
    
knuth_algorithm :: Pattern -> [Pattern] -> Pattern -> [Pattern]
knuth_algorithm secret options guess
        | reaction secret guess == (4,0) = [guess]
        | otherwise = knuth_algorithm secret (filterOptions options secret guess) (nextGuess (filterOptions options secret guess)) ++ [guess]

filterOptions :: [Pattern] -> Pattern -> Pattern -> [Pattern]
filterOptions [] secret guess = []
filterOptions [y] secret guess
    | (reaction y guess == reaction secret guess) = [y]
filterOptions (y:ys) secret guess
    | (reaction y guess == reaction secret guess) = [y] ++ filterOptions ys secret guess
    | otherwise = filterOptions ys secret guess
    
scoreAll react guessX options = foldr (\x y -> x + y) 0 [if reaction guessX (options !! k) == react then 1 else 0 |k <- [0 .. (length options)-1]]
scoresAll guessX options = [scoreAll react guessX options | react <- allReactions]
maxScoreAll options = [(maximum (scoresAll guessX options), guessX) | guessX <- options]
minMax options = minimumBy (compare `on` fst) (maxScoreAll options)
nextGuess options = snd (minMax options)




        
        
        
