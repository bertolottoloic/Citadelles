package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.Deck;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BotIATest{

    BotIA bot;
    District d1 = new District(3,4,"religion", "quartier");
    District d2 = new District(6,6, "merveille","rue");
    Hand hand;


    @BeforeEach
	void setup(){
		bot=new BotIA(1);
		hand=new Hand();
	}

    @Test
    void coinsOrDistrictTest() {
 	   assertTrue(bot.coinsOrDistrict());
 	   
 	   City c= mock(City.class);
 	   when(c.getSizeOfCity()).thenReturn(7);
 	   bot.setCity(c);
 	   assertTrue(bot.coinsOrDistrict());
 	   
 	   bot.addMoney(10);
 	   when(c.getSizeOfCity()).thenReturn(5);
 	   bot.setBoard(new Board());
 	   Deck d = bot.getBoard().getDeck();
 	   d.getList().clear();
 	   d.getList().add(new District(3,4,"religion", "random1"));
 	   d.getList().add(new District(3,4,"religion", "random2"));
 	   d.getList().add(new District(3,4,"religion", "random3"));
 	   d.getList().add(new District(3,4,"religion", "random4"));
 	   d.getList().add(new District(3,4,"religion", "random5"));
 	   assertTrue(bot.coinsOrDistrict());
 	   
 	   hand.add(d1);
 	   hand.add(new District(2, 2, "unecouleur", "random6"));
 	   bot.setHand(hand);
 	   assertFalse(bot.coinsOrDistrict());
    }
   
	@Test
	void discardTest() {
		ArrayList<District> dis = new ArrayList<>();
		District d1 = new District(5, 3, "religion", "quartier1");
		District d2 = new District(6, 6, "religion", "quartier2");
		District d3 = new District(2, 4, "religion", "quartier3");
		dis.add(d1);
		dis.add(d2);
		dis.add(d3);
		
		bot.setBoard(new Board());
		bot.addMoney(4);
		assertEquals(3, dis.size());
		bot.discard(dis);
		assertEquals(1, dis.size());
		assertEquals(2, dis.get(0).getCost());

		dis.clear();
		d1 = new District(8, 3, "religion", "quartier1");
		d2 = new District(6, 6, "religion", "quartier2");
		d3 = new District(5, 4, "religion", "quartier3");
		dis.add(d1);
		dis.add(d2);
		dis.add(d3);
		
		assertEquals(3, dis.size());
		bot.discard(dis);
		assertEquals(1, dis.size());
		assertTrue(dis.contains(d3));
		
		dis.clear();
		d1 = new District(3, 3, "religion", "quartier1");
		d2 = new District(1, 6, "religion", "quartier2");
		d3 = new District(2, 4, "religion", "quartier3");
		dis.add(d1);
		dis.add(d2);
		dis.add(d3);
		
		assertEquals(3, dis.size());
		bot.discard(dis);
		assertEquals(1, dis.size());
		assertTrue(dis.contains(d2));
	
		dis.clear();
		d1 = new District(5, 3, "religion", "quartier1");
		d2 = new District(1, 6, "religion", "quartier2");
		d3 = new District(2, 4, "religion", "quartier3");
		dis.add(d1);
		dis.add(d2);
		dis.add(d3);
		
		assertEquals(3, dis.size());
		bot.discard(dis);
		assertEquals(1, dis.size());
		assertTrue(dis.contains(d2));
		
		dis.clear();
		d1 = new District(5, 3, "religion", "quartier1");
		d2 = new District(1, 6, "religion", "quartier2");
		dis.add(d1);
		dis.add(d2);
		
		assertEquals(2, dis.size());
		bot.discard(dis);
		assertEquals(1, dis.size());
		assertTrue(dis.contains(d2));
	}

	/*@Test
	void targetToChooseForMurder(){
		DealRoles dealRoles = new DealRoles();
		ArrayList<Role> roles = new ArrayList<Role>(dealRoles.getRoles());
		ArrayList<Role> visible = new ArrayList<>();
		Role hidden;
		hidden = roles.remove(6);
		visible.add(roles.remove(4));
		DealRoles dr = mock(DealRoles.class);
		when(dr.getLeftRoles()).thenReturn(roles);
		when(dr.getVisible()).thenReturn(visible);
		dealRoles.readyToDistribute();
		bot.setCharacter(roles.remove(0));
		Board board = new Board();
		board.setDealRoles(dr);
		bot.setBoard(board);
		assertEquals(dealRoles.getRole(5),bot.targetToChooseForMurderer());
	}*/

	@Test
	void whatToBuildTest(){

    	Role role = mock(Role.class);
    	when(role.toString()).thenReturn("Architect");
    	bot.setCharacter(role);
    	hand.add(d1);
    	hand.add(d2);
    	bot.setHand(hand);
    	assertEquals(d1,bot.whatToBuild(10));
		assertEquals(null,bot.whatToBuild(2));


		when(role.toString()).thenReturn("Warlord");
		assertEquals(d2,bot.whatToBuild(10));

		assertEquals(null,bot.whatToBuild(2));

	}

	@Test
	void isBuildingFirstTest(){
		Role role = mock(Role.class);
		bot.setCharacter(role);
		hand.add(d1);
		hand.add(d2);
		bot.setHand(hand);

		when(role.toString()).thenReturn("Wizard");
		assertTrue(bot.isBuildingFirst());

		when(role.toString()).thenReturn("Warlord");
		bot.addMoney(10);
		assertTrue(bot.isBuildingFirst());

		when(role.toString()).thenReturn("Bishop");
		assertFalse(bot.isBuildingFirst());

	}
	
	@Test
	void isUsingFabricTest() {
		assertFalse(bot.isUsingFabric());

		bot.setBoard(new Board());
		bot.takeCoinsFromBank(5);
		assertTrue(bot.isUsingFabric());
				
		hand.add(d2);
		bot.setHand(hand);
		assertTrue(bot.isUsingFabric());
		
		City c = mock(City.class);
		when(c.getSizeOfCity()).thenReturn(8);
		bot.setCity(c);
		assertFalse(bot.isUsingFabric());
	}
	
	@Test
	void isUsingLaboTest() {
		City c = mock(City.class);
		when(c.containsWonder("Laboratoire")).thenReturn(true);
		bot.setCity(c);
		
		bot.setHand(hand);
		hand.add(d1);
		hand.add(d2);
		hand.add(new District(8, 10, "exemple", "rue"));
		bot.setBoard(new Board());
		bot.takeCoinsFromBank(5);
		assertFalse(hand.cardsAboveGold(bot.getGold()).isEmpty());
		
		when(c.getSizeOfCity()).thenReturn(8);
		District tmpDis = hand.highCostDistrict(bot.getGold());
		assertFalse(hand.cardsAboveGold(bot.getGold()).contains(tmpDis));
		
		int tmpDeckNb = bot.getBoard().numberOfCardsOfDeck();
		int tmpGold = bot.getGold();
		int tmpHandSize = bot.getHand().size();
		bot.isUsingLabo();
		
		assertEquals(tmpDeckNb + 1, bot.getBoard().numberOfCardsOfDeck());
		assertEquals(tmpGold + 1, bot.getGold());
		assertEquals(tmpHandSize - 1, bot.getHand().size());
	}
}