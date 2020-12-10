package model;

import java.util.Random;

/**
 * Contains the model data that then interacts with the view and controller
 */

public class MinesweeperModel implements MSModel<Tile>{

    /**
     * game board width
     */
    private final int WIDTH;
    /**
     * game board height
     */
    private final int HEIGHT;
    /**
     * amount of mines in game board
     */
    private final int numMines;

    private Tile[][] grid;
    public int numMinesLeft;
    public int numMinesMarked;
    private boolean gameOver;
    private int numberOfMoves;

    /**
     * constructor that sets game board dimensions values and stores in integer array
     * @param args
     */
    public MinesweeperModel(int[] args) {
        this.WIDTH = args[0];
        this.HEIGHT = args[1];
        this.numMines = args[2];
        numMinesLeft = numMines;
        numMinesMarked = 0;
        gameOver = false;
        numberOfMoves = 0;
        initializeBoard();
    }

    /**
     * initializing game board
     */

    @Override
    public void initializeBoard() {

        grid = new Tile[HEIGHT][WIDTH];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = new Tile(Tile.BLANK, row, col);

            }
        }

        Random random = new Random();
        for (int i = 0; i < numMines; i++) {
            int row = random.nextInt(HEIGHT);
            int col = random.nextInt(WIDTH);
            if (grid[row][col].getType() == Tile.MINE || grid[row][col].getType() == Tile.NUMBER) {
                i--;
            } else {
                grid[row][col] = new Tile(Tile.MINE, row, col);
            }
        }

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (getNumNeighboringMines(row, col) > 0 && grid[row][col].getType() != Tile.MINE) {
                    Tile tile = new Tile(Tile.NUMBER, getNumNeighboringMines(row, col), row, col);
                    grid[row][col] = tile;
                }
            }
        }
    }

    /**
     * initializing the data for the playing board
     * @param r
     * @param c
     */

    @Override
    public void initializeBoard(int r, int c) {

        grid = new Tile[HEIGHT][WIDTH];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = new Tile(Tile.BLANK, row, col);
            }
        }

        Random random = new Random();
        for (int i = 0; i < numMines; i++) {
            int row = random.nextInt(HEIGHT);
            int col = random.nextInt(WIDTH);
            if ((row == r && col == c) || grid[row][col].getType() == Tile.MINE || grid[row][col].getType() == Tile.NUMBER) {
                i--;
            } else {
                grid[row][col] = new Tile(Tile.MINE, row, col);
            }
        }

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (getNumNeighboringMines(row, col) > 0 && grid[row][col].getType() != Tile.MINE) {
                    Tile tile = new Tile(Tile.NUMBER, getNumNeighboringMines(row, col), row, col);
                    grid[row][col] = tile;
                }
            }
        }
    }

    /**
     * shows board's data and recursively updates based off of user's actions
     */
    @Override
    public void recursiveReveal(int row, int col){
        numberOfMoves++;
        Tile currentTile = grid[row][col];
        if (currentTile.getState() != Tile.REVEALED){
            currentTile.setState(Tile.REVEALED);
        }
        if (currentTile.getType() == Tile.MINE){
            if(numberOfMoves == 1){
                initializeBoard(row, col);
                recursiveReveal(row, col);
            } else
                endGame();
        } else if (currentTile.getType() == Tile.BLANK){
            if (isInBounds(row - 1, col) && grid[row - 1][col].getState() != Tile.REVEALED && grid[row - 1][col].getType() != Tile.MINE && grid[row - 1][col].getState() != Tile.FLAGGED && grid[row - 1][col].getState() != Tile.QUESTION) recursiveReveal(row - 1, col);
            if (isInBounds(row - 1, col + 1) && grid[row - 1][col + 1].getState() != Tile.REVEALED && grid[row - 1][col + 1].getType() != Tile.MINE && grid[row - 1][col + 1].getState() != Tile.FLAGGED && grid[row - 1][col + 1].getState() != Tile.QUESTION) recursiveReveal(row - 1, col + 1);
            if (isInBounds(row, col + 1) && grid[row][col + 1].getState() != Tile.REVEALED && grid[row][col + 1].getType() != Tile.MINE && grid[row][col + 1].getState() != Tile.FLAGGED && grid[row][col + 1].getState() != Tile.QUESTION) recursiveReveal(row, col + 1);
            if (isInBounds(row + 1, col + 1) && grid[row + 1][col + 1].getState() != Tile.REVEALED && grid[row + 1][col + 1].getType() != Tile.MINE && grid[row + 1][col + 1].getState() != Tile.FLAGGED && grid[row + 1][col + 1].getState() != Tile.QUESTION) recursiveReveal(row + 1, col + 1);
            if (isInBounds(row + 1, col) && grid[row + 1][col].getState() != Tile.REVEALED && grid[row + 1][col].getType() != Tile.MINE && grid[row + 1][col].getState() != Tile.FLAGGED && grid[row + 1][col].getState() != Tile.QUESTION) recursiveReveal(row + 1, col);
            if (isInBounds(row + 1, col - 1) && grid[row + 1][col - 1].getState() != Tile.REVEALED && grid[row + 1][col - 1].getType() != Tile.MINE && grid[row + 1][col - 1].getState() != Tile.FLAGGED && grid[row + 1][col - 1].getState() != Tile.QUESTION) recursiveReveal(row + 1, col - 1);
            if (isInBounds(row, col - 1) && grid[row][col - 1].getState() != Tile.REVEALED && grid[row][col - 1].getType() != Tile.MINE && grid[row][col - 1].getState() != Tile.FLAGGED && grid[row][col - 1].getState() != Tile.QUESTION) recursiveReveal(row, col - 1);
            if (isInBounds(row - 1, col - 1) && grid[row - 1][col - 1].getState() != Tile.REVEALED && grid[row - 1][col - 1].getType() != Tile.MINE && grid[row - 1][col - 1].getState() != Tile.FLAGGED && grid[row - 1][col - 1].getState() != Tile.QUESTION) recursiveReveal(row - 1, col - 1);
        }
    }

    /**
     * ends game and sets gameOver to true
     */
    @Override
    public void endGame(){
        gameOver = true;
    }

    /**
     * reveals all the mines on the game board -- only happens when game is over
     */

    @Override
    public void revealAllMines() {
        for (Tile[] tiles : grid) {
            for (Tile t : tiles) {
                if (t.getType() == Tile.MINE) {
                    t.setState(Tile.REVEALED);
                }
            }
        }
    }

    /**
     * this method calculates data and counts how many neighboring mines there are
     * @param row
     * @param col
     * @return count
     */

    @Override
    public int getNumNeighboringMines(int row, int col) {
        int count = 0, rowStart = Math.max(row - 1, 0), rowFinish = Math.min(row + 1, grid.length - 1), colStart = Math.max(col - 1, 0), colFinish = Math.min(col + 1, grid[0].length - 1);

        for (int curRow = rowStart; curRow <= rowFinish; curRow++) {
            for (int curCol = colStart; curCol <= colFinish; curCol++) {
                if (grid[curRow][curCol].getType() == Tile.MINE) count++;
            }
        }
        return count;
    }


    /**
     * checks to see if a specific point is inside game board
     * @param row
     * @param col
     * @return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
     */

    @Override
    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    /**
     *getting the number of rows
     * @return HEIGHT
     */

    @Override
    public int getNumRows(){
        return HEIGHT;
    }

    /**
     * getting the number of columns
     * @return WIDTH
     */

    @Override
    public int getNumCols(){ return WIDTH; }

    /**
     * getting entire playing grid
     * @return grid
     */

    @Override
    public Tile[][] getGrid() { return grid; }

    /**
     * getting number of mines on playing board
     * @return numMines
     */

    public int getNumMines() {
        return numMines;
    }

    /**
     * checks to see if the game is over or not
     * @return gameOver
     */

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * getting how many mines are left
     * @return numMinesLeft
     */

    public int getNumMinesLeft() {
        return numMinesLeft;
    }

}