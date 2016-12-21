import org.logic.anagram.AnagramGenerator;
import org.logic.anagram.PrimeFactor;
import org.logic.labyrinth.ConsoleUtil;
import org.logic.labyrinth.Coordinate2D;
import org.logic.labyrinth.LabyrinthPathFinder;

/**
 * @author Oleh Dashkevych
 */
public class Demo {

    public static void main(String[] args) {
        AnagramGenerator anagramGenerator = new AnagramGenerator();

        System.out.println(anagramGenerator.generate("kote"));
        System.out.println(PrimeFactor.primeFactors(Integer.MAX_VALUE ));

        Character[][] labyrinth = {
                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0'},
                {'1', '0', '1', '0', '1', '1', '0', '1', '0', '1', '1'},
                {'1', '0', '1', '0', '0', '1', '0', '1', '0', '1', '1'},
                {'1', '1', '1', '0', '1', '1', '0', '1', '0', '0', '1'},
                {'1', '0', '0', '0', '1', '1', '0', '0', '0', '0', '1'},
                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1'},
                {'1', '0', '0', '0', '1', '1', '0', '0', '1', '0', '1'},
                {'1', '0', '1', '1', '0', '0', '1', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1'}};

        LabyrinthPathFinder<Character> labFinder = new LabyrinthPathFinder<>('1', ' ');
        Character[][] array = labFinder.getLabyrinthWithPath(labyrinth, Coordinate2D.getFromXandY(10, 7), Coordinate2D.getFromXandY(3, 1));

        System.out.println(ConsoleUtil.drawConsoleImage(array));
    }
}
