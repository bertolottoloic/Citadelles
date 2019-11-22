package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.Deck;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.*;

import fr.unice.polytech.startingpoint.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BotIAHighCostTest{


    BotIAHighCost bot;
    District d1 = new District(3,4,"religion", "quartier");
    District d2 = new District(6,6, "merveille","rue");
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

        when(c.getSizeOfCity()).thenReturn(5);
        bot.setCity(c);
        Hand h=mock(Hand.class);
        when(h.nbBadCards(10)).thenReturn(1);
        when(h.size()).thenReturn(1);
        bot.setHand(h);
        assertFalse(bot.coinsOrDistrict());

        Deck d = bot.getBoard().getDeck();
        d.getList().clear();
        assertTrue(bot.coinsOrDistrict());

        hand.add(new District(2, 2, "unecouleur", "random6"));
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

        assertEquals("Warlord",bot.bestRoleToChoose(roles,"militaire").toString());
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
        bot.addMoney(4);
        assertEquals(3, dis.size());
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
    void bestColorDistrictTest(){
        Hand handTest= new Hand();
        bot.setHand(handTest);
        assertEquals("commerce",bot.bestColorDistrict());
        handTest.add(d1);
        assertEquals("religion",bot.bestColorDistrict());
        handTest.add(d2);
        assertEquals("merveille",bot.bestColorDistrict());

    }
    @Test
    void isBuildingFirstTest(){
        Role role = mock(Role.class);
        bot.setCharacter(role);
        Hand handTest = new Hand();
        bot.setHand(handTest);
        when(role.toString()).thenReturn("Wizard");
        assertTrue(bot.isUsingPowerFirst());

        bot.setHand(hand);
        when(role.toString()).thenReturn("Wizard");
        assertFalse(bot.isUsingPowerFirst());

        when(role.toString()).thenReturn("Architect");
        bot.addMoney(10);
        assertTrue(bot.isUsingPowerFirst());

        when(role.toString()).thenReturn("Bishop");
        assertFalse(bot.isUsingPowerFirst());

    }

}