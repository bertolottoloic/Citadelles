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
	DealRoles dealer;
	ArrayList<Role> myRoles;
	
	@BeforeEach
	void setUp() throws Exception {
		dealer = new DealRoles();
		myRoles = dealer.getRoles();
	}
	
	@Test
	void testReadyToDistribute() {
		for (Player p : players) {
            p.reInitializeForNextTurn();
        }
		dealer.readyToDistribute();
		assertEquals(dealer.getRoles().size(), dealer.getLeftRoles().size()+dealer.getVisible().size()+1);
		assertEquals(1, dealer.getVisible().size());
	}
	
	/*@Test
	void testDistributeRoles() {
		for (Player p : players) {
            p.reInitializeForNextTurn();
        }
		Player[] myPlayers = {p1, p2, p3};
		for (int i = 0; i < myPlayers.length - 1; i++) {
			myPlayers[i].setNextPlayer(myPlayers[i + 1]);
        }
		myPlayers[myPlayers.length - 1].setNextPlayer(myPlayers[0]);
		dealer.readyToDistribute();
		
		Crown c = new Crown();
		c.goesTo(p2);
		dealer.distributeRoles(c);
		for (Player p : players) {
			assertNotNull(p.getCharacter());
		}
	}*/
	
	@Test
	void reInitializeRoles(){
		for(Role r : dealer.getRoles()) {
			r.setPlayer(new Player(6));
		}
		for(Role r : dealer.getRoles()) {
			assertNotNull(r.getPlayer());
		}
		dealer.reInitializeRoles();
		for(Role r : dealer.getRoles()) {
			assertNull(r.getPlayer());
		}
	}
}
