package ru.nsu.skovalev4.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BlackjackGameTest {

    @Test
    void playerWinsWithHigherScore() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.NINE));

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));

        GameResult result = game.determineResult();

        assertEquals(GameResult.PLAYER_WINS, result);
    }

    @Test
    void dealerWinsWithHigherScore() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.TWO));

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));

        GameResult result = game.determineResult();

        assertEquals(GameResult.DEALER_WINS, result);
    }

    @Test
    void roundIsDrawWithEqualScores() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.TWO));

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.TWO));

        GameResult result = game.determineResult();

        assertEquals(GameResult.DRAW, result);
    }

    @Test
    void dealerWinsWhenPlayerIsBust() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.SEVEN));

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));

        GameResult result = game.determineResult();

        assertEquals(GameResult.DEALER_WINS, result);
    }

    @Test
    void playerWinsWhenDealerIsBust() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.TEN));

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));

        GameResult result = game.determineResult();

        assertEquals(GameResult.PLAYER_WINS, result);
    }

    @Test
    void playerWinsWithBlackjack() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.ACE));

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));

        GameResult result = game.determineResult();

        assertEquals(GameResult.PLAYER_WINS, result);
    }

    @Test
    void dealerWinsWithBlackjack() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.SEVEN));

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.ACE));

        GameResult result = game.determineResult();

        assertEquals(GameResult.DEALER_WINS, result);
    }

    @Test
    void drawWhenBothHaveBlackjack() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.ACE));

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.ACE));

        GameResult result = game.determineResult();

        assertEquals(GameResult.DRAW, result);
    }

    @Test
    void addOneCardWhenPlayerHit() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        int playerCardsCountBeforeHit = game.getPlayerHand().getCardCount();
        Card hitCard = game.playerHit();
        int playerCardsCountAfterHit = game.getPlayerHand().getCardCount();
        assertEquals(playerCardsCountBeforeHit, playerCardsCountAfterHit - 1);
        assertSame(hitCard, game.getPlayerHand().getCard(1));
    }

    @Test
    void addOneCardWhenDealerHit() {
        BlackjackGame game = new BlackjackGame();

        game.getDealerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        int dealerCardsCountBeforeHit = game.getDealerHand().getCardCount();
        Card hitCard = game.dealerHit();
        int dealerCardsCountAfterHit = game.getDealerHand().getCardCount();
        assertEquals(dealerCardsCountBeforeHit, dealerCardsCountAfterHit - 1);
        assertSame(hitCard, game.getDealerHand().getCard(1));
    }

    @Test
    void handsResetWhenNewRoundStarts() {
        BlackjackGame game = new BlackjackGame();

        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.TEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.SEVEN));
        game.getPlayerHand().addCard(new Card(Suit.HEARTS, Rank.TWO));

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.SEVEN));

        game.startNewRound();

        assertEquals(2, game.getDealerHand().getCardCount());
        assertEquals(2, game.getPlayerHand().getCardCount());
    }

    @Test
    void shouldDealerHit() {
        BlackjackGame game = new BlackjackGame();

        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.SIX));

        assertTrue(game.shouldDealerHit());

        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.ACE));

        assertFalse(game.shouldDealerHit());
    }
}