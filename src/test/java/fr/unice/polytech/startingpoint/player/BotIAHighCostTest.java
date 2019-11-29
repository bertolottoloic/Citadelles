package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.*;
import fr.unice.polytech.startingpoint.role.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BotIAHighCostTest{


    BotIAHighCost bot, anotherBot;
    District d1 = new District(3,4,DistrictColor.Religion, "quartier");
    District d2 = new District(6,6, DistrictColor.Wonder,"rue");
    Hand hand;
    Bank bank;
    Deck deck;

    @BeforeEach
    void setup() {
        deck=new Deck();
        bank=new Bank();
        hand = new Hand();
        bot = new BotIAHighCost(1);
        bank.setBourses(List.of(bot));
        bot.setDeck(deck);
        hand.add(d1);
        hand.add(d2);
    }

    @Disabled
    @Test
    void coinsOrDistrictTest() {
        assertTrue(bot.coinsOrDistrict());
        City c= mock(City.class);

        when(c.getSizeOfCity()).thenReturn(7);
        bot.setCity(c);
        assertTrue(bot.coinsOrDistrict());

        bank.withdraw(10,bot);
        when(c.getSizeOfCity()).thenReturn(5);
        bot.setBoard(new Board());

        when(c.getSizeOfCity()).thenReturn(5);
        bot.setCity(c);
        Hand h=mock(Hand.class);
        when(h.badCards(bot.getGold()).size()).thenReturn(2);
        when(h.size()).thenReturn(2);
        bot.setHand(h);
        assertFalse(bot.coinsOrDistrict());

        Deck d = bot.getDeck();
        d.getList().clear();
        assertTrue(bot.coinsOrDistrict());

        hand.add(new District(2, 2, DistrictColor.Commerce, "random6"));
        bot.setHand(hand);
        assertTrue(bot.coinsOrDistrict());

    }

    @Test
    void bestRoleToChoose(){
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new Merchant());
        roles.add(new Warlord());
        roles.add(new Architect());
        assertEquals("Merchant",bot.bestRoleToChoose(roles,"religion").toString());

        roles.add(new Bishop());
        assertEquals("Bishop",bot.bestRoleToChoose(roles,"religion").toString());

        roles.add(new King());
        assertEquals("King",bot.bestRoleToChoose(roles,"noble").toString());

        assertEquals("Warlord",bot.bestRoleToChoose(roles,"soldatesque").toString());
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
        bot.takeCoinsFromBank(4);
        assertEquals(3, dis.size());
        bot.setCharacter(new Merchant());
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
        assertTrue(dis.contains(d1));

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
    void whatToBuild(){
        Role role = mock(Role.class);
        when(role.toString()).thenReturn("Architect");
        bot.setCharacter(role);
        hand.add(d1);
        hand.add(d2);
        bot.setHand(hand);
        assertEquals(d2,bot.whatToBuild(10));

        when(role.toString()).thenReturn("Warlord");
        assertEquals(d1,bot.whatToBuild(4));

        assertEquals(null,bot.whatToBuild(2));
    }

    
    @Test
    void isBuildingFirstTest(){
        Role role = mock(Role.class);
        bot.setCharacter(role);
        Hand handTest = new Hand();
        bot.setHand(handTest);
        when(role.toString()).thenReturn("Wizard");
        assertTrue(bot.isBuildingFirst());

        bot.setHand(hand);
        when(role.toString()).thenReturn("Wizard");
        assertFalse(bot.isBuildingFirst());

        when(role.toString()).thenReturn("Architect");
        bot.takeCoinsFromBank(10);
        assertFalse(bot.isBuildingFirst());

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
		District tmpDis = hand.lowCostDistrict();
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
		anotherBot = new BotIAHighCost(2);
		bot.city.add(d2);
		
		Board b = new Board();
		anotherBot.setBoard(b);
		b.setPlayers(bot, anotherBot);
	}

    @Test
	void findDestroyedDistrictTest(){
		bot.deleteDistrictFromCity(d2);
		assertEquals(d2, anotherBot.findDestroyedDistrict());
	}
	
	@Test
	void isUsingGraveyardTest(){
		bot.deleteDistrictFromCity(d2);
		
		City c = mock(City.class);
		when(c.containsWonder("Cimetiere")).thenReturn(true);
		anotherBot.setCity(c);
		anotherBot.setBank(new Bank());
		anotherBot.getBank().setBourses(List.of(bot,anotherBot));
		anotherBot.takeCoinsFromBank(7);
		anotherBot.setCharacter(new Merchant());
		
		int tmpGold = anotherBot.getGold();
		int tmpHandSize = anotherBot.getHand().size();
		anotherBot.isUsingGraveyard();
		
		assertEquals(tmpGold - 1, anotherBot.getGold());
		assertEquals(tmpHandSize + 1, anotherBot.getHand().size());
		assertTrue(anotherBot.hand.toList().contains(d2));
		
		bot.city.add(d1);
		bot.deleteDistrictFromCity(d1);
		anotherBot.takeCoinsFromBank(3);
		
		tmpGold = anotherBot.getGold();
		tmpHandSize = anotherBot.getHand().size();
		anotherBot.isUsingGraveyard();
		
		assertEquals(tmpGold, anotherBot.getGold());
		assertEquals(tmpHandSize, anotherBot.getHand().size());
		assertFalse(anotherBot.hand.toList().contains(d1));
    }
    
    @Test
    void isUsingFabricTest(){
        Player p=new BotIAHighCost(5);
        assertEquals(false, p.isUsingFabric());
    }
}