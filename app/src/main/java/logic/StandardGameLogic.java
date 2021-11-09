package logic;

import java.util.*;

// game logic for a game with a square board and square tiles
public class StandardGameLogic extends GameLogic {
    public StandardGameLogic(int size) {
        super();

        board = new ArrayList<List<Integer>>(size);
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<Integer>(size);
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            board.add(row);
        }
    }

    @Override
    public int getSize() {
        return board.size();
    }
    
    @Override
    public int getTile(int x, int y) {
        return board.get(y).get(x);
    }

    private void moveUp() {
        // this only works for a square board
        for (int x = 0; x < board.size(); x++) {
            List<Integer> col = new ArrayList<Integer>(board.size());

            // read column
            for (int y = 0; y < board.size(); y++) {
                col.add(board.get(y).get(x));
            }

            collapseLeft(col);

            // set column
            for (int y = 0; y < board.size(); y++) {
                board.get(y).set(x, col.get(y));
            }
        }
    }

    private void moveDown() {
        // this only works for a square board
        for (int x = 0; x < board.size(); x++) {
            List<Integer> col = new ArrayList<Integer>(board.size());

            // read column
            for (int y = board.size() - 1; y >= 0; y--) {
                col.add(board.get(y).get(x));
            }

            collapseLeft(col);

            // set column
            for (int y = 0; y < board.size(); y++) {
                board.get(y).set(x, col.get(board.size() - y - 1));
            }
        }
    }

    private void moveLeft() {
        for (List<Integer> row : board) {
            collapseLeft(row);
        }
    }

    private void moveRight() {
        for (List<Integer> row : board) {
            Collections.reverse(row);
            collapseLeft(row);
            Collections.reverse(row);
        }
    }

    @Override
    public void move(Direction d) {
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

        addNewRandomTile();
    }
}
