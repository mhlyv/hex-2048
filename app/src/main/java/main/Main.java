package main;

import graphics.*;

public class Main {
    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.addGamePanel(new StandardGamePanel(4));
        gf.setVisible(true);
    }
}
