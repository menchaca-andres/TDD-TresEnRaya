package com.ucb.tdd;

import java.util.Arrays;

public final class TresEnRaya {

    public static final int BOARD_SIZE = 3;

    private static final char EMPTY = ' ';
    private static final char X = 'X';
    private static final char PLUS = '+';

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

    public GameState state() {
        return state;
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
        if (x < 1 || x > BOARD_SIZE) {
            throw new IllegalArgumentException(
                    "Coordinate x=" + x + " is outside the board");
        }
        if (y < 1 || y > BOARD_SIZE) {
            throw new IllegalArgumentException(
                    "Coordinate y=" + y + " is outside the board");
        }
    }

    private boolean hasWon(char player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        boolean mainDiagonal = board[0][0] == player
                && board[1][1] == player
                && board[2][2] == player;
        boolean antiDiagonal = board[0][2] == player
                && board[1][1] == player
                && board[2][0] == player;

        return mainDiagonal || antiDiagonal;
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
