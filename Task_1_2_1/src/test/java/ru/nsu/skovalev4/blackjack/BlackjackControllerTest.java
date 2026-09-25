package ru.nsu.skovalev4.blackjack;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.Scanner;
import org.junit.jupiter.api.Test;

class BlackjackControllerTest {

    @Test
    void playerWinsAfterStandingWithHigherScore() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.EIGHT);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.SEVEN);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("0\n");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();

        verify(view).showRoundNumber(1);
        verify(view).showInitialDeal();
        verify(view).showPlayerTurn();
        verify(view).showDealerTurn();
        verify(view).showRoundResult(GameResult.PLAYER_WINS);
        verify(view).showScore(1, 0);
    }

    @Test
    void showErrorAndReadActionAgainAfterInvalidInput() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.EIGHT);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.SEVEN);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("abc\n0\n");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();

        verify(view).showInvalidInput();
        verify(view, times(2)).showActionPrompt();
        verify(view).showRoundResult(GameResult.PLAYER_WINS);
        verify(view).showScore(1, 0);
    }

    @Test
    void playerDrawsCardAndThenStands() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.EIGHT);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card playerThirdCard = new Card(Suit.SPADES, Rank.TWO);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard,
            playerThirdCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("1\n0\n");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();

        verify(view, times(2)).showActionPrompt();
        verify(view).showPlayerHit(playerThirdCard, 2);
        verify(view).showRoundResult(GameResult.PLAYER_WINS);
        verify(view).showScore(1, 0);
    }

    @Test
    void dealerWinsAndRevealsCardWhenPlayerBusts() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.EIGHT);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card playerThirdCard = new Card(Suit.SPADES, Rank.EIGHT);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard,
            playerThirdCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("1\n");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();

        verify(view).showActionPrompt();
        verify(view).showPlayerHit(playerThirdCard, 8);
        verify(view).showDealerHiddenCard(dealerSecondCard, 7);
        verify(view).showFullHands(game.getPlayerHand(), game.getDealerHand());
        verify(view).showRoundResult(GameResult.DEALER_WINS);
        verify(view).showScore(0, 1);
    }

    @Test
    void playerStopsDrawingAtTwentyOne() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.EIGHT);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card playerThirdCard = new Card(Suit.SPADES, Rank.THREE);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard,
            playerThirdCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("1\n");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();

        verify(view).showActionPrompt();
        verify(view).showPlayerHit(playerThirdCard, 3);
        verify(view).showDealerHiddenCard(dealerSecondCard, 7);
        verify(view).showFullHands(game.getPlayerHand(), game.getDealerHand());
        verify(view).showRoundResult(GameResult.PLAYER_WINS);
        verify(view).showScore(1, 0);
    }

    @Test
    void dealerDrawsUntilScoreReachesSeventeen() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.EIGHT);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.TWO);
        Card dealerThirdCard = new Card(Suit.SPADES, Rank.FIVE);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard,
            dealerThirdCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("0\n");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();

        verify(view).showActionPrompt();
        verify(view).showDealerHiddenCard(dealerSecondCard, 2);
        verify(view).showDealerTurn();
        verify(view).showDealerHit(dealerThirdCard, 5);
        verify(view, times(2)).showFullHands(game.getPlayerHand(), game.getDealerHand());
        verify(view).showRoundResult(GameResult.PLAYER_WINS);
        verify(view).showScore(1, 0);
    }

    @Test
    void playerWinsWithInitialBlackjack() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.ACE);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.TWO);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();


        verify(view).showHandsWithHiddenDealerCard(
            game.getPlayerHand(),
            game.getDealerHand()
        );
        verify(view).showFullHands(game.getPlayerHand(), game.getDealerHand());
        verify(view).showBlackjackResult(GameResult.PLAYER_WINS);
        verify(view).showScore(1, 0);
    }

    @Test
    void dealerWinsWithInitialBlackjack() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.EIGHT);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.ACE);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();


        verify(view).showHandsWithHiddenDealerCard(
            game.getPlayerHand(),
            game.getDealerHand()
        );
        verify(view).showFullHands(game.getPlayerHand(), game.getDealerHand());
        verify(view).showBlackjackResult(GameResult.DEALER_WINS);
        verify(view).showScore(0, 1);
    }

    @Test
    void drawWhenBothHaveInitialBlackjack() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.ACE);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.ACE);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();


        verify(view).showHandsWithHiddenDealerCard(
            game.getPlayerHand(),
            game.getDealerHand()
        );
        verify(view).showFullHands(game.getPlayerHand(), game.getDealerHand());
        verify(view).showBlackjackResult(GameResult.DRAW);
        verify(view).showScore(0, 0);
    }

    @Test
    void roundEndsInDrawWithEqualScores() {
        Card playerFirstCard = new Card(Suit.SPADES, Rank.TEN);
        Card playerSecondCard = new Card(Suit.HEARTS, Rank.EIGHT);
        Card dealerFirstCard = new Card(Suit.CLUBS, Rank.TEN);
        Card dealerSecondCard = new Card(Suit.DIAMONDS, Rank.TWO);
        Card dealerThirdCard = new Card(Suit.SPADES, Rank.SIX);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            playerFirstCard,
            playerSecondCard,
            dealerFirstCard,
            dealerSecondCard,
            dealerThirdCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("0\n");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();

        verify(view).showActionPrompt();
        verify(view).showDealerHiddenCard(dealerSecondCard, 2);
        verify(view).showDealerTurn();
        verify(view).showDealerHit(dealerThirdCard, 6);
        verify(view, times(2)).showFullHands(game.getPlayerHand(), game.getDealerHand());
        verify(view).showRoundResult(GameResult.DRAW);
        verify(view).showScore(0, 0);
    }

    @Test
    void scoreAndRoundNumberAccumulateAcrossRounds() {
        Card firstRoundPlayerFirstCard =
            new Card(Suit.SPADES, Rank.TEN);
        Card firstRoundPlayerSecondCard =
            new Card(Suit.HEARTS, Rank.EIGHT);
        Card firstRoundDealerFirstCard =
            new Card(Suit.CLUBS, Rank.TEN);
        Card firstRoundDealerSecondCard =
            new Card(Suit.DIAMONDS, Rank.SEVEN);

        Card secondRoundPlayerFirstCard =
            new Card(Suit.SPADES, Rank.TEN);
        Card secondRoundPlayerSecondCard =
            new Card(Suit.HEARTS, Rank.SIX);
        Card secondRoundDealerFirstCard =
            new Card(Suit.CLUBS, Rank.TEN);
        Card secondRoundDealerSecondCard =
            new Card(Suit.DIAMONDS, Rank.NINE);

        Deck deck = mock(Deck.class);
        when(deck.getCard()).thenReturn(
            firstRoundPlayerFirstCard,
            firstRoundPlayerSecondCard,
            firstRoundDealerFirstCard,
            firstRoundDealerSecondCard,
            secondRoundPlayerFirstCard,
            secondRoundPlayerSecondCard,
            secondRoundDealerFirstCard,
            secondRoundDealerSecondCard
        );

        BlackjackGame game = new BlackjackGame(deck);
        ConsoleView view = mock(ConsoleView.class);
        Scanner scanner = new Scanner("0\n0\n");

        BlackjackController controller =
            new BlackjackController(game, view, scanner);

        controller.playRound();
        controller.playRound();

        verify(view).showRoundNumber(1);
        verify(view).showScore(1, 0);

        verify(view).showRoundNumber(2);
        verify(view).showScore(1, 1);

        verify(view).showRoundResult(GameResult.PLAYER_WINS);
        verify(view).showRoundResult(GameResult.DEALER_WINS);
        verify(view, times(2)).showActionPrompt();
    }

}