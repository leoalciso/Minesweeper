package controller;

/**
 * Sends message from view to model to indicate that the game is over and user lost.
 */

public class GameOverMessage extends BaseMessage {

    /**
     * sends message of game being over to model
     * @param row
     * @param col
     */

    public GameOverMessage(int row, int col) {
        super(row, col);
    }

}
