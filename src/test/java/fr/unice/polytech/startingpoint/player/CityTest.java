package fr.unice.polytech.startingpoint.player;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;

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
        city.add(new District(4,4,DistrictColor.Noble,"Palais"));
        assertEquals(1, city.getSizeOfCity());
        city.add(new District(4,4,DistrictColor.Noble,"Palais"));
        assertEquals(1, city.getSizeOfCity());

    }

    @Test
    void testGetTotalValue(){
        assertEquals(0, city.netValue());
        city.add(new District(2,2,DistrictColor.Noble,"Chateau"));
        assertEquals(2, city.netValue());
    }

    @Test
	void testCityContainsAllColors(){
		
		city.add(new District(2, 2, DistrictColor.Wonder, "Poudlard"));
		city.add(new District(2, 2, DistrictColor.Warlord, "Caserne"));
		city.add(new District(2, 2, DistrictColor.Noble, "Chateau"));
		city.add(new District(2, 2, DistrictColor.Religion, "Eglise"));

		assertEquals(false, city.containsAllColors());

		city.add(new District(2, 2, DistrictColor.Commerce, "Casino"));

		assertEquals(true, city.containsAllColors());
    }

    @Test
    public void testTotalValue(){
        city.add(new District(2, 2, DistrictColor.Wonder, "Poudlard"));
		city.add(new District(2, 2, DistrictColor.Warlord, "Caserne"));
		city.add(new District(2, 2, DistrictColor.Noble, "Chateau"));
		city.add(new District(2, 2, DistrictColor.Religion, "Eglise"));

		assertEquals(false, city.containsAllColors());

		city.add(new District(2, 2, DistrictColor.Wonder, "Cour des Miracles"));

		assertEquals(true, city.containsAllColors());
    }

    @Test
    public void testTotalValue2(){
        city.add(new District(2, 2, DistrictColor.Wonder, "Poudlard"));
		city.add(new District(2, 2, DistrictColor.Warlord, "Caserne"));
		city.add(new District(2, 2, DistrictColor.Noble, "Chateau"));
		city.add(new District(2, 2, DistrictColor.Religion, "Eglise"));

		assertEquals(false, city.containsAllColors());

		city.add(new District(2, 2,"merveille-soldatesque-noblesse-religion-commerce" , "Ecole de Magie"));
        //L'ecole de magie ne compte pas pour le décompte des points
		assertEquals(false, city.containsAllColors());
    }
    
    @Test
	void testCityContainsAllColors2(){
		city.add(new District(2, 2, DistrictColor.Wonder, "Poudlard"));
		city.add(new District(2, 2, DistrictColor.Warlord, "Caserne"));
		city.add(new District(2, 2, DistrictColor.Noble, "Chateau"));
		city.add(new District(2, 2, DistrictColor.Religion, "Eglise"));

		assertEquals(false, city.containsAllColors());

		city.add(new District(2, 2, DistrictColor.Commerce, "Casino"));
		
		assertEquals(true, city.containsAllColors());
    }

    @Test
	void testCheckDateContainsAllColors(){
		city.add(new District(2, 2, DistrictColor.Wonder, "Poudlard"));
		city.add(new District(2, 2, DistrictColor.Warlord, "Caserne"));
		city.add(new District(2, 2, DistrictColor.Noble, "Chateau"));
		city.add(new District(2, 2, DistrictColor.Religion, "Eglise"));

        assertEquals(false, city.containsAllColors());
        
        var districtMock=mock(District.class);

        for (int i = 0; i < 3; i++) {
            city.nextDay();
        }
        when(districtMock.getName()).thenReturn("Cour des Miracles");
        when(districtMock.primaryColor()).thenReturn(DistrictColor.Wonder);
        when(districtMock.getBuildDate()).thenReturn(2);
        city.toList().add(districtMock);
		
		
        assertEquals(true, city.checkDateContainsAllColors());
        
        when(districtMock.getBuildDate()).thenReturn(3);
        //Quand la date de construction est égale à celle courante de la city
        //l'effet de Cour des Miracles n'est pas utilisable
        assertEquals(false, city.checkDateContainsAllColors());

        when(districtMock.getBuildDate()).thenReturn(4);
        //Quand la date de construction est superieure à celle courante de la city
        //l'effet de Cour des Miracles n'est pas utilisable
        assertEquals(false, city.checkDateContainsAllColors());
    }
    
    @Test
    void testMostPotentiallyPayingColor(){
        assertEquals(null,city.mostPotentiallyPayingColor());
        city.add(new District(2, 2, DistrictColor.Wonder, "Poudlard"));
        city.add(new District(2, 2, DistrictColor.Warlord, "Caserne"));
        

        city.add(new District(2, 2, DistrictColor.Noble, "Chateau"));
        city.add(new District(2, 2, DistrictColor.Noble, "Palais"));
        city.add(new District(2, 2, DistrictColor.Religion, "Eglise"));
        
        assertEquals(DistrictColor.Noble,city.mostPotentiallyPayingColor());
        
    }

    @Test
    void testNbOfOccurences(){
        city.add(new District(2, 2, DistrictColor.Wonder, "Poudlard"));
		city.add(new District(2, 2, DistrictColor.Wonder, "Caserne"));
		city.add(new District(2, 2, DistrictColor.Noble, "Chateau"));
        city.add(new District(2, 2, DistrictColor.Religion, "Eglise"));
        
        assertEquals(2, city.nbOcurrencesOf(DistrictColor.Wonder));
        assertEquals(1, city.nbOcurrencesOf(DistrictColor.Noble));
        assertEquals(0, city.nbOcurrencesOf(DistrictColor.Commerce));
    }
    
    @Test
    void testContainsWonder() {
    	assertFalse(city.containsWonder("Caserne"));
		city.add(new District(2, 2, DistrictColor.Wonder, "Caserne"));
		assertTrue(city.containsWonder("Caserne"));
    }

}