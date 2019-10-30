package fr.unice.polytech.startingpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.player.Player;

public class PlayerTest {

    Player player;
    @BeforeEach
    void setUp(){
        player=new Player(1);
        player.setBoard(new Board());
    }
    
    @Test
    void testPickNewDistrict(){
        assertEquals(0, player.getHand().size());
        player.pickNewDistrict(new District(3,3,"noblesse","Eglise"));

        assertEquals(1, player.getHand().size());

    }

    @Test
    void testAddToTheCity(){
        assertEquals(false, player.addToTheCity(new District(3,3,"noblesse","Eglise")));
        player.addMoney(3);
        assertEquals(true, player.addToTheCity(new District(3,3,"noblesse","Eglise")));
    }

    

    
}