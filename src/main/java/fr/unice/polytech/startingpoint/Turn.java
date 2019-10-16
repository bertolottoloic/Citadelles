package fr.unice.polytech.startingpoint;

class Turn{
    Player player;
    Role role;
    Deck deck;
    Turn(Player p,Deck deck){
        this.player=p;
        this.deck=deck;
        this.role=p.character;
        player.addMoney(2);
        //role.action();
        //player.build();
    }
}