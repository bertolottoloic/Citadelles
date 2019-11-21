package fr.unice.polytech.startingpoint.role;


public class Merchant extends Role {

    public Merchant(){
        super(6);
        this.numberGold++;
        this.setColor("commerce");
    }

    void action(Role c){
    }

    @Override
    public void useSpecialPower() {
    }

    
}
