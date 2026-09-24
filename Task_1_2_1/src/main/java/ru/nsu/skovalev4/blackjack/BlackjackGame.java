package ru.nsu.skovalev4.blackjack;

/**
 * Represents a blackjack game.
 */
public class BlackjackGame {

    private final Deck deck = new Deck();
    private final Player player = new Player();
    private final Dealer dealer = new Dealer();

    /**
     * Creates a blackjack game and shuffles the deck.
     */
    public BlackjackGame() {
        deck.shuffleDeck();
    }

    /**
     * Deals two initial cards to the player and the dealer.
     */
    public void dealInitialCards() {

        player.getHand().addCard(deck.getCard());
        player.getHand().addCard(deck.getCard());

        dealer.getHand().addCard(deck.getCard());
        dealer.getHand().addCard(deck.getCard());

    }

    /**
     * Deals one card to the player.
     *
     * @return card dealt to the player
     */
    public Card playerHit() {
        Card card = deck.getCard();
        player.getHand().addCard(card);
        return card;
    }

    /**
     * Checks whether the dealer should take another card.
     *
     * @return true if the dealer should take another card
     */
    public boolean shouldDealerHit() {
        return dealer.shouldHit();
    }

    /**
     * Deals one card to the dealer.
     *
     * @return card dealt to the dealer
     */
    public Card dealerHit() {
        Card card = deck.getCard();
        dealer.getHand().addCard(card);
        return card;
    }

    /**
     * Determines the result of the round.
     *
     * @return {@link GameResult#PLAYER_WINS} if the player wins,
     *     {@link GameResult#DEALER_WINS} if the dealer wins, or
     *     {@link GameResult#DRAW} if both have blackjack or equal scores
     */
    public GameResult determineResult() {
        if (dealer.getHand().isBlackjack() && player.getHand().isBlackjack()) {
            return GameResult.DRAW;
        } else if (player.getHand().isBlackjack()) {
            return GameResult.PLAYER_WINS;
        } else if (dealer.getHand().isBlackjack()) {
            return GameResult.DEALER_WINS;
        }

        if (player.getHand().isBust()) {
            return GameResult.DEALER_WINS;
        } else if (dealer.getHand().isBust()) {
            return GameResult.PLAYER_WINS;
        }

        if (dealer.getHand().getScore() > player.getHand().getScore()) {
            return GameResult.DEALER_WINS;
        } else if (dealer.getHand().getScore() < player.getHand().getScore()) {
            return GameResult.PLAYER_WINS;
        }

        return GameResult.DRAW;
    }

    /**
     * Returns the player's hand.
     *
     * @return player's hand
     */
    public Hand getPlayerHand() {
        return player.getHand();
    }

    /**
     * Returns the dealer's hand.
     *
     * @return dealer's hand
     */
    public Hand getDealerHand() {
        return dealer.getHand();
    }

    /**
     * Starts a new round.
     */
    public void startNewRound() {
        player.getHand().clear();
        dealer.getHand().clear();
        deck.reset();
        dealInitialCards();
    }
}
