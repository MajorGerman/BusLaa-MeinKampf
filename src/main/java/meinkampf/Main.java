package meinkampf;

import engine.GameEngine;
import engine.IGameLogic;

public class Main {
 
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new MeinKampf();
            GameEngine gameEng = new GameEngine("GAME", 1600, 900, vSync, gameLogic);
            gameEng.run();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
