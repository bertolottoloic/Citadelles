package fr.unice.polytech.startingpoint.player;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import fr.unice.polytech.startingpoint.board.*;
import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.role.Merchant;

class BotIAMultiColorsTest {

	BotIAMultiColors bot, anotherBot;
    District d1 = new District(3,4,DistrictColor.Religion, "quartier");
    District d2 = new District(6,6, DistrictColor.Wonder,"rue");
	Hand hand;
	Bank bank;
	Deck deck;
	
	MatchingProb matches;
	DealRoles dealRoles;


    @BeforeEach
	void setup(){
		bot=new BotIAMultiColors(1);
		hand=new Hand();
		bank=new Bank();
		deck=new Deck();
		dealRoles=new DealRoles();

		bot.setDeck(deck);
		bank.setBourses(List.of(bot));
	}

	@Test
	void wantToUseLaboTest() {
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
		anotherBot = new BotIAMultiColors(2);
		bot.city.add(d1);
		
		Board b = new Board();
		anotherBot.setBoard(b);
		b.setPlayers(bot, anotherBot);
	}
	
	@Test
	void wantsToUseGraveyardTest(){
		Board b = mock(Board.class);
		when(b.existsGraveyardPlayer()).thenReturn(anotherBot);
		bot.setBoard(b);
		assertTrue(bot.deleteDistrictFromCity(d1));
		
		City c = mock(City.class);
		when(c.containsWonder("Cimetiere")).thenReturn(true);
		anotherBot.setCity(c);
		anotherBot.setBank(new Bank());
		anotherBot.getBank().setBourses(List.of(bot,anotherBot));
		anotherBot.takeCoinsFromBank(5);
		anotherBot.setCharacter(new Merchant());
		
		assertTrue(anotherBot.wantsToUseGraveyard(d1));
		anotherBot.isUsingGraveyard(d1);

		bot.city.add(d2);
		bot.deleteDistrictFromCity(d2);
		anotherBot.takeCoinsFromBank(3);
		
		assertTrue(anotherBot.wantsToUseGraveyard(d2));
		
		anotherBot.hand.add(new District(2, 2, DistrictColor.Noble, "un quartier"));
		District tmp = new District(1, 1, DistrictColor.Noble, "un autre quartier");
		bot.city.add(tmp);
		
		anotherBot.takeCoinsFromBank(2);
		bot.deleteDistrictFromCity(tmp);
		
		assertFalse(anotherBot.wantsToUseGraveyard(tmp));
	}

}
