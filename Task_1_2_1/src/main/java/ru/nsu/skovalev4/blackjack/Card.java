package ru.nsu.skovalev4.blackjack;

/**
 * Represents a playing card.
 */
public class Card {
    private Suit suit;
    private Rank rank;

    /**
     * Creates a card with the specified suit and rank.
     *
     * @param suit suit of the card
     * @param rank rank of the card
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the rank of the card.
     *
     * @return rank of the card
     */
    public Rank getRank() {
        return this.rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return suit of the card
     */
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Makes readable card rank and suit.
     *
     * @return
     */
    public String toString(){
        return rank + " of " + suit + "S";
    }

}