package fr.unice.polytech.startingpoint.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.player.BotRnd;
import fr.unice.polytech.startingpoint.player.Player;

class GameTest {
	Player p1 = new BotRnd(1);
    Player p2 = new BotRnd(2);
    Player p3 = new BotRnd(3);
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
    void startGameTest() {
    	game.startGame();
    	for(Player p : game.getPlayers()) {
    		assertEquals(2, p.getGold());
    		assertEquals(4, p.getHand().size());
    	}
    }
}