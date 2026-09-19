package ru.nsu.skovalev4.blackjack;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of playing cards.
 */
public class Deck {
    private final List<Card> cards = new ArrayList<>();

    /**
     * Creates a deck with 52 cards.
     */
    public Deck() {
        for (Suit suit : Suit.values()) {

            for (Rank rank : Rank.values()) {

                Card card = new Card(suit, rank);
                cards.add(card);

            }
        }
    }

    /**
     * Shuffles the deck.
     */
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    /**
     * Draws one card from the deck.
     *
     * @return drawn card
     */
    public Card getCard() {
        return cards.removeLast();
    }

}
