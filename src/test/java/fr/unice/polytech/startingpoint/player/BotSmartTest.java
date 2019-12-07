package fr.unice.polytech.startingpoint.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Bank;
import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.Deck;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.role.Architect;
import fr.unice.polytech.startingpoint.role.Bishop;
import fr.unice.polytech.startingpoint.role.King;
import fr.unice.polytech.startingpoint.role.Merchant;
import fr.unice.polytech.startingpoint.role.Role;
import fr.unice.polytech.startingpoint.role.Warlord;

public class BotSmartTest {
    BotSmart botSmart;
    Bank bank;
    DealRoles dealRoles;
    @BeforeEach
    void setUp(){
        botSmart=new BotSmart(7);
        bank = new Bank();
        dealRoles = new DealRoles();
        bank.setBourses(botSmart);
    }
    @Test
    void missingColorsTest(){
        assertEquals(5,botSmart.missingColors().size());
        botSmart.getCity().toList().add(
            new District(7,4,DistrictColor.Commerce,"something"));
        botSmart.getCity().toList().add(
            new District(7,4,DistrictColor.Warlord,"anotherthing"));
        botSmart.getCity().toList().add(
            new District(7,4,DistrictColor.Religion,"anotherthing"));

        assertEquals(true, botSmart.missingColors().contains(DistrictColor.Noble));
        assertEquals(true, botSmart.missingColors().contains(DistrictColor.Wonder));
        
        
    }

    @Test
    public void coinsOrDistrict(){
       botSmart.takeCoinsFromBank(4);
       assertTrue(botSmart.coinsOrDistrict());
       botSmart.takeCoinsFromBank(4);
       assertFalse(botSmart.coinsOrDistrict());
    }

    @Test
	public void buildablesTest(){
		Tmp t=botSmart.buildables(List.of(
		new District(5, 5, DistrictColor.Commerce, "dontcare"),
		new District(1, 1, DistrictColor.Noble, "dontcare"),
		new District(5, 5, DistrictColor.Commerce, "dontcare")
		),9);

		assertEquals(6,t.getVal());
		assertEquals(2, t.getDistricts().size());
	}

    @Test
    void processWhoToExchangeHandWithTest() {
        Deck deck= new Deck();
        District d1 = new District(1, 1, "religion", "Temple");
        District d2 = new District(1, 1, "commerce", "Taverne");
        ArrayList<District> districts = new ArrayList<>();
        Bank b = mock(Bank.class);
        when(b.getPlayerMoney(botSmart)).thenReturn(3);
        botSmart.setBank(b);
        districts.add(d1);
        districts.add(d2);
        Hand hand = new Hand(districts);
        botSmart.setHand(hand);
        Player target = new Player(2);
        target.setDeck(deck);
        target.takeCardsAtBeginning();
        Board board = mock(Board.class);
        when(board.playerWithTheBiggestHand(botSmart)).thenReturn(target);
        botSmart.setBoard(board);
        assertEquals(target, botSmart.processWhoToExchangeHandWith());
        d1 = new District(4, 4, "noblesse", "Palais");
        d2 = new District(4, 4, "noblesse", "Palais");
        District d3 = new District(1, 1, "religion", "Eglise");
        District d4 = new District(1, 1, "religion", "Eglise");
        districts = new ArrayList<>();
        districts.add(d1);
        districts.add(d2);
        districts.add(d3);
        districts.add(d4);
        botSmart.setHand(new Hand(districts));
        when(b.getPlayerMoney(botSmart)).thenReturn(5);
        assertEquals(null, botSmart.processWhoToExchangeHandWith());
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
	void bestRoleToChoose(){
		ArrayList<Role> roles = new ArrayList<>();
		roles.add(new Merchant());
		roles.add(new Warlord());

		assertEquals("Merchant",botSmart.bestRoleToChoose(roles,"religion").toString());

		roles.add(new Bishop());
		assertEquals("Bishop",botSmart.bestRoleToChoose(roles,"religion").toString());

		roles.add(new King());
		assertEquals("King",botSmart.bestRoleToChoose(roles,"noble").toString());

		assertEquals("Warlord",botSmart.bestRoleToChoose(roles,"soldatesque").toString());
		roles.add(new Architect());
		assertEquals("Architect",botSmart.bestRoleToChoose(roles,"religion").toString());
	}


}