package fr.unice.polytech.startingpoint.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Crown;
import fr.unice.polytech.startingpoint.player.*;
import fr.unice.polytech.startingpoint.role.*;

class DealRolesTest {
	
	Player p1 = new Bot(1);
    Player p2 = new Bot(2);
    Player p3 = new Bot(3);
    ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(p1, p2, p3));
	DealRoles dealer = new DealRoles(players);
	ArrayList<Role> myRoles;
	
	@BeforeEach
	void setUp() throws Exception {
		myRoles = new ArrayList<Role>(Arrays.asList(new Wizard(), new King(), new Thief(), new King(), new Merchant()));
	
	}

	@Test
	void testSelectRole() {
		assertEquals(5, myRoles.size());
		Role tmp = myRoles.get(0);
		dealer.selectRole(p1, myRoles);
		assertEquals(tmp, p1.getCharacter());
		assertEquals(p1, tmp.getPlayer());
		assertEquals(4, myRoles.size());
	}
	
	@Test
	void testReadyToDistribute() {
		for (Player p : players) {
            p.reInitializeForNextTurn();
        }
		dealer.readyToDistribute(myRoles);
		assertEquals((myRoles.size()/2) + myRoles.size()%2, dealer.getLeftRoles().size());
		assertEquals(1, dealer.getVisible().size());
	}
	
	@Test
	void testDistributeRoles() {
		for (Player p : players) {
            p.reInitializeForNextTurn();
        }
		Player[] myPlayers = {p1, p2, p3};
		for (int i = 0; i < myPlayers.length - 1; i++) {
			myPlayers[i].setNextPlayer(myPlayers[i + 1]);
        }
		myPlayers[myPlayers.length - 1].setNextPlayer(myPlayers[0]);
		dealer.readyToDistribute(myRoles);
		Crown c = new Crown();
		c.goesTo(p2);
		dealer.distributeRoles(c);
		for (Player p : players) {
			assertNotEquals(null , p.getCharacter());
		}
	}
}
