package logic;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class TestGameLogic extends GameLogic {
    // empty abstract methods
    @Override public void move(Direction direction) throws IllegalArgumentException { }
    @Override public int getSize() { return 0; }
    @Override public int getTile(int x, int y) { return 0; }
    @Override protected void moveBoard(Direction d) throws IllegalArgumentException { }
    @Override public boolean hasEnded() { return false; }

    @Test
    public void testCollapseZeroes() {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 0, 0));
        collapseLeft(list);
        assertTrue(list.equals(Arrays.asList(0, 0, 0)));
    }

    @Test
    public void testNotCollapsable() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        collapseLeft(list);
        assertTrue(list.equals(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void testSingleCollapsable() {
        List<Integer> list = new ArrayList<>(Arrays.asList(128, 128, 32, 16));
        collapseLeft(list);
        assertTrue(list.equals(Arrays.asList(256, 32, 16, 0)));
    }

    @Test
    public void testMultipleCollapsable() {
        List<Integer> list = new ArrayList<>(Arrays.asList(2, 2, 8, 4, 4));
        collapseLeft(list);
        assertTrue(list.equals(Arrays.asList(4, 8, 8, 0, 0)));
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
    public void testAddNewRandomTileFail() {
        board = new ArrayList<>();
        addNewRandomTile();
        assertTrue(hasEnded());
    }

    @Test
    public void testAddNewRandomTile() {
        board = new ArrayList<>();
        board.add(new ArrayList<>());
        board.get(0).add(0);

        addNewRandomTile();
        assertTrue(board.get(0).get(0) != 0);
    }
}
