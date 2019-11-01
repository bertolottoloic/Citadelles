package fr.unice.polytech.startingpoint.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.player.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
	Player p1 = new Bot(1);
    Player p2 = new Bot(2);
    Player p3 = new Bot(3);
    Game game = new Game(p1, p2, p3);
    
    @BeforeEach
    void setUp() {
    	Board board = new Board();
    	game.setBoard(board);
    	
    }

    @Test
    void dealGoldsTest() {
    	game.dealGolds(2);
    	for(Player p : game.getPlayers()) {
    		assertEquals(2, p.getGold());
    	}
    }
    
    @Test
    void dealCardsTest() {
    	game.dealCards(5);
    	for(Player p : game.getPlayers()) {
    		assertEquals(5, p.getHand().size());
    	}
    }
    
    @Test
    void countPointsTest() {    	
    	game.countPoints(p1);
    	assertEquals(0, game.getPoints().get(p1));
    	p2.getCity().add(new District(1, 3, "blue", "unDistrict"));
    	game.countPoints(p2);
    	assertEquals(3, game.getPoints().get(p2));    	
    }    
}