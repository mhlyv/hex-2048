package logic;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.stream.*;

public class TestHexagonalGameLogic extends HexagonalGameLogic {
    private List<List<Integer>> orig_board = Arrays.asList(
        new ArrayList<>(Arrays.asList( 0, 16,  0)),
        new ArrayList<>(Arrays.asList( 0,  2,  0,  0)),
        new ArrayList<>(Arrays.asList( 0,  2,  2, 16,  0)),
        new ArrayList<>(Arrays.asList(32,  0,  0,  0)),
        new ArrayList<>(Arrays.asList(32,  4,  0))
    );

    private Map<Direction, List<List<Integer>>> moves = new EnumMap<>(Direction.class);

    {
        // Arrays.asList( 0, 16,  0),
        // Arrays.asList( 0,  2,  0,  0),
        // Arrays.asList( 0,  2,  2, 16,  0),
        // Arrays.asList(32,  0,  0,  0),
        // Arrays.asList(32,  4,  0)
        moves.put(Direction.Left, Arrays.asList(
            Arrays.asList(16,  0,  0),
            Arrays.asList( 2,  0,  0,  0),
            Arrays.asList( 4, 16,  0,  0,  0),
            Arrays.asList(32,  0,  0,  0),
            Arrays.asList(32,  4,  0)
        ));

        moves.put(Direction.Right, Arrays.asList(
            Arrays.asList( 0,  0, 16),
            Arrays.asList( 0,  0,  0,  2),
            Arrays.asList( 0,  0,  0,  4, 16),
            Arrays.asList( 0,  0,  0, 32),
            Arrays.asList( 0, 32,  4)
        ));

        moves.put(Direction.UpLeft, Arrays.asList(
            Arrays.asList(         4, 32,  0),
            Arrays.asList(     2,  0,  0,  0),
            Arrays.asList(64,  4,  0,  0,  0),
            Arrays.asList( 0,  0,  0,  0),
            Arrays.asList( 0,  0,  0)
        ));

        moves.put(Direction.UpRight, Arrays.asList(
            Arrays.asList( 0, 16,  2),
            Arrays.asList( 0,  4, 32, 16),
            Arrays.asList( 0, 32,  0,  4,  0),
            Arrays.asList(     0,  0,  0,  0),
            Arrays.asList(         0,  0,  0)
        ));

        moves.put(Direction.DownLeft, Arrays.asList(
            Arrays.asList( 0,  0,  0),
            Arrays.asList( 0, 16,  0,  0),
            Arrays.asList( 0,  4,  0,  0,  0),
            Arrays.asList(    32,  2, 16,  0),
            Arrays.asList(        32,  4,  0)
        ));

        moves.put(Direction.DownRight, Arrays.asList(
            Arrays.asList(         0,  0,  0),
            Arrays.asList(     0,  0,  0,  0),
            Arrays.asList( 0,  0,  0,  0,  0),
            Arrays.asList( 0,  2,  0, 32),
            Arrays.asList(64,  4,  4)
        ));
    }

    public TestHexagonalGameLogic() {
        super(3);
    }

    @Before
    public void reset() {
        board = new ArrayList<>(orig_board.stream().map(row -> new ArrayList<>(row)).collect(Collectors.toList()));
    }

    @Test
    public void testMoveUpLeft() {
        moveUpLeft();
        assertEquals(moves.get(Direction.UpLeft), board);
    }

    @Test
    public void testMoveUpRight() {
        moveUpRight();
        assertEquals(moves.get(Direction.UpRight), board);
    }

    @Test
    public void testMoveDownLeft() {
        moveDownLeft();
        assertEquals(moves.get(Direction.DownLeft), board);
    }

    @Test
    public void testMoveDownRight() {
        moveDownRight();
        assertEquals(moves.get(Direction.DownRight), board);
    }

    @Test
    public void testMoveLeft() {
        moveLeft();
        assertEquals(moves.get(Direction.Left), board);
    }

    @Test
    public void testMoveRight() {
        moveRight();
        assertEquals(moves.get(Direction.Right), board);
    }

    @Test
    public void testMove() {
        for (Direction d : Arrays.asList(Direction.UpLeft, Direction.UpRight, Direction.DownLeft, Direction.DownRight, Direction.Left, Direction.Right)) {
            reset();
            move(d);
            List<List<Integer>> solution = moves.get(d);

            int diffcounter = 0;
            for (int y = 0; y < board.size(); y++) {
                for (int x = 0; x < board.get(y).size(); x++) {
                    if (board.get(y).get(x) != solution.get(y).get(x)) {
                        diffcounter++;
                    }
                }
            }
            assertEquals(1, diffcounter);
        }
    }
}
