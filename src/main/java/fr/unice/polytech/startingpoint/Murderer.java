package fr.unice.polytech.startingpoint;

class Murderer extends Role {

    Murderer(){
        super(1);
    }

    void action(Role c){
        if(!(c instanceof Murderer)){
            c.isMurdered();
        }
    }

}
