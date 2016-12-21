package org.logic.labyrinth;

import java.util.Objects;

/**
 * Holder bean for two-dimensional coordinates.
 *
 * @author Oleh Dashkevych
 */
public final class Coordinate2D {
    private int x;
    private int y;

    private Coordinate2D() {
    }

    private Coordinate2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Coordinate2D getFromXandY(int x, int y){
        return new Coordinate2D(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate2D that = (Coordinate2D) o;
        return getX() == that.getX() &&
                getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return String.format("Coordinate2D{x=%d, y=%d}", x, y);
    }
}
