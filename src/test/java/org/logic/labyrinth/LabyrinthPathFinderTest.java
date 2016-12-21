package org.logic.labyrinth;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.logic.labyrinth.Coordinate2D.getFromXandY;

/**
 * @author Oleh Dashkevych
 */
public class LabyrinthPathFinderTest {
    private LabyrinthPathFinder<Character> finder;

    private static final Character DEFAULT_BLOCK = '1';

    private static final Character DEFAULT_ROAD = ' ';
    private Character[][] labyrinth;
    private Deque<Coordinate2D> rightCoordinates;

    @Before
    public void setUp() throws Exception {
        finder = new LabyrinthPathFinder<>(DEFAULT_BLOCK, DEFAULT_ROAD);
        labyrinth = new Character[][]{
                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0'},
                {'1', '0', '1', '0', '1', '1', '0', '1', '0', '1', '1'},
                {'1', '0', '1', '0', '0', '1', '0', '1', '0', '1', '1'},
                {'1', '1', '1', '0', '1', '1', '0', '1', '0', '0', '1'},
                {'1', '0', '0', '0', '1', '1', '0', '1', '0', '0', '1'},
                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1'},
                {'1', '0', '0', '0', '1', '1', '0', '0', '1', '0', '1'},
                {'1', '0', '1', '1', '0', '0', '1', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1'}};
        rightCoordinates = new LinkedList<>(Arrays.asList(getFromXandY(9, 7),
                getFromXandY(8, 7), getFromXandY(8, 8), getFromXandY(8, 9), getFromXandY(7, 9), getFromXandY(6, 9),
                getFromXandY(5, 9), getFromXandY(5, 8), getFromXandY(4, 8), getFromXandY(3, 8), getFromXandY(2, 8),
                getFromXandY(1, 8), getFromXandY(1, 7), getFromXandY(1, 6), getFromXandY(1, 5), getFromXandY(1, 4)
                , getFromXandY(1, 3), getFromXandY(1, 2), getFromXandY(1, 1), getFromXandY(2, 1)));
    }

    @Test
    public void findListWithPathCoordinates() throws Exception {
        Coordinate2D start = getFromXandY(3, 1);
        Coordinate2D finish = getFromXandY(10, 7);

        List<Coordinate2D> foundPath = finder.findPathCoordinates(labyrinth, start, finish);

        rightCoordinates.addFirst(finish);
        rightCoordinates.add(start);

        assertTrue(rightCoordinates.equals(foundPath));
    }

    @Test
    public void pathToFinishWasNotFound() throws Exception {
        Coordinate2D start = getFromXandY(3, 1);
        Coordinate2D finish = getFromXandY(1, 10);

        List<Coordinate2D> foundPathCoords = finder.findPathCoordinates(labyrinth, start, finish);

        assertTrue(foundPathCoords.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void exceptionOnNullStartParam() throws Exception {
        finder.findPathCoordinates(labyrinth, getFromXandY(3, 1), null);
    }

    @Test(expected = NullPointerException.class)
    public void exceptionOnNullFinishParam() throws Exception {
        finder.findPathCoordinates(labyrinth, null, getFromXandY(3, 1));
    }

    @Test(expected = NullPointerException.class)
    public void exceptionOnNullLabyrinthArray() throws Exception {
        finder.findPathCoordinates(null, getFromXandY(3, 1), getFromXandY(1, 10));
    }

    @Test(expected = NullPointerException.class)
    public void exceptionOnNullLabyrinthCells() throws Exception {
        labyrinth[labyrinth.length - 1] = null;
        finder.findPathCoordinates(labyrinth, getFromXandY(3, 1), getFromXandY(1, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionOnOutOfLabyrinthCoordinates() throws Exception {
        finder.findPathCoordinates(labyrinth, getFromXandY(Integer.MAX_VALUE, Integer.MIN_VALUE), getFromXandY(1, 10));
    }
}