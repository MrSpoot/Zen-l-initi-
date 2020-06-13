package game;

import java.util.ArrayList;

public class Move {

    private int posX;
    private int posY;
    private Square[][] theGrid;

    public Move(int posX, int posY,Square[][] theGrid){
        this.theGrid = theGrid;
        this.posX = posX;
        this.posY = posY;
    }

    public ArrayList<String> checkMove(){

        ArrayList<String> moveTab = new ArrayList<>();
        //Check the number of pawn in the x line of actual pawn
        int nbPawnX = 0;
        for(int x = 0; x < this.theGrid.length;x++){
            if(!this.theGrid[x][posY].isFree()){
                nbPawnX++;
            }
        }

        //Check the number of pawn in the y line of actual pawn
        int nbPawnY = 0;
        for(int y = 0; y < this.theGrid.length;y++){
            if(!this.theGrid[posX][y].isFree()){
                nbPawnY++;
            }
        }

        //Check the number of pawn in the top left to bottom right diagonal of actual pawn
        int nbPawnLR = 1;

        int i = 1;
        while(posX+i < this.theGrid.length && posY+i < this.theGrid.length){
            if(!this.theGrid[posX+i][posY+i].isFree()){
                nbPawnLR++;
            }
            i++;
        }

        i = 1;
        while(posX-i >= 0 && posY-i >= 0){
            if(!this.theGrid[posX-i][posY-i].isFree()){
                nbPawnLR++;
            }
            i++;
        }

        //Check the number of pawn in the top right to bottom left diagonal of actual pawn
        int nbPawnRL = 1;

        i = 1;
        while(posX-i >= 0 && posY+i < this.theGrid.length){
            if(!this.theGrid[posX-i][posY+i].isFree()){
                nbPawnRL++;
            }
            i++;
        }

        i = 1;
        while(posX+i < this.theGrid.length && posY-i >= 0){
            if(!this.theGrid[posX+i][posY-i].isFree()){
                nbPawnRL++;
            }
            i++;
        }


        boolean canMove = true;
        //Move x line
        if(posX-nbPawnX >= 0){
            canMove = true;
            int j = 0;
            while(j < nbPawnX && canMove){
                if(!this.theGrid[posX-j][posY].isFree() && !this.theGrid[posX-j][posY].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam()) && j < nbPawnX){
                    canMove = false;
                    System.out.println("←");
                }
                j++;
            }
            if(!this.theGrid[posX-nbPawnX][posY].isFree() && this.theGrid[posX-nbPawnX][posY].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam())){
                canMove = false;
                System.out.println("←");
            }
            if(canMove) {
                moveTab.add(posX - nbPawnX + ";" + posY);
            }
        }
        if(posX+nbPawnX < this.theGrid.length){
            canMove = true;
            int j = 0;
            while(j < nbPawnX && canMove){
                if(!this.theGrid[posX+j][posY].isFree() && !this.theGrid[posX+j][posY].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam()) && j < nbPawnX){
                    canMove = false;
                    System.out.println("→");
                }
                j++;
            }
            if(!this.theGrid[posX+nbPawnX][posY].isFree() && this.theGrid[posX+nbPawnX][posY].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam())){
                canMove = false;
                System.out.println("→");
            }
            if(canMove) {
                moveTab.add(posX + nbPawnX + ";" + posY);
            }
        }

        //Move y line
        if(posY-nbPawnY >= 0){
            canMove = true;
            int j = 0;
            while(j < nbPawnY && canMove){
                if(!this.theGrid[posX][posY-j].isFree() && !this.theGrid[posX][posY-j].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam()) && j < nbPawnY){
                    canMove = false;
                    System.out.println("↑");
                }
                j++;
            }
            if(!this.theGrid[posX][posY-nbPawnY].isFree() && this.theGrid[posX][posY-nbPawnY].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam())){
                canMove = false;
                System.out.println("↑");
            }
            if(canMove) {
                moveTab.add(posX + ";" + (posY - nbPawnY));
            }
        }
        if(posY+nbPawnY < this.theGrid.length){
            canMove = true;
            int j = 0;
            while(j < nbPawnY && canMove){
                if(!this.theGrid[posX][posY+j].isFree() && !this.theGrid[posX][posY+j].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam()) && j < nbPawnY){
                    canMove = false;
                    System.out.println("↓");
                }
                j++;
            }
            if(!this.theGrid[posX][posY+nbPawnY].isFree() && this.theGrid[posX][posY+nbPawnY].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam())){
                canMove = false;
                System.out.println("↓");
            }
            if(canMove) {
                moveTab.add(posX + ";" + (posY + nbPawnY));
            }
        }

        //Move top left to bottom right
        System.out.println(posX-nbPawnLR+" "+(posY-nbPawnLR));
        if(posX-nbPawnLR >= 0 && posY-nbPawnLR >= 0){
            canMove = true;
            int j = 0;
            while(j < nbPawnLR && canMove){

                if(!this.theGrid[posX-j][posY-j].isFree() && !this.theGrid[posX-j][posY-j].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam()) && j < nbPawnLR){
                    canMove = false;
                    System.out.println("↖");
                }
                j++;
            }
            if(!this.theGrid[posX-nbPawnLR][posY-nbPawnLR].isFree() && this.theGrid[posX-nbPawnLR][posY-nbPawnLR].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam())){
                canMove = false;
                System.out.println("↖");
            }
            if(canMove) {
                moveTab.add((posX - nbPawnLR) + ";" + (posY - nbPawnLR));
            }

        }

        if(posX+nbPawnLR < this.theGrid.length && posY+nbPawnLR < this.theGrid.length){
            canMove = true;
            int j = 0;
            while(j < nbPawnLR && canMove){
                if(!this.theGrid[posX+j][posY+j].isFree() && !this.theGrid[posX+j][posY+j].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam()) && j < nbPawnLR){
                    canMove = false;
                    System.out.println("↘1");
                }
                j++;
            }
            if(!this.theGrid[posX+nbPawnLR][posY+nbPawnLR].isFree() && this.theGrid[posX+nbPawnLR][posY+nbPawnLR].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam())){
                canMove = false;
                System.out.println("↘2");
                System.out.println(nbPawnLR+" "+nbPawnLR);
            }
            if(canMove) {
                moveTab.add((posX + nbPawnLR) + ";" + (posY + nbPawnLR));
            }
        }

        //Move top right to bottom left
        if(posX+nbPawnRL < this.theGrid.length && posY-nbPawnRL >= 0){
            canMove = true;
            int j = 0;
            while(j < nbPawnRL && canMove){
                if(!this.theGrid[posX+j][posY-j].isFree() && !this.theGrid[posX+j][posY-j].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam()) && j < nbPawnRL){
                    canMove = false;
                    System.out.println("↗");
                }
                j++;
            }
            if(!this.theGrid[posX+nbPawnRL][posY-nbPawnRL].isFree() && this.theGrid[posX+nbPawnRL][posY-nbPawnRL].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam())){
                canMove = false;
                System.out.println("↗");
            }
            if(canMove) {
                moveTab.add((posX + nbPawnRL) + ";" + (posY - nbPawnRL));
            }
        }

        if(posX-nbPawnRL >= 0 && posY+nbPawnRL < this.theGrid.length){
            canMove = true;
            int j = 0;
            while(j < nbPawnRL && canMove){
                if(!this.theGrid[posX-j][posY+j].isFree() && !this.theGrid[posX-j][posY+j].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam()) && j < nbPawnRL ){
                    canMove = false;
                    System.out.println("↙");
                }
                j++;
            }
            if(!this.theGrid[posX-nbPawnRL][posY+nbPawnRL].isFree() && this.theGrid[posX-nbPawnRL][posY+nbPawnRL].getThePawn().getTeam().equals(this.theGrid[posX][posY].getThePawn().getTeam())){
                canMove = false;
                System.out.println("↙");
            }
            if(canMove) {
                moveTab.add((posX - nbPawnRL) + ";" + (posY + nbPawnRL));
            }
        }


        return moveTab;
    }
}
