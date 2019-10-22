package fr.unice.polytech.startingpoint;

class Murderer extends Role {

    Murderer(){
        super(1);
    }

    void action(Role c){
        c.murdered();
        System.out.println(toString()+" ( joueur numero "+ player.getId()+" )"+"tue le "+c.toString());
    }

    void stolen(){
        return;
    }

    void murdered(){
        return;
    }

    

    @Override
    void useSpecialPower() {
        this.action(this.player.getTargetToKill());

    }

    

}
