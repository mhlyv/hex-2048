package logic;

import java.util.*;
import java.util.stream.*;

public class HexagonalGameLogic extends GameLogic {
    public HexagonalGameLogic(int size) {
        board = new ArrayList<List<Integer>>(2 * size - 1);
        int i;

        // top of the board, and the widest row
        for (i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<Integer>(size + i);

            for (int j = 0; j < size + i; j++) {
                row.add(0);
            }

            board.add(row);
        }

        // bottom of the board
        for (; i < 2 * size - 1; i++) {
            int rowsize = board.get(i - 1).size() - 1;
            List<Integer> row = new ArrayList<Integer>(rowsize);
            for (int j = 0; j < rowsize; j++) {
                row.add(0);
            }
            board.add(row);
        }

        addNewRandomTile();
        addNewRandomTile();
    }

    @Override
    public int getSize() {
        return board.get(0).size();
    }

    // collapse indirectly column by column
    private void collapseIndexColumns(List<List<Index>> indexboard) {
        for (int x = 0; x < indexboard.size(); x++) {
            List<Index> indexes = new ArrayList<>();

            for (int y = 0; y < indexboard.size(); y++) {
                Index index = indexboard.get(y).get(x);
                if (index != null) {
                    indexes.add(index);
                }
            }
            
            collapseLeftIndirect(indexes);
        }
    }

    // Copy of the board, but with indexes
    private List<List<Index>> boardToIndexes() {
        return IntStream.range(0, board.size())
            .mapToObj(i -> IntStream.range(0, board.get(i).size())
                .mapToObj(j -> new Index(j, i))
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    private void moveVerticalLeft(Direction d) {
        List<List<Index>> indexboard = boardToIndexes();

        /*
          a b c
         d e f g
        h i j k l
         m n o p
          q r s
        
        becomes

            a b c
          d e f g
        h i j k l
        m n o p
        q r s

        and the colums are collapsed
        */

        // pad top rows from left
        int i;
        for (i = 0; i < getSize() - 1; i++) {
            while (indexboard.get(i).size() != indexboard.size()) {
                indexboard.get(i).add(0, null);
            }
        }

        i++;

        // pad bottom rows from right
        for (; i < indexboard.size(); i++) {
            while (indexboard.get(i).size() != indexboard.size()) {
                indexboard.get(i).add(null);
            }
        }

        if (d == Direction.Down) {
            Collections.reverse(indexboard);
        } else if (d != Direction.Up) {
            throw new IllegalArgumentException("d must be a vertical direction");
        }

        collapseIndexColumns(indexboard);
    }

    private void moveVerticalRight(Direction d) {
        List<List<Index>> indexboard = boardToIndexes();

        /*
          a b c
         d e f g
        h i j k l
         m n o p
          q r s
        
        becomes

        a b c
        d e f g
        h i j k l
          m n o p
            q r s

        and the colums are collapsed
        */

        // pad top rows from right
        int i;
        for (i = 0; i < getSize() - 1; i++) {
            while (indexboard.get(i).size() != indexboard.size()) {
                indexboard.get(i).add(null);
            }
        }

        i++;

        // pad bottom rows from left
        for (; i < indexboard.size(); i++) {
            while (indexboard.get(i).size() != indexboard.size()) {
                indexboard.get(i).add(0, null);
            }
        }

        if (d == Direction.Down) {
            Collections.reverse(indexboard);
        } else if (d != Direction.Up) {
            throw new IllegalArgumentException("d must be a vertical direction");
        }

        collapseIndexColumns(indexboard);
    }

    protected void moveUpLeft() {
        moveVerticalLeft(Direction.Up);
    }

    protected void moveUpRight() {
        moveVerticalRight(Direction.Up);
    }

    protected void moveDownLeft() {
        moveVerticalRight(Direction.Down);
    }

    protected void moveDownRight() {
        moveVerticalLeft(Direction.Down);
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
    protected void moveBoard(Direction d) throws IllegalArgumentException {
        switch (d) {
        case UpRight:
            moveUpRight();
            break;
        case UpLeft:
            moveUpLeft();
            break;
        case DownRight:
            moveDownRight();
            break;
        case DownLeft:
            moveDownLeft();
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
        return false;
    }

    @Override
    public int getTile(int x, int y) {
        return board.get(y).get(x);
    }
}
