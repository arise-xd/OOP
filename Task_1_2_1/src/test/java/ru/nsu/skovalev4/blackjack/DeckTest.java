package ru.nsu.skovalev4.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;



class DeckTest {

    @Test
    void deckContainsFiftyTwoUniqueCards() {
        Deck deck = new Deck();
        deck.shuffleDeck();
        Set<String> cards = new HashSet<>();
        for (int i = 0; i < 52; i++) {
            Card card = deck.getCard();
            String key = card.getSuit() + ":" + card.getRank();
            assertTrue(cards.add(key));
        }
        assertEquals(52, cards.size());
    }

    @Test
    void resetRestoresFullDeck() {
        Deck deck = new Deck();
        deck.getCard();
        deck.getCard();
        deck.getCard();
        deck.reset();
        Set<String> cards = new HashSet<>();
        for (int i = 0; i < 52; i++) {
            Card card = deck.getCard();
            String key = card.getSuit() + ":" + card.getRank();
            assertTrue(cards.add(key));
        }
        assertEquals(52, cards.size());
    }
}