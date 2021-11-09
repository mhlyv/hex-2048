package graphics;

import javax.swing.*;
import java.awt.*;
import logic.GameLogic;

public abstract class GamePanel extends JPanel {
    protected GameLogic gl;

    GamePanel(GameLogic gl) {
        this.gl = gl;
    }

    protected abstract void drawGame(Graphics g);

    @Override
    public void paint(Graphics g) {
        System.out.println("paint");
        super.paint(g);
        drawGame(g);
    }
}
