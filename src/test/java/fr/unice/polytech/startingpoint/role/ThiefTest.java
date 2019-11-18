package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.game.DealRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.startingpoint.player.*;

import java.util.ArrayList;

public class ThiefTest {

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
    void thiefTest(){
        Role thief = board.getRole("Thief");
        Role merchant = board.getRole("Merchant");
        player.setCharacter(thief);
        target.setCharacter(merchant);
        target.setBoard(board);
        player.setTargetToRob(merchant);
        target.addMoney(4);
        thief.useSpecialPower();
        target.playTurn();
        assertEquals(player,thief.getPlayer());
        assertEquals(2,thief.getPosition());
        assertEquals(player,thief.getPlayer());
        assertEquals(4,player.getGold());
    }
}
