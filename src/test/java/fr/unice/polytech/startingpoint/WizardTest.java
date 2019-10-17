package fr.unice.polytech.startingpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class WizardTest{


    @Test
    public void playerOfWizardTest(){
        Player p1=new Player(7);
        p1.setCharacter(Assets.TheWizard);
        assertEquals(7, Assets.TheWizard.getPlayer().getId());
    }

}