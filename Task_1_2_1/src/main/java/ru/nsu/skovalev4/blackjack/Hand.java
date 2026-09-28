package ru.nsu.skovalev4.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand of playing cards.
 */
public class Hand {
    private final List<Card> cards = new ArrayList<>();
    private final List<Integer> aceIndexes = new ArrayList<>();

    private int score = 0;
    private int reducedAces = 0;

    /**
     * Adds a card to the hand and updates the score.
     *
     * @param card card to add
     */
    public void addCard(Card card) {
        cards.add(card);
        score += card.getRank().getValue();

        if (card.getRank() == Rank.ACE) {
            aceIndexes.add(cards.size() - 1);
        }

        while (score > 21 && reducedAces < aceIndexes.size()) {
            score -= 10;
            reducedAces++;
        }
    }

    /**
     * Returns the score of the hand.
     *
     * @return score of the hand
     */
    public int getScore() {
        return score;
    }

    /**
     * Checks if the hand is bust.
     *
     * @return true if the hand is bust
     */
    public boolean isBust() {
        return score > 21;
    }

    /**
     * Checks if the hand is a blackjack.
     *
     * @return true if the hand is a blackjack
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && score == 21;
    }

    /**
     * Returns the card at the specified index.
     *
     * @param index index of the card
     * @return card at the specified index
     */
    public Card getCard(int index) {
        return cards.get(index);
    }

    /**
     * Returns the number of cards in the hand.
     *
     * @return number of cards in the hand
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * Clears all cards and calculated values from the hand.
     */
    public void clear() {
        cards.clear();
        aceIndexes.clear();
        score = 0;
        reducedAces = 0;
    }

    /**
     * Returns the current value of the card at the specified index.
     *
     * @param index index of the required card
     * @return current value of the required card
     */
    public int getCardValue(int index) {
        Card card = cards.get(index);

        if (card.getRank() != Rank.ACE) {
            return card.getRank().getValue();
        }

        if (reducedAces > 0) {
            int lastReducedAceIndex = aceIndexes.get(reducedAces - 1);
            if (index <= lastReducedAceIndex) {
                return 1;
            }
        }

        return Rank.ACE.getValue();
    }
}