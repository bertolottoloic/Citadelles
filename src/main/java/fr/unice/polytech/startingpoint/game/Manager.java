package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.Crown;
import fr.unice.polytech.startingpoint.player.Player;
import fr.unice.polytech.startingpoint.role.Role;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Manager implements PropertyChangeListener {

    private Crown crown = new Crown();
    private Board board = new Board();
    private Boolean gameOver = false;

    ArrayList<Player> winner = new ArrayList<>();

    /**
     * Tout ce qui est commun à un tour complet de table excluant ce qui ce passe en
     * début de partie
     * 
     * @param players
     */
    void oneRound(Player... players) {

        /**
         * On reInitialize les Roles on reInitialize les Personnages
         */

        Assets.reInitializeRoles();
        for (Player p : players) {
            p.reInitializeForNextTurn();
        }

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
        Iterator<Role> it = Assets.getRoles().iterator();
        while(it.hasNext() && board.getDeck().numberOfCards() != 0) {
        	Player p = it.next().getPlayer();
            if(p != null){
                System.out.println("Tour du joueur " +p.getId()+" : "+p.getCharacter());
                p.playTurn();
            }
        }
    }

    public void letsPlay(Player... players) {

        // On crée met les players en cercle
        for (int i = 0; i < players.length - 1; i++) {
            players[i].setNextPlayer(players[i + 1]);
        }
        players[players.length - 1].setNextPlayer(players[0]);
        ArrayList<Player> list = new ArrayList<>(Arrays.asList(players));
        board.setPlayers(list);

        for (Player p : players) {
            p.addPropertyChangeListener(this);
            p.setBoard(board);
            p.takeCardsAtBeginning();
            p.takeCoinsAtBeginning();

        }

        crown.goesTo(players[0]);

        /**
         * distribution des roles déclenchement de la distrib des roles par le joueur
         * ayant la couronne
         */
        while(!gameOver) {
            oneRound(players);
        }
        endGame(players);

    }

    public void printResults(Player... players) {
        String res = "";
        for (Player p : players) {
            res += "******************\n" + "Player: " + p.getId() + "\n" + "Points: " + p.points() + "\n"
                    + "******************\n";
        }
        res += "WINNER : \n" + winner.toString();
        System.out.println(res);
    }

    void endGame(Player... players) {

        int maxScore = -1;
        for (Player p : players) {
            int score = p.points();
            if (score > maxScore) {
                maxScore = score;
                winner.clear();
                winner.add(p);
            } else if (score == maxScore) {
                winner.add(p);
            }

        }
        printResults(players);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        gameOver=true;
    }
}