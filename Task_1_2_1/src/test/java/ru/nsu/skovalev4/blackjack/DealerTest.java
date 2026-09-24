package ru.nsu.skovalev4.blackjack;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;


class DealerTest {

    @Test
    void dealerShouldHitWithScoreBelowSeventeen() {
        Dealer dealer = new Dealer();
        dealer.getHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        dealer.getHand().addCard(new Card(Suit.CLUBS, Rank.SIX));
        assertTrue(dealer.shouldHit());
    }

    @Test
    void dealerShouldStandWithScoreSeventeen() {
        Dealer dealer = new Dealer();
        dealer.getHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        dealer.getHand().addCard(new Card(Suit.CLUBS, Rank.SEVEN));
        assertFalse(dealer.shouldHit());
    }

    @Test
    void dealerShouldStandWithScoreAboveSeventeen() {
        Dealer dealer = new Dealer();
        dealer.getHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        dealer.getHand().addCard(new Card(Suit.CLUBS, Rank.NINE));
        assertFalse(dealer.shouldHit());
    }
}