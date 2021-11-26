package logic;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.stream.*;

public class TestGameLogic extends GameLogic {
    // empty abstract methods
    @Override public void move(Direction direction) throws IllegalArgumentException { }
    @Override public int getSize() { return 0; }
    @Override public int getTile(int x, int y) { return 0; }
    @Override protected void moveBoard(Direction d) throws IllegalArgumentException { }
    @Override public boolean hasEnded() { return false; }

    @Test
    public void testCollapseLeft() {
        board = new ArrayList<>();

        {
            board.add(new ArrayList<>(Arrays.asList(0, 0, 0)));
            final int y = board.size() - 1;
            collapseLeftIndirect(
                IntStream.range(0, board.get(y).size())
                .mapToObj(x -> new Index(x, y))
                .collect(Collectors.toList()));
            assertTrue("zeroes", board.get(y).equals(Arrays.asList(0, 0, 0)));
        }

        {
            board.add(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
            final int y = board.size() - 1;
            collapseLeftIndirect(
                IntStream.range(0, board.get(y).size())
                .mapToObj(x -> new Index(x, y))
                .collect(Collectors.toList()));
            assertTrue("unmodified", board.get(y).equals(Arrays.asList(1, 2, 3, 4, 5)));
        }

        {
            board.add(new ArrayList<>(Arrays.asList(128, 128, 32, 16)));
            final int y = board.size() - 1;
            collapseLeftIndirect(
                IntStream.range(0, board.get(y).size())
                .mapToObj(x -> new Index(x, y))
                .collect(Collectors.toList()));
            assertTrue("unmodified", board.get(y).equals(Arrays.asList(256, 32, 16, 0)));
        }

        {
            board.add(new ArrayList<>(Arrays.asList(2, 2, 8, 4, 4)));
            final int y = board.size() - 1;
            collapseLeftIndirect(
                IntStream.range(0, board.get(y).size())
                .mapToObj(x -> new Index(x, y))
                .collect(Collectors.toList()));
            assertTrue("multiple collapse", board.get(y).equals(Arrays.asList(4, 8, 8, 0, 0)));
        }
    }

    @Test
    public void testCollapseLeftIndirect() {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 0, 0));
        collapseLeft(list);
        assertTrue("zeroes", list.equals(Arrays.asList(0, 0, 0)));

        list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        collapseLeft(list);
        assertTrue("unmodified", list.equals(Arrays.asList(1, 2, 3, 4, 5)));

        list = new ArrayList<>(Arrays.asList(128, 128, 32, 16));
        collapseLeft(list);
        assertTrue("single collapse", list.equals(Arrays.asList(256, 32, 16, 0)));

        list = new ArrayList<>(Arrays.asList(2, 2, 8, 4, 4));
        collapseLeft(list);
        assertTrue("multiple collapse", list.equals(Arrays.asList(4, 8, 8, 0, 0)));
    }

    @Test
    public void testNewRandomTile() {
        int c2 = 0;
        int c4 = 0;

        while (c2 < 10 && c4 < 10) {
            int n = newRandomTile();
            assertTrue(n == 2 || n == 4);
            if (n == 2) {
                c2++;
            } else if (n == 4) {
                c4++;
            }
        }
    }

    @Test
    public void testAddNewRandomTile() {
        board = new ArrayList<>();
        board.add(new ArrayList<>());

        addNewRandomTile();

        board.get(0).add(0);

        addNewRandomTile();
        assertTrue(board.get(0).get(0) != 0);
    }

    @Test
    public void testUndo() {
        List<List<Integer>> b = Arrays.asList(Arrays.asList(1));
        board = b;
        updateUndo();
        board = Arrays.asList(Arrays.asList(2));
        undo();
        assertEquals("has undo", b, board);
        undo();
        assertEquals("no undo", b, board);
    }

    @Test
    public void testMove() {
        // the stored states shouldn't change if the board doesn't change on move
        board = Arrays.asList(Arrays.asList(1));
        states.add(board.stream().map(row -> new ArrayList<>(row)).collect(Collectors.toList()));
        int hash = states.hashCode();
        move(Direction.Up);
        assertEquals(hash, states.hashCode());
    }

    @Test
    public void testGetNumberOfTiles() {
        final int y = 10;
        final int x = 11;
        board = IntStream.range(0, y)
            .mapToObj(i -> IntStream.range(0, x).mapToObj(j -> j).collect(Collectors.toList()))
            .collect(Collectors.toList());

        assertEquals(x * y, getNumberOfTiles());
    }
}
