package ru.nsu.skovalev4.blackjack;

/**
 * Represents a blackjack game.
 */
public class BlackjackGame {

    private final Deck deck = new Deck();
    private final Player player = new Player();
    private final Dealer dealer = new Dealer();

    /**
     * Initial shuffling the deck.
     */
    public BlackjackGame() {
        deck.shuffleDeck();
    }

    /**
     * Initial deal cards to player and dealer.
     */
    public void dealInitialCards() {

        player.getHand().addCard(deck.getCard());
        player.getHand().addCard(deck.getCard());

        dealer.getHand().addCard(deck.getCard());
        dealer.getHand().addCard(deck.getCard());

    }

    /**
     * Gives one card to the player.
     */
    public void playerHit() {
        player.getHand().addCard(deck.getCard());
    }

    /**
     * Plays the dealer's turn.
     */
    public void playDealerTurn() {
        dealer.playTurn(deck);
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
}
