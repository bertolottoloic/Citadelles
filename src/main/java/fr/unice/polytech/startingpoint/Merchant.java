package fr.unice.polytech.startingpoint;


public class Merchant extends Role {

    Merchant(){
        super(6);
        this.numberGold++;
        this.setColor("commerce");
    }

    void action(Role c){
    }

    @Override
    void useSpecialPower() {
        // TODO Auto-generated method stub
    }

    
}
