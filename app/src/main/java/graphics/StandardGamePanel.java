package graphics;

import java.awt.*;
import logic.StandardGameLogic;
import java.awt.event.KeyEvent;
import java.math.BigInteger;

public class StandardGamePanel extends GamePanel {
    public StandardGamePanel(Integer size) {
        super(new StandardGameLogic(size));

        keymap.put(KeyEvent.VK_RIGHT, StandardGameLogic.Direction.Right);
        keymap.put(KeyEvent.VK_LEFT,  StandardGameLogic.Direction.Left);
        keymap.put(KeyEvent.VK_UP,    StandardGameLogic.Direction.Up);
        keymap.put(KeyEvent.VK_DOWN,  StandardGameLogic.Direction.Down);
    }

    @Override
    protected void drawGame(Graphics g) {
        Dimension size = game.getSize();
        int tileSize = Math.min(size.height, size.width) / gl.getSize();
        Dimension offset = new Dimension(
            (size.width  - tileSize * gl.getSize()) / 2,
            (size.height - tileSize * gl.getSize()) / 2);

        for (int y = 0; y < gl.getSize(); y++) {
            for (int x = 0; x < gl.getSize(); x++) {
                int tile = gl.getTile(x, y);
                Rectangle r = new Rectangle(
                    offset.width + x * tileSize,
                    offset.height + y * tileSize,
                    tileSize,
                    tileSize);

                g.setColor(getTileColor(tile));
                g.fillRect(r.x, r.y, r.width, r.height);
                g.setColor(Color.BLACK);
                g.drawRect(r.x, r.y, r.width, r.height);

                drawCenterText(g, tile == 0 ? "" : BigInteger.TWO.pow(tile).toString(), r);
            }
        }
    }
}
