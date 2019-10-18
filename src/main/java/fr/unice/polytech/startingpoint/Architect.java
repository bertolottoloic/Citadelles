package fr.unice.polytech.startingpoint;

public class Architect extends Role {

    Architect(){
        super(7);
        this.numberDistrictBuildable+=2;
        this.numberDistrictPickable+=2;
    }

    void action(Role c){
    }

    @Override
    void useSpecialPower() {
        // TODO Auto-generated method stub

    }
}
