package fr.unice.polytech.startingpoint;

class Murderer extends Role {

    Murderer(){
        super(1);
    }

    void action(Role c){
        if(!(c instanceof Murderer)){
            c.murdered();
            System.out.println(toString()+" ( joueur numero "+ player.getId()+" )"+"tue le "+c.toString());
        }
    }

    void murdered(){
        this.isMurdered = false;
    }

    @Override
    void useSpecialPower() {
        this.action(this.player.getTargetToKill());

    }

    

}
