package graphics;

import java.awt.*;
import logic.HexagonalGameLogic;
import java.awt.event.KeyEvent;
import java.math.BigInteger;

public class HexagonalGamePanel extends GamePanel {

    HexagonalGamePanel(Integer size) {
        super(new HexagonalGameLogic(size));

        keymap.put(KeyEvent.VK_D, HexagonalGameLogic.Direction.Right);
        keymap.put(KeyEvent.VK_A,  HexagonalGameLogic.Direction.Left);
        keymap.put(KeyEvent.VK_W,    HexagonalGameLogic.Direction.UpLeft);
        keymap.put(KeyEvent.VK_E,    HexagonalGameLogic.Direction.UpRight);
        keymap.put(KeyEvent.VK_Z,  HexagonalGameLogic.Direction.DownLeft);
        keymap.put(KeyEvent.VK_X,  HexagonalGameLogic.Direction.DownRight);
        keymap.put(KeyEvent.VK_Y,  HexagonalGameLogic.Direction.DownRight);
    }

    private Polygon getHexagon(int x, int y, int r) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            hexagon.addPoint(
                (int)(x + r * Math.cos((i * 2 + 1) * Math.PI / 6)),
                (int)(y + r * Math.sin((i * 2 + 1) * Math.PI / 6))
            );
        }
        return hexagon;
    }

    @Override
    protected void drawGame(Graphics g) {
        Dimension size = game.getSize();
        int N = gl.getSize();
        int D = 2 * N - 1;
        int R = Math.min(
            (int) (size.width / (D * Math.sqrt(3))),
            (int) (size.height / (2 + 2 * (D - 1) * 3 / 4))
        );
        int W = (int) (Math.sqrt(3) * R);
        int H = 2 * R;

        int y_margin = (size.height - H - (D - 1) * H * 3 / 4) / 2;
        int y = y_margin + H / 2;

        int row_tiles = N;

        for (int i = 0; i < D; i++) {
            int x = (size.width / 2) - row_tiles * (W / 2) + (W / 2);
            for (int j = 0; j < row_tiles; j++) {
                int tile = gl.getTile(j, i);
                Polygon hexagon = getHexagon(x, y, R);
                g.setColor(getTileColor(tile));
                g.fillPolygon(hexagon);
                g.setColor(Color.BLACK);
                g.drawPolygon(hexagon);

                Rectangle r = new Rectangle(
                    x - W / 2,
                    y - H / 4,
                    W,
                    H / 2
                );
                BigInteger two = BigInteger.TWO;
                drawCenterText(g, tile == 0 ? "" : two.pow(tile).toString(), r);

                x += W;
            }

            y += H * 3 / 4;

            if (i < D / 2) {
                row_tiles++;
            } else {
                row_tiles--;
            }
        }
    }
    
}
