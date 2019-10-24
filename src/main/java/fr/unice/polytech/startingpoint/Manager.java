package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Manager{

    private static Crown crown=new Crown();

    /**
     * Tout ce qui est commun à un tour complet de table excluant ce qui ce passe en début de partie
     * @param players
     */
    static void oneRound(Player ...players){

        Assets.readyToDistribute();
        crown.getCrownOwner().chooseRole();

         /**
          * On remet la couronne au Roi s'il est dans la partie
          */
        
        if(Assets.TheKing.getPlayer()!=null){
             crown.goesTo(Assets.TheKing.getPlayer());
         }

         /**
          * On commence à jouer par l'Assassin
          (s'il est dans la partie)
          */
        ArrayList<Role> roles=Assets.getRoles();
        for(Role r:roles){
              Player p=r.getPlayer();
              if(p!=null){
                  p.playTurn();
              }
        }

          /**
           * On reInitialize les Roles 
           * on reInitialize les Personnages
           */

           Assets.reInitializeRoles();
           for(Player p:players){
               p.reInitializeForNextTurn();
           }


    }
    static void letsPlay(Player ...players){

        //On crée met les players en cercle
        for(int i=0;i<players.length-1;i++){
            players[i].setNextPlayer(players[i+1]);
        }
        players[players.length-1].setNextPlayer(players[0]);

        for(Player p:players){
            p.takeCardsAtBeginning();
            if(Assets.TheBank.canWithdraw(2)){
                p.takeCoinsAtBeginning();
            }
            p.setTargetToKill(Assets.TheArchitect);
            p.setTargetToRob(Assets.TheMerchant);
            
        }

        crown.goesTo(players[0]);

        /**
         * distribution des roles
         * déclenchement de la distrib des roles par le
         * joueur ayant la couronne
         */

         oneRound(players);
        
           // et on boucle super !!!!

    }
}