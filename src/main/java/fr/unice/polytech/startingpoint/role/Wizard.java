package fr.unice.polytech.startingpoint.role;

public class Wizard extends Role{

    public Wizard(){
        super(3);
    }

    private void action(){
        this.player.exchangeHands();
    }

    @Override
    public void useSpecialPower() {
        if(this.player.getTargetToExchangeHandWith()==null){
          this.player.setHand(player.getBoard().exchangeMany(this.player.getHand()));
        }
        else{
            this.action();
        }
        

    }

    
}
