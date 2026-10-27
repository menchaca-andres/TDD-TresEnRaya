package com.ucb.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TresEnRayaTest {

    private TresEnRaya game;

    @BeforeEach
    void setUp() {
        game = new TresEnRaya();
    }

    @Test
    void placesAPieceOnAnEmptyCell() {
        game.play(1, 1);

        assertEquals('X', game.pieceAt(1, 1));
    }

    @Test
    void rejectsXCoordinateOutsideTheBoard() {
        assertThrows(IllegalArgumentException.class, () -> game.play(0, 1));
        assertThrows(IllegalArgumentException.class, () -> game.play(4, 1));
    }

    @Test
    void rejectsYCoordinateOutsideTheBoard() {
        assertThrows(IllegalArgumentException.class, () -> game.play(1, 0));
        assertThrows(IllegalArgumentException.class, () -> game.play(1, 4));
    }

    @Test
    void rejectsAnAlreadyOccupiedCell() {
        game.play(1, 1);

        assertThrows(IllegalArgumentException.class, () -> game.play(1, 1));
    }

    @Test
    void firstTurnBelongsToX() {
        assertEquals('X', game.currentPlayer());
    }

    @Test
    void turnAlternatesFromXToPlus() {
        game.play(1, 1);

        assertEquals('+', game.currentPlayer());
    }

    @Test
    void turnAlternatesFromPlusToX() {
        game.play(1, 1);
        game.play(1, 2);

        assertEquals('X', game.currentPlayer());
    }

    @Test
    void keepsTheTurnWhenAMoveIsRejected() {
        game.play(1, 1);

        assertThrows(IllegalArgumentException.class, () -> game.play(1, 1));
        assertEquals('+', game.currentPlayer());
    }

    @Test
    void reportsInProgressWhenThereIsNoWinningLine() {
        assertEquals(GameState.IN_PROGRESS, game.play(1, 1));
    }

    @Test
    void xWinsWithACompleteHorizontalLine() {
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 1);
        game.play(2, 2);

        assertEquals(GameState.X_WINS, game.play(3, 1));
    }

    @Test
    void plusWinsWithACompleteHorizontalLine() {
        game.play(1, 2);
        game.play(1, 1);
        game.play(2, 3);
        game.play(2, 1);
        game.play(3, 2);

        assertEquals(GameState.PLUS_WINS, game.play(3, 1));
    }

    @Test
    void xWinsWithACompleteVerticalLine() {
        game.play(1, 1);
        game.play(2, 1);
        game.play(1, 2);
        game.play(2, 2);

        assertEquals(GameState.X_WINS, game.play(1, 3));
    }

    @Test
    void plusWinsWithACompleteVerticalLine() {
        game.play(1, 1);
        game.play(2, 1);
        game.play(3, 1);
        game.play(2, 2);
        game.play(1, 3);

        assertEquals(GameState.PLUS_WINS, game.play(2, 3));
    }

    @Test
    void xWinsWithTheMainDiagonal() {
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 2);
        game.play(1, 3);

        assertEquals(GameState.X_WINS, game.play(3, 3));
    }

    @Test
    void plusWinsWithTheAntiDiagonal() {
        game.play(1, 1);
        game.play(3, 1);
        game.play(2, 1);
        game.play(2, 2);
        game.play(3, 2);

        assertEquals(GameState.PLUS_WINS, game.play(1, 3));
    }

    @Test
    void reportsDrawWhenTheBoardIsFullWithoutAWinner() {
        game.play(1, 1);
        game.play(1, 2);
        game.play(1, 3);
        game.play(2, 2);
        game.play(2, 1);
        game.play(2, 3);
        game.play(3, 2);
        game.play(3, 1);

        assertEquals(GameState.DRAW, game.play(3, 3));
    }

    @Test
    void rejectsMovesAfterTheGameIsOver() {
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 1);
        game.play(2, 2);
        game.play(3, 1);

        assertThrows(IllegalStateException.class, () -> game.play(3, 3));
    }
}
