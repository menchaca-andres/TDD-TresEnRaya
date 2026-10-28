package com.ucb.tdd;

import java.util.Arrays;

public final class TresEnRaya {

    public static final int BOARD_SIZE = 3;

    private static final char EMPTY = ' ';
    private static final char X = 'X';
    private static final char PLUS = '+';

    private static final int[][] WINNING_LINES = {
            {0, 0, 1, 0, 2, 0},
            {0, 1, 1, 1, 2, 1},
            {0, 2, 1, 2, 2, 2},
            {0, 0, 0, 1, 0, 2},
            {1, 0, 1, 1, 1, 2},
            {2, 0, 2, 1, 2, 2},
            {0, 0, 1, 1, 2, 2},
            {0, 2, 1, 1, 2, 0}
    };

    private final char[][] board;
    private char currentPlayer;
    private GameState state;

    public TresEnRaya() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (char[] row : board) {
            Arrays.fill(row, EMPTY);
        }
        currentPlayer = X;
        state = GameState.IN_PROGRESS;
    }

    public char currentPlayer() {
        return currentPlayer;
    }

    public char pieceAt(int x, int y) {
        validateCoordinates(x, y);
        return board[x - 1][y - 1];
    }

    public GameState play(int x, int y) {
        validateCoordinates(x, y);
        if (state != GameState.IN_PROGRESS) {
            throw new IllegalStateException("The game is already over");
        }
        if (board[x - 1][y - 1] != EMPTY) {
            throw new IllegalArgumentException(
                    "The cell (" + x + ", " + y + ") is already occupied");
        }

        board[x - 1][y - 1] = currentPlayer;
        if (hasWon(currentPlayer)) {
            state = currentPlayer == X ? GameState.X_WINS : GameState.PLUS_WINS;
        } else if (isFull()) {
            state = GameState.DRAW;
        } else {
            currentPlayer = nextPlayer(currentPlayer);
        }

        return state;
    }

    private void validateCoordinates(int x, int y) {
        validateCoordinate("x", x);
        validateCoordinate("y", y);
    }

    private void validateCoordinate(String axis, int value) {
        if (value < 1 || value > BOARD_SIZE) {
            throw new IllegalArgumentException(
                    "Coordinate " + axis + "=" + value + " is outside the board");
        }
    }

    private boolean hasWon(char player) {
        for (int[] line : WINNING_LINES) {
            if (isLine(player, line)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLine(char player, int[] line) {
        return board[line[0]][line[1]] == player
                && board[line[2]][line[3]] == player
                && board[line[4]][line[5]] == player;
    }

    private boolean isFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private char nextPlayer(char player) {
        return player == X ? PLUS : X;
    }
}
