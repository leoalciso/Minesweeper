package controller;

/**
 * Represents question message (event) sent from View to Model. Extends BaseMessage.
 */

public class QuestionMessage extends BaseMessage {


    /**
     * QuestionMessage constructor that calls super(row, col)
     * @param row
     * @param col
     */
    public QuestionMessage(int row, int col){
        super(row, col);
    }

}

