package fr.unice.polytech.startingpoint.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DistrictTest{

    District d1 ;
    District d2;
    District d3 ;
    @BeforeEach
    public void setUp(){
        d1 = new District(3,3,DistrictColor.Religion,"test");
        d2 = new District(3,4,DistrictColor.Religion,"test");
        d3 = new District(3,4,DistrictColor.Religion,"test2");
    }
    
    @Test
    public void equalsTest(){
        assertEquals(true, d1.equals(d2));
    }
    @Test
    public void notEqualsTest(){
        assertEquals(false, d1.equals(d3));
    }

    @Test
    public void hasColorTest(){
        assertEquals(true, d1.hasColor(DistrictColor.Religion));
        assertEquals(false, d1.hasColor(DistrictColor.Commerce)); 
    }

    @Test
    public void hasColorTestMulticolor(){
        District d=new District(4,4,
            "merveille-religion-commerce","Polytech");

        assertEquals(true, d.hasColor(DistrictColor.Religion));
        assertEquals(true, d.hasColor(DistrictColor.Commerce));
        assertEquals(true, d.hasColor(DistrictColor.Wonder));
        assertEquals(false, d.hasColor(DistrictColor.Warlord));
    }
}