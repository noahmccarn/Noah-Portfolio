# Noah McCarn
# Evil Hangman Game

import string


# This method reads in the file of words and puts them into a list.It also returns that list

def createList(filename, wordLength):
    inputFile = open(filename, 'r')
    words = []
    for line in inputFile:
        cleanLine = string.strip(line)
        if len(string.strip(line)) == wordLength:
            words.append(cleanLine)
    return words

# This method prompts the user for the length of word puzzle that they would like.
# It will reprompt if the user inputs something that is not a number, if the number
# is equal to or less than 0. It will also reprompt if there is not a word of the
# specified length

def promptLengthOfWord():
    x = 0
    y = 0
    user = 0
    while x == 0:
        try:
            user = int(raw_input("Welcome to Hangman! Please enter the length of the word: "))
            x = 1
        except ValueError:
            print "That was not a valid number please try again"
            x = 0
        if user <= 0:
            x = 0
    return user         

# This method prompts the user for the number of guesses that the
# user wishes to have. Will reask if the input is not a number or if the number
# is less than or equal to zero.

def promptNumberOfGuesses():
    x = 0
    user = 0
    while x == 0:
        try:
            user = int(raw_input("Please Enter the number of Guesses you would like: "))
            if user > 0:
                x = 1
        except ValueError:
            print "That was not a valid number please try again"
            x = 0
    return user

# Tells the user how many guesses the user has left, what letters
# the user has guessed and asks the user for a letter guess
# guessNum is the number of guesses the user has left
# pastLetters is the is the letters that the user has guessed previously
# puzzle is the current state of the puzzle

def promptLetterGuess(guessNum, pastLetters, puzzle, isInPuzzle, isInitial):
    letters = ""
    user = ""
    for letter in pastLetters:
        letters = letters + letter + " "
    x = 0
    while x == 0:
        if isInitial == True:
            print "You have ", guessNum, " guesses left \n\
You have guessed:", letters, "\n\
The current state of the puzzle is", puzzle
            user = raw_input("Please enter a letter to guess: ")
            x = 1
        if isInitial == False and isInPuzzle == False:
            print "The letter ", letter," was not in the puzzle. \n\
You have", guessNum," guesses left \n\
You have guessed:", letters, "\n\
The current state of the puzzle is", puzzle,"\n"
            user = raw_input("Please enter a letter to guess: ")
            x = 1
        if isInitial == False and isInPuzzle == True:
            print "The letter ", letter," was in the puzzle. \n\
You have", guessNum," guesses left \n\
You have guessed:", letters, "\n\
The current state of the puzzle is", puzzle,"\n"
            user = raw_input("Please enter a letter to guess: ")
            x = 1
        if user.isalpha() == False or len(user) != 1:
            x = 0
        for y in pastLetters:
            if user == y:
                x = 0
    return user

# Sorts the current word list for possible next lists and determines which
# list should be used next.
# letter is the letter that the user guessed
# wordlist is the current wordlist that is being used and contains the words
#           that still can be used
# puzzle is the current state of the puzzle

def puzzle(letter, wordList, puzzle):
    dict = {}
    keyList = []
    for word in wordList:
        stringKey = keyMaker(letter, word, puzzle)
        if dict.has_key(stringKey) == False:
            newList = []
            newList.append(word)
            keyList.append(stringKey)
            dict[stringKey] = newList
            
        else:
            currentList = dict.get(stringKey)
            currentList.append(word)
            newList = currentList
            dict[stringKey] = newList
    
    longestLength = []             
    useKey = ""
    for key in dict.keys():
        if listLength(longestLength) <= listLength(dict.get(key)):
            longestLength = dict.get(key)
            useKey = key
        
    return longestLength, useKey

# Makes key for dictionary

def keyMaker(letter, word, puzzle):
    key = ""
    i = 0   
    while i < len(word):
        if word[i] == letter:
            key = key + letter + " "
        if word[i] != letter:
            if puzzle[2*i] == "_":
                key = key + "_ "
            if puzzle[2*i] != "_":
                key = key + puzzle[2*i] + " "
        i = i + 1
    return key

# This method computes the length of a List
# returns the length

def listLength(aList):
    length = 0
    for i in aList:
        length = length + 1
    return length

# Keeps up with the old letters that the user has guessed
# returns the new list of letters

def pastLetters(oldLetters, letter):
    oldLetters.append(letter)
    return oldLetters
    
# creates the initial puzzle to be updated later
# The puzzle will look like underscores with spaces between them
# such as "_ _ _ _ _"
# lengthOfWord is the length of the word puzzle

def initializePuzzle(lengthOfWord):
    puzzle = ""
    for x in range(lengthOfWord):
        base = "_ "
        puzzle = puzzle + base
    return puzzle

# Updates the puzzle for the user. Returns the update puzzle:
# If word is 4 letters long, an example is "_ e _ _"
# isInPuzzle is a boolean that tells if the letter is in the puzzle (True if in puzzle)
# letter is the letter that the user guessed
# location is the location of the letter in relation to the puzzle. (Location of first
#           letter is 0, location of second letter is 1, and so on)
# puzzle is the old puzzle that needs to be updated

def updatePuzzle(isInPuzzle, letter, location, puzzle):
    if isInPuzzle == False:
        return puzzle
    else:
        index = location * 2
        return puzzle[0:index] + letter + puzzle[index + 1:]

def gameOver(guesses, puzzle):
    status = False
    aPuzzle = ""
    for x in range(len(puzzle)):
        if x % 2 == 0:
            aPuzzle = aPuzzle + puzzle[x]
    
    if guesses == 0:
        status = True
    if aPuzzle.isalpha() == True:
        status = True
    return status

# This method tells whether or not the letter is the puzzle
# Will return true if the letter is, in fact, in the puzzle

def isInPuzzle(puzzle, letter):
    inPuzzle = True
    index = puzzle.find(letter)
    if index == -1:
        inPuzzle = False
    if index >= 0:
        inPuzzle = True
    return inPuzzle

# Main method controls/calls all the other methods and runs the game

def main():
    lengthOfWord = promptLengthOfWord()
    wordList = createList('dictionary.txt', lengthOfWord)
    while len(wordList) == 0:
        print "There was not a word of that length. Please enter another number: "
        lengthOfWord = promptLengthOfWord()
    guesses = promptNumberOfGuesses()
    aPuzzle = initializePuzzle(lengthOfWord)
    aPastLetters = []
    letter = promptLetterGuess(guesses,aPastLetters,aPuzzle, True, True)

    while gameOver(guesses, aPuzzle) == False:      
        pastLetters1 = pastLetters(aPastLetters, letter)
        wordList1, puzzle1 = puzzle(letter, wordList, aPuzzle)
        
        aPuzzle = puzzle1
        wordList = wordList1
        aPastLetters = pastLetters1
        
        isInAPuzzle = isInPuzzle(aPuzzle, letter)

        if isInAPuzzle == False:
            guesses = guesses - 1

            
        if gameOver(guesses, aPuzzle) == False:
            letter = promptLetterGuess(guesses,aPastLetters,aPuzzle,isInAPuzzle, False)
            
        if gameOver(guesses, aPuzzle) == True:
            if guesses == 0:
                print "The word was ", wordList[0]
            if guesses > 0:
                print "Congratulations, you won"
            newGame = raw_input("Would you like to play again (Enter y for yes and n for no): ")
            game = string.lower(newGame)
            if game == 'y':
                main()


main()
