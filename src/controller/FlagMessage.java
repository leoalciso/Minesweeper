package controller;

/**
 * Message sent from view to model for game state flags.
 */

public class FlagMessage extends BaseMessage {

    /**
     * sends message of game state flags to model
     * @param row
     * @param col
     */
    public FlagMessage(int row, int col){
        super(row, col);
    }

}
