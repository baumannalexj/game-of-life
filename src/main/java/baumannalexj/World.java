package baumannalexj;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class World extends Canvas {

    public final int width;
    public final int height;
    public int[][] grid;
    public int[][] updateGrid;
    private int startPopulation;
    private int generationCounter;

    private Color whatColor;

    private BufferedImage image;

    public World(int width, int height, int startPopulation) {

        super();

        grid = new int[height][width];
        updateGrid = new int[height][width];
        this.width = width;
        this.height = height;

        this.startPopulation = Math.min(startPopulation, width * height);

        JFrame frame = new JFrame("The World");
        //
        frame.setSize(width, height);
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.add(this);
        setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        generationCounter += 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (updateGrid[j][i] > 0) {
                    grid[j][i] += 1; //add to generation
                    whatColor = (grid[j][i] >= 5)
                            ? ((grid[j][i] >= 10) ? Color.MAGENTA : Color.RED)
                            : Color.BLACK;
                    graphics.setColor(whatColor);
                    graphics.drawLine(j, i, j, i);
                } else if (updateGrid[j][i] < 0) {
                    grid[j][i] = 0;
                }
                updateGrid[j][i] = 0;
            }
        }
    }

    public void populate() {
        Random r = new Random();
        while (startPopulation-- > 0) {

            int i = r.nextInt(height);
            int j = r.nextInt(width);
            updateGrid[i][j] = (updateGrid[i][j] <= 0)
                    ? updateGrid[i][j] = 1
                    : 0; // this will flip some it's already set. ok for now

        }

    }


    public void create(int x, int y) {
        updateGrid[y][x] = 1;
    }

    public void kill(int x, int y) {
        updateGrid[y][x] = -1;
    }

    public boolean isPopulated(int x, int y) {

        return grid
                [(y % height + height) % height]
                [(x % width + width) % width]

                > 0;
    }

    //	public void display(){
    //		for(int i=0; i<height; i++){
    //			for(int j=0; j<width; j++){
    //				grid[i][j] += updateGrid[i][j];
    ////				System.out.print(grid[i][j]+ " ");
    ////				xCoord = j;
    ////				yCoord = i;
    ////				if (grid[i][j] ==1){
    ////					whatColor = Color.BLACK;
    ////					//					repaint();
    ////				}else if (grid[i][j] ==0){
    ////					whatColor = Color.WHITE;
    ////					//					repaint();
    ////				}
    //
    //			}
    //
    //
    ////			System.out.println();
    //		}
    //
    //		repaint();
    //
    ////		System.out.println();
    ////		System.out.println();
    //		updateGrid = new int[height][width];
    //	}
}