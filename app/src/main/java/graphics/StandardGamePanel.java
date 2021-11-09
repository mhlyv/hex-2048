package graphics;

import java.awt.*;
import logic.StandardGameLogic;

public class StandardGamePanel extends GamePanel {
    public StandardGamePanel(int size) {
        super(new StandardGameLogic(size));
    }


    @Override
    protected void DrawGame(Graphics g) {
        Dimension size = getSize();
        int tileSize = Math.min(size.height, size.width) / gl.GetSize();
        Dimension offset = new Dimension(
            (size.width  - tileSize * gl.GetSize()) / 2,
            (size.height - tileSize * gl.GetSize()) / 2);
        
        System.out.println(size);
        System.out.println(tileSize);
        System.out.println(offset);

        for (int y = 0; y < gl.GetSize(); y++) {
            for (int x = 0; x < gl.GetSize(); x++) {
                g.drawRect(
                    offset.width + x * tileSize,
                    offset.height + y * tileSize,
                    tileSize,
                    tileSize);
            }
        }
    }
}
