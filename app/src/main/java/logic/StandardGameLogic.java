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
}
