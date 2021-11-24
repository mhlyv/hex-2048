package logic;

import java.io.*;
import java.util.*;
import java.util.stream.*;
    
public abstract class GameLogic implements Serializable {
    protected List<List<Integer>> board;
    protected Deque<List<List<Integer>>> states;
    protected final int maxUndo = 3;
    private Random rand;

    GameLogic() {
        rand = new Random();
        states = new LinkedList<>();
    }

    protected class Index {
        private int x;
        private int y;

        Index(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int get() {
            return board.get(y).get(x);
        }

        public void set(int n) {
            board.get(y).set(x, n);
        }
    }

    public enum Direction {
        Up, Down, Left, Right,
        UpLeft, UpRight, DownLeft, DownRight
    }

    protected abstract void moveBoard(Direction d) throws IllegalArgumentException;

    protected void updateUndo() {
        // push state of board
        states.addFirst(board.stream().map(row -> new ArrayList<>(row)).collect(Collectors.toList()));

        while (states.size() > maxUndo) {
            states.removeLast();
        }
    }

    public void undo() {
        if (states.size() > 0) {
            board = states.removeFirst();
        }
    }

    public void move(Direction d) {
        updateUndo();
        moveBoard(d);
        if (states.getFirst().equals(board)) {
            // if the board didn't change, dont save state
            states.removeFirst();
        } else {
            // if the board changed add new random tile
            addNewRandomTile();
        }
    }

    public abstract boolean hasEnded();

    public abstract int getSize();
    public abstract int getTile(int x, int y);

    public int getNumberOfTiles() {
        int n = 0;

        for (List<Integer> row : board) {
            n += row.size();
        }

        return n;
    }

    protected void collapseLeft(List<Integer> list) {
        int size = list.size();
        list.removeIf(x -> x == 0); // remove all empty tiles

        // from the left, if an element (i) equals the next element (i + 1), remove the
        // next element and multiply the original element by 2.
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).equals(list.get(i + 1))) {
                int removed = list.remove(i + 1);
                list.set(i, removed * 2);
            }
        }

        // fill up the list with zeroes to be the same size as originally
        while (list.size() != size) {
            list.add(0);
        }
    }

    // collapse a list from right to left indirectly
    protected void collapseLeftIndirect(List<Index> indexes) {
        List<Integer> list = indexes.stream()
            .map(i -> i.get())
            .collect(Collectors.toList());

        collapseLeft(list);

        // commit changes to board
        for (int i = 0; i < indexes.size(); i++) {
            indexes.get(i).set(list.get(i));
        }
    }

    // return 2 or 4 (10% probablility for 4)
    protected int newRandomTile() {
        return rand.nextInt(10) == 0 ? 4 : 2;
    }

    // switch a random empty tile for a random new tile (NewRandomTile())
    // if no empty tiles were found, `end` is set to true
    protected void addNewRandomTile() {
        class Vec {
            public int x;
            public int y;
            Vec(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        List<Vec> emptyTiles = new ArrayList<Vec>();

        // find and store all empty tiles
        int y = 0;
        for (List<Integer> row : board) {
            int x = 0;
            for (int tile : row) {
                if (tile == 0) {
                    emptyTiles.add(new Vec(x, y));
                }
                x++;
            }
            y++;
        }

        if (emptyTiles.size() != 0) {
            // select random empty tile
            Vec selected = emptyTiles.get(rand.nextInt(emptyTiles.size()));

            // fill with a new random tile
            board.get(selected.y).set(selected.x, newRandomTile());
        }
    }
}
