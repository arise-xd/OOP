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
     * Checks whether the dealer should take another card.
     *
     * @return true if the dealer's score is less than 17
     */
    public boolean shouldHit() {

        return (hand.getScore() < DEALERS_BREAK_POINT);

    }

}
