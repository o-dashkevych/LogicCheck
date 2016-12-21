package org.logic.labyrinth;

/**
 * Has utility methods for simple work with console output.
 *
 * @author Oleh Dashkevych
 */
public class ConsoleUtil {

    public static <T> String drawConsoleImage(T[][] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (T[] column : array) {
            for (T cell : column) {
                stringBuilder.append(cell);
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
