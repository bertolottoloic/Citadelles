package fr.unice.polytech.startingpoint;


abstract class Role {
    protected Player player;
    protected final int position;
    protected int numberDistrict = 1;
    protected int numberGold = 2;

    Role(int position) {
        this.position=position;
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

    void buildDistrict(District d){
        this.player.addToTheCity(d);
    }

    abstract void useSpecialPower();

    public void setPlayer(Player player) {
        this.player = player;
    }

    

}
