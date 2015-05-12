-----------------------------------------------------------------------------
-- | Programmeertalen Haskel Week 2
--
-- Student Name   : Rose Browne
-- Student Number : 10492674

-- Compile & Run game: ghci > :l Game > main > doe een gok bijv: RRRR
-----------------------------------------------------------------------------

module Game where

import Data.Char
import Data.List
import Control.Monad
import Control.Monad.IO.Class
import System.Random
import System.Exit

import Gamestate
import GameLib

-- Import your work from last week here
import MM

-- | Returns true if the given string is a valid command
-- At least the following commands must be recognised:
-- help, quit, history
--
-- For example: map isCommand ["help", "quit", "history", "hello"] == [True, True, True, False]
isCommand :: String -> Bool
isCommand string 
            | string == "help" = True
            | string == "quit" = True
            | string == "history" = True
            | otherwise = False

-- | Converts a given command to an event.
-- At least the quit, history and help command must be recognized.
--
-- For example: commandToEvent "quit" == Quit
commandToEvent :: String -> GameEvent
commandToEvent command
            | command == "help" = Help
            | command == "quit" = Quit
            | command == "history" = PrintHistory
            | otherwise = undefined

-- | This function trims any leading or trailing whitespace.
-- For example: trim "  Hello    " == "Hello"
trim :: String -> String
trim x = dropWhile isSpace (dropWhileEnd isSpace x)


isValidSet :: Char -> Bool
isValidSet c
            | c == 'R' = True
            | c == 'G' = True
            | c == 'B' = True
            | c == 'P' = True
            | c == 'Y' = True
            | c == 'O' = True
            | otherwise = False

-- || c == 'G' || c == 'B') &&

-- | This function converts a character to a color.
--
-- For example: map charToColor "RGB" = [Red, Green, Blue]
charToColor :: Char -> Color
charToColor c 
            | c == 'R' = Red
            | c == 'G' = Green
            | c == 'B' = Blue
            | c == 'P' = Purple
            | c == 'Y' = Yellow
            | c == 'O' = Orange
            | otherwise = undefined

-- | This function converts a color to a character.
--
-- For example: map colorToChar [Red, Green, Blue] = "RGB"
colorToChar :: Color -> Char
colorToChar color 
            | color == Red = 'R'
            | color == Green = 'G'
            | color == Blue = 'B'
            | color == Purple = 'P'
            | color == Yellow = 'Y'
            | color == Orange = 'O'

-- | This function prints a string, and then ask the user for input
promptUser :: String -> GameM String
promptUser string = do
  liftIO $ putStrLn string
  liftIO $ getLine

-- | This function creates the initial state for a player played by the computer.
-- The knuth algorithm is used to determine the moves that the computer must make.
-- You may change this to another algorithm.
makeCpu :: Pattern -> PlayerState
makeCpu secret = CPU secret 0 plays
    where plays = knuth_algorithm secret store startGuess

-- | This function creates an initial state for a human player.
makePlayer :: Pattern -> PlayerState
makePlayer secret = Player secret []

-- | Prints a list of strings, that are joined by the given separator.
printStrings :: String -> [String] -> IO ()
printStrings sep strings = putStrLn $ (intercalate sep strings)

-- | This loop is executed by a given player.
-- The player can be either a real player or a computer.
-- This function must return a GameEvent, in a GameM context.
--
-- For example, the event can be: UpdatePlayer newState move.
-- Where the newState is the new state of the player, and move
-- the master mind move the player has made.
--
-- Different clauses are used for different Constructors.
playerLoop :: PlayerState -> GameM GameEvent
playerLoop ps@(Player secret history) = do
    line <- promptUser "get input:"
    if (isCommand line) || (and' (map isValidSet line) && (length (map isValidSet line) == 4))
        then (if isCommand line then return (commandToEvent line) else (if (map charToColor line) == secret then return (GameOver secret ps) else return (UpdatePlayer ps (map charToColor line)))) 
        else playerLoop ps

playerLoop (CPU secret turn plays) = do
    if plays !! turn == secret then return (GameOver secret (CPU secret (turn + 1) plays)) 
        else return (UpdatePlayer (CPU secret (turn + 1) plays)(plays !! turn))

and' x = foldr(\ x y -> y && x) True x

-- | This main loop of the game. This loops does the playerLoop, and
-- processes the generated event.
gameLoop :: GameM ()
gameLoop = do
  let loop = do
        -- retrieve the id, and the state of the current player
        id <- currentPlayer
        player <- getPlayer id

        -- generate a new event
        event <- playerLoop player

        -- process the different kind of events
        case event of
            UpdatePlayer (Player secret history) play -> do
                -- print the move that the current player has made
                liftIO $ putStrLn $ ("jij gokt: " ++ show play)
                -- print the reaction for the guess of the current player
                liftIO $ putStrLn $ ("feedback: " ++ show (reaction play secret))
                -- update the player state of the current player
                setPlayer id (Player secret history)
                -- give the turn to the next player
                nextPlayer
                -- call loop
                loop
            UpdatePlayer (CPU secret count guesses) play -> do
                -- print the move that the current player has made
                liftIO $ putStrLn $ ("de computer gokt: " ++ show play)
                -- print the reaction for the guess of the current player
                liftIO $ putStrLn $ ("feedback: " ++ show (reaction play secret))
                -- update the player state of the current player
                setPlayer id (CPU secret count guesses)
                -- give the turn to the next player
                nextPlayer
                -- call loop
                loop
            Quit -> do
                liftIO $ putStrLn $ "doei"
            Help -> do
                liftIO $ putStrLn helpMenu
                loop
            PrintHistory -> do
                liftIO $ (printStrings (" ") (getHistory player))
                loop
            GameOver secretP (Player secret history) -> do
                liftIO $ putStrLn ("U wint! De geheime code was: " ++ (show secretP))
            GameOver secretC (CPU secret count guesses) -> do
                liftIO $ putStrLn ("de computer wint! De geheime code was: " ++ (show secretC))        

  -- here you can do any extra work, before starting the loop
  liftIO $ putStrLn "Starting game..."
  loop

helpMenu = unlines $[
       "The goal of the game is trying to crack the code.",
       "The code consists of 4 colors and the possible colors are Red, Blue, Orange, Green, Purple and Yellow.",
       "The code may contain 1 or more of the same color.",
       "When you guess a code you will get a response.",
       "The first number is the number of correct colors on the right spot.",
       "The second number is the number of correct colors, i.e. not in the right spot.",
       "The winner is the one that cracks the code first.",
       "To quit enter quit and to see your previous guesses enter history."]

getHistory :: PlayerState -> [String]
getHistory ps@(Player secret history) = [map colorToChar (history !! x)| x <- [0..(length history-1)]]


main = do
  -- Generate a secret for both players
  secrets <- randomPatterns 2

  -- define the players
  let players :: [PlayerState]
      players = zipWith (\f x -> f x) [makePlayer, makeCpu] secrets

  -- run the game
  runGame gameLoop players
