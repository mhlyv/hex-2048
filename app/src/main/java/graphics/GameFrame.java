package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameFrame extends JFrame {
    private GamePanel gp;
    private Map<String, Class<? extends GamePanel>> gameModes;

    private void addMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New");

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] modes = new String[gameModes.keySet().size()];
                gameModes.keySet().toArray(modes);
                JComboBox<String> gameModeChooser = new JComboBox<>(modes);
                JSpinner gameSizeChooser = new JSpinner(new SpinnerNumberModel(4, 1, 32, 1));

                Object[] message = {
                    "Mode: ", gameModeChooser,
                    "Size: ", gameSizeChooser
                };

                int option = JOptionPane.showConfirmDialog(null, message, "New Game", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    // TODO: save current game
                    removeGamePanel();
                    Class<? extends GamePanel> gamePanelClass = gameModes.get(gameModeChooser.getSelectedItem());
                    int size = (int)gameSizeChooser.getValue();

                    try {
                        addGamePanel(gamePanelClass.getDeclaredConstructor(Integer.class).newInstance(size));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        game.add(newGame);

        menubar.add(game);

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
        // init game modes
        gameModes = new HashMap<>();
        gameModes.put("Standard", StandardGamePanel.class);
        // gameModes.put("Hexagonal", HexagonalGamePanel.class);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());
        addMenu();
    }
}
