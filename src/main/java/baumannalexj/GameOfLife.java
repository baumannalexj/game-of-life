package baumannalexj;

import java.util.concurrent.TimeUnit;

/**
 * RubyQuiz 193 Game of Life
 * http://rubyquiz.strd6.com/quizzes/193-game-of-life
 * <p>
 * compile:
 * javac GameOfLife.java
 * javac World.java
 * <p>
 * run:
 * java GameOfLife input1 input2 input3 input4
 * 1 = width
 * 2 = height
 * 3 = fraction populated (.2 == 20%)
 * <p>
 * e.g.: java GameOfLife 10000 10000 .2
 * <p>
 * Click terminal to pause -- Press "Enter" to resume.
 * <p>
 * Future improvements:
 * <p>
 * - improve  draw speed for larger worlds
 * - Create a new thread for each living "thing"
 * - "thing" threads have a run method which constantly check their neighbors and live/die based on rules
 * - allow user to draw initial population
 * - store/write inputs and pixel 'heat map' to local file
 * - color pixels based on their age - gray is new, red is old, black is oldest
 */
public class GameOfLife extends Thread {

    public static final int ITERATION_SLEEP_MILLIS = 100 * 1_000;

    public static void main(String[] args) throws InterruptedException {
        // use args as time to run (iterations)
        System.out.println("Starting Game Of Life");


       final World world = getWorldFromArguments(args);

       world.populate();

        while (true) {

            for (int i = 0; i < world.height; i++) { //y
                for (int j = 0; j < world.width; j++) { //x

                    //look at the 8 neighbor cells around current cell
                    int neighborCount = 0;
                    for (int upDown = -1; upDown < 2; upDown++) {
                        for (int leftRight = -1; leftRight < 2; leftRight++) {
                            if (upDown == 0 && leftRight == 0)
                                continue; //don't count center cell
                            else if (world.isPopulated(j + leftRight, i + upDown))
                                neighborCount++;
                        }
                    }

                    if (world.isPopulated(j, i)) {

                        //rule 1
                        //Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.

                        // rule 2
                        //Any live cell with more than three live neighbours dies, as if by overcrowding.


                        // rule 3
                        //Any live cell with two or three live neighbours lives, unchanged, to the next generation.

                        if (neighborCount < 2 || neighborCount > 3) {
                            world.kill(j, i);
                        }

                    }
                    // rule 4
                    //Any dead cell with exactly three live neighbours becomes a live cell.
                    if (!world.isPopulated(j, i) && neighborCount == 3) {
                        world.create(j, i);
                    }


                }

            }
            world.repaint();
            TimeUnit.MICROSECONDS.sleep(ITERATION_SLEEP_MILLIS);
        }
    }

    private static World getWorldFromArguments(String[] args) {
        World world;
        switch (args.length) {

            case 3:
                final int width = Integer.parseInt(args[0]);
                final int height = Integer.parseInt(args[1]);
                final double startPopulationFraction = Double.parseDouble(args[2]);
                final int startPopulation = (int) Math.floor(width * height * startPopulationFraction);

                world = new World(width, height, startPopulation);

                break;
            default:
                final int defaultWidth = 1_000;
                final int defaultHeight = 1_000;
                final double defaultPopulationFraction = 0.5;

                world = new World(defaultWidth, defaultHeight, (int) Math.floor(defaultHeight * defaultWidth * defaultPopulationFraction));
                break;
        }
        return world;
    }
}