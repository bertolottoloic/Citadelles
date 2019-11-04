package fr.unice.polytech.startingpoint.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DistrictTest{
    District d1 = new District(3,3,"religion","test");
    District d2 = new District(3,4,"religion","test");
    District d3 = new District(3,4,"religion","test2");
    @Test
    public void equalsTest(){
        assertEquals(true, d1.equals(d2));
    }
    @Test
    public void notEqualsTest(){
        assertEquals(false, d1.equals(d3));
    }
}