package fr.unice.polytech.startingpoint.board;

import fr.unice.polytech.startingpoint.board.Deck;
import fr.unice.polytech.startingpoint.board.District;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class  DeckTest{
    Deck d;
    ArrayList<District> toAdd=new ArrayList<>();
    
    @BeforeEach
    void setUp() {
        d=new Deck();
        
        
    }

    @Test
    void putBackOneTest() {
        int oldLenght=d.numberOfCards();
        d.putbackOne(new District(5,5,"noblesse",""));
        assertEquals(oldLenght+1, d.numberOfCards());
    }

    @Test
    void putBackManyTest() {
        int oldLenght=d.numberOfCards();

        toAdd.add(new District(4,4,"religion",""));
        toAdd.add(new District(1,3,"commerce",""));
        
        d.putbackMany(toAdd);
        assertEquals(oldLenght+toAdd.size(), d.numberOfCards());
    }

    @Test
    void exchangeManyTest() {
        int oldLenght=d.numberOfCards();

        toAdd.add(new District(4,4,"religion",""));
        toAdd.add(new District(1,3,"commerce",""));
        
        d.exchangeMany(toAdd);
        assertEquals(oldLenght, d.numberOfCards());
    }

    @Test
    void exchangeOneTest() {
        int oldLenght=d.numberOfCards();
        d.exchangeOne(new District(5,5,"noblesse",""));
        assertEquals(oldLenght, d.numberOfCards());
    }
    
}