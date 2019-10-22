package fr.unice.polytech.startingpoint;

class Turn{
    Player player;
    Role role;
    Turn(Player p){
        this.player=p;
        this.role=p.getCharacter();
        if(role.isStolen()){
            int gold = player.getGold();
            player.addMoney(-1*gold);
            Player PlayerThief=Assets.TheThief.getPlayer();
            PlayerThief.addMoney(gold);
        }
        if(!role.isMurdered()) {
            //player.choice1();
            //player.choice2();
        }
    }


}