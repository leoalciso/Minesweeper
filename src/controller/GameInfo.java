package controller;

/**
 * Setting flags for different possible states of game. Contains row and column info for different game state possibilities.
 */

public class GameInfo {
    /**
     * game over flag
     */
    public static int GAME_OVER = 0;
    /**
     * game won flag
     */
    public static int GAME_WON = 1;
    /**
     * game still in progress flag
     */
    public static int GAME_IN_PROGRESS = 2;
    /**
     * game state field
     */
    private int state;
    /**
     * row field
     */
    private int r;
    /**
     * column field
     */
    private int c;

    /**
     * setting flags
     */

    /**
     * GameInfo constructor that sets game state, row and column to certain values
     */
    public GameInfo(){
        state = 2;
        r = -1;
        c = -1;
    }

    /**
     * set method for row
     * @param r
     */

    public void setRow(int r){
        this.r = r;
    }

    /**
     * set method for column
     * @param c
     */

    public void setCol(int c){
        this.c = c;
    }

    /**
     * get method for row
     * @return r
     */

    public int getRow(){
        return r;
    }

    /**
     * get method for column
     * @return c
     */

    public int getCol(){
        return c;
    }

    /**
     * set method for game state flag
     * @param state
     */

    public void setState(int state){
        this.state = state;
    }

    /**
     * get method for game's state
     * @return state
     */

    public int getState(){
        return state;
    }
    // the state of the Game/Application
    // information that is needed to repaint the View
}
