package ru.nsu.skovalev4.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand of playing cards.
 */
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    /**
     * Adds a card to the hand.
     *
     * @param card card to add
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Returns the score of the hand.
     *
     * @return score of the hand
     */
    public int getScore() {

        int score = 0;
        int aces = 0;
        for (Card card : cards) {
            score += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                aces++;
            }
        }

        while (aces > 0 && score > 21) {
            score -= 10;
            aces--;
        }

        return score;
    }

    /**
     * Checks if the hand is bust.
     *
     * @return true if the hand is bust
     */
    public boolean isBust() {

        int score = getScore();

        return (score > 21);
    }

    /**
     * Checks if the hand is a blackjack.
     *
     * @return true if the hand is a blackjack
     */
    public boolean isBlackjack() {

        int score = getScore();
        int size = cards.size();

        return (size == 2 && score == 21);
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
     * Clears all cards from the hand.
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Returns the value of the card at the specified index.
     *
     * @param index index of the required card
     * @return value of the required card
     */
    public int getCardValue(int index) {
        if (cards.get(index).getRank() != Rank.ACE) {
            return cards.get(index).getRank().getValue();
        } else {
            int reducedAces = getReducedAceCount();
            int aces = 0;
            for (int i = 0; i <= index; i++) {
                if (cards.get(i).getRank() == Rank.ACE) {
                    aces++;
                }
            }
            if (aces <= reducedAces) {
                return 1;
            } else {
                return 11;
            }
        }
    }

    private int getReducedAceCount() {

        int currentScore = 0;
        int aces = 0;
        int reducedAces = 0;
        for (Card card : cards) {
            currentScore += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                aces++;
            }
        }
        while (currentScore > 21 && aces > 0) {
            currentScore -= 10;
            aces--;
            reducedAces++;
        }

        return reducedAces;
    }
}
