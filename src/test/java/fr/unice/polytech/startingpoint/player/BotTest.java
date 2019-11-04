package fr.unice.polytech.startingpoint.player;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.role.Warlord;

class BotTest {

	Bot b1, b2, b3, b4;
	Board board;
	
	@BeforeEach
	void setUp() throws Exception {
		b1 = new Bot(1);
		b2 = new Bot(2);
		b3 = new Bot(3);
		b4 = new Bot(4);
		board = new Board();
		DealRoles rolesDealer=new DealRoles();
		board.setDealRoles(rolesDealer);
		board.setPlayers(new ArrayList<Player>(Arrays.asList(b1, b2, b3, b4)));
		b1.setBoard(board);

		b1.setCharacter(new Warlord());
	}

	@Test
	void testSpecialMove() {
		assertNull(b1.targetToExchangeHandWith);
		assertNull(b1.targetToKill);
		assertNull(b1.targetToRob);
		//assertThrows(NullPointerException.class, ()->{
			b1.specialMove();
		//});
		assertNotNull(b1.targetToExchangeHandWith);
		assertNotNull(b1.targetToKill);
		assertNotNull(b1.targetToRob);
	}
	
	@Test
	void testAction() {
		b1.addMoney(10);
		b1.pickNewDistrict(new District(3, 2, "blue", "Église"));
		b1.pickNewDistrict(new District(2, 2, "orange", "Chateau"));
		b1.pickNewDistrict(new District(6, 2, "violet", "Pont"));
		assertTrue(b1.city.isEmpty());
		b1.action();
		assertEquals(1, b1.city.size());
		b1.action();
		assertEquals(2, b1.city.size());
		b1.action();
		assertEquals(2, b1.city.size());
		b1.getBoard().getDeck().getList().clear();
		b1.action();
		assertTrue(b1.gameOver);
	}
}
