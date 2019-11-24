package fr.unice.polytech.startingpoint.role;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.board.Bank;
import fr.unice.polytech.startingpoint.player.Player;

public class MerchantTest {
    Player player;
    Bank bank;
    @BeforeEach
    void setUp(){
        player=new Player(1);
        bank=new Bank();
        bank.setBourses(List.of(player));
    }

    @Test
    void merchantTest(){
        Role merchant = new Merchant();
        player.setCharacter(merchant);
        player.playTurn();
        assertEquals(6,merchant.getPosition());
        assertEquals(player,merchant.getPlayer());
        assertEquals(1,merchant.getNumberDistrictBuildable());
        assertEquals(3,merchant.getNumberGold());
        assertEquals(3, player.getGold());
    }
}
