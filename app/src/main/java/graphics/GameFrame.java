package graphics;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    private GamePanel gp;
    private Map<String, Class<? extends GamePanel>> gameModes;

    private boolean save() {
        if (gp != null) {
            // try to save until success or cancel
            while (!gp.save()) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int option = fc.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selected = fc.getSelectedFile();
                    gp.setFileName(selected.getAbsolutePath());
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    private void newGame() {
        String[] modes = new String[gameModes.keySet().size()];
        gameModes.keySet().toArray(modes);
        JComboBox<String> gameModeChooser = new JComboBox<>(modes);
        JSpinner gameSizeChooser = new JSpinner(new SpinnerNumberModel(4, 1, 32, 1));

        Object[] message = { "Mode: ", gameModeChooser, "Size: ", gameSizeChooser };

        // offer to save game
        if (this.gp != null) {
            int option = JOptionPane.showConfirmDialog(null, "Save game?");
            if (option == JOptionPane.OK_OPTION) {
                if (!save()) {
                    return;
                }
            }

            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        int option = JOptionPane.showConfirmDialog(null, message, "New Game", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            removeGamePanel();

            // get the game parameters
            Class<? extends GamePanel> gamePanelClass = gameModes.get(gameModeChooser.getSelectedItem());
            int size = (int) gameSizeChooser.getValue();

            // try to create new game with given parameters
            try {
                addGamePanel(gamePanelClass.getDeclaredConstructor(Integer.class).newInstance(size));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void addMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu game = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem undo = new JMenuItem("Undo");

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gp != null) {
                    gp.gl.undo();
                    gp.repaint();
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // offer to save game
                if (GameFrame.this.gp != null) {
                    int option = JOptionPane.showConfirmDialog(null, "Save game?");
                    if (option == JOptionPane.OK_OPTION) {
                        if (!save()) {
                            return;
                        }
                    }

                    if (option == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int option = fc.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selected = fc.getSelectedFile();
                    try {
                        addGamePanel(GamePanel.read(selected));
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Failed to load game");
                    }
                }
            }
        });

        game.add(newGame);
        game.add(save);
        game.add(load);

        menubar.add(game);
        menubar.add(undo);
        setJMenuBar(menubar);
    }

    private void removeGamePanel() {
        if (gp != null) {
            gp.setVisible(false);
            remove(gp);
        }
    }

    public void addGamePanel(GamePanel gp) {
        removeGamePanel();
        this.gp = gp;
        add(gp, BorderLayout.CENTER);
        gp.setVisible(true);
        gp.repaint();
        setVisible(true);
        // ask for focus
        SwingUtilities.invokeLater(gp::requestFocus);
    }

    public GameFrame() {
        super();
        // init game modes
        gameModes = new HashMap<>();
        gameModes.put("Standard", StandardGamePanel.class);
        gameModes.put("Hexagonal", HexagonalGamePanel.class);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());
        addMenu();
    }
}
