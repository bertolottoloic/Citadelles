package fr.unice.polytech.startingpoint.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Bank;
import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.Deck;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.role.Merchant;
import fr.unice.polytech.startingpoint.role.Role;


class BotSpenderTest {


    BotSpender bot, anotherBot;
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
        bot = new BotSpender(1);
        bank.setBourses(List.of(bot));
        bot.setDeck(deck);
        hand.add(d1);
        hand.add(d2);
    }

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
        ArrayList<District> badCards = new ArrayList<>();
        badCards.add(new District(1, 1, "religion", "Temple"));
        badCards.add(new District(1, 1, "religion", "Temple"));
        when(c.getSizeOfCity()).thenReturn(5);
        bot.setCity(c);
        Hand h=mock(Hand.class);
        when(h.toList()).thenReturn(badCards);
        when(h.size()).thenReturn(2);
        bot.setHand(h);
        assertFalse(bot.coinsOrDistrict());

        Deck d = bot.getDeck();
        d.getList().clear();
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
        Hand handTest = mock(Hand.class);
        bot.setHand(handTest);
        when(role.toString()).thenReturn("Wizard");
        assertTrue(bot.isBuildingFirst());
        District d1 = new District(2,2,"religion","Temple");
        District d2 = new District(2,2,"religion","Temple");
        ArrayList<District> cards = new ArrayList<>();
        cards.add(d1);
        cards.add(d2);
        when(handTest.toList()).thenReturn(cards);
        bot.setHand(handTest);
        when(role.toString()).thenReturn("Wizard");
        assertFalse(bot.isBuildingFirst());

        when(role.toString()).thenReturn("Architect");
        bot.takeCoinsFromBank(10);
        assertFalse(bot.isBuildingFirst());

        when(role.toString()).thenReturn("Bishop");
        assertTrue(bot.isBuildingFirst());

    }
    
	@Test
	void wantsToUseFabric() {
		assertFalse(bot.wantsToUseFabric());
		
		bot.takeCoinsFromBank(9);
		assertTrue(bot.wantsToUseFabric());
		
		Hand h = mock(Hand.class);
		when(h.highValuedDistrict(bot.getGold()-3)).thenReturn(true);
		bot.setHand(h);
		
		assertFalse(bot.wantsToUseFabric());
	}
    
    @Test
	void wantsToUseLaboTest() {
		assertEquals(Optional.empty(), bot.wantsToUseLabo());
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
		
		bot.reInitializeForNextTurn();
		bot.bank.deposit(5,bot);
		hand.add(new District(8, 5, DistrictColor.Noble, "pas picked"));
		bot.isUsingLabo();
		
		assertEquals(tmpDeckNb + 1, bot.getDeck().numberOfCards());
		assertEquals(tmpGold - 4, bot.getGold());
		assertEquals(tmpHandSize, bot.getHand().size());
	}
    
    @BeforeEach
	void setMultiPlayers() {
		anotherBot = new BotSpender(2);
		bot.city.add(d2);
		
		Board b = new Board();
		anotherBot.setBoard(b);
		b.setPlayers(bot, anotherBot);
	}

    @Test
    public void badCardsTest(){
        District d = new District(2,2,"noblesse","Palais");
        ArrayList<District> card = new ArrayList<>();
        card.add(d);
        Hand h = mock(Hand.class);
        when(h.toList()).thenReturn(card);
        bot.setHand(h);
        assertEquals(d,bot.badCards().get(0));
    }

	@Test
	void wantsToUseGraveyardTest(){	
		Board b = mock(Board.class);
		when(b.existsGraveyardPlayer()).thenReturn(anotherBot);
		bot.setBoard(b);
		assertFalse(bot.deleteDistrictFromCity(d1));

		City c = mock(City.class);
		when(c.containsWonder("Cimetiere")).thenReturn(true);
		anotherBot.setCity(c);
		anotherBot.setBank(new Bank());
		anotherBot.getBank().setBourses(List.of(bot,anotherBot));
		anotherBot.takeCoinsFromBank(7);
		anotherBot.setCharacter(new Merchant());
		
		assertTrue(anotherBot.wantsToUseGraveyard(d2));
		anotherBot.isUsingGraveyard(d2);
		
		bot.city.add(d1);
		bot.deleteDistrictFromCity(d1);
		anotherBot.takeCoinsFromBank(3);

		assertFalse(anotherBot.wantsToUseGraveyard(d1));
	}
}