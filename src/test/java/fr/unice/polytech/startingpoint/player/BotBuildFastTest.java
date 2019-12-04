package fr.unice.polytech.startingpoint.player;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Bank;
import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.Deck;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.role.Merchant;
import fr.unice.polytech.startingpoint.role.Role;
import fr.unice.polytech.startingpoint.role.Thief;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BotBuildFastTest {

	BotBuildFast bot, anotherBot;
	District d1 = new District(3, 4, DistrictColor.Religion, "quartier");
	District d2 = new District(6, 6, DistrictColor.Wonder, "rue");
	Hand hand;
	Bank bank;
	Deck deck;

	MatchingProb matches;
	DealRoles dealRoles;


	@BeforeEach
	void setup() {
		bot = new BotBuildFast(1);
		hand = new Hand();
		bank = new Bank();
		deck = new Deck();
		dealRoles = new DealRoles();

		bot.setDeck(deck);
		bank.setBourses(List.of(bot));
	}

	@Test
	void coinsOrDistrictTest() {
		assertTrue(bot.coinsOrDistrict());

		bank.withdraw(10, bot);

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
	void whatToBuildTest() {

		Role role = mock(Role.class);
		when(role.toString()).thenReturn("Architect");
		bot.setCharacter(role);
		hand.add(d1);
		hand.add(d2);
		bot.setHand(hand);
		assertEquals(d1, bot.whatToBuild(10));
		assertEquals(null, bot.whatToBuild(2));


		when(role.toString()).thenReturn("Warlord");
		assertEquals(d1, bot.whatToBuild(10));
		assertEquals(null, bot.whatToBuild(2));

		District donjon =(new District(5,5,"merveille","Donjon"));
		bot.hand.add(donjon);
		assertTrue(bot.whatToBuild(5).equals(donjon));

		District Laboratoire =(new District(5,5,"merveille","Laboratoire"));
		bot.hand.remove(donjon);
		bot.hand.add(Laboratoire);
		assertTrue(bot.whatToBuild(7).equals(Laboratoire));


	}

	@Test
	void isBuildingTest() {
		Role role = mock(Role.class);
		bot.setCharacter(role);
		when(role.toString()).thenReturn("Wizard");
		assertFalse(bot.isBuildingFirst());
		hand.add(d1);
		hand.add(d2);
		bot.setHand(hand);
		System.out.println(bot.getGold() + "	" +
				bot.getHand().badCards(bot.getGold()).size());
		assertFalse(bot.isBuildingFirst());

		when(role.toString()).thenReturn("Warlord");
		assertTrue(bot.isBuildingFirst());

		when(role.toString()).thenReturn("Bishop");
		assertTrue(bot.isBuildingFirst());

	}

	@Test
	void wantsToUseFabric() {
		assertFalse(bot.wantsToUseFabric());

		bot.takeCoinsFromBank(6);
		assertTrue(bot.wantsToUseFabric());

		City c = mock(City.class);
		when(c.getSizeOfCity()).thenReturn(8);
		bot.setCity(c);

		assertFalse(bot.wantsToUseFabric());
	}

	@Test
	void isUsingLaboTest() {
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
		anotherBot = new BotBuildFast(2);
		bot.city.add(d1);

		Board b = new Board();
		anotherBot.setBoard(b);
		b.setPlayers(bot, anotherBot);
	}

	@Test
	void wantsToUseGraveyardTest() {
		Board b = mock(Board.class);
		when(b.existsGraveyardPlayer()).thenReturn(anotherBot);
		bot.setBoard(b);
		assertTrue(bot.deleteDistrictFromCity(d1));

		City c = mock(City.class);
		when(c.containsWonder("Cimetiere")).thenReturn(true);
		anotherBot.setCity(c);
		anotherBot.setBank(new Bank());
		anotherBot.getBank().setBourses(List.of(bot, anotherBot));
		anotherBot.takeCoinsFromBank(5);
		anotherBot.setCharacter(new Merchant());

		assertTrue(anotherBot.wantsToUseGraveyard(d1));
		anotherBot.isUsingGraveyard(d1);

		bot.city.add(d2);
		bot.deleteDistrictFromCity(d2);
		anotherBot.takeCoinsFromBank(3);

		assertTrue(anotherBot.wantsToUseGraveyard(d2));
	}

	@Test
	void targetToChooseForMurdererTest() {
		dealRoles = new DealRoles();
		bot.setDealRoles(dealRoles);
		bot.setCharacter(new Merchant());
		Player p = mock(Player.class);
		when(p.getId()).thenReturn(1);
		Board board = mock(Board.class);
		when(board.playerWithTheBiggestCity(bot)).thenReturn(p);
		bot.setBoard(board);
		Set<String> s = new HashSet<String>();
		s.add("Thief");
		matches = mock(MatchingProb.class);
		when(matches.possibleRolesFor(1)).thenReturn(s);
		bot.setMatches(matches);
		assertEquals(dealRoles.getRole("Thief"), bot.processWhoToKill());
	}

	@Test
	void targetToChooseForThiefTest() {
		dealRoles = new DealRoles();
		bot.setDealRoles(dealRoles);
		bot.setCharacter(new Thief());
		Player p = mock(Player.class);
		when(p.getId()).thenReturn(1);
		Board board = mock(Board.class);
		when(board.richestPlayer(bot)).thenReturn(p);
		bot.setBoard(board);
		Set<String> s = new HashSet<String>();
		s.add("Merchant");
		matches = mock(MatchingProb.class);
		when(matches.possibleRolesFor(1)).thenReturn(s);
		bot.setMatches(matches);
		assertEquals(dealRoles.getRole("Merchant"), bot.processWhoToRob());
	}

	@Test
	void processWhoToExchangeHandWithTest() {
		District d1 = new District(1, 1, "religion", "Temple");
		District d2 = new District(1, 1, "commerce", "Taverne");
		District d3 = new District(2, 2, "religion", "Eglise");
		ArrayList<District> districts = new ArrayList<>();
		bot.takeCoinsFromBank(3);
		districts.add(d1);
		districts.add(d2);
		districts.add(d3);
		hand = new Hand(districts);
		bot.setHand(hand);
		Player target = new Player(2);
		target.setDeck(deck);
		target.takeCardsAtBeginning();
		Board board = mock(Board.class);
		when(board.playerWithTheBiggestHand(bot)).thenReturn(target);
		bot.setBoard(board);
		assertEquals(target, bot.processWhoToExchangeHandWith());
		d1 = new District(4, 4, "noblesse", "Palais");
		d2 = new District(4, 4, "noblesse", "Palais");
		d3 = new District(1, 1, "religion", "Eglise");
		District d4 = new District(1, 1, "religion", "Eglise");
		districts = new ArrayList<>();
		districts.add(d1);
		districts.add(d2);
		districts.add(d3);
		districts.add(d4);
		bot.setHand(new Hand(districts));
		bot.takeCoinsFromBank(2);
		assertEquals(null, bot.processWhoToExchangeHandWith());
	}

	@Test
	void attributeProbsToPlayerTest() {

		Player p1 = new BotBuildFast(7);
		Player p2 = new BotBuildFast(2);
		Player p3 = new BotBuildFast(3);
		Player p4 = new BotBuildFast(5);
		dealRoles.readyToDistribute(4);
		Board b = new Board();

		Player[] players = {p1, p2, p3, p4};
		for (int i = 0; i < players.length - 1; i++) {
			players[i].setNextPlayer(players[i + 1]);
		}
		players[players.length - 1].setNextPlayer(players[0]);

		b.setPlayers(p1, p2, p3, p4);
		List.of(p1, p2, p3, p4).forEach(p -> p.setBoard(b));


		p1.setDealRoles(dealRoles);
		p2.setDealRoles(dealRoles);
		p3.setDealRoles(dealRoles);
		p4.setDealRoles(dealRoles);

		int nblefts = dealRoles.getLeftRoles().size();
		List.of(p1, p2, p3, p4).forEach(p -> p.chooseRole());


		assertEquals(2, dealRoles.getVisible().size());
		assertTrue(dealRoles.getLeftRoles().size() <= nblefts - 3);
		assertEquals(3, p4.getMatches().possibleRolesFor(p1.getId()).size());
		assertEquals(6, p1.getMatches().possibleRolesFor(p4.getId()).size());
		assertEquals(2, p2.getMatches().possibleRolesFor(p1.getId()).size());
	}

	@Test
	public void buildablesTest() {
		Tmp t = bot.buildables(List.of(
				new District(5, 5, DistrictColor.Commerce, "dontcare"),
				new District(1, 1, DistrictColor.Noble, "dontcare"),
				new District(5, 5, DistrictColor.Commerce, "dontcare")
		), 9);

		assertEquals(6, t.getVal());
		assertEquals(2, t.getDistricts().size());
	}

	@Test
	public void roleToOptimizeCoinsTest(){
		BotBuildFast botMock = mock(BotBuildFast.class);
		assertEquals(Optional.empty(),botMock.roleToOptimizeCoins(dealRoles.getRoles()));

		//voir beforeEach
		assertEquals("Bishop",bot.roleToOptimizeCoins(dealRoles.getRoles()).get().toString());
	}

}