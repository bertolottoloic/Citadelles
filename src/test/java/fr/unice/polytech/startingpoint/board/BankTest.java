package fr.unice.polytech.startingpoint.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.startingpoint.player.BotIA;



public class BankTest {
    Bank b;
    BotIA p;
    BotIA p2;

    @BeforeEach
    public void setUp() {

        b=new Bank();
        p=new BotIA(1);
        p2=new BotIA(2);
        b.setBourses(List.of(p,p2));
       
    }

    @Test
    public void testNbCoins(){
        assertEquals(30,b.getCurrNbCoins());
    }
    
    @Test
   public void canWithdrawTest(){
       
        assertEquals(false,b.canWithdraw(31));
        assertEquals(true,b.canWithdraw(0));
        assertEquals(false,b.canWithdraw(-1));
    }

    @Test
   public void withdrawTest(){
        assertEquals(true,b.withdraw(7,p));

        assertEquals(23,b.getCurrNbCoins());

        assertEquals(false,b.withdraw(25,p));

        assertEquals(23,b.getCurrNbCoins());

    }

    @Test
    public void depositTest(){
        assertEquals(true, b.deposit(0,p));

        assertEquals(false, b.deposit(-1,p));

        assertEquals(false, b.deposit(1,p));
    }

    @Test
    public void transferFromToTest(){
        b.withdraw(7, p);
        assertEquals(0, p2.getGold());
        assertEquals(7, p.getGold());
        b.transferFromTo(p, p2);
        assertEquals(7, p2.getGold());
        assertEquals(0, p.getGold());
    }



}