package fr.unice.polytech.startingpoint.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.startingpoint.player.*;

public class RoleTest {
    Player player;
    @BeforeEach
    void setUp(){
        player=new Player(1);
    }

    

    @Test
    void murdererTest(){
        Role murderer = new Murderer();

        assertEquals(1,murderer.getPosition());
        assertEquals(null,murderer.getPlayer());
        assertEquals(1,murderer.getNumberDistrictBuildable());
        assertEquals(2,murderer.getNumberGold());
    }

    @Test
    void thiefTest(){
        Role thief = new Thief();
        player.setCharacter(thief);

        assertEquals(player,thief.getPlayer());
        assertEquals(2,thief.getPosition());
        assertEquals(player,thief.getPlayer());
        
    }

    @Test
    void wizardTest(){
        Role wizard = new Wizard();
        assertEquals(3,wizard.getPosition());
        
        
    }

    @Test
    void kingTest(){
        Role king = new King();
        assertEquals(4,king.getPosition());
    }

    @Test
    void bishopTest(){
        Role bishop = new Bishop();
        assertEquals(5,bishop.getPosition());
        assertEquals(null,bishop.getPlayer());
        assertEquals(1,bishop.getNumberDistrictBuildable());
        assertEquals(2,bishop.getNumberGold());
    }

    @Test
    void merchantTest(){
        Role merchant = new Merchant();
        assertEquals(6,merchant.getPosition());
        assertEquals(null,merchant.getPlayer());
        assertEquals(1,merchant.getNumberDistrictBuildable());
        assertEquals(3,merchant.getNumberGold());
    }

    @Test
    void architectTest(){
        Role architect = new Architect();
        assertEquals(7,architect.getPosition());
        assertEquals(null,architect.getPlayer());
        assertEquals(3,architect.getNumberDistrictBuildable());
        assertEquals(2,architect.getNumberGold());
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
