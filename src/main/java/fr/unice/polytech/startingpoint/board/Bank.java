package fr.unice.polytech.startingpoint.board;
import java.util.ArrayList;
import java.util.HashMap;
import fr.unice.polytech.startingpoint.player.*;


class Bank{
    private final int  NBCOINS=30;
    private int currNbCoins=NBCOINS;
    private HashMap<Player,Integer> bourses;

    Bank(){
        
    }

    /**
     * @param bourses the bourses to set
     */
    void setBourses(ArrayList<Player> players) {
        bourses = new HashMap<>();
        for(Player player : players){
            bourses.put(player,0);
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

    public boolean deposit(int nb,Player player){
        if((currNbCoins+nb) <=NBCOINS && nb>=0 && bourses.get(player)>nb){
            currNbCoins+=nb;
            int retireMoney = bourses.get(player);
            retireMoney -= nb;
            bourses.put(player,retireMoney);
            return true;
        }
        else{
            return false;
        }

    }

	public int getPlayerMoney(Player player) {
		return bourses.get(player);
    }
    
    public void distributeCoinsAtBeggining(){
        for(Player p : bourses.keySet()){
            bourses.put(p,2);
        }
    }
}