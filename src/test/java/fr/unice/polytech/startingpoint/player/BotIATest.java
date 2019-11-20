package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.role.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BotIATest{

    BotIA bot = new BotIA(1);
    District d1 = new District(3,4,"religion", "quartier");
    District d2 = new District(6,6, "merveille","rue");
    Hand hand = new Hand();

    

   /* @Test
    void discardTest(){
        hand=new ArrayList<>();
        hand.add(d2);
        hand.add(d1);
        bot.discard(hand);
        assertEquals(d1,hand.get(0));
    }*/

   @Test
   void coinsOrDistrictTest() {
	   assertTrue(bot.coinsOrDistrict());
	   City c= mock(City.class);
	   when(c.getSizeOfCity()).thenReturn(7);
	   bot.setCity(c);
	   assertTrue(bot.coinsOrDistrict());
	   when(c.getSizeOfCity()).thenReturn(6);
	   bot.addMoney(3);
	   assertFalse(bot.coinsOrDistrict());
	   hand.add(d1);
	   hand.add(d2);
	   bot.setHand(hand);
	   assertTrue(bot.coinsOrDistrict());	   
   }
   
	@Test
	void discardWonderEffectTest() {
		ArrayList<District> dis = new ArrayList<>();
		District d1 = new District(5, 3, "religion", "quartier1");
		District d2 = new District(6, 6, "religion", "quartier2");
		District d3 = new District(2, 4, "religion", "quartier3");
		dis.add(d1);
		dis.add(d2);
		dis.add(d3);
		
		bot.setBoard(new Board());
		bot.addMoney(4);
		assertEquals(3, dis.size());
		bot.discardWonderEffect(dis);
		assertEquals(1, dis.size());
		assertEquals(2, dis.get(0).getCost());

		dis.clear();
		d1 = new District(8, 3, "religion", "quartier1");
		d2 = new District(6, 6, "religion", "quartier2");
		d3 = new District(5, 4, "religion", "quartier3");
		dis.add(d1);
		dis.add(d2);
		dis.add(d3);
		
		assertEquals(3, dis.size());
		bot.discardWonderEffect(dis);
		assertEquals(1, dis.size());
		assertTrue(dis.contains(d3));
		
		dis.clear();
		d1 = new District(3, 3, "religion", "quartier1");
		d2 = new District(1, 6, "religion", "quartier2");
		d3 = new District(2, 4, "religion", "quartier3");
		dis.add(d1);
		dis.add(d2);
		dis.add(d3);
		
		assertEquals(3, dis.size());
		bot.discardWonderEffect(dis);
		assertEquals(1, dis.size());
		assertTrue(dis.contains(d2));
	
		dis.clear();
		d1 = new District(5, 3, "religion", "quartier1");
		d2 = new District(1, 6, "religion", "quartier2");
		d3 = new District(2, 4, "religion", "quartier3");
		dis.add(d1);
		dis.add(d2);
		dis.add(d3);
		
		assertEquals(3, dis.size());
		bot.discardWonderEffect(dis);
		assertEquals(1, dis.size());
		assertTrue(dis.contains(d2));
		
		dis.clear();
		d1 = new District(5, 3, "religion", "quartier1");
		d2 = new District(1, 6, "religion", "quartier2");
		dis.add(d1);
		dis.add(d2);
		
		assertEquals(2, dis.size());
		bot.discardWonderEffect(dis);
		assertEquals(1, dis.size());
		assertTrue(dis.contains(d2));
	}

	@Test
	void targetToChooseForMurder(){
		DealRoles dealRoles = new DealRoles();
		ArrayList<Role> roles = new ArrayList<Role>(dealRoles.getRoles());
		ArrayList<Role> visible = new ArrayList<>();
		Role hidden;
		hidden = roles.remove(6);
		visible.add(roles.remove(4));
		DealRoles dr = mock(DealRoles.class);
		when(dr.getLeftRoles()).thenReturn(roles);
		when(dr.getVisible()).thenReturn(visible);
		dealRoles.readyToDistribute();
		bot.setCharacter(roles.remove(0));
		Board board = new Board();
		board.setDealRoles(dr);
		bot.setBoard(board);
		assertEquals(dealRoles.getRole(5),bot.targetToChooseForMurderer());
	}

}