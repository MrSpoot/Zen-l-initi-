package game;

import java.util.ArrayList;

/**
 * This class create and manage the game
 */
public class Game {
    private Square[][] theGrid;
    private Player player1;
    private Player player2;
    private Player current;
    //private int[][] whiteLocation = {{0, 0}, {4, 1}, {6, 1}, {2, 3}, {8, 3}, {0, 5}, {10, 5}, {8, 7}, {2, 7}, {6, 9}, {4, 9}, {10, 10}};
    //private int[][] blackLocation = {{5, 0}, {10, 0}, {3, 2}, {7, 2}, {1, 4}, {9, 4}, {1, 6}, {9, 6}, {3, 8}, {7, 8}, {0, 10}, {5, 10}};

    public Game(String player1, String player2) {
        this.player1 = new HumanPlayer(player1, Team.WHITE);
        this.player2 = new BotPlayer(player2, Team.BLACK);
        this.current = this.player1;
        this.theGrid = new Square[11][11];
        update();
        start();
    }

    public Square[][] getTheGrid() {
        return theGrid;
    }

    public void start() {
        turn();
    }

    public void turn() {
        boolean isFinish = false;
        int[] lastPosZen = this.current.zenPawnLocation;
        screen();
        int i = 0;
        //TODO Enlever le i un jour
        while (i < 8 && !isFinish) {
            boolean canBeMove = false;
            while (!canBeMove) {
                int[] coord = this.current.choosePawn(this.theGrid);
                int x = coord[0];
                int y = coord[1];
                if (this.theGrid[x][y].getThePawn().getTeam().equals(Team.ZEN)) {
                    int xZenPos;
                    int yZenPos;
                    if(lastPosZen != null) {
                        do {
                            canBeMove = this.current.move(x, y, this.theGrid);
                            xZenPos = this.current.getPawnList().get(0).getPosX();
                            yZenPos = this.current.getPawnList().get(0).getPosY();
                        } while (xZenPos == lastPosZen[0] && yZenPos == lastPosZen[1]);
                    }else{
                        canBeMove = this.current.move(x, y, this.theGrid);
                        xZenPos = this.current.getPawnList().get(0).getPosX();
                        yZenPos = this.current.getPawnList().get(0).getPosY();
                    }
                    lastPosZen = new int[]{xZenPos,yZenPos};
                    changeCurrentPlayer();
                    this.current.getPawnList().get(0).setPosX(xZenPos);
                    this.current.getPawnList().get(0).setPosY(yZenPos);
                    changeCurrentPlayer();
                } else {
                    canBeMove = this.current.move(x, y, this.theGrid);
                }
            }

            checkDeathOfPawn();

            update();
            screen();
            isFinish = isFinish();
            changeCurrentPlayer();
            i++;
        }
        theEnd();

    }

    public void changeCurrentPlayer() {
        if (this.player1 == this.current) {
            this.current = this.player2;
        } else if (this.player2 == this.current) {
            this.current = this.player1;
        }

    }

