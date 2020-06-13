package game;

import java.util.ArrayList;

/**
 * This class create and manage a BotPlayer
 * @author T.Desormeaux
 */
public class BotPlayer extends Player {

    public BotPlayer(String name, Team team) {
        super(name, team);
        this.pawnList = new ArrayList<>();
        configurePawn();
    }

    public void configurePawn() {
        if (this.team.equals(Team.WHITE)) {
            this.pawnList.add(new Pawn(zenPawnLocation[0], zenPawnLocation[1], Team.ZEN));
            int[][] whiteLocation = {{0, 0}, {4, 1}, {6, 1}, {2, 3}, {8, 3}, {0, 5}, {10, 5}, {8, 7}, {2, 7}, {6, 9}, {4, 9}, {10, 10}};
            for (int i = 0; i < whiteLocation.length; i++) {
                int x = whiteLocation[i][0];
                int y = whiteLocation[i][1];
                this.pawnList.add(new Pawn(x, y, Team.WHITE));
            }
        } else if (this.team.equals(Team.BLACK)) {
            this.pawnList.add(new Pawn(zenPawnLocation[0], zenPawnLocation[1], Team.ZEN));
            int[][] blackLocation = {{5, 0}, {10, 0}, {3, 2}, {7, 2}, {1, 4}, {9, 4}, {1, 6}, {9, 6}, {3, 8}, {7, 8}, {0, 10}, {5, 10}};
            for (int i = 0; i < blackLocation.length; i++) {
                int x = blackLocation[i][0];
                int y = blackLocation[i][1];
                this.pawnList.add(new Pawn(x, y, Team.BLACK));

            }
        }
    }

    public int[] choosePawn(Square[][] theGrid){
        int xBot;
        int yBot;
        do {
            xBot = (int) (Math.random() * 11);
            yBot = (int) (Math.random() * 11);
        }while (theGrid[xBot][yBot].isFree() || !theGrid[xBot][yBot].getThePawn().getTeam().equals(getTeam()) && !theGrid[xBot][yBot].getThePawn().getTeam().equals(Team.ZEN));
        return new int[]{xBot,yBot};
    }

    public boolean move(int pawnXPos, int pawnYPos, Square[][] theGrid) {
        boolean canBeMove = false;
        int i = 0;
        boolean found = false;
        while (i < this.pawnList.size() && !found) {
            if (getPawnList().get(i).getPosX() == pawnXPos && getPawnList().get(i).getPosY() == pawnYPos) {
                found = true;
                canBeMove = this.pawnList.get(i).moveBot(theGrid);
            }
            i++;
        }
        return canBeMove;
    }
}
