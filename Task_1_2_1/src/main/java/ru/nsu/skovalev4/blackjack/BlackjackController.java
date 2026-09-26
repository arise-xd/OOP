package ru.nsu.skovalev4.blackjack;

import java.util.Scanner;

/**
 * Controls the flow of a blackjack game.
 */
public class BlackjackController {
    private final BlackjackGame game;
    private final ConsoleView view;
    private final Scanner scanner;

    private int playerWins = 0;
    private int dealerWins = 0;
    private int roundNumber = 0;

    /**
     * Creates a controller with the specified game, view, and input scanner.
     *
     * @param game blackjack game
     * @param view console view
     * @param scanner scanner used to read player input
     */
    public BlackjackController(
        BlackjackGame game,
        ConsoleView view,
        Scanner scanner) {
        this.game = game;
        this.view = view;
        this.scanner = scanner;
    }

    /**
     * Starts the blackjack game.
     */
    public void run() {
        view.showWelcome();
        while (true) {
            playRound();
        }
    }

    void playRound() {
        roundNumber++;
        view.showRoundNumber(roundNumber);
        game.startNewRound();
        view.showInitialDeal();
        view.showHandsWithHiddenDealerCard(game.getPlayerHand(), game.getDealerHand());
        if (hasInitialBlackjack()) {
            view.showFullHands(game.getPlayerHand(), game.getDealerHand());
            GameResult gameResult = game.determineResult();
            updateScore(gameResult);
            view.showBlackjackResult(gameResult);
            view.showScore(playerWins, dealerWins);

        } else {
            view.showPlayerTurn();
            int playerTakeCard = readPlayerAction();
            while (playerTakeCard == 1) {
                Card playerHitCard = game.playerHit();
                int playerCardsCount = game.getPlayerHand().getCardCount();
                int playerHitValue = game.getPlayerHand().getCardValue(playerCardsCount - 1);
                view.showPlayerHit(playerHitCard, playerHitValue);
                view.showHandsWithHiddenDealerCard(game.getPlayerHand(), game.getDealerHand());
                if (game.getPlayerHand().isBust() || game.getPlayerHand().getScore() == 21) {
                    break;
                }
                playerTakeCard = readPlayerAction();
            }

            boolean playerBust = game.getPlayerHand().isBust();

            if (!playerBust) {
                view.showDealerTurn();
            }

            Card hiddenCard = game.getDealerHand().getCard(1);
            int hiddenCardValue = game.getDealerHand().getCardValue(1);
            view.showDealerHiddenCard(hiddenCard, hiddenCardValue);
            view.showFullHands(game.getPlayerHand(), game.getDealerHand());

            if (!playerBust) {
                while (game.shouldDealerHit()) {
                    Card dealerHitCard = game.dealerHit();
                    int dealerCardsCount = game.getDealerHand().getCardCount();
                    int dealerHitValue = game.getDealerHand().getCardValue(dealerCardsCount - 1);
                    view.showDealerHit(dealerHitCard, dealerHitValue);
                    view.showFullHands(game.getPlayerHand(), game.getDealerHand());
                }
            }

            GameResult gameResult = game.determineResult();
            updateScore(gameResult);
            view.showRoundResult(gameResult);
            view.showScore(playerWins, dealerWins);
        }


    }

    /**
     * Checks whether either participant has an initial blackjack.
     *
     * @return true if the player or the dealer has an initial blackjack
     */
    private boolean hasInitialBlackjack() {
        return game.getDealerHand().isBlackjack() || game.getPlayerHand().isBlackjack();
    }

    /**
     * Reads one valid action selected by the player.
     *
     * @return 1 to draw a card or 0 to stand
     */
    private int readPlayerAction() {
        while (true) {
            view.showActionPrompt();
            String input = scanner.nextLine().trim();
            if ("1".equals(input)) {
                return 1;
            } else if ("0".equals(input)) {
                return 0;
            } else {
                view.showInvalidInput();
            }
        }
    }

    /**
     * Updates the game score according to the result of the round.
     *
     * @param result result of the round
     */
    private void updateScore(GameResult result) {
        if (result == GameResult.DEALER_WINS) {
            dealerWins++;
        } else if (result == GameResult.PLAYER_WINS) {
            playerWins++;
        }
    }
}
