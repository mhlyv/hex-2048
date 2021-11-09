package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

import logic.GameLogic;

public abstract class GamePanel extends JPanel {
    protected GameLogic gl;
    protected Map<Integer, GameLogic.Direction> keymap;

    GamePanel(GameLogic gl) {
        super();
        setFocusable(true);
        this.gl = gl;
        keymap = new HashMap<>();

        addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) { }
            @Override public void keyReleased(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                gl.move(keymap.get(keyCode));
                repaint();
            }
        });
    }

    public void setKeyMap(Map<Integer, GameLogic.Direction> km) {
        keymap = km;
    }

    private int log2(int i) {
        int n = 0;
        while (i != 0) {
            n++;
            i >>= 1;
        }
        return n;
    }

    protected Color getTileColor(int tile) {
        // the board can have as many different tiles as the number of tiles
        // so we need one shade for each tile

        double tint = 1.0 - (double)log2(tile) / gl.getNumberOfTiles();
        Color c = new Color(255, (int)(tint*100), (int)(tint*100), (int)(255-tint*255));
        return c;
    }


    // draw text centered in rectangle
    protected void drawCenterText(Graphics g, String s, Rectangle r) {
        FontMetrics fm = getFontMetrics(getFont());
        Rectangle2D fr = fm.getStringBounds(s, g);

        int x = (int)(r.getWidth() - fr.getWidth()) / 2;
        int y = (int)(r.getHeight() - fr.getHeight()) / 2 + fm.getAscent();

        g.drawString(s, r.x + x, r.y + y);
    }

    protected abstract void drawGame(Graphics g);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawGame(g);
    }
}
