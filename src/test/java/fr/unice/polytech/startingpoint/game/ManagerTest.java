package fr.unice.polytech.startingpoint.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import fr.unice.polytech.startingpoint.player.*;
import fr.unice.polytech.startingpoint.role.Role;

class ManagerTest {
	
	Manager manager = new Manager();
	Player p1 = new Bot(1);
    Player p2 = new Bot(2);
    Player p3 = new Bot(3);
    Player p4 = new Bot(4);

    Player[] players = {p1, p2, p3, p4};
    ArrayList<Role> myRoles;

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testLetsPlayAndOneRound() {
		manager.letsPlay(players);
		for(Player p : players) {
			assertNotEquals(null , p.getNextPlayer());
		}
	}

	@Test
	void testEndGame() {
		manager.endGame(players);
		assertEquals(4, manager.winner.size());
	}
}
