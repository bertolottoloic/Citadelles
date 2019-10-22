package fr.unice.polytech.startingpoint;

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
        int oldLenght=d.lenght();
        d.putbackOne(new District(5,5,"noblesse",""));
        assertEquals(oldLenght+1, d.lenght());
    }

    @Test
    void putBackManyTest() {
        int oldLenght=d.lenght();

        toAdd.add(new District(4,4,"religion",""));
        toAdd.add(new District(1,3,"commerce",""));
        
        d.putbackMany(toAdd);
        assertEquals(oldLenght+toAdd.size(), d.lenght());
    }

    @Test
    void exchangeManyTest() {
        int oldLenght=d.lenght();

        toAdd.add(new District(4,4,"religion",""));
        toAdd.add(new District(1,3,"commerce",""));
        
        d.exchangeMany(toAdd);
        assertEquals(oldLenght, d.lenght());
    }

    @Test
    void exchangeOneTest() {
        int oldLenght=d.lenght();
        d.exchangeOne(new District(5,5,"noblesse",""));
        assertEquals(oldLenght, d.lenght());
    }
    
}