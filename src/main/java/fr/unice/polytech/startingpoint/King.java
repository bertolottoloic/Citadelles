package fr.unice.polytech.startingpoint;


public class King extends Role{

    King(){
        super(4);
        this.setColor("noblesse");
    }

    void action(Crown c){
        c.goesTo(this.player);
    }

    @Override
    void useSpecialPower() {
        // TODO Auto-generated method stub

    }
}