    //x > 0 && y > 0 && x < this.theGrid.length && y < this.theGrid.length
    public boolean isFinish() {
        boolean isFinish = false;

        Pawn pawnInProcess;
        ArrayList<Pawn> pawnInBuffer = new ArrayList<>();
        ArrayList<Pawn> pawnProcess = new ArrayList<>();

        int i = 0;
        boolean found = false;
        while (i < this.current.getPawnList().size() && !found) {
            if (this.current.getPawnList().get(i).getIsAlive() && !this.current.getPawnList().get(i).getTeam().equals(Team.ZEN)) {
                pawnInBuffer.add(this.current.getPawnList().get(i));
                found = true;
            }
            i++;
        }
        if (!found) {
            throw new NullPointerException("Erreur : Buffer vide");
        }

        do {
            pawnInProcess = pawnInBuffer.get(0);
            pawnInBuffer.remove(0);
            int x = pawnInProcess.getPosX();
            int y = pawnInProcess.getPosY();
            System.out.println("X :" + pawnInProcess.getPosX());
            System.out.println("Y :" + pawnInProcess.getPosY());
            if (!this.theGrid[x][y].isFree()) {
                if (x > 0 && y > 0) {
                    if (!this.theGrid[x - 1][y - 1].isFree()
                            && !pawnProcess.contains(this.theGrid[x - 1][y - 1].getThePawn())
                            && !pawnInBuffer.contains(this.theGrid[x - 1][y - 1].getThePawn())
                            && (this.theGrid[x - 1][y - 1].getThePawn().getTeam().equals(this.theGrid[x][y].getThePawn().getTeam())
                            || this.theGrid[x - 1][y - 1].getThePawn().getTeam().equals(Team.ZEN))) {

                        pawnInBuffer.add(this.theGrid[x - 1][y - 1].getThePawn());
                    }
                }

                if (y > 0) {
                    if (!this.theGrid[x][y - 1].isFree()
                            && !pawnProcess.contains(this.theGrid[x][y - 1].getThePawn())
                            && !pawnInBuffer.contains(this.theGrid[x][y - 1].getThePawn())
                            && (this.theGrid[x][y - 1].getThePawn().getTeam().equals(this.theGrid[x][y].getThePawn().getTeam())
                            || this.theGrid[x][y - 1].getThePawn().getTeam().equals(Team.ZEN))) {

                        pawnInBuffer.add(this.theGrid[x][y - 1].getThePawn());
                    }
                }

                if (y > 0 && x < this.theGrid.length - 1) {
                    if (!this.theGrid[x + 1][y - 1].isFree()
                            && !pawnProcess.contains(this.theGrid[x + 1][y - 1].getThePawn())
                            && !pawnInBuffer.contains(this.theGrid[x + 1][y - 1].getThePawn())
                            && (this.theGrid[x + 1][y - 1].getThePawn().getTeam().equals(this.theGrid[x][y].getThePawn().getTeam())
                            || this.theGrid[x + 1][y - 1].getThePawn().getTeam().equals(Team.ZEN))) {

                        pawnInBuffer.add(this.theGrid[x + 1][y - 1].getThePawn());
                    }
                }

                if (x < this.theGrid.length - 1) {
                    if (!this.theGrid[x + 1][y].isFree()
                            && !pawnProcess.contains(this.theGrid[x + 1][y].getThePawn())
                            && !pawnInBuffer.contains(this.theGrid[x + 1][y].getThePawn())
                            && (this.theGrid[x + 1][y].getThePawn().getTeam().equals(this.theGrid[x][y].getThePawn().getTeam())
                            || this.theGrid[x + 1][y].getThePawn().getTeam().equals(Team.ZEN))) {

                        pawnInBuffer.add(this.theGrid[x + 1][y].getThePawn());
                    }
                }

                if (x < this.theGrid.length - 1 && y < this.theGrid.length - 1) {
                    if (!this.theGrid[x + 1][y + 1].isFree()
                            && !pawnProcess.contains(this.theGrid[x + 1][y + 1].getThePawn())
                            && !pawnInBuffer.contains(this.theGrid[x + 1][y + 1].getThePawn())
                            && (this.theGrid[x + 1][y + 1].getThePawn().getTeam().equals(this.theGrid[x][y].getThePawn().getTeam())
                            || this.theGrid[x + 1][y + 1].getThePawn().getTeam().equals(Team.ZEN))) {

                        pawnInBuffer.add(this.theGrid[x + 1][y + 1].getThePawn());
                    }
                }

                if (y < this.theGrid.length - 1) {
                    if (!this.theGrid[x][y + 1].isFree()
                            && !pawnProcess.contains(this.theGrid[x][y + 1].getThePawn())
                            && !pawnInBuffer.contains(this.theGrid[x][y + 1].getThePawn())
                            && (this.theGrid[x][y + 1].getThePawn().getTeam().equals(this.theGrid[x][y].getThePawn().getTeam())
                            || this.theGrid[x][y + 1].getThePawn().getTeam().equals(Team.ZEN))) {

                        pawnInBuffer.add(this.theGrid[x][y + 1].getThePawn());
                    }
                }

                if (x > 0 && y < this.theGrid.length - 1) {
                    if (!this.theGrid[x - 1][y + 1].isFree()
                            && !pawnProcess.contains(this.theGrid[x - 1][y + 1].getThePawn())
                            && !pawnInBuffer.contains(this.theGrid[x - 1][y + 1].getThePawn())
                            && (this.theGrid[x - 1][y + 1].getThePawn().getTeam().equals(this.theGrid[x][y].getThePawn().getTeam())
                            || this.theGrid[x - 1][y + 1].getThePawn().getTeam().equals(Team.ZEN))) {

                        pawnInBuffer.add(this.theGrid[x - 1][y + 1].getThePawn());
                    }
                }

                if (x > 0) {
                    if (!this.theGrid[x - 1][y].isFree()
                            && (this.theGrid[x - 1][y].getThePawn().getTeam().equals(this.theGrid[x][y].getThePawn().getTeam())
                            && !pawnInBuffer.contains(this.theGrid[x - 1][y].getThePawn())
                            || this.theGrid[x - 1][y].getThePawn().getTeam().equals(Team.ZEN))
                            && !pawnProcess.contains(this.theGrid[x - 1][y].getThePawn())) {

                        pawnInBuffer.add(this.theGrid[x - 1][y].getThePawn());
                    }
                }

            }
            pawnProcess.add(pawnInProcess);
        } while (pawnInBuffer.size() > 0);

        int nbPawnfound = 0;
        for (Pawn p : this.current.getPawnList()) {
            if (p.getIsAlive()) {
                nbPawnfound++;
            }
        }


        if (nbPawnfound == pawnProcess.size()) {
            isFinish = true;
        }

        System.out.println("nbPawnFound " + nbPawnfound);
        System.out.println("nbPawnProcess " + pawnProcess.size());
        return isFinish;

    }

