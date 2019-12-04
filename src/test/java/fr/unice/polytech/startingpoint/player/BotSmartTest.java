package fr.unice.polytech.startingpoint.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;

public class BotSmartTest {
    BotSmart botSmart;
    @BeforeEach
    void setUp(){
        botSmart=new BotSmart(7);
    }
    @Test
    void missingColorsTest(){
        assertEquals(5,botSmart.missingColors().size());
        botSmart.getCity().toList().add(
            new District(7,4,DistrictColor.Commerce,"something"));
        botSmart.getCity().toList().add(
            new District(7,4,DistrictColor.Warlord,"anotherthing"));
        botSmart.getCity().toList().add(
            new District(7,4,DistrictColor.Religion,"anotherthing"));

        assertEquals(true, botSmart.missingColors().contains(DistrictColor.Noble));
        assertEquals(true, botSmart.missingColors().contains(DistrictColor.Wonder));
        
        
    }
}