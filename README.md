# TDD Tres en Raya

[![Java CI with Maven](https://github.com/menchaca-andres/TDD-TresEnRaya/actions/workflows/maven.yml/badge.svg)](https://github.com/menchaca-andres/TDD-TresEnRaya/actions/workflows/maven.yml)

Tic-Tac-Toe kata solved with Test-Driven Development (TDD) in Java, Maven and JUnit 5.

## Requirements covered

### 1. Place a piece on a 3x3 board
- Reject an X coordinate outside the board.
- Reject a Y coordinate outside the board.
- Reject a cell that is already occupied.

### 2. Determine whose turn is next
- The first turn always belongs to `X`.
- After `X` plays, the next turn belongs to `+`.
- After `+` plays, the next turn belongs to `X`.

### 3. Detect a winner
- A player wins by completing a horizontal, vertical or diagonal line.
- The game stays in progress while no winning line exists.
- A full board without a winning line is a draw.

## TDD cycle

The project was built in small red-green-refactor steps:

1. Write a failing test for the next requirement.
2. Run the suite and confirm the failure.
3. Write the minimum implementation to make it pass.
4. Refactor while keeping every test green.

## Project structure

```
src/main/java/com/ucb/tdd/GameState.java    Game result states
src/main/java/com/ucb/tdd/TresEnRaya.java   Board and game rules
src/test/java/com/ucb/tdd/TresEnRayaTest.java
```

## API

Coordinates are 1-based: `x` is the column and `y` is the row, both from 1 to 3.

```java
TresEnRaya game = new TresEnRaya();

game.currentPlayer();          // 'X'
game.play(1, 1);               // GameState.IN_PROGRESS
game.currentPlayer();          // '+'
game.pieceAt(1, 1);            // 'X'
game.play(1, 1);               // IllegalArgumentException: cell already occupied
game.play(4, 1);               // IllegalArgumentException: x outside the board
```

## Run the tests

Requirements: JDK 17 (the Maven wrapper downloads Maven automatically).

```bash
./mvnw clean verify
```

On Windows:

```bat
mvnw.cmd clean verify
```

## Continuous integration

Every push or pull request to `main` runs the unit test suite through GitHub Actions
in `.github/workflows/maven.yml`.
