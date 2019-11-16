package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BotIATest{

    BotIA bot = new BotIA(1);
    District d1 = new District(3,4,"religion", "quartier");
    District d2 = new District(6,6, "merveille","rue");
    ArrayList<District> hand;

    @Test
    void nbTooExpensivesDistrictsTest(){
        hand = new ArrayList<>();
        hand.add(d1); hand.add(d2); hand.add(d2);
        assertEquals(2,bot.nbTooExpensivesDistricts(hand,4));
    }

   /* @Test
    void discardTest(){
        hand=new ArrayList<>();
        hand.add(d2);
        hand.add(d1);
        bot.discard(hand);
        assertEquals(d1,hand.get(0));
    }*/

   @Test
    void lowCostDistrictTest(){
       hand=new ArrayList<>();
       hand.add(d2);
       hand.add(d1);
       assertEquals(d1,bot.lowCostDistrict(hand));
   }

   @Test
    void highValuedDistrictTest(){
       hand=new ArrayList<>();
       hand.add(d2);
       hand.add(d1);
       assertEquals(true,bot.highValuedDistrict(hand,3));
   }
}