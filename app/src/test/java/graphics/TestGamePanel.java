package graphics;

import org.junit.*;
import logic.*;
import static org.junit.Assert.*;
import java.awt.*;

public class TestGamePanel extends GamePanel {
    public TestGamePanel() {
        super(new StandardGameLogic(4));
    }

    @Override protected void drawGame(Graphics g) { }

    @Test
    public void testGetTileColor() {
        Color last = getTileColor(0);
        for (int i = 1; i < gl.getNumberOfTiles(); i++) {
            assertNotEquals(last, getTileColor(i));
        }
    }
}
