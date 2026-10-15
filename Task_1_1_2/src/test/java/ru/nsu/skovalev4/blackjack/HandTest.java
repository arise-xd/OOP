package ru.nsu.skovalev4.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void addCardToHand() {
        Hand hand = new Hand();
        Card card = new Card(Suit.HEARTS, Rank.TEN);

        hand.addCard(card);

        assertEquals(1, hand.getCardCount());
        assertSame(card, hand.getCard(0));
    }

    @Test
    void calculateScoreOfRegularCards() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.KING));
        hand.addCard(new Card(Suit.HEARTS, Rank.SEVEN));

        int score = hand.getScore();

        assertEquals(17, score);
    }

    @Test
    void checkIfHandIsBust() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.KING));
        hand.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
        assertFalse(hand.isBust());
        hand.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
        assertTrue(hand.isBust());
    }

    @Test
    void blackjackWithThreeCards() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.KING));
        hand.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
        assertFalse(hand.isBlackjack());
        hand.addCard(new Card(Suit.CLUBS, Rank.FOUR));
        assertFalse(hand.isBlackjack());
    }

    @Test
    void checkIfHandHasBlackjack() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.KING));
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        assertTrue(hand.isBlackjack());
    }

    @Test
    void calculateCardsCountInHand() {
        Hand hand = new Hand();
        assertEquals(0, hand.getCardCount());
        hand.addCard(new Card(Suit.SPADES, Rank.KING));
        hand.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
        hand.addCard(new Card(Suit.CLUBS, Rank.FOUR));
        assertEquals(3, hand.getCardCount());
    }

    @Test
    void clearHand() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.KING));
        hand.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
        hand.addCard(new Card(Suit.CLUBS, Rank.FOUR));
        hand.clear();
        assertEquals(0, hand.getCardCount());
    }

    @Test
    void calculateValuesOfTwoAces() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.CLUBS, Rank.ACE));

        assertEquals(12, hand.getScore());
        assertEquals(1, hand.getCardValue(0));
        assertEquals(11, hand.getCardValue(1));
    }

    @Test
    void calculateValueOfReducedAce() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.CLUBS, Rank.NINE));
        hand.addCard(new Card(Suit.CLUBS, Rank.FOUR));

        assertEquals(14, hand.getScore());
        assertEquals(1, hand.getCardValue(0));
        assertEquals(9, hand.getCardValue(1));
        assertEquals(4, hand.getCardValue(2));
    }
}