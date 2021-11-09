package logic;

import java.util.*;
    
public abstract class GameLogic {
    protected List<List<Integer>> board;
    private Random rand;

    GameLogic() {
        rand = new Random();
    }

    public enum Direction {
        Up, Down, Left, Right,
        UpLeft, UpRight, DownLeft, DownRight
    }

    protected abstract void moveBoard(Direction d) throws IllegalArgumentException;

    public void move(Direction d) {
        int hash = board.hashCode();
        moveBoard(d);
        if (hash != board.hashCode()) {
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
    
    // collapse a list from right to left
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

    // return 2 or 4
    protected int newRandomTile() {
        return (1 + rand.nextInt(2)) * 2;
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
