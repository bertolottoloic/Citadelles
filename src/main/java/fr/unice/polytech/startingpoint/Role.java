package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

abstract class Role {
    protected Player player;
    protected final int position;
    protected int numberDistrictBuildable = 1;
    /**
     * Nombre de pieces qu'on peut retirer de la banque
     * n'inclut pas le nombre de pieces qu'on
     * gagne grace au matching Role.color==Disctrict.color
     */
    protected int numberGold = 2;
    /**
     * Permet de savoir si un Role est volé
     */
    protected boolean isStolen = false;
    /**
     * Permet de savoir si on est tué
     */
    protected boolean isMurdered = false;
    /**
     * Nombre de districts qu'on peut prendre dans le Deck 
     * Change pour l'Architect
     */
    protected int numberDistrictPickable=2;
    /**
     * Couleur du Role 
     * Penser à utiliser une enum Gem ou Color au lieu de String
     */
    protected String color="#";
    Role(int position) {
        this.position = position;
    }

    /**
     * ???
     * @param d
     */
    void buildDistrict(District d){
        this.player.addToTheCity(d);
    }
    /**
     * Cette méthode doit remettre les paramètres potentiellement 
     * affecté par un tour précédent à leur valeur par défaut 
     * eg isStolen,isMurdered,player ...
     */
    void reInitialize(){
        isMurdered=false;
        isStolen=false;
        player=null;
    }

    /**
     * Interface commune permettant à un Player de communiquer avec
     * son Role de façon indépendante de la nature exacte de ce personnage
     * (Murderer,Thief,Wizard ...)
     * Cette méthode va de concert avec la création dans Player d'attributs
     * permettants au Player de désigner sa cible au personnage 
     * Il suffit pour le personnage d'interroger le bon attribut avec son getter
     * eg: Murderer ---> player.getTargetToKill
     *  */

    abstract void useSpecialPower();

    public int getNumberDistrictPickable() {
        return numberDistrictPickable;
    }

    public void setNumberDistrictPickable(int numberDistrictPickable) {
        this.numberDistrictPickable = numberDistrictPickable;
    }

    
    /**
     * Le Role est chargé de collecter l'argent généré par les cités
     * Ainsi lorsqu'on aura plusieurs roles par Player
     * il suffira d'appeler les méthodes collectRentMoney de chacun
     * 
     * Le problème ne se pose pas pour les roles sans couleur parce qu'il ne 
     * correspondront à rien
     * 
     */
    public void collectRentMoney(){
        ArrayList<District> districts=this.player.getCity();

        for(District d:districts){
            if(d.getColor().equals(this.getColor())){
                if(Assets.TheBank.canWithdraw(1)){ 
                    //Que se passe t-il si il y a pas assez d'argent dans la banque?
                    this.player.takeCoinsFromBank(1);
                }
                
            }
        }

    }

    /**
     * On renvoie le nom de la classe 
     * Permet d'afficher facilement des messages pour suivre 
     * le jeu
     */
    @Override
    public String toString() {
    
        return this.getClass().getSimpleName();
    }

    /* Les getters et setters */

    Player getPlayer(){
        return this.player;
    }

    int getPosition(){
        return this.position;
    }

    int getNumberDistrictBuildable(){
        return this.numberDistrictBuildable;
    }

    int getNumberGold(){
        return this.numberGold;
    }

    void setPlayer(Player player){
        this.player = player;
    }

    void stolen(){
        this.isStolen = true;
    }
    void murdered(){
        this.isMurdered = true;
    }

    boolean isMurdered(){
        return this.isMurdered;
    }

    boolean isStolen(){
        return this.isStolen;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
