package fr.unice.polytech.startingpoint.role;


import fr.unice.polytech.startingpoint.board.Crown;

public class King extends Role {

    public King(){
        super(4);
        this.setColor("noblesse");
    }

    void action(Crown c){
        c.goesTo(this.player);
    }

    

    @Override
    public void useSpecialPower() {
        // TODO Auto-generated method stub

    }
}
