package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.game.DealRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.startingpoint.player.*;

import java.util.ArrayList;

public class WarlordTest {
    Player player;
    Player target;
    Board board;
    DealRoles dealRoles;

    @BeforeEach
    void setUp(){
        player=new Player(1);
        target=new Player(2);
        board = new Board();
        dealRoles = new DealRoles();
        board.setDealRoles(dealRoles);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(target);
        player.setBoard(board);
        target.setBoard(board);
    }

    @Test
    void warlordTest(){
        Role warlord = board.getRole(7);
        player.setCharacter(warlord);
        target.takeCardsAtBeginning();
        player.takeCoinsFromBank(10);
        target.takeCoinsFromBank(10);
        target.addToTheCity(target.getHand().get(0));
        ArrayList<District> city = target.getCity().getListDistricts();
        player.setTargetToDestroyDistrict(target);
        player.setDistrictToDestroy(target.getCity().getListDistricts().get(0));
        player.specialMove();
        assertEquals(8,warlord.getPosition());
        assertEquals(player,warlord.getPlayer());
        assertEquals(1,warlord.getNumberDistrictBuildable());
        assertEquals(0,city.size());
        assertEquals(0,target.getCity().getListDistricts().size());
    }
}
