package fr.unice.polytech.startingpoint.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.District;

public class HandTest {
    District d1 = new District(3,4,"religion", "quartier");
    District d2 = new District(6,6, "merveille","rue");
    Hand hand;

    @BeforeEach
    void setUp(){
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
       hand.add(new District(1, 2, "uneCouleur", "cul-de-sac"));
       hand.add(new District(5, 2, "uneCouleur", "rue"));
       assertEquals(2, hand.nbBadCards(5));
   }
   
   @Test
   void cardsAboveGoldTest() {
	   hand.add(d2);
       hand.add(d1);
       assertEquals(1, hand.cardsAboveGold(5).size());
   }
}