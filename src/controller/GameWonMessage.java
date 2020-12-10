package controller;

/**
 * Sends message from view to model to indicate that the game is over and user won.
 */

public class GameWonMessage extends BaseMessage {


    /**
     * sends message of user winning over to model for it to handle data
     * @param row
     * @param col
     */
    public GameWonMessage(int row, int col) {
        super(row, col);
    }

}
