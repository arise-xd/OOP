package ru.nsu.skovalev4.blackjack;

/**
 * Represents a player in the blackjack game.
 */
public class Player {
    private final Hand hand = new Hand();

    /**
     * Returns the player's hand
     *
     * @return player's hand
     */
    public Hand getHand() {
        return hand;
    }
}
