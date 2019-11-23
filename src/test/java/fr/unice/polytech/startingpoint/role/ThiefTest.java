package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.Bank;
import fr.unice.polytech.startingpoint.board.Board;
import fr.unice.polytech.startingpoint.game.DealRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.startingpoint.player.*;

import java.util.ArrayList;

public class ThiefTest {

    Player player;
    Player target;
    Board board;
    DealRoles dealRoles;
    Bank bank;

    @BeforeEach
    void setUp(){
        player=new Player(1);
        target = new Player(2);
        bank=new Bank();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(target);
        dealRoles = new DealRoles();
        bank.setBourses(players);
        player.setDealRoles(dealRoles);
        target.setDealRoles(dealRoles);
    }

    @Test
    void thiefTest(){
        Role thief = dealRoles.getRole("Thief");
        Role merchant = dealRoles.getRole("Merchant");
        player.setCharacter(thief);
        target.setCharacter(merchant);

        player.setTargetToRob(merchant);
        bank.withdraw(4, player);
        thief.useSpecialPower();
        target.playTurn();
        assertEquals(player,thief.getPlayer());
        assertEquals(2,thief.getPosition());
        assertEquals(player,thief.getPlayer());
        assertEquals(4,player.getGold());
    }
}
