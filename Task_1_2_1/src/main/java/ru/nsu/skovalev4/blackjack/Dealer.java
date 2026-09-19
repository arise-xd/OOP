package ru.nsu.skovalev4.blackjack;

/**
 * Represents a dealer in the blackjack game.
 */
public class Dealer {

    private static final int DEALERS_BREAK_POINT = 17;
    private final Hand hand = new Hand();

    /**
     * Returns the dealer's hand.
     *
     * @return dealer's hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Check if the dealer should hit.
     *
     * @return true if dealer's score is less than the minimum score
     */
    public boolean shouldHit() {

        return (hand.getScore() < DEALERS_BREAK_POINT);

    }

    /**
     * Plays the dealer's turn.
     *
     * @param deck deck from which the dealer draws cards
     */
    public void playTurn(Deck deck) {

        while (shouldHit()) {
            hand.addCard(deck.getCard());
        }

    }
}
