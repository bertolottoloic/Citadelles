package fr.unice.polytech.startingpoint.role;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.unice.polytech.startingpoint.game.Assets;
import fr.unice.polytech.startingpoint.player.Player;
import org.junit.Test;

public class WizardTest{


    @Test
    public void playerOfWizardTest(){
        Player p1=new Player(7);
        p1.setCharacter(Assets.TheWizard);
        assertEquals(7, Assets.TheWizard.getPlayer().getId());
    }

}