package model;

/**
 * MinesweeperModel Interface that contains the methods needed in order to initialize and work with the data in the model
 * @param <T>
 */

public interface MSModel<T> {
    /**
     * gets number of rows
     * @return HEIGHT
     */
    int getNumRows();

    /**
     * gets number of columns
     * @return WIDTH
     */
    int getNumCols();

    /**
     * gets playing grid
     * @return grid
     */
    T[][] getGrid();

    /**
     * gets the amount of mines that are around a certain tile
     * @param row
     * @param col
     * @return count
     */
    int getNumNeighboringMines(int row, int col);

    /**
     *checks to see if a specific point is inside game board
     * @param row
     * @param col
     * @return
     */
    boolean isInBounds(int row, int col);

    /**
     * initializes gameBoard
     */
    void initializeBoard();

    /**
     * initializes data for game board
     * @param r
     * @param c
     */
    void initializeBoard(int r, int c);

    /**
     * shows board's data and recursively updates based off of user's actions
     * @param row
     * @param col
     */
    void recursiveReveal(int row, int col);

    /**
     * ends game and sets gameOver to true
     */
    void endGame();

    /**
     * reveals all the mines on the game board -- only happens when game is over
     */
    void revealAllMines();
}
