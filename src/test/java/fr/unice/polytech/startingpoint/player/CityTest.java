package fr.unice.polytech.startingpoint.player;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.District;

public class CityTest {
    City city;
    @BeforeEach
    void setUp(){
        city=new City();
    }

    @Test
    void testAdd(){
        city.add(null);
        assertEquals(0, city.getSizeOfCity());
        city.add(new District(4,4,"noblesse","Palais"));
        assertEquals(1, city.getSizeOfCity());
        city.add(new District(4,4,"noblesse","Palais"));
        assertEquals(1, city.getSizeOfCity());

    }

    @Test
    void testGetTotalValue(){
        assertEquals(0, city.getTotalValue());
        city.add(new District(2,2,"noblesse","Chateau"));
        assertEquals(2, city.getTotalValue());
    }

    @Test
	void testCityContainsAllColors(){
		
		city.add(new District(2, 2, "merveille", "Poudlard"));
		city.add(new District(2, 2, "soldatesque", "Caserne"));
		city.add(new District(2, 2, "noblesse", "Chateau"));
		city.add(new District(2, 2, "religion", "Eglise"));

		assertEquals(false, city.containsAllColors());

		city.add(new District(2, 2, "commerce", "Casino"));

		assertEquals(true, city.containsAllColors());
    }
    
    @Test
	void testCityContainsAllColors2(){
		city.add(new District(2, 2, "merveille", "Poudlard"));
		city.add(new District(2, 2, "merveille", "Caserne"));
		city.add(new District(2, 2, "noblesse", "Chateau"));
		city.add(new District(2, 2, "religion", "Eglise"));

		assertEquals(false, city.containsAllColors());

		city.add(new District(2, 2, "commerce", "Casino"));
		
		assertEquals(false, city.containsAllColors());
    }
    
    @Test
    void testMostPotentiallyPayingColor(){
        assertEquals(null,city.mostPotentiallyPayingColor());
        city.add(new District(2, 2, "merveille", "Poudlard"));
        city.add(new District(2, 2, "merveille", "Caserne"));
        

        city.add(new District(2, 2, "noblesse", "Chateau"));
        city.add(new District(2, 2, "noblesse", "Palais"));
        city.add(new District(2, 2, "religion", "Eglise"));
        
        assertEquals("noblesse",city.mostPotentiallyPayingColor());
        
    }

    @Test
    void testNbOfOccurences(){
        city.add(new District(2, 2, "merveille", "Poudlard"));
		city.add(new District(2, 2, "merveille", "Caserne"));
		city.add(new District(2, 2, "noblesse", "Chateau"));
        city.add(new District(2, 2, "religion", "Eglise"));
        
        assertEquals(2, city.nbOcurrencesOf("merveille"));
        assertEquals(1, city.nbOcurrencesOf("noblesse"));
        assertEquals(0, city.nbOcurrencesOf("commerce"));
    }
    
    @Test
    void testContainsWonder() {
    	assertFalse(city.containsWonder("Caserne"));
		city.add(new District(2, 2, "merveille", "Caserne"));
		assertTrue(city.containsWonder("Caserne"));
    }

}