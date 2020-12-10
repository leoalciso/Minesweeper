import controller.MinesweeperController;
import controller.Message;
import model.MinesweeperModel;
import view.MinesweeperView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * Class that contains the main method
 */

public class MinesweeperMain {
    private static final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    static int[] beginner = new int[]{9, 9, 10};
    static int[] intermediate = new int[]{16, 16, 40};
    static int[] expert = new int[]{30, 16, 99};

    public static void main(String[] args) {
        //Beginner Mode
        MinesweeperModel model = new MinesweeperModel(expert);
        MinesweeperView view = MinesweeperView.init(queue, new int[]{expert[0], expert[1]});

        //Intermediate Mode
        //MinesweeperModel model = new MinesweeperModel(intermediate);
        //MinesweeperView view = MinesweeperView.init(queue, new int[]{intermediate[0], intermediate[1]});

        //Expert Mode
        //MinesweeperModel model = new MinesweeperModel(expert);
        //MinesweeperView view = MinesweeperView.init(queue, new int[]{expert[0], expert[1]});

        MinesweeperController controller = new MinesweeperController(view, model, queue);
        controller.run();
        new Thread(controller).start();
        view.dispose();
        queue.clear();
    }

}

