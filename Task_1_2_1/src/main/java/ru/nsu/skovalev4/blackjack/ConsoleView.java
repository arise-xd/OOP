package ru.nsu.skovalev4.blackjack;

/**
 * Prints game messages to the console.
 */
public class ConsoleView {

    /**
     * Prints the welcome message.
     */
    public void showWelcome() {
        System.out.println("Welcome to Blackjack!");
    }

    /**
     * Prints the round number.
     *
     * @param round round number
     */
    public void showRoundNumber(int round) {
        System.out.println("Round " + round);
    }

    /**
     * Announces the initial deal.
     */
    public void showInitialDeal() {
        System.out.println("Dealer deals the cards:");
    }

    /**
     * Prints the player's current hand and the dealer's visible card.
     *
     * @param playerHand player's hand
     * @param dealerHand dealer's hand
     */
    public void showHandsWithHiddenDealerCard(Hand playerHand, Hand dealerHand) {

        System.out.print("    Your cards: ");
        printFullHand(playerHand);
        System.out.println(" => " + playerHand.getScore());

        System.out.println("    Dealer's cards: "
            + dealerHand.getCard(0)
            + " (" + dealerHand.getCard(0).getRank().getValue() + ")"
            + ", "
            + "<hidden card>"
        );
    }

    /**
     * Prints the beginning of the player's turn.
     */
    public void showPlayerTurn() {
        System.out.println("Your turn");
        System.out.println("-----");
    }

    /**
     * Prompts the player to draw a card or stand.
     */
    public void showActionPrompt() {
        System.out.println("Enter '1' to draw a card or '0' to stand...");
    }

    /**
     * Reports invalid player input.
     */
    public void showInvalidInput() {
        System.out.println("Invalid input.");
        System.out.println("Expected '1' to draw a card or '0' to stand...");
    }

    /**
     * Prints the card drawn by the player.
     *
     * @param card card drawn by the player
     * @param value value of the card
     */
    public void showPlayerHit(Card card, int value) {
        System.out.println("You draw the card " + card.toString() + " (" + value + ")");
    }

    /**
     * Prints the beginning of the dealer's turn.
     */
    public void showDealerTurn() {
        System.out.println("Dealer's turn");
        System.out.println("-----");
    }

    /**
     * Prints the dealer's revealed hidden card.
     *
     * @param hiddenCard dealer's hidden card
     * @param value value of the card
     */
    public void showDealerHiddenCard(Card hiddenCard, int value) {
        System.out.println("Dealer reveals the hidden card "
            + hiddenCard.toString()
            + " (" + value + ")");
    }

    /**
     * Prints the card drawn by the dealer.
     *
     * @param card card drawn by the dealer
     * @param value value of the card
     */
    public void showDealerHit(Card card, int value) {
        System.out.println("Dealer draws the card " + card.toString() + " (" + value + ")");
    }

    /**
     * Prints the player's and the dealer's full hands.
     *
     * @param playerHand player's hand
     * @param dealerHand dealer's hand
     */
    public void showFullHands(Hand playerHand, Hand dealerHand) {

        System.out.print("  Your cards: ");
        printFullHand(playerHand);
        System.out.print(" => " + playerHand.getScore() + '\n');


        System.out.print("  Dealer's cards: ");
        printFullHand(dealerHand);
        System.out.print(" => " + dealerHand.getScore() + '\n');

    }

    /**
     * Prints the result of the round.
     *
     * @param result result of the round
     */
    public void showRoundResult(GameResult result) {
        if (result == GameResult.DEALER_WINS) {
            System.out.print("Dealer wins the round! ");
        } else if (result == GameResult.PLAYER_WINS) {
            System.out.print("You win the round! ");
        } else {
            System.out.print("Draw! ");
        }
    }

    /**
     * Prints the result of the round if there is a blackjack.
     *
     * @param result result of the round
     */
    public void showBlackjackResult(GameResult result) {
        if (result == GameResult.DEALER_WINS) {
            System.out.print("The dealer has blackjack and wins the round! ");
        } else if (result == GameResult.PLAYER_WINS) {
            System.out.print("Blackjack! You win the round! ");
        } else {
            System.out.print("Both have blackjack. Draw! ");
        }
    }

    /**
     * Prints the score of the game.
     *
     * @param playerWins number of rounds won by the player
     * @param dealerWins number of rounds won by the dealer
     */
    public void showScore(int playerWins, int dealerWins) {
        System.out.print("Score: " + playerWins + ":" + dealerWins + " ");
        if (playerWins > dealerWins) {
            System.out.print("You are leading.");
        } else if (playerWins < dealerWins) {
            System.out.print("The dealer is leading.");
        }
        System.out.print('\n');
    }

    private void printFullHand(Hand hand) {
        for (int i = 0; i < hand.getCardCount(); i++) {
            if (i == hand.getCardCount() - 1) {
                System.out.print(hand.getCard(i).toString() + " (" + hand.getCardValue(i) + ")");
            } else {
                System.out.print(hand.getCard(i).toString() + " ("
                    + hand.getCardValue(i) + ")" + ", ");
            }
        }
    }
}
