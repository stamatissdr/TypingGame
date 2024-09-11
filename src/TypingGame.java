package com.example.typinggame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TypingGame extends Application {
    private Label wordLabel;
    private TextField inputField;
    private Label timerLabel;
    private Label overallTimerLabel;
    private Label scoreLabel;
    private Button startButton;
    private Button retryButton;
    private Button exitButton;
    private Timer wordTimer;
    private Timer overallTimer;
    private int wordTimeRemaining;
    private int overallTimeRemaining;
    private List<String> words;
    private int currentWordIndex;
    private int attempts;
    private int correctWords;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Typing Game");

        // Initial layout with start button and instructions
        Label headerLabel = new Label("TYPING GAME");
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label instructionsLabel = new Label("Instructions:\n" +
                "1. Click the Start button to begin the game.\n" +
                "2. A word will appear, type it as fast as you can.\n" +
                "3. You have 10 seconds to type each word.\n" +
                "4. If you type incorrectly 3 times, the word will change.\n" +
                "5. Try to type as many correct words as you can in 90 seconds.");
        instructionsLabel.setTextAlignment(TextAlignment.CENTER);
        instructionsLabel.setStyle("-fx-padding: 10;");

        startButton = new Button("Start");
        startButton.setStyle("-fx-font-size: 16px;");
        startButton.setOnAction(e -> startGame(primaryStage));

        VBox startLayout = new VBox(20);
        startLayout.getChildren().addAll(headerLabel, instructionsLabel, startButton);
        startLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene startScene = new Scene(startLayout, 400, 350);
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private void startGame(Stage primaryStage) {
        wordLabel = new Label("Word");
        wordLabel.setStyle("-fx-font-size: 18px;");

        inputField = new TextField();
        inputField.setStyle("-fx-font-size: 16px;");

        timerLabel = new Label("Word Timer: 10");
        timerLabel.setStyle("-fx-font-size: 14px;");

        overallTimerLabel = new Label("Overall Timer: 90");
        overallTimerLabel.setStyle("-fx-font-size: 14px;");

        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 14px;");

        retryButton = new Button("Play Again");
        retryButton.setStyle("-fx-font-size: 14px;");
        exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 14px;");

        retryButton.setVisible(false);
        exitButton.setVisible(false);

        VBox gameLayout = new VBox(10);
        gameLayout.getChildren().addAll(wordLabel, inputField, timerLabel, overallTimerLabel, scoreLabel, retryButton, exitButton);
        gameLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        retryButton.setOnAction(e -> retryGame(primaryStage));
        exitButton.setOnAction(e -> primaryStage.close());
        inputField.setOnAction(e -> checkInput());

        words = loadWordsFromFile("src/words.txt");
        currentWordIndex = 0;
        attempts = 0;
        correctWords = 0;

        Scene gameScene = new Scene(gameLayout, 400, 350);
        primaryStage.setScene(gameScene);

        Collections.shuffle(words); // Shuffle words
        correctWords = 0;
        currentWordIndex = 0;
        attempts = 0;
        overallTimeRemaining = 90; // 1.5 minutes
        startButton.setVisible(false);
        retryButton.setVisible(false);
        exitButton.setVisible(false);
        inputField.setDisable(false);
        startOverallTimer();
        startNewRound();
    }

    private void retryGame(Stage primaryStage) {
        startGame(primaryStage);
    }

    private void startNewRound() {
        if (currentWordIndex >= words.size()) {
            System.out.println("Congratulations! You have completed the game.");
            stopGame();
            return;
        }

        wordLabel.setText(words.get(currentWordIndex));
        inputField.setText("");
        wordTimeRemaining = 10;
        timerLabel.setText("Word Timer: " + wordTimeRemaining);
        startWordTimer();
    }

    private void startWordTimer() {
        if (wordTimer != null) {
            wordTimer.cancel();
        }
        wordTimer = new Timer();
        wordTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    wordTimeRemaining--;
                    timerLabel.setText("Word Timer: " + wordTimeRemaining);
                    if (wordTimeRemaining <= 0) {
                        wordTimer.cancel();
                        attempts = 0; // Reset attempts when time is up
                        currentWordIndex++; // Move to the next word
                        if (currentWordIndex >= words.size()) {
                            stopGame();
                        } else {
                            startNewRound();
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    private void startOverallTimer() {
        if (overallTimer != null) {
            overallTimer.cancel();
        }
        overallTimer = new Timer();
        overallTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    overallTimeRemaining--;
                    overallTimerLabel.setText("Overall Timer: " + overallTimeRemaining);
                    if (overallTimeRemaining <= 0) {
                        overallTimer.cancel();
                        System.out.println("Time's up! You correctly typed " + correctWords + " words.");
                        stopGame();
                    }
                });
            }
        }, 1000, 1000);
    }

    private void checkInput() {
        if (inputField.getText().equals(wordLabel.getText())) {
            correctWords++;
            scoreLabel.setText("Score: " + correctWords);
            currentWordIndex++;
            attempts = 0; // Reset attempts on correct input
            if (currentWordIndex >= words.size()) {
                stopGame();
            } else {
                startNewRound();
            }
        } else {
            attempts++;
            if (attempts >= 3) {
                attempts = 0;
                currentWordIndex++; // Move to the next word
                if (currentWordIndex >= words.size()) {
                    stopGame();
                } else {
                    startNewRound();
                }
            }
        }
    }

    private void stopGame() {
        if (wordTimer != null) {
            wordTimer.cancel();
        }
        if (overallTimer != null) {
            overallTimer.cancel();
        }
        inputField.setDisable(true);
        retryButton.setVisible(true);
        exitButton.setVisible(true);
        wordLabel.setText("Game over! Correct words: " + correctWords);
        startButton.setVisible(true);
    }

    private List<String> loadWordsFromFile(String filename) {
        List<String> wordsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordsList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading words from file: " + filename);
        }
        return wordsList;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
