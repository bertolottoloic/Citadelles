package fr.unice.polytech.startingpoint;

abstract class Role {
    protected Player player;
    protected int position;
    protected int numberDistrict = 1;
    protected int numberGold = 2;
    protected boolean isStolen = false;
    protected boolean isMurdered = false;

    Role(int position) {
        this.position = position;
    }

    Player getPlayer(){
        return this.player;
    }

    int getPosition(){
        return this.position;
    }

    int getNumberDistrict(){
        return this.numberDistrict;
    }

    int getNumberGold(){
        return this.numberGold;
    }

    void setPlayer(Player player){
        this.player = player;
    }

    void isStolen(){
        this.isStolen = true;
    }
    void isMurdered(){
        this.isMurdered = true;
    }

    boolean hasBeenMurdered(){
        return this.isMurdered;
    }

    boolean hasBeenStolen(){
        return this.isStolen;
    }

    void buildDistrict(District d){
        this.player.addToTheCity(d);
    }

    //abstract void action(Role...c);
}
