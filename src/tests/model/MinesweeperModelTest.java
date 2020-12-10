package model;

/**
 * JUnit tests for model
 */

import org.junit.Test;

import static org.junit.Assert.*;

public class MinesweeperModelTest {

    MinesweeperModel testModel = new MinesweeperModel(new int[]{10,10,12});
    Tile[][] t = new Tile[10][10];

    /**
     * testing getNumRows method
     */

    @Test
    public void getNumRows() {
        assertEquals(10,testModel.getNumRows());
    }

    /**
     * testing getNumCols method
     */

    @Test
    public void getNumCols() {
        assertEquals(10,testModel.getNumCols());
    }

    /**
     * testing getNumMines method
     */

    @Test
    public void getNumMines(){
        assertEquals(12,testModel.getNumMines());
    }

    /**
     * testing getNumMinesLeft method
     */

    @Test
    public void getNumMinesLeft() {
        assertEquals(12, testModel.getNumMinesLeft());
    }

    /**
     * testing isInBounds method
     */

    @Test
    public void isInBounds() {
        assertTrue(testModel.isInBounds(9, 9));
        assertFalse(testModel.isInBounds(11, 11));
    }

}