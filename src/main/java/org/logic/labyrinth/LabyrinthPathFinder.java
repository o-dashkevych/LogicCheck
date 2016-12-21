package org.logic.labyrinth;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.logic.labyrinth.CommonMessages.COORDINATE_IS_OUT_OF_ARRAY;
import static org.logic.labyrinth.Coordinate2D.getFromXandY;

/**
 * Finds path in labyrinth from T blocks and roads.
 *
 * @author Oleh Dashkevych
 */
public class LabyrinthPathFinder<T> {

    private static final int BLOCK = -2;
    private static final int WHITESPACE = -1;

    private final T block;
    private final T roadForResult;

    private Integer[][] numberedLabyrinth;

    private int endX, endY;

    /**
     * Constructs path finder for labyrinth with specified T objects
     *
     * @param wallObject       represents wall or block in labyrinth
     * @param resultRoadObject will represent road for generating labyrinth with path
     * @throws NullPointerException if any parameter is null
     */
    public LabyrinthPathFinder(T wallObject, T resultRoadObject) {
        requireNonNull(wallObject);
        requireNonNull(resultRoadObject);
        this.block = wallObject;
        this.roadForResult = resultRoadObject;
    }

    /**
     * Finds a path in labyrinth from start to finish
     *
     * @param labyrinth two dimensional array of labyrinth
     * @param start     start point of seeking
     * @param finish    endpoint for path
     * @return List of coordinates from start to finish or empty
     * @throws NullPointerException     if #labyrinth is null or any of it arrays and cells is null;
     * @throws NullPointerException     if start or finish coordinate is null
     * @throws IllegalArgumentException if start or finish coordinate is out of labyrinth dimensions
     */
    public List<Coordinate2D> findPathCoordinates(T[][] labyrinth, Coordinate2D start, Coordinate2D finish) {
        checkInputData(labyrinth, start, finish);
        this.endX = finish.getX();
        this.endY = finish.getY();
        numberedLabyrinth = transformToNumericLabyrinth(labyrinth);

        if (markCellsDistanceAndCheckPath(start.getX(), start.getY(), 1)) {
            return buildResolutionPathFromFinish(endX, endY);
        }
        return Collections.emptyList();
    }

    private void checkInputData(T[][] labyrinth, Coordinate2D start, Coordinate2D finish) {
        for (T[] column : requireNonNull(labyrinth)) {
            for (T cell : requireNonNull(column))
                requireNonNull(cell);
        }
        requireNonNull(start);
        requireNonNull(finish);
        if (isOutOfArray(start.getX(), start.getY(), labyrinth)) {
            throw new IllegalArgumentException(String.format(COORDINATE_IS_OUT_OF_ARRAY, start.getX(), start.getY()));
        }
        if (isOutOfArray(finish.getX(), finish.getY(), labyrinth)) {
            throw new IllegalArgumentException(String.format(COORDINATE_IS_OUT_OF_ARRAY, finish.getX(), finish.getY()));
        }
    }

    private boolean isOutOfArray(int currentX, int currentY, Object[][] array) {
        return (currentX > array.length - 1) ||
                (currentY > array[0].length - 1) && (currentX < 0 || currentY < 0);
    }

    private Integer[][] transformToNumericLabyrinth(T[][] labyrinthToProcess) {
        Integer[][] numericLab = new Integer[labyrinthToProcess[0].length][labyrinthToProcess.length];
        for (int indexForX = 0; indexForX < labyrinthToProcess.length; indexForX++) {
            for (int indexForY = 0; indexForY < labyrinthToProcess[0].length; indexForY++) {
                T cell = labyrinthToProcess[indexForX][indexForY];
                numericLab[indexForX][indexForY] = cell.equals(block) ? BLOCK : WHITESPACE;
            }
        }
        return numericLab;
    }

