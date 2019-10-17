package fr.unice.polytech.startingpoint;

class Murderer extends Role {
    //Player player;

    Murderer(){
        super(1);
    }

    void action(Role c){
        //c.player=null;
    }

    @Override
    void useSpecialPower() {
        this.action(this.player.getTargetToKill());

    }

}
