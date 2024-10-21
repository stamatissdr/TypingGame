
Java Typing Game

 Overview

This project is a **JavaFX-based typing game** where players must type words as fast as possible before the timer runs out. The game challenges players to type as many correct words as they can within a set time limit. Players must also avoid typing errors, and if they make three mistakes on a word, they will move on to the next one.

 Features:
- Timed Gameplay: Players have 10 seconds to type each word.
- Score Tracking: The game tracks the number of correctly typed words.
- Word Randomization: The words are shuffled randomly at the start of each game.
- Retry Option: Players can restart the game after it ends.
- Word List: Words are loaded from an external file (`words.txt`).

 Gameplay Instructions

1. Start the Game: Click the "Start" button to begin the game.
2. Type the Word: Type the word displayed on the screen as fast as possible.
   - You have 10 seconds to type each word.
   - If you type incorrectly three times, the game will automatically move to the next word.
3. Overall Timer: The game has a total time limit of **90 seconds**.
4. End of Game: Once the time runs out, the game ends and shows the number of correct words typed.
5. Retry or Exit: After the game ends, you can either play again or exit the game.

Installation and Setup

Prerequisites

- Java JDK 11+ installed on your machine.
- JavaFX SDK properly set up in your development environment.

Steps to Run the Game

1. Clone the repository:
   bash
   git clone https://github.com/username/TypingGame.git
   
   Replace `username` with your GitHub username.

2. Download JavaFX SDK:
   You can download JavaFX from [here](https://openjfx.io/).

3. Set up JavaFX** in your IDE:
   - Make sure you configure your JavaFX SDK path in your project settings. For example, in **IntelliJ IDEA, go to:
     - `File > Project Structure > Libraries` and add the JavaFX SDK library.

4. Run the Game:
   - Navigate to the `TypingGame` class and run the `main` method to start the game.

Words List
- The words used in the game are stored in the `src/words.txt` file. You can modify this file to add or remove words as you see fit.
  
Future Improvements

- Advanced Scoring: Add more scoring options, such as bonus points for fast typing.
- Different Game Modes: Implement various difficulty levels or timed challenges.
- High Score Tracking: Implement functionality to track high scores across multiple game sessions.

Project Structure


TypingGame/
├── src/
│   ├── com/example/typinggame/
│   │   ├── TypingGame.java         # Main JavaFX Application
│   ├── words.txt                   # List of words for the game
├── README.md                       # Project documentation

---

Αυτό το **README.md** αρχείο θα βοηθήσει άλλους να καταλάβουν το project σου, καθώς και να δουν πώς να το εγκαταστήσουν και να παίξουν το παιχνίδι. Μπορείς να το τροποποιήσεις αν έχεις πρόσθετα χαρακτηριστικά ή προσαρμογές που θες να περιγράψεις.
