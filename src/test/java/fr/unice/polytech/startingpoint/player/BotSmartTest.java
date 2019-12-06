package fr.unice.polytech.startingpoint.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.startingpoint.board.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class BotSmartTest {
    BotSmart botSmart;
    Bank bank;
    @BeforeEach
    void setUp(){
        botSmart=new BotSmart(7);
        bank = new Bank();
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

	@Disabled
    @Test
    void processWhoToExchangeHandWithTest() {
        Deck deck= new Deck();
        District d1 = new District(1, 1, "religion", "Temple");
        District d2 = new District(1, 1, "commerce", "Taverne");
        District d3 = new District(2, 2, "religion", "Eglise");
        ArrayList<District> districts = new ArrayList<>();
        botSmart.takeCoinsFromBank(3);
        districts.add(d1);
        districts.add(d2);
        districts.add(d3);
        Hand hand = new Hand(districts);
        botSmart.setHand(hand);
        Player target = new Player(2);
        target.setDeck(deck);
        target.takeCardsAtBeginning();
        Board board = mock(Board.class);
        when(board.playerWithTheBiggestHand(botSmart)).thenReturn(target);
        botSmart.setBoard(board);
        assertEquals(target.toString(), botSmart.processWhoToExchangeHandWith());
        d1 = new District(4, 4, "noblesse", "Palais");
        d2 = new District(4, 4, "noblesse", "Palais");
        d3 = new District(1, 1, "religion", "Eglise");
        District d4 = new District(1, 1, "religion", "Eglise");
        districts = new ArrayList<>();
        districts.add(d1);
        districts.add(d2);
        districts.add(d3);
        districts.add(d4);
        botSmart.setHand(new Hand(districts));
        botSmart.takeCoinsFromBank(2);
        assertEquals(null, botSmart.processWhoToExchangeHandWith());
    }

    @Test //TODO
    public void attributeProbsToPlayerTest(){

    }

}