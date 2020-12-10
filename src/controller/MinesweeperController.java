package controller;

import model.Tile;
import view.MinesweeperView;
import model.MinesweeperModel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Controller implementing Runnable class that performs actions based off various user inputs/interactions with game.
 */

public class MinesweeperController implements Runnable {
    /**
     * BlockingQueue of type Message
     */
    private BlockingQueue<Message> queue;
    /**
     * used as direct reference to view
     */
    private MinesweeperView view; // Direct reference to view
    /**
     * used as direct reference to model
     */
    private MinesweeperModel model; // Direct reference to model
    /**
     * used as direct reference to game's state
     */
    private GameInfo gameInfo; // Direct reference to the state of the Game/Application
    private List<Valve> valves = new LinkedList<Valve>();

    /**
     * contains setters for view, model, queue, and gameInfo, and adds valve calls to valve linkedlist
     * @param view
     * @param model
     * @param queue
     */
    public MinesweeperController(MinesweeperView view, MinesweeperModel model, BlockingQueue<Message> queue) {
        this.view = view;
        this.model = model;
        this.queue = queue;
        this.gameInfo = new GameInfo();
        this.view.repaintView(model);
        valves.add(new DoNewGameValve());
        valves.add(new DoFlagValve());
        valves.add(new DoQuestionValve());
        valves.add(new DoRevealValve());
        valves.add(new DoNewGameValve());
//        valves.add(new GameOverValve());
//        valves.add(new GameWonValve());
    }


    /**
     * runs the game
     */
    @Override
    public void run() {
        ValveResponse response = ValveResponse.EXECUTED;
        Message message = null;
        while (response != ValveResponse.FINISH) {
            if (gameInfo.getState() != GameInfo.GAME_IN_PROGRESS){
                if ((gameInfo.getState() == GameInfo.GAME_WON)) {
                    view.gameWon();
                } else {
                    model.revealAllMines();
                    view.repaintView(model);
                    view.gameOver();
                }
            }
            try {
                message = queue.take(); // <--- take next message from the queue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Look for a Valve that can process a message
            for (Valve valve : valves) {
                response = valve.execute(message);
                // if successfully processed or game over, leave the loop
                if (response != ValveResponse.MISS) {
                    break;
                }
            }
        }
    }


    //javadocs not required for private fields

    private interface Valve {
        ValveResponse execute(Message message);
    }

//    private class GameOverValve implements Valve {
//        @Override
//        public ValveResponse execute(Message message) {
//            if (message.getClass() != GameOverMessage.class) {
//                return ValveResponse.MISS;
//            }
//            int row = message.getEvent().getSecond().getFirst();
//            int col = message.getEvent().getSecond().getSecond();
//            model.revealAllMines(row, col);
//            view.repaintView(model);
//            view.gameOver();
//            return ValveResponse.EXECUTED;
//        }
//    }

//    private class GameWonValve implements Valve {
//        @Override
//        public ValveResponse execute(Message message) {
//            if (message.getClass() != GameWonMessage.class) {
//                return ValveResponse.MISS;
//            }
//            view.gameWon();
//            return ValveResponse.EXECUTED;
//        }
//    }

    private class DoNewGameValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != NewGameMessage.class) {
                return ValveResponse.MISS;
            }
            queue.clear();
            view.reset();
            gameInfo.setState(GameInfo.GAME_IN_PROGRESS);
            model = new MinesweeperModel(new int[]{model.getGrid()[0].length, model.getGrid().length, model.getNumMines()});
            view.repaintView(model);
            return ValveResponse.EXECUTED;
        }
    }

    private class DoFlagValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != FlagMessage.class) {
                return ValveResponse.MISS;
            }
            int row = message.getEvent().getFirst();
            int col = message.getEvent().getSecond();
            if (model.getGrid()[row][col].getState() != Tile.FLAGGED) {
                model.getGrid()[row][col].setState(Tile.FLAGGED);
                model.numMinesMarked++;
                if (model.getGrid()[row][col].getType() == Tile.MINE){
                    model.numMinesLeft--;
                }
            }
            else {
                model.getGrid()[row][col].setState(Tile.COVERED);
                model.numMinesMarked++;
                if (model.getGrid()[row][col].getType() == Tile.MINE){
                    model.numMinesLeft++;
                }
            }
            if (model.numMinesMarked == model.getNumMines() && model.numMinesLeft == 0)
                gameInfo.setState(GameInfo.GAME_WON);
            view.repaintView(model);
            return ValveResponse.EXECUTED;
        }
    }

    private class DoQuestionValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != QuestionMessage.class) {
                return ValveResponse.MISS;
            }
            int row = message.getEvent().getFirst();
            int col = message.getEvent().getSecond();
            if (model.getGrid()[row][col].getState() != Tile.QUESTION) {
                model.getGrid()[row][col].setState(Tile.QUESTION);
            }
            else {
                model.getGrid()[row][col].setState(Tile.COVERED);
            }
            view.repaintView(model);
            return ValveResponse.EXECUTED;
        }
    }

    private class DoRevealValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != RevealMessage.class) {
                return ValveResponse.MISS;
            }
//            System.out.println("Event code: " + message.getEvent().getFirst() +
//                    " @ Row: " + message.getEvent().getSecond().getFirst() +
//                    " @ Col: " + message.getEvent().getSecond().getSecond());
            int row = message.getEvent().getFirst();
            int col = message.getEvent().getSecond();
            model.recursiveReveal(row, col);
            view.repaintView(model);
            if (model.isGameOver()){
                gameInfo.setState(GameInfo.GAME_OVER);
                gameInfo.setRow(row);
                gameInfo.setCol(col);
            }
            return ValveResponse.EXECUTED;
        }
    }
}

