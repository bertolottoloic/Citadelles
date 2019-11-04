package fr.unice.polytech.startingpoint.game;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DealRolesTest {
	
	DealRoles dealer;
	
	@BeforeEach
	void setUp() throws Exception {
		dealer = new DealRoles();
	}
	
	@Test
	void testReadyToDistribute() {
		dealer.readyToDistribute();
		assertEquals(dealer.getRoles().size(), dealer.getLeftRoles().size()+dealer.getVisible().size()+1);
		assertEquals(1, dealer.getVisible().size());
	}
	
}
