package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Player p=new Player(1);
    Game game = new Game(p);
    String varToBeInitInSetup;

    @BeforeEach
    void setUp() {
    }

    @Test
    void dealGoldsTest() {
        game.dealGolds(3);
        assertEquals(7, p.getGold());
    }
    @Test
    void dealCountPointsTest() {
        game.countPoints(p);
        assertEquals(0, game.getPoints().get(p));
    }
    @Test
    void dealCardsTest() {
        game.dealCards(4);
        assertEquals(8, p.getHand().getMyDistricts().size());
    }
}