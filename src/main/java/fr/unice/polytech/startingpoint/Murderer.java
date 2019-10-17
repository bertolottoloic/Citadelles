package fr.unice.polytech.startingpoint;

class Murderer extends Role {

    Murderer(){
        super(1);
    }

    void action(Role c){
        if(!c.equals(Assets.TheMurderer)){
            c.isMurdered();
        }
    }

    @Override
    void useSpecialPower() {
        this.action(this.player.getTargetToKill());

    }

}
