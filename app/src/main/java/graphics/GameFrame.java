package graphics;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private GamePanel gp;

    private void addMenu() {
        JMenuBar menubar = new JMenuBar();
        menubar.add(new JMenu("asd"));
        add(menubar, BorderLayout.NORTH);
    }

    private void removeGamePanel() {
        if (gp != null) {
            gp.setVisible(false);
            remove(gp);
        }
    }

    public void addGamePanel(GamePanel gp) {
        this.gp = gp;
        gp.setVisible(true);
        add(gp, BorderLayout.CENTER);
    }

    public GameFrame() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());
        addMenu();
    }
}