    public void checkDeathOfPawn() {
        for (Pawn p1 : this.player1.getPawnList()) {
            for (Pawn p2 : this.player2.getPawnList()) {
                if (p1.getPosX() == p2.getPosX() && p1.getPosY() == p2.getPosY() && p1.getIsAlive() && p2.getIsAlive() && !p1.getTeam().equals(Team.ZEN) && p2.getTeam().equals(Team.ZEN)) {
                    clearDeathPawn(p1.getPosX(), p1.getPosY());
                }
            }
        }
    }

    public void clearDeathPawn(int posX, int posY) {
        changeCurrentPlayer();
        for (Pawn p : this.current.getPawnList()) {
            if (p.getPosX() == posX && p.getPosY() == posY) {
                p.setNotAlive();
            }
        }
        changeCurrentPlayer();
    }

    public void update() {
        for (int x = 0; x < this.theGrid.length; x++) {
            for (int y = 0; y < this.theGrid.length; y++) {
                int i = 0;
                boolean found = false;
                while (i < 13 && !found) {
                    if (this.player1.getPawnList().get(i).getPosX() == x && this.player1.getPawnList().get(i).getPosY() == y) {
                        found = true;
                        this.theGrid[x][y] = new Square(x, y, this.player1.getPawnList().get(i));
                    } else if (this.player2.getPawnList().get(i).getPosX() == x && this.player2.getPawnList().get(i).getPosY() == y) {
                        found = true;
                        this.theGrid[x][y] = new Square(x, y, this.player2.getPawnList().get(i));
                    }
                    i++;
                }
                if (!found) {
                    this.theGrid[x][y] = new Square(x, y);
                }
            }
        }


    }

    public void theEnd() {
        System.out.println("The end");
    }

    //Temporaire
    public void screen() {
        for (int y = 0; y < 11; y++) {
            for (int x = 0; x < 11; x++) {
                if (!this.theGrid[x][y].isFree() && this.theGrid[x][y].getThePawn().getIsAlive()) {
                    if (this.theGrid[x][y].getThePawn().getTeam().equals(Team.WHITE)) {
                        System.out.print("\033[1;37m|\033[1;37m");
                        System.out.print("\033[1;35mW\033[1;35m");
                        System.out.print("\033[1;37m|\033[1;37m");
                    } else if (this.theGrid[x][y].getThePawn().getTeam().equals(Team.BLACK)) {
                        System.out.print("\033[1;37m|\033[1;37m");
                        System.out.print("\033[1;31mB\033[1;31m");
                        System.out.print("\033[1;37m|\033[1;37m");
                    } else if (this.theGrid[x][y].getThePawn().getTeam().equals(Team.ZEN)) {
                        System.out.print("\033[1;37m|\033[1;37m");
                        System.out.print("\033[1;32mZ\033[1;32m");
                        System.out.print("\033[1;37m|\033[1;37m");
                    }
                } else {
                    System.out.print("\033[1;37m| |\033[1;37m");
                }

            }
            System.out.print("\n");
        }
    }

}
