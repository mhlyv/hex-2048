package logic;

import java.io.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;
    
public abstract class GameLogic implements Serializable {
    protected List<List<Integer>> board;
    protected Deque<GameState> states;
    protected final int maxUndo = 3;
    protected BigInteger score;
    private Random rand;

    GameLogic() {
        score = BigInteger.ZERO;
        rand = new Random();
        states = new LinkedList<>();
    }

    private class GameState implements Serializable {
        protected List<List<Integer>> board;
        protected BigInteger score;

        GameState() {
            board = new ArrayList<>(GameLogic.this.board.stream()
                .map(r -> new ArrayList<>(r))
                .collect(Collectors.toList()));
            score = GameLogic.this.score;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (!(obj instanceof GameState)) {
                return false;
            } else {
                GameState gs = (GameState) obj;
                return board.equals(gs.board) && score.equals(gs.score);
            }
        }

        public void restore() {
            GameLogic.this.board = board;
            GameLogic.this.score = score;
        }

        public void save() {
            GameLogic.this.states.addFirst(new GameState());
        }
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

    public String getScore() {
        return score.toString();
    }

    protected abstract void moveBoard(Direction d) throws IllegalArgumentException;

    protected void updateUndo() {
        // push state of board
        GameState gs = new GameState();
        gs.save();

        while (states.size() > maxUndo) {
            states.removeLast();
        }
    }

    public void undo() {
        if (states.size() > 0) {
            GameState gs = states.removeFirst();
            gs.restore();
        }
    }

    public void move(Direction d) {
        updateUndo();
        moveBoard(d);
        GameState gs = new GameState();
        if (states.size() != 0 && states.getFirst().equals(gs)) {
            // if the game state didn't change, dont save state
            states.removeFirst();
        } else {
            // if the game state changed add new random tile
            addNewRandomTile();
        }
    }

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
                list.set(i, removed + 1);
                score = score.add(BigInteger.TWO.pow(removed + 1));
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
        return rand.nextInt(10) == 0 ? 2 : 1;
    }

    // switch a random empty tile for a random new tile (NewRandomTile())
    // if no empty tiles were found, `end` is set to true
    protected void addNewRandomTile() {
        List<Index> emptyTiles = new ArrayList<>();

        // find and store all empty tiles
        int y = 0;
        for (List<Integer> row : board) {
            int x = 0;
            for (int tile : row) {
                if (tile == 0) {
                    emptyTiles.add(new Index(x, y));
                }
                x++;
            }
            y++;
        }

        if (emptyTiles.size() != 0) {
            // select random empty tile
            Index selected = emptyTiles.get(rand.nextInt(emptyTiles.size()));

            // fill with a new random tile
            selected.set(newRandomTile());
        }
    }
}
