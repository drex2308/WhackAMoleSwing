# Whack-a-Mole Game

## Overview

This repository contains my implementation of a Whack-a-Mole game using Swing. The game allows a player to play for 20 seconds, scoring points by clicking on moles that pop up. The game demonstrates the use of Swing for GUI development and threading for managing game timers and mole appearances.

## Description

The Whack-a-Mole game consists of a GUI with a "Start" button, a timer, a score display, and multiple buttons representing mole holes. The objective is to click on the moles when they pop up to score points. The game ends after 20 seconds, and the start button is re-enabled after a brief delay.

## Features

### GUI Components
- **Start Button**: Initiates the game and starts the timer and mole threads.
- **Timer Display**: Shows the remaining time in seconds.
- **Score Display**: Shows the player's current score.
- **Mole Buttons**: Represent mole holes where moles pop up and can be clicked to score points.

### Game Mechanics
- **Mole States**: Each mole button has three states:
  - **Down**: The mole is not visible.
  - **Up**: The mole is visible and can be clicked to score points.
  - **Hit**: The mole has been clicked and is in a hit state.
- **Start Game**: Disables the start button, starts the timer, and initializes mole threads.
- **Timer Thread**: Counts down from 20 to 0 seconds and updates the timer display. Re-enables the start button after 5 seconds.
- **Mole Threads**: Each mole has its own thread that manages its up and down states. Moles pop up for a random duration between 1 and 4 seconds and stay down for at least 2 seconds.

### Game Logic
- **Score Calculation**: Clicking an "up" mole increments the score and changes the mole to a "hit" state. Clicking a "down" or "hit" mole has no effect.
- **End of Game**: When the timer reaches zero, moles stop popping up, and the game stops accepting clicks for scoring.

## Implementation Details

To deepen my understanding of GUI development and threading, I implemented this project with specific constraints and rules:

- **Custom Timer**: Implemented a custom timer thread without using `java.util.Timer`.
- **Thread Management**: Each mole is managed by its own thread to handle its pop-up logic independently.
- **Swing GUI**: Developed the GUI using Swing components without using any GUI builder tools or plugins.

## Usage

To use the Whack-a-Mole game, compile and run the `Game.java` file. The game interface will appear, allowing you to start the game and whack moles to score points.

## Learnings

- **Swing Development**: I learned to create a responsive and interactive GUI using Swing components.
- **Threading**: I gained experience in managing multiple threads for game mechanics, including a custom timer and mole threads.
- **Event Handling**: I developed skills in handling user interactions and updating the GUI based on game events.
- **Game Logic**: I implemented game logic to manage mole states, score calculation, and game timing.

## Conclusion

This project was a valuable experience in developing a simple game using Swing and threading in Java. The Whack-a-Mole game demonstrates my ability to create interactive applications with responsive GUIs and concurrent processing.
