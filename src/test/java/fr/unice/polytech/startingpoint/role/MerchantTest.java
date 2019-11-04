package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.startingpoint.player.*;

public class MerchantTest {
    Player player;
    Board board;
    @BeforeEach
    void setUp(){
        player=new Player(1);
        board = new Board();
        player.setBoard(board);
    }

    @Test
    void merchantTest(){
        Role merchant = new Merchant();
        player.setCharacter(merchant);
        player.playTurn();
        assertEquals(6,merchant.getPosition());
        assertEquals(player,merchant.getPlayer());
        assertEquals(1,merchant.getNumberDistrictBuildable());
        assertEquals(3,merchant.getNumberGold());
        assertEquals(3, player.getGold());
    }
}
