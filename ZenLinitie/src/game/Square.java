package game;

/**
 * This class create and manage the case of game square
 */
public class Square {

    private int x;
    private int y;
    private boolean isFree = true;
    private Pawn thePawn;

    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Square(int x, int y,Pawn thePawn){
        this.x = x;
        this.y = y;
        this.thePawn = thePawn;
        this.isFree = false;
    }

    public boolean isFree(){
        return this.isFree;
    }

    public void addPawn(Pawn pawn){
        if(this.thePawn == null) {
            this.thePawn = pawn;
            this.isFree = false;
        }
    }

    public void removePawn(){
        if(this.thePawn != null) {
            this.thePawn = null;
            this.isFree = true;
        }
    }

    public Pawn getThePawn() {
        return thePawn;
    }

    public String toString(){
        return "Square : X = "+this.x+" Y = "+this.y+" Is free : "+this.isFree+" The pawn : "+this.thePawn;
    }
}
