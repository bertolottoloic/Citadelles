package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.game.DealRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.startingpoint.player.*;

import java.util.ArrayList;

public class RoleTest {
    Player player;
    Player target;
    Board board;
    DealRoles dealRoles;

    @BeforeEach
    void setUp(){
        player=new Player(1);
        target = new Player(2);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(target);
        dealRoles = new DealRoles();
        board = new Board();
        board.setDealRoles(dealRoles);
    }

    

    @Test
    void murdererTest(){
        Role murderer = board.getRole(0);
        Role merchant = board.getRole(5);
        player.setCharacter(murderer);
        target.setCharacter(merchant);
        player.setTargetToKill(merchant);
        player.getCharacter().useSpecialPower();
        assertEquals(1,board.getRole(0).getPosition());
        assertEquals(player,murderer.getPlayer());
        assertEquals(1,murderer.getNumberDistrictBuildable());
        assertEquals(2,murderer.getNumberGold());
        assertEquals(true, merchant.isMurdered());
    }

    @Test
    void thiefTest(){
        Role thief = board.getRole(1);
        Role merchant = board.getRole(5);
        player.setCharacter(thief);
        target.setCharacter(merchant);
        target.setBoard(board);
        player.setTargetToRob(merchant);
        target.addMoney(4);
        thief.useSpecialPower();
        target.playTurn();
        assertEquals(player,thief.getPlayer());
        assertEquals(2,thief.getPosition());
        assertEquals(player,thief.getPlayer());
        assertEquals(4,player.getGold());
        
    }

    @Test
    void wizardTest(){
        Role wizard = new Wizard();
        assertEquals(3,wizard.getPosition());
        
        
    }

    @Test
    void kingTest(){
        Role king = new King();
        assertEquals(4,king.getPosition());
    }

    @Test
    void bishopTest(){
        Role bishop = new Bishop();
        assertEquals(5,bishop.getPosition());
        assertEquals(null,bishop.getPlayer());
        assertEquals(1,bishop.getNumberDistrictBuildable());
        assertEquals(2,bishop.getNumberGold());
    }

    @Test
    void merchantTest(){
        Role merchant = new Merchant();
        assertEquals(6,merchant.getPosition());
        assertEquals(null,merchant.getPlayer());
        assertEquals(1,merchant.getNumberDistrictBuildable());
        assertEquals(3,merchant.getNumberGold());
    }

    @Test
    void architectTest(){
        Role architect = new Architect();
        assertEquals(7,architect.getPosition());
        assertEquals(null,architect.getPlayer());
        assertEquals(3,architect.getNumberDistrictBuildable());
        assertEquals(2,architect.getNumberGold());
    }

    @Test
    void warlordTest(){
        Role warlord = new Warlord();
        assertEquals(8,warlord.getPosition());
        assertEquals(null,warlord.getPlayer());
        assertEquals(1,warlord.getNumberDistrictBuildable());
        assertEquals(2,warlord.getNumberGold());
    }
}
