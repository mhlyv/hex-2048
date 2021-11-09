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
    public int GetSize() {
        return board.size();
    }
    
    @Override
    public int GetTile(int x, int y) {
        return board.get(y).get(x);
    }

    private void MoveUp() {
        // this only works for a square board
        for (int x = 0; x < board.size(); x++) {
            List<Integer> col = new ArrayList<Integer>(board.size());

            // read column
            for (int y = 0; y < board.size(); y++) {
                col.add(board.get(y).get(x));
            }

            CollapseLeft(col);

            // set column
            for (int y = 0; y < board.size(); y++) {
                board.get(y).set(x, col.get(y));
            }
        }
    }

    private void MoveDown() {
        // this only works for a square board
        for (int x = 0; x < board.size(); x++) {
            List<Integer> col = new ArrayList<Integer>(board.size());

            // read column
            for (int y = board.size() - 1; y >= 0; y--) {
                col.add(board.get(y).get(x));
            }

            CollapseLeft(col);

            // set column
            for (int y = 0; y < board.size(); y++) {
                board.get(y).set(x, col.get(board.size() - y - 1));
            }
        }
    }

    private void MoveLeft() {
        for (List<Integer> row : board) {
            CollapseLeft(row);
        }
    }

    private void MoveRight() {
        for (List<Integer> row : board) {
            Collections.reverse(row);
            CollapseLeft(row);
            Collections.reverse(row);
        }
    }

    @Override
    public void Move(Direction d) {
        switch (d) {
            case Up:
                MoveUp();
                break;
            case Down:
                MoveDown();
                break;
            case Left:
                MoveLeft();
                break;
            case Right:
                MoveRight();
                break;
            default:
                throw new IllegalArgumentException();
        }

        AddNewRandomTile();
    }
}
