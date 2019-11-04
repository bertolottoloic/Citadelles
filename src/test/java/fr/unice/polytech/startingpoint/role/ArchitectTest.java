package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.startingpoint.player.*;

public class ArchitectTest {
    Player player;
    Board board;
    @BeforeEach
    void setUp(){
        player=new Player(1);
        board = new Board();
        player.setBoard(board);
    }

    @Test
    void architectTest(){
        Role architect = new Architect();
        player.setCharacter(architect);
        architect.useSpecialPower();
        assertEquals(7,architect.getPosition());
        assertEquals(player,architect.getPlayer());
        assertEquals(3,architect.getNumberDistrictBuildable());
        assertEquals(2,architect.getNumberGold());
        assertEquals(false, player.getHand().isEmpty());
        assertEquals(2,player.getHand().size());
    }
}
