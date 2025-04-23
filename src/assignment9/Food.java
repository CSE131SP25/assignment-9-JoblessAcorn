package assignment9;

import java.util.Random;
import edu.princeton.cs.introcs.StdDraw;

public class Food {
    public static final double FOOD_SIZE = 0.02;
    private double x, y;

    public Food() {
        Random rand = new Random();
        x = rand.nextDouble() * (1.0 - 2 * FOOD_SIZE) + FOOD_SIZE;
        y = rand.nextDouble() * (1.0 - 2 * FOOD_SIZE) + FOOD_SIZE;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(x, y, FOOD_SIZE);
    }
}
