package fr.unice.polytech.startingpoint.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.player.Bot;
import fr.unice.polytech.startingpoint.player.Player;
import fr.unice.polytech.startingpoint.role.Role;

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
