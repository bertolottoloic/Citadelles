package fr.unice.polytech.startingpoint.board;

import java.util.HashMap;
import java.util.List;

import fr.unice.polytech.startingpoint.player.Player;


public class Bank{
    private final int  NBCOINS=30;
    private int currNbCoins=NBCOINS;
    private HashMap<Player,Integer> bourses=new HashMap<>();

    public Bank(){
        
    }

    /**
     * @param players 
     */
    public void setBourses(List<Player> players) {
        bourses = new HashMap<>();
        for(Player player : players){
            bourses.put(player,0);
            player.setBank(this);
        }
    }

    public int getCurrNbCoins() {
        return currNbCoins;
    }

    public boolean canWithdraw(int desiredAmount){
        if(desiredAmount<0){
            return false;
        }
        else{
            return !(currNbCoins<desiredAmount);
        }
        
    }
    public boolean withdraw(int nb, Player player){

        if(currNbCoins < nb || nb < 0){
            return false;
        }
        else{
            currNbCoins-=nb;
            int addMoney = bourses.get(player);
            addMoney+=nb;
            bourses.put(player,addMoney);
            return true; 
        }
    }

    /**
     * Fonction pour enlever l'argent du compte du joueur
     * @param nb
     * @param player
     * @return
     */
    public boolean deposit(int nb,Player player){
        if((currNbCoins+nb) <=NBCOINS && nb>=0 && bourses.get(player)>=nb){
            currNbCoins+=nb;
            bourses.computeIfPresent(player, (k,v)->v-nb);
            return true;
        }
        else{
            return false;
        }

    }

	public int getPlayerMoney(Player player) {
		return bourses.get(player);
    }
    /**
	 * En tout début de partie chacun prend deux pièces avant 
	 * même de prendre son personnage
	 * D'où cette méthode
	 */
    public void distributeCoinsAtBeggining(){
        for(Player p : bourses.keySet()){
            bourses.put(p,2);
        }
    }

    public void transferFromTo(Player from,Player to){
        bourses.computeIfPresent(to, (k,v)->v+bourses.get(from));
        bourses.put(from, 0);
    }



}