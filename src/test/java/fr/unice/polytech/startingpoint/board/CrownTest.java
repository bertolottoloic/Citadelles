package fr.unice.polytech.startingpoint.board;


import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.unice.polytech.startingpoint.player.Player;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class CrownTest{
    Crown crown = new Crown();
    Player player=new Player(1);


    @Test
    public void goesToTest(){
        crown.goesTo(player);
        assertEquals(player, crown.getCrownOwner());
    }

}