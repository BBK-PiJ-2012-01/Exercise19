package myExamples;

import BBK.PiJ01.common.BadInput;
import BBK.PiJ01.common.IOGeneric;

/**
 * Created with IntelliJ IDEA.
 * User: eatmuchpie
 * Date: 07/12/2012
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class ResponsiveUI {
    private static String finished_msg = "Finished tasks: ";
    private static boolean some_tasks_completed = false;
    private static boolean finished_user_input = false;

    public static void main(String[] args) {
        int delay;

        for (int i = 1; i <= 10; ++i) {
            System.out.format("Enter the duration (in ms) of task %d: ", i);
            try {
                delay = IOGeneric.getInteger();
            } catch (BadInput badInput) {
                System.out.println("That wasn't an integer!");
                --i;
                continue;
            }
            Thread t = new Thread(new Waiter(i, delay));
            t.start();

            printNewlyFinished();
        }

        markFinishedUserInput();
    }

    public static synchronized void markFinishedUserInput() {
        finished_user_input = true;
        System.out.println("Waiting for remaining tasks to finish...");
    }

    public static synchronized void markFinishedTask(int index) {
        finished_msg +=  index + ", ";
        if (finished_user_input)
            System.out.println(finished_msg);
        else
            some_tasks_completed = true;
    }

    public static synchronized void printNewlyFinished() {
        if (some_tasks_completed) {
            System.out.println(finished_msg);
            some_tasks_completed = false;
        }

    }
}

class Waiter implements Runnable {
    private final int delay;
    private final int index;

    public Waiter(int index, int delay) {
        this.delay = delay;
        this.index = index;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException("I was interrupted...", e);
        }
        ResponsiveUI.markFinishedTask(index);
    }
}