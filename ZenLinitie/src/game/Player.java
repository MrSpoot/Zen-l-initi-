package game;

import java.util.ArrayList;

/**
 * This class create and manage player info
 * @author T.Desormeaux
 */
abstract class Player {
    protected String name;
    protected Team team;
    protected ArrayList<Pawn> pawnList;
    protected int[] zenPawnLocation = {5, 5};

    public Player(String name, Team team) {
        this.pawnList = new ArrayList<>();
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public ArrayList<Pawn> getPawnList() {
        return pawnList;
    }

    abstract void configurePawn();

    abstract boolean move(int pawnXPos, int pawnYPos, Square[][] theGrid);

    abstract int[] choosePawn(Square[][] theGrid);

}
