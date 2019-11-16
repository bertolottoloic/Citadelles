package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.District;
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
        dealRoles = new DealRoles();
        board = new Board();
        board.setDealRoles(dealRoles);
        player.setBoard(board);
        target.setBoard(board);
    }


    @Test
    void exchangeWithPlayerTest(){
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

    @Test
    void exchangeWithDeckTest(){
        Role wizard = board.getRole(2);
        player.setCharacter(wizard);
        player.takeCardsAtBeginning();
        ArrayList<District> cardsToExchange = new ArrayList<District>(player.getHand().toList());
        ArrayList<District> hand = new ArrayList<District>(player.getHand().toList());
        cardsToExchange.remove(0);
        cardsToExchange.remove(0);
        player.setCardsToExchange(cardsToExchange);
        wizard.useSpecialPower();
        assertEquals(hand.get(0),player.getHand().toList().get(0));
        assertEquals(hand.get(1),player.getHand().toList().get(1));
        assertNotSame(hand.get(2),player.getHand().toList().get(2));
        assertNotSame(hand.get(3),player.getHand().toList().get(3));
    }
}
