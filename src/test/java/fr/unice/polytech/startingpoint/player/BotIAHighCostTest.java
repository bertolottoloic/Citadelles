package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.Deck;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BotIAHighCostTest{


    BotIAHighCost bot;
    District d1 = new District(3,4,DistrictColor.Religion, "quartier");
    District d2 = new District(6,6, DistrictColor.Wonder,"rue");
    Hand hand;

    @BeforeEach
    void setup() {
        hand = new Hand();
        bot = new BotIAHighCost(1);
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

        bot.addMoney(10);
        when(c.getSizeOfCity()).thenReturn(5);
        bot.setBoard(new Board());
        Deck d = bot.getBoard().getDeck();
        d.getList().clear();
        d.getList().add(new District(3,4,DistrictColor.Religion, "random1"));
        d.getList().add(new District(3,4,DistrictColor.Religion, "random2"));
        d.getList().add(new District(3,4,DistrictColor.Religion, "random3"));
        d.getList().add(new District(3,4,DistrictColor.Religion, "random4"));
        d.getList().add(new District(3,4,DistrictColor.Religion, "random5"));
        assertTrue(bot.coinsOrDistrict());

        hand.add(d1);
        hand.add(new District(2, 2, DistrictColor.Commerce, "random6"));
        bot.setHand(hand);
        assertTrue(bot.coinsOrDistrict());
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
}