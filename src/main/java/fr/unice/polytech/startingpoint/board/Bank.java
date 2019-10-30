package fr.unice.polytech.startingpoint.board;


class Bank{
    private final int  NBCOINS=30;
    private int currNbCoins=NBCOINS;

    Bank(){
        
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
    public boolean withdraw(int nb){

        if(currNbCoins < nb || nb < 0){
            return false;
        }
        else{
            currNbCoins-=nb;
            return true;
            
        }
    }

    public boolean deposit(int nb){
        if((currNbCoins+nb) <=NBCOINS && nb>=0){
            currNbCoins+=nb;
            return true;
        }
        else{
            return false;
        }

    }
}