package controller;

/**
 * Second design pattern used for message. Represents all the other message classes. Holds the overridden method to account for the message interface that
 * that it implements.
 *
 */

public abstract class BaseMessage implements Message {

    /**
     * row field
     */
    public int row;
    /**
     * column field
     */
    public int col;


    /**
     * BaseMessage constructor
     */
    BaseMessage(){ }

    /**
     * setter for BaseMessage row and column
     * @param row
     * @param col
     */

    BaseMessage(int row, int col){
        this.row = row;
        this.col = col;
    }


    /**
     * getter for Event
     * @return Pair<Integer, Integer>(row, col)
     */
    @Override
    public Pair<Integer, Integer> getEvent() {
        return new Pair<Integer, Integer>(row, col);
    }
}
