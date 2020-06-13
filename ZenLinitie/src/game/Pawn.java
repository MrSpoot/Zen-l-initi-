package game;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class can create different pawn in this game
 */
public class Pawn {

    private int posX;
    private int posY;
    private Team team;
    private boolean isAlive = true;

    public Pawn(int posX, int posY, Team team) {
        this.posX = posX;
        this.posY = posY;
        this.team = team;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setNotAlive() {
        this.isAlive = false;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public boolean movePlayer(Square[][] theGrid) {
        boolean canBeMove = false;
        Scanner in = new Scanner(System.in);
        Move move = new Move(this.posX, this.posY, theGrid);
        ArrayList<String> moveList = move.checkMove();
        if (moveList.size() > 0) {
            System.out.println("Possible move for this pawn : ");
            for (int i = 0; i < moveList.size(); i++) {
                System.out.println(i + "- " + moveList.get(i));
            }
            System.out.print("Choice : ");
            int type;
            do {
                type = Integer.parseInt(in.next());
            } while (type < 0 || type >= moveList.size());
            movePawn(moveList.get(type));
            canBeMove = true;
        }
        return canBeMove;
    }


    public boolean moveBot(Square[][] theGrid) {
        boolean canBeMove = false;
        Move move = new Move(this.posX, this.posY, theGrid);
        ArrayList<String> moveList = move.checkMove();
        if(moveList.size() > 0) {
            int type;
            do {
                type = (int) (Math.random() * moveList.size());
            } while (type < 0 || type >= moveList.size());
            movePawn(moveList.get(type));
            canBeMove = true;
        }
        return canBeMove;
    }


    public void movePawn(String coordinate) {

        int newPosX = Integer.parseInt(coordinate.split(";")[0]);
        int newPosY = Integer.parseInt(coordinate.split(";")[1]);

        setPosX(newPosX);
        setPosY(newPosY);

    }
}
