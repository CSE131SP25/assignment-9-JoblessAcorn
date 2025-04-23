package assignment9;

import java.util.LinkedList;
import java.util.Random;

public class Snake {
    private static final double SEGMENT_SIZE = 0.02;
    private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 1.5;
    private LinkedList<BodySegment> segments;
    private double deltaX;
    private double deltaY;
    private boolean exploded = false;

    public Snake() {
        segments = new LinkedList<>();
        segments.add(new BodySegment(0.5, 0.5, SEGMENT_SIZE));
        deltaX = 0;
        deltaY = 0;
    }

    public void changeDirection(int direction) {
        if (direction == 1) { deltaY = MOVEMENT_SIZE; deltaX = 0; }
        else if (direction == 2) { deltaY = -MOVEMENT_SIZE; deltaX = 0; }
        else if (direction == 3) { deltaX = -MOVEMENT_SIZE; deltaY = 0; }
        else if (direction == 4) { deltaX = MOVEMENT_SIZE; deltaY = 0; }
    }

    public void move() {
        if (exploded) return;  // Freeze if exploded

        double newX = segments.getFirst().getX() + deltaX;
        double newY = segments.getFirst().getY() + deltaY;
        BodySegment newHead = new BodySegment(newX, newY, SEGMENT_SIZE);
        segments.addFirst(newHead);
        segments.removeLast();

        if (checkSelfCollision()) {
            explode();
        }
    }

    public void draw() {
        for (BodySegment seg : segments) {
            seg.draw();
        }
    }

    public boolean eatFood(Food f) {
        double dist = Math.sqrt(Math.pow(segments.getFirst().getX() - f.getX(), 2) +
                                Math.pow(segments.getFirst().getY() - f.getY(), 2));
        if (dist < SEGMENT_SIZE + Food.FOOD_SIZE) {
            BodySegment tail = segments.getLast();
            segments.add(new BodySegment(tail.getX(), tail.getY(), SEGMENT_SIZE));
            return true;
        }
        return false;
    }

    public boolean isInbounds() {
        double x = segments.getFirst().getX();
        double y = segments.getFirst().getY();
        return x >= 0 && x <= 1 && y >= 0 && y <= 1;
    }

    private boolean checkSelfCollision() {
        BodySegment head = segments.getFirst();
        for (int i = 1; i < segments.size(); i++) {
            BodySegment seg = segments.get(i);
            double dist = Math.sqrt(Math.pow(head.getX() - seg.getX(), 2) +
                                    Math.pow(head.getY() - seg.getY(), 2));
            if (dist < SEGMENT_SIZE * 0.9) return true;
        }
        return false;
    }

    private void explode() {
        exploded = true;
        Random rand = new Random();
        for (BodySegment seg : segments) {
            double offsetX = (rand.nextDouble() - 0.5) * 0.3;
            double offsetY = (rand.nextDouble() - 0.5) * 0.3;
            seg.setPosition(seg.getX() + offsetX, seg.getY() + offsetY);
        }
        System.out.println("Self-collision! Snake exploded!");
    }
}