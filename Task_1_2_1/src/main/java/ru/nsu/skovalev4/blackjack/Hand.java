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
        for (Card card : cards){
            score += card.getRank().getValue();
            if (card.getRank() == Rank.ACE){
                aces++;
            }
        }

        while (aces > 0 && score > 21){
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
}
