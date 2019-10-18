package fr.unice.polytech.startingpoint;

class Murderer extends Role {

    Murderer(){
        super(1);
    }

    void action(Role c){
        if(!c.equals(Assets.TheMurderer)){
            c.isMurdered();
            System.out.println(toString()+" ( joueur numero "+ player.getId()+" )"+"tue le "+c.toString());
        }
    }

    @Override
    void useSpecialPower() {
        this.action(this.player.getTargetToKill());

    }

    

}
