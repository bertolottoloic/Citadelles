package fr.unice.polytech.startingpoint.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import fr.unice.polytech.startingpoint.player.*;
import fr.unice.polytech.startingpoint.role.Role;

class ManagerTest {
	
	Manager manager = new Manager();
	Player p1 ;
    Player p2 ;
    Player p3 ;
    Player p4;

    
    ArrayList<Role> myRoles;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new BotRnd(1);
		p2 = new BotRnd(2);
		p3 = new BotRnd(3);
		p4 = new BotRnd(4);
		
	}
	
	@Test
	void testLetsPlayAndOneRound() {
		Player[] players = {p1, p2, p3, p4};
		manager.letsPlay(players);
		for(Player p : players) {
			assertNotEquals(null , p.getNextPlayer());
		}
	}

	@Test
	void testEndGame() {
		Player[] players = {p1, p2, p3, p4};
		manager.endGame(players);
		assertEquals(4, manager.winner.size());
	}
}
