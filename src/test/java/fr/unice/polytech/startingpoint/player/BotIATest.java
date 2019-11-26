package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.Bank;
import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.Deck;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.role.Merchant;
import fr.unice.polytech.startingpoint.role.Role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BotIATest{

    BotIA bot, anotherBot;
    District d1 = new District(3,4,DistrictColor.Religion, "quartier");
    District d2 = new District(6,6, DistrictColor.Wonder,"rue");
	Hand hand;
	Bank bank;
	Deck deck;


    @BeforeEach
	void setup(){
		bot=new BotIA(1);
		hand=new Hand();
		bank=new Bank();
		deck=new Deck();

		bot.setDeck(deck);
		bank.setBourses(List.of(bot));
	}

    @Test
    void coinsOrDistrictTest() {
 	   assertTrue(bot.coinsOrDistrict());
 	   
 	   City c= mock(City.class);
 	   when(c.getSizeOfCity()).thenReturn(7);
 	   bot.setCity(c);
 	   assertTrue(bot.coinsOrDistrict());
		
		
 	   bank.withdraw(10, bot);
 	   when(c.getSizeOfCity()).thenReturn(5);
 	   Deck d = bot.getDeck();
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
		assertEquals(d1,bot.whatToBuild(10));

		assertEquals(null,bot.whatToBuild(2));

	}

	@Test
	void isBuildingTest(){
		Role role = mock(Role.class);
		bot.setCharacter(role);
		when(role.toString()).thenReturn("Wizard");
		assertTrue(bot.isBuildingFirst());
		hand.add(d1);
		hand.add(d2);
		bot.setHand(hand);
		System.out.println(bot.getGold()+"	"+
		bot.getHand().nbBadCards(bot.getGold()));
		assertFalse(bot.isBuildingFirst());

		when(role.toString()).thenReturn("Warlord");
		assertTrue(bot.isBuildingFirst());

		when(role.toString()).thenReturn("Bishop");
		assertTrue(bot.isBuildingFirst());

	}
	
	@Test
	void isUsingLaboTest() {
		City c = mock(City.class);
		when(c.containsWonder("Laboratoire")).thenReturn(true);
		bot.setCity(c);
		
		bot.setHand(hand);
		hand.add(d1);
		hand.add(d2);
		hand.add(new District(8, 10, DistrictColor.Commerce, "rue"));
		bot.setBoard(new Board());
		bot.takeCoinsFromBank(5);
		assertFalse(hand.cardsAboveGold(bot.getGold()).isEmpty());
		
		when(c.getSizeOfCity()).thenReturn(8);
		District tmpDis = hand.highCostDistrict(bot.getGold());
		assertFalse(hand.cardsAboveGold(bot.getGold()).contains(tmpDis));
		
		int tmpDeckNb = bot.getDeck().numberOfCards();
		int tmpGold = bot.getGold();
		int tmpHandSize = bot.getHand().size();
		bot.isUsingLabo();
		
		assertEquals(tmpDeckNb + 1, bot.getDeck().numberOfCards());
		assertEquals(tmpGold + 1, bot.getGold());
		assertEquals(tmpHandSize - 1, bot.getHand().size());
	}
	
	@BeforeEach
	void setMultiPlayers() {
		anotherBot = new BotIA(2);
		bot.city.add(d1);
		
		Board b = new Board();
		anotherBot.setBoard(b);
		b.setPlayers(bot, anotherBot);
	}
	
	@Test
	void findDestroyedDistrictTest(){
		bot.deleteDistrictFromCity(d1);
		assertEquals(d1, anotherBot.findDestroyedDistrict());
	}
	
	@Test
	void isUsingGraveyardTest(){
		bot.deleteDistrictFromCity(d1);
		
		City c = mock(City.class);
		when(c.containsWonder("Cimetiere")).thenReturn(true);
		anotherBot.setCity(c);
		anotherBot.setBank(new Bank());
		anotherBot.getBank().setBourses(List.of(bot,anotherBot));
		anotherBot.takeCoinsFromBank(5);
		anotherBot.setCharacter(new Merchant());
		
		int tmpGold = anotherBot.getGold();
		int tmpHandSize = anotherBot.getHand().size();
		anotherBot.isUsingGraveyard();
		
		assertEquals(tmpGold - 1, anotherBot.getGold());
		assertEquals(tmpHandSize + 1, anotherBot.getHand().size());
		assertTrue(anotherBot.hand.toList().contains(d1));
		
		bot.city.add(d2);
		bot.deleteDistrictFromCity(d2);
		anotherBot.takeCoinsFromBank(3);
		
		tmpGold = anotherBot.getGold();
		tmpHandSize = anotherBot.getHand().size();
		anotherBot.isUsingGraveyard();
		
		assertEquals(tmpGold - 1, anotherBot.getGold());
		assertEquals(tmpHandSize +1, anotherBot.getHand().size());
		assertTrue(anotherBot.hand.toList().contains(d2));
	}
}