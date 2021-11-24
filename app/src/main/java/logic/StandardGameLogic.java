package logic;

import java.util.*;

// game logic for a game with a square board and square tiles
public class StandardGameLogic extends GameLogic {
    public StandardGameLogic(int size) {
        board = new ArrayList<List<Integer>>(size);
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<Integer>(size);
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            board.add(row);
        }

        addNewRandomTile();
        addNewRandomTile();
    }

    @Override
    public int getSize() {
        return board.size();
    }
    
    @Override
    public int getTile(int x, int y) {
        return board.get(y).get(x);
    }

    protected void moveUp() {
        // this only works for a square board
        for (int x = 0; x < board.size(); x++) {
            List<Index> indexes = new ArrayList<>(board.size());

            // read column
            for (int y = 0; y < board.size(); y++) {
                indexes.add(new Index(x, y));
            }

            collapseLeftIndirect(indexes);
        }
    }

    protected void moveDown() {
        // this only works for a square board
        for (int x = 0; x < board.size(); x++) {
            List<Index> indexes = new ArrayList<>(board.size());

            // read column
            for (int y = board.size() - 1; y >= 0; y--) {
                indexes.add(new Index(x, y));
            }

            collapseLeftIndirect(indexes);
        }
    }

    protected void moveLeft() {
        for (List<Integer> row : board) {
            collapseLeft(row);
        }
    }

    protected void moveRight() {
        for (List<Integer> row : board) {
            Collections.reverse(row);
            collapseLeft(row);
            Collections.reverse(row);
        }
    }

    @Override
    protected void moveBoard(Direction d) {
        switch (d) {
            case Up:
                moveUp();
                break;
            case Down:
                moveDown();
                break;
            case Left:
                moveLeft();
                break;
            case Right:
                moveRight();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean hasEnded() {
        int size = board.size();

        if (size == 0) {
            return true;
        }

        for (int y = 1; y < size; y++) {
            for (int x = 0; x < size - 1; x++) {
                int tile = board.get(y).get(x);
                int tileAbove = board.get(y - 1).get(x);
                int tileRight = board.get(y).get(x + 1);

                if (tile == 0 || tileAbove == 0 || tileRight == 0 ||
                    tile == tileAbove || tile == tileRight) {
                    return false;
                }
            }

            int tile = board.get(y).get(size - 1);
            int tileAbove = board.get(y - 1).get(size - 1);

            if (tile == 0 || tileAbove == 0 || tile == tileAbove) {
                return false;
            }
        }

        return true;
    }
}
