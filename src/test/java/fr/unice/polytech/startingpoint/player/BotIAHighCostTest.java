package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;


class BotIAHighCostTest{
    BotIA bot = new BotIA(1);
    District d1 = new District(3,4,"religion", "quartier");
    District d2 = new District(6,6, "merveille","rue");
    Hand hand = new Hand();
    @Test
    void coinsOrDistrictTest(){
        assertTrue(bot.coinsOrDistrict());

    }
}