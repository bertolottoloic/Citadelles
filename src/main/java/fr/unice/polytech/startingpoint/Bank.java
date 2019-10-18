package fr.unice.polytech.startingpoint;


class Bank{
    private final int  NBCOINS=30;
    private int currNbCoins=30;

    Bank(){
        
    }

    public int getCurrNbCoins() {
        return currNbCoins;
    }

    public boolean canWithdraw(int desiredAmount){
        return !(currNbCoins<desiredAmount);
    }
    public boolean withdraw(int nb){

        if(currNbCoins<nb){
            return false;
        }
        else{
            currNbCoins-=nb;
            return true;
            
        }
    }

    public boolean deposit(int nb){
        if((currNbCoins+nb) <=NBCOINS){
            currNbCoins+=nb;
            return true;
        }
        else{
            return false;
        }

    }
}