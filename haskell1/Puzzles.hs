module Puzzles
where
import Data.List

length' x = foldr (\_ y -> y + 1) 0 x
or' x = foldr(\ x y -> y || x) False x


