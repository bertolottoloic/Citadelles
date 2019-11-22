package fr.unice.polytech.startingpoint.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.role.Bishop;
import fr.unice.polytech.startingpoint.role.King;
import fr.unice.polytech.startingpoint.role.Role;
import fr.unice.polytech.startingpoint.role.Warlord;
import fr.unice.polytech.startingpoint.role.Wizard;

class PlayerTest {

	Player player;
	Board board;
	DealRoles dl;
	
    @BeforeEach
    void setUp(){
        player = new Player(1);
        
        board = new Board();
        dl = new DealRoles();
		board.setDealRoles(dl);
        dl.readyToDistribute();
        player.setBoard(board);
    }
    
    @Test
    void testTakeCardsAtBeginning(){
    	assertEquals(0, player.getGold());
    	player.takeCardsAtBeginning();
    	assertEquals(4, player.getHand().size());
	}
    
    @Test
    void testPickNewDistrict(){
        assertEquals(0, player.getHand().size());
        player.pickNewDistrict(new District(3,3,"noblesse","Eglise"));
        assertEquals(1, player.getHand().size());
    }

    @Test
    void testAddToTheCity(){
        assertEquals(false, player.addToTheCity(new District(3,3,DistrictColor.Religion,"Eglise")));
        player.addMoney(3);
        assertEquals(true, player.addToTheCity(new District(3,3,DistrictColor.Religion,"Eglise")));
        assertEquals(false, player.addToTheCity(new District(3,3,DistrictColor.Religion,"Eglise")));
    }
    
    @Test
    public void testAddMoney() {
    	assertEquals(0, player.getGold());
    	player.addMoney(5);
    	assertEquals(5, player.getGold());
	}
    
    @Test
    void testDeleteDistrictFromCity(){
    	assertEquals(0, player.getCity().getSizeOfCity());
    	player.addMoney(3);
    	player.addToTheCity(new District(3,3,DistrictColor.Religion,"Eglise"));
    	assertEquals(1, player.getCity().getSizeOfCity());
    	player.deleteDistrictFromCity(player.getCity().getListDistricts().get(0));
    	assertEquals(0, player.getCity().getSizeOfCity());
    	
    	player.addMoney(3);
    	player.addToTheCity(new District(3,3,DistrictColor.Religion,"Eglise"));
    	player.setCharacter(new Bishop());
    	player.deleteDistrictFromCity(player.getCity().getListDistricts().get(0));
    	assertEquals(1, player.getCity().getSizeOfCity());
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
    	Role r = player.getBoard().getRole("Thief");
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
    	player.getBoard().getDealRoles().readyToDistribute();
    	player.chooseRole();
    	assertNotNull(player.getCharacter());
    }
    
    @Test
    void testReInitializeForNextTurn(){
    	player.setCharacter(new Warlord());
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
    	player.addMoney(20);//assez d'argent
    	player.addToTheCity(new District(3,3,"noblesse","Chateau"));
		assertEquals(3, player.points());
		
		player.addToTheCity(new District(2, 2, "merveille", "Poudlard"));
		player.addToTheCity(new District(2, 2, "soldatesque", "Caserne"));
		player.addToTheCity(new District(2, 2, "commerce", "Casino"));
		player.addToTheCity(new District(2, 2, "religion", "Eglise"));
		assertEquals(14, player.points());
	}
	@Test
	void testChekFinishBuilding(){
		Player p3=new Player(3);
		
		Player p2=new Player(7);
		Player p1=new Player(4);
		board.setPlayers(p3,p2,p1);
		p1.setBoard(board);
		
		assertEquals(true,p1.isFirstToFinish());
		assertEquals(false,p1.checkFinishBuilding());
	}

	@Test
	void testCheckFinishBuilding2(){
		Player p3=new Player(3);
		Player p2=new Player(7);
		Player p1=new Player(4);
		board.setPlayers(p3,p2,p1);
		p1.setBoard(board);
		City c= mock(City.class);
		when(c.getSizeOfCity()).thenReturn(9);
		p1.setCity(c);
		
		assertEquals(true,p1.checkFinishBuilding());
	}

	@Test
	void testIsFirstToFinish3(){
		Player p3=new Player(3);
		Player p2=new Player(7);
		Player p1=new Player(4);
		board.setPlayers(p3,p2,p1);
		p1.setBoard(board);
		City c= mock(City.class);
		when(c.getSizeOfCity()).thenReturn(7);
		p1.setCity(c);
		
		assertEquals(false,p1.checkFinishBuilding());
	}

	

	


    
    
}
