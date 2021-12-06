package logic;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.stream.*;

public class TestStandardGameLogic extends StandardGameLogic {
    List<List<Integer>> orig_board = Arrays.asList(
        new ArrayList<>(Arrays.asList(0,  1,  1,  2)),
        new ArrayList<>(Arrays.asList(0,  1,  1,  2)),
        new ArrayList<>(Arrays.asList(0,  1,  1,  2)),
        new ArrayList<>(Arrays.asList(3,  4,  4,  5))
    );

    Map<Direction, List<List<Integer>>> moves = new EnumMap<>(Direction.class);

    {
        moves.put(Direction.Up,
            Arrays.asList(
                Arrays.asList(3,  2,  2,  3),
                Arrays.asList(0,  1,  1,  2),
                Arrays.asList(0,  4,  4,  5),
                Arrays.asList(0,  0,  0,  0)
            )
        );
        moves.put(Direction.Down,
            Arrays.asList(
                Arrays.asList(0,  0,  0,  0),
                Arrays.asList(0,  1,  1,  2),
                Arrays.asList(0,  2,  2,  3),
                Arrays.asList(3,  4,  4,  5)
            )
        );
        moves.put(Direction.Left,
            Arrays.asList(
                Arrays.asList(2,  2,  0,  0),
                Arrays.asList(2,  2,  0,  0),
                Arrays.asList(2,  2,  0,  0),
                Arrays.asList(3,  5,  5,  0)
            )
        );
        moves.put(Direction.Right,
            Arrays.asList(
                Arrays.asList(0, 0,  2,  2),
                Arrays.asList(0, 0,  2,  2),
                Arrays.asList(0, 0,  2,  2),
                Arrays.asList(0, 3,  5,  5)
            )
        );
    }

    public TestStandardGameLogic() {
        super(4);
    }

    private void reset() {
        board = new ArrayList<>(orig_board.stream().map(row -> new ArrayList<>(row)).collect(Collectors.toList()));
    }

    @Before
    public void setBoard() {
        reset();
    }

    @Test
    public void testMoveUp() {
        moveUp();
        assertEquals(moves.get(Direction.Up), board);
    }

    @Test
    public void testMoveDown() {
        moveDown();
        assertEquals(moves.get(Direction.Down), board);
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
        for (Direction d : Arrays.asList(Direction.Up, Direction.Down, Direction.Left, Direction.Right)) {
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

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMove() {
        move(Direction.DownLeft);
    }
}
