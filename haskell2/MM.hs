--------------------------------------------------------------------------------
-- Made By:          David Veenstra
-- StudentNumber:    10094423
--
--------------------------------------------------------------------------------

-- renamed to Mastermind, because Main.hs fails to import MM
module MM where

import Data.List
import Control.Monad
import Data.Function(on)
import Data.Char


data Color = Red | Yellow | Blue | Green | Orange | Purple
             deriving (Eq, Show, Bounded, Enum)

newtype Play = Play [Color] deriving (Eq)

type Pattern = [Color]
type Secret  = [Color]
type Feedback = (Int, Int)
type Colors = [Color]

colors :: Colors
colors = [minBound..maxBound]

-- | Store contains all possible codes
store = replicateM 4 colors

-- | This function matches both the colours and the positions.
correctGuess :: Pattern -> Pattern -> Int
correctGuess [] [] = 0
correctGuess (x:xs) (y:ys)
    | (x == y)  = (correctGuess xs ys) + 1
    | otherwise = correctGuess xs ys

-- | This function matches the colours.
correctColor :: Pattern -> Pattern -> Int
correctColor _ [] = 0
correctColor [] _ = 0
correctColor (x:xs) secret
    | (x `elem` secret) = (correctColor xs secret') + 1
    | otherwise         = correctColor xs secret
      where secret' = delete x secret

-- | This function shows the reaction of a mastermind game given two
-- | patterns. The tuple returned tells how many colours match whilst
-- | sharing the same position, and how many colours match whilst not
-- | sharing the same position.
reaction :: Pattern -> Pattern -> Feedback
reaction secret guess = (b, w)
	where b = correctGuess secret guess
	      w = (correctColor secret guess) - b

-- | This function is a simple mastermind strategy.
-- | Given a secret this function returns a list of guesses.
-- | The first guess can be found on the head of the list.
naive_algorithm :: Secret -> [Pattern] -> Pattern -> [Pattern]
naive_algorithm secret options guess
        | reaction secret (head options) == (4,0) = [head options]
        | otherwise = naive_algorithm secret (filterOptions options secret (head options)) guess  ++ [head options]
	      
-- | This function filters the possible codes needed for the knuth
-- | and the naive algorithm.
filterOptions :: [Pattern] -> Pattern -> Pattern -> [Pattern]
filterOptions [] secret guess = []
filterOptions [y] secret guess
    | (reaction y guess == reaction secret guess) = [y]
filterOptions (y:ys) secret guess
    | (reaction y guess == reaction secret guess) = [y] ++ filterOptions ys secret guess
    | otherwise = filterOptions ys secret guess
        
startGuess = [Red,Red,Yellow,Yellow]

-- | This function implements the Minimax algorithm as described by
-- | Knuth and Kooi.
knuth_algorithm :: Secret -> [Pattern] -> Pattern -> [Pattern]
knuth_algorithm secret options guess
        | reaction secret guess == (4,0) = [guess]
        | otherwise = [guess] ++ knuth_algorithm secret (filterOptions options secret guess) (nextGuess (filterOptions options secret guess)) 

allReactions = [(0,0), (0,1), (0,2), (0,3), (0,4), (1,0), (1,1), (1,2), (1,3), (2,0), (2,1), (2,2), (3,0), (4,0)]

scoreAll react guessX options = foldr (\x y -> x + y) 0 [if reaction guessX (options !! k) == react then 1 else 0 |k <- [0 .. (length options)-1]]
scoresAll guessX options = [scoreAll react guessX options | react <- allReactions]
maxScoreAll options = [(maximum (scoresAll guessX options), guessX) | guessX <- options]
minMax options = minimumBy (compare `on` fst) (maxScoreAll options)
nextGuess options = snd (minMax options)

-- | This function takes a master mind strategy, and returns the average
-- | turns taken before the strategy finds the solution.
average :: (Secret -> [Pattern] -> Pattern -> [Pattern]) -> Double
average f = (foldr (\x y -> x + y) 0 [fromIntegral(length(f secret store startGuess))| secret <- store])/1296

test = map (uncurry . flip $ reaction) a ++ map (uncurry reaction) a
    where a = [([Green, Blue, Red, Yellow], [Green, Green, Green, Green]),
               ([Blue, Blue, Blue, Blue], [Blue, Blue, Blue, Red]),
               ([Green, Purple, Green, Green], [Green, Purple, Blue, Yellow]),
               ([Green, Purple, Green, Green], [Purple, Green, Blue, Yellow]),
               ([Green, Purple, Green, Green], [Green, Blue, Yellow, Purple])]
