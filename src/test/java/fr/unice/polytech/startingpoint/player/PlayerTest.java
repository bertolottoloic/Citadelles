package fr.unice.polytech.startingpoint.player;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.*;

class PlayerTest {

	Player player;
    @BeforeEach
    void setUp(){
        player=new Player(1);
        player.setBoard(new Board());
    }
    
    @Test
    void testTakeCardsAtBeginning(){
    	assertEquals(0, player.getGold());
    	player.takeCardsAtBeginning();
    	assertEquals(4, player.getHand().size());
	}
    
    @Test
    void testTakeCoinsAtBeginning(){
    	assertEquals(0, player.getGold());
    	player.takeCoinsAtBeginning();
    	assertEquals(2, player.getGold());
    }
    
    @Test
    void testPickNewDistrict(){
        assertEquals(0, player.getHand().size());
        player.pickNewDistrict(new District(3,3,"noblesse","Eglise"));
        assertEquals(1, player.getHand().size());
    }

    @Test
    void testAddToTheCity(){
        assertEquals(false, player.addToTheCity(new District(3,3,"noblesse","Eglise")));
        player.addMoney(3);
        assertEquals(true, player.addToTheCity(new District(3,3,"noblesse","Eglise")));
        assertEquals(false, player.addToTheCity(new District(3,3,"noblesse","Eglise")));
    }
    
    @Test
    public void testAddMoney() {
    	assertEquals(0, player.getGold());
    	player.addMoney(5);
    	assertEquals(5, player.getGold());
	}
    
    @Test
    void testDeleteDistrictFromCity(){
    	assertEquals(0, player.getCity().size());
    	player.addMoney(3);
    	player.addToTheCity(new District(3,3,"noblesse","Eglise"));
    	assertEquals(1, player.getCity().size());
    	player.deleteDistrictFromCity(player.getCity().get(0));
    	assertEquals(0, player.getCity().size());
    	
    	player.addMoney(3);
    	player.addToTheCity(new District(3,3,"noblesse","Eglise"));
    	player.setCharacter(new Bishop());
    	player.deleteDistrictFromCity(player.getCity().get(0));
    	assertEquals(1, player.getCity().size());
	}
    
    @Test
    void testTakeCoinsFromBank(){
    	assertEquals(0, player.getGold());
		player.takeCoinsFromBank(3);
		assertEquals(3, player.getGold());
		player.takeCoinsFromBank(-2);
		assertEquals(3, player.getGold());
	}
    
    @Test
	void testExchangeHands() {
    	Player p2 = new Player(3);
    	p2.pickNewDistrict(new District(3,3,"noblesse","Eglise"));
    	player.setTargetToExchangeHandWith(p2);
    	assertEquals(0, player.getHand().size());
    	assertEquals(1, p2.getHand().size());
    	player.exchangeHands();
    	assertEquals(1, player.getHand().size());
    	assertEquals(0, p2.getHand().size());
	}
    
    @Test
    void testSurrenderToThief(){
    	player.addMoney(6);
    	
    	Player p2 = new Player(3);
    	Role r = player.getBoard().getRole(1);
    	r.setPlayer(p2);
    	
    	assertEquals(6, player.getGold());
    	assertEquals(0, r.getPlayer().getGold());
    	player.surrenderToThief();
    	assertEquals(0, player.getGold());
    	assertEquals(6, r.getPlayer().getGold());
    }

    @Test
    void testChooseRole(){
    	assertEquals(null, player.getCharacter());
    	player.chooseRole();
    	assertTrue(player.getCharacter() != null);
    }
    
    @Test
    void testReInitializeForNextTurn(){
    	player.chooseRole();
		player.setTargetToDestroyDistrict(new Player(2));
		player.setTargetToExchangeHandWith(new Player(3));
		player.setTargetToKill(new Bishop());
		player.setTargetToRob(new Wizard());
		
		player.reInitializeForNextTurn();
		assertNull(player.getCharacter());
		assertNull(player.getTargetToDestroyDistrict());
		assertNull(player.getTargetToExchangeHandWith());
		assertNull(player.getTargetToKill());
		assertNull(player.getTargetToRob());
    }
    
    @Test
    void testCollectMoneyFromDistricts() {
    	player.setCharacter(new King());
    	player.addMoney(5);
    	player.addToTheCity(new District(3,3,"noblesse","Chateau"));
    	
    	assertEquals(2, player.getGold());
    	player.collectMoneyFromDistricts();   	
    	assertEquals(3, player.getGold());
    }
    
    //Update when counting system will change
    @Test
    void testPoints() {
    	assertEquals(0, player.points());
    	player.addMoney(5);
    	player.addToTheCity(new District(3,3,"noblesse","Chateau"));
    	assertEquals(3, player.points());
    }
    
    
}
