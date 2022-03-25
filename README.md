# Whodunit

## Description
The game "whodunit?" is mildly similar to a well-known board game called "Clue". For a 
three-minute introduction to the board game Clue, please see this [video](https://www.youtube.com/watch?v=sg_57S4l5Ng).

## Unit Tests  
1. If a computer player has no cards, then canAnswer should return null.
2. If a computer player has exactly one card from a guess, canAnswer should return that
   card.
3. If a computer player has more than one card from a guess, canAnswer should return
   one of the cards.
4. If a computer player is given all but n cards (for some number n > 2 that you should
   choose) from the set of cards, a call to getGuess should return a guess that does not
   contain any of the cards that the player has been given. That is, an initial guess from a
   computer player must consist of cards it does not have.
5. If a computer player is given all but three cards from the set of cards, a call to
   getGuess should return the correct accusation (not a suggestion).
6. If a computer player is given all but four cards from the set of cards, a call to getGuess
   should not return an accusation. However, if receiveInfo is called with one of the four
   cards, then after that, a second call to getGuess should return the correct accusation.
7. If a human player is given some cards, and then canAnswer is called with a guess that
   includes one (or more) of the cards the player has, the method must return one of
   those cards (that is, the human player cannot give a card that they do not have in their
   hand – this will be achieved through input validation in your implementation).

## There are some Makefile commands below:  
+ Build the JUnit test
```
make
```
+ Go through the test
```
make test
```
+ Build my Main.java
```
make build
```
+ Play this game
```
make run
```
+ Remove all that are not source files
```
make clean
```
