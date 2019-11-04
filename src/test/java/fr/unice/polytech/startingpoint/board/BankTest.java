package fr.unice.polytech.startingpoint.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
    Bank b=new Bank();

    @BeforeEach
    public void setUp() {

        b=new Bank();
       
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
        assertEquals(true,b.withdraw(7));

        assertEquals(23,b.getCurrNbCoins());

        assertEquals(false,b.withdraw(25));

        assertEquals(23,b.getCurrNbCoins());

    }

    @Test
    public void depositTest(){
        assertEquals(true, b.deposit(0));

        assertEquals(false, b.deposit(-1));

        assertEquals(false, b.deposit(1));
    }



}