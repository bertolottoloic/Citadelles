package fr.unice.polytech.startingpoint.player;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;

public class HandTest {
    District d1;
    District d2,d3;
    Hand hand;

    @BeforeEach
    void setUp(){
        d2 = new District(6,6, DistrictColor.Wonder,"rue");
        d1 = new District(3,4,DistrictColor.Religion, "quartier");
        d3=new District(3,4,DistrictColor.Religion, "quartier");
        hand=new Hand();
    }
    @Test
    void nbTooExpensivesDistrictsTest(){
        hand.add(d1); hand.add(d2); hand.add(d2);
        assertEquals(2,hand.nbTooExpensiveDistricts(4));
    }

    @Test
    void lowCostDistrictTest(){
       hand.add(d2);
       hand.add(d1);
       assertEquals(d1,hand.lowCostDistrict());
   }

   @Test
    void highValuedDistrictTest(){
       hand.add(d2);
       hand.add(d1);
       assertEquals(true,hand.highValuedDistrict(3));
   }
   
   @Test
   void nbBadCardsTest() {
	   hand.add(d2);
       hand.add(new District(1, 2, DistrictColor.Commerce, "cul-de-sac"));
       hand.add(new District(5, 2, DistrictColor.Warlord, "rue"));
       assertEquals(2, hand.badCards(5).size());
   }
   
   @Test
   void cardsAboveGoldTest() {
	   hand.add(d2);
       hand.add(d1);
       assertEquals(1, hand.cardsAboveGold(5).size());
   }

   @Test
    void bestColorDistrictTest(){
        assertEquals("commerce",hand.bestColorDistrict());
        hand.add(d1);
        assertEquals("religion",hand.bestColorDistrict());
        hand.add(d2);
        hand.add(new District(5, 2, DistrictColor.Wonder, "rue"));
        assertEquals("merveille",hand.bestColorDistrict());

    }

    @Test
    void lowCostDistrictForNextTurnTest(){
        hand.add(d1);
        hand.add(d2);
        District d3=new District(4,4,DistrictColor.Religion,"test");
        hand.add(d3);
        assertEquals(d2,hand.lowCostDistrictForNextTurn(10));
        assertEquals(d3,hand.lowCostDistrictForNextTurn(5));
        assertEquals(d1,hand.lowCostDistrictForNextTurn(3));
    }

    @Test
    void contentExceptStrictTest(){
        hand.add(d1);
        hand.add(d2);
        hand.add(d3);
        List<District> resultat=hand.contentExceptStrict(List.of(d1));
        assertEquals(false, resultat.contains(d1));
        assertEquals(false, resultat.contains(d3));
        assertEquals(true, resultat.contains(d2));
        
    }

    @Test
    void contentExceptTest(){
        hand.add(d1);
        hand.add(d2);
        hand.add(d3);

        List<District> resultat=hand.contentExcept(List.of(d1));
        assertEquals(true, resultat.contains(d1));
        assertEquals(true, resultat.contains(d2));
        assertEquals(2, resultat.size());
    }
        
    
    @Test
    void discardDistrictsForMultiColorsTest() {
    	assertTrue(hand.discardDistrictsForMultiColors().isEmpty());
    	hand.add(new District(4, 5, DistrictColor.Religion, "un quartier"));
    	assertTrue(hand.discardDistrictsForMultiColors().isEmpty());
    	hand.add(d1);
    	assertEquals(2, hand.discardDistrictsForMultiColors().size());
    	assertTrue(hand.discardDistrictsForMultiColors().contains(d1));
    	hand.add(d2);
    	hand.add(new District(5, 8, DistrictColor.Wonder, "un autre quartier"));
    	assertEquals(4, hand.discardDistrictsForMultiColors().size());
    	assertEquals(d1, hand.discardDistrictsForMultiColors().get(0));
    	
    }
}