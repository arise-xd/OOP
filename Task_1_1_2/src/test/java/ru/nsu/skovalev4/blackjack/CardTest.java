package ru.nsu.skovalev4.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void getRankOfCard() {
        Card card = new Card(Suit.CLUBS, Rank.ACE);
        assertSame(Rank.ACE, card.getRank());
    }

    @Test
    void getSuitOfCard() {
        Card card = new Card(Suit.CLUBS, Rank.ACE);
        assertSame(Suit.CLUBS, card.getSuit());
    }

    @Test
    void testToString() {
        Card card = new Card(Suit.CLUBS, Rank.ACE);
        assertEquals("ACE of CLUBS", card.toString());
    }
}