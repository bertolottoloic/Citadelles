package fr.unice.polytech.startingpoint.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.startingpoint.player.*;

public class WarlordTest {
    Player player1, player2;
    @BeforeEach
    void setUp(){
        player1=new Player(1);
        player2=new Player(2);
    }

    @Test
    void warlordTest(){
        Role warlord = new Warlord();
        assertEquals(8,warlord.getPosition());
        assertEquals(null,warlord.getPlayer());
        assertEquals(1,warlord.getNumberDistrictBuildable());
        assertEquals(2,warlord.getNumberGold());
    }
}
