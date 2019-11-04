package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.game.DealRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.startingpoint.player.*;

import java.util.ArrayList;

public class WizardTest {
    Player player;
    Player target;
    Board board;
    DealRoles dealRoles;

    @BeforeEach
    void setUp(){
        player=new Player(1);
        target = new Player(2);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(target);
        dealRoles = new DealRoles(players);
        board = new Board();
        board.setDealRoles(dealRoles);
        player.setBoard(board);
        target.setBoard(board);
    }


    @Test
    void wizardTest(){
        Role wizard = board.getRole(2);
        Role merchant = board.getRole(5);
        player.setCharacter(wizard);
        target.setCharacter(merchant);
        target.takeCardsAtBeginning();
        player.setTargetToExchangeHandWith(target);
        wizard.useSpecialPower();
        assertEquals(3,wizard.getPosition());
        assertEquals(true, target.getHand().isEmpty());
        assertEquals(false, player.getHand().isEmpty());
    }
}
