package fr.unice.polytech.startingpoint;

class Turn{
    Player player;
    Role role;
    Turn(Player p){
        this.player=p;
        this.role=p.getCharacter();
        player.addMoney(2);
        //role.action();
        //player.build();
    }
}