    private boolean markCellsDistanceAndCheckPath(int currentX, int currentY, int distanceValue) {
        if (foundFinishCell(currentX, currentY)) {
            numberedLabyrinth[currentX][currentY] = distanceValue;
            return true;
        }
        if (isOutOfArray(currentX, currentY, numberedLabyrinth)) {
            return false;
        }
        if (isNotMarked(numberedLabyrinth[currentX][currentY])) {
            numberedLabyrinth[currentX][currentY] = distanceValue++;
        } else {
            return false;
        }
        boolean up = markCellsDistanceAndCheckPath(currentX - 1, currentY, distanceValue);
        boolean down = markCellsDistanceAndCheckPath(currentX + 1, currentY, distanceValue);
        boolean right = markCellsDistanceAndCheckPath(currentX, currentY + 1, distanceValue);
        boolean left = markCellsDistanceAndCheckPath(currentX, currentY - 1, distanceValue);
        return up || down || left || right;
    }

    private boolean foundFinishCell(int currentX, int currentY) {
        return (currentX == endX) && (currentY == endY);
    }

    private boolean isNotMarked(int cell) {
        return cell == WHITESPACE;
    }

    private List<Coordinate2D> buildResolutionPathFromFinish(int currentX, int currentY) {
        int finishDistanceMark = numberedLabyrinth[currentX][currentY];
        List<Coordinate2D> resultPathCoordinates = new LinkedList<>();
        addToResult(getFromXandY(currentX, currentY), finishDistanceMark, resultPathCoordinates);

        for (int currentMark = finishDistanceMark; currentMark != 0; currentMark--) {
            if (addToResult(getFromXandY(currentX - 1, currentY), currentMark, resultPathCoordinates)) {
                currentX--;
                continue;
            }
            if (addToResult(getFromXandY(currentX + 1, currentY), currentMark, resultPathCoordinates)) {
                currentX++;
                continue;
            }
            if (addToResult(getFromXandY(currentX, currentY + 1), currentMark, resultPathCoordinates)) {
                currentY++;
                continue;
            }
            if (addToResult(getFromXandY(currentX, currentY - 1), currentMark, resultPathCoordinates)) {
                currentY--;
            }
        }
        return resultPathCoordinates;
    }

    private boolean addToResult(Coordinate2D coordinate, int currentMark, List<Coordinate2D> resultPathCoordinates) {
        if (!isOutOfArray(coordinate.getX(), coordinate.getY(), numberedLabyrinth)
                && numberedLabyrinth[coordinate.getX()][coordinate.getY()] == currentMark) {
            resultPathCoordinates.add(coordinate);
            return true;
        }
        return false;
    }


    /**
     * Generates labyrinth with only right path from start to finish.
     * Finds coordinates at first than makes labyrinth with {@link #block} and {@link #roadForResult}
     *
     * @param labyrinth two dimensional array of labyrinth
     * @param start     start point of seeking
     * @param finish    endpoint for path
     * @return List of coordinates from start to finish or empty
     * @throws NullPointerException     if #labyrinth is null or any of it arrays and cells is null;
     * @throws NullPointerException     if start or finish coordinate is null
     * @throws IllegalArgumentException if start or finish coordinate is out of labyrinth dimensions
     * @see #findPathCoordinates(Object[][], Coordinate2D, Coordinate2D)
     */
    public T[][] getLabyrinthWithPath(T[][] labyrinth, Coordinate2D start, Coordinate2D finish) {
        List<Coordinate2D> pathCoordinates = findPathCoordinates(labyrinth, start, finish);
        T[][] labyrinthOnlyWithPath = Arrays.copyOf(labyrinth, labyrinth.length);
        for (T[] row : labyrinthOnlyWithPath) {
            Arrays.fill(row, block);
        }
        pathCoordinates.forEach(coordinate -> labyrinthOnlyWithPath[coordinate.getX()][coordinate.getY()] = roadForResult);
        return labyrinthOnlyWithPath;
    }
}
