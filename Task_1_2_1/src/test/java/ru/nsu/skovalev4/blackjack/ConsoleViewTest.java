package ru.nsu.skovalev4.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleViewTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream output;
    private ConsoleView view;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        view = new ConsoleView();
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void showWelcomeMessage() {
        view.showWelcome();

        String expected = "Welcome to Blackjack!" + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showRoundNumber() {
        view.showRoundNumber(7);

        String expected = "Round 7" + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showInitialDeal() {
        view.showInitialDeal();

        String expected = "Dealer deals the cards:" + System.lineSeparator();

        assertEquals(expected, output.toString());

    }

    @Test
    void showHandsWithHiddenDealerCard() {
        BlackjackGame game = new BlackjackGame();
        game.getPlayerHand().addCard(new Card(Suit.CLUBS, Rank.SIX));
        game.getPlayerHand().addCard(new Card(Suit.DIAMONDS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.SPADES, Rank.SEVEN));
        game.getDealerHand().addCard(new Card(Suit.HEARTS, Rank.ACE));

        view.showHandsWithHiddenDealerCard(game.getPlayerHand(), game.getDealerHand());
        String expected = "    Your cards: SIX of CLUBS (6), TEN of DIAMONDS (10) => 16"
            + System.lineSeparator()
            + "    Dealer's cards: SEVEN of SPADES (7), <hidden card>"
            + System.lineSeparator();

        assertEquals(expected, output.toString());

    }

    @Test
    void showPlayerTurn() {
        view.showPlayerTurn();

        String expected = "Your turn"
            + System.lineSeparator()
            + "-----"
            + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showActionPrompt() {
        view.showActionPrompt();

        String expected = "Enter '1' to draw a card or '0' to stand..." + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showInvalidInput() {
        view.showInvalidInput();

        String expected = "Invalid input."
            + System.lineSeparator()
            + "Expected '1' to draw a card or '0' to stand..."
            + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showPlayerHit() {
        BlackjackGame game = new BlackjackGame();
        Card card = new Card(Suit.CLUBS, Rank.ACE);
        int value = card.getRank().getValue();
        game.getPlayerHand().addCard(card);
        view.showPlayerHit(card, value);
        String expected = "You draw the card "
            + card.toString()
            + " (" + value + ")"
            + System.lineSeparator();

        assertEquals(expected, output.toString());

    }

    @Test
    void showDealerTurn() {
        view.showDealerTurn();

        String expected = "Dealer's turn"
            + System.lineSeparator()
            + "-----"
            +System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showDealerHiddenCard() {
        BlackjackGame game = new BlackjackGame();
        game.getDealerHand().addCard(new Card(Suit.SPADES, Rank.SEVEN));
        game.getDealerHand().addCard(new Card(Suit.HEARTS, Rank.ACE));
        Card hiddenCard = game.getDealerHand().getCard(1);

        view.showDealerHiddenCard(hiddenCard, hiddenCard.getRank().getValue());
        String expected = "Dealer reveals the hidden card ACE of HEARTS (11)"
            + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showDealerHit() {
        BlackjackGame game = new BlackjackGame();
        Card card = new Card(Suit.DIAMONDS, Rank.KING);
        int value = card.getRank().getValue();
        game.getDealerHand().addCard(card);
        view.showDealerHit(card, value);
        String expected = "Dealer draws the card "
            + card.toString()
            + " (" + value + ")"
            + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showFullHands() {
        BlackjackGame game = new BlackjackGame();
        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.EIGHT));
        game.getPlayerHand().addCard(new Card(Suit.CLUBS, Rank.SIX));
        game.getPlayerHand().addCard(new Card(Suit.DIAMONDS, Rank.NINE));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.TWO));
        game.getDealerHand().addCard(new Card(Suit.HEARTS, Rank.ACE));

        view.showFullHands(game.getPlayerHand(), game.getDealerHand());
        String expected = "  Your cards: EIGHT of SPADES (8), SIX of CLUBS (6), NINE of DIAMONDS (9) => 23"
            + System.lineSeparator()
            + "  Dealer's cards: TWO of DIAMONDS (2), ACE of HEARTS (11) => 13"
            + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showDealerWinRoundResult() {
        BlackjackGame game = new BlackjackGame();
        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.EIGHT));
        game.getPlayerHand().addCard(new Card(Suit.CLUBS, Rank.SIX));
        game.getPlayerHand().addCard(new Card(Suit.DIAMONDS, Rank.NINE));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.TWO));
        game.getDealerHand().addCard(new Card(Suit.HEARTS, Rank.ACE));

        GameResult result = game.determineResult();
        view.showRoundResult(result);
        String expected = "Dealer wins the round! ";

        assertEquals(expected, output.toString());
    }

    @Test
    void showPlayerWinRoundResult() {
        BlackjackGame game = new BlackjackGame();
        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.EIGHT));
        game.getPlayerHand().addCard(new Card(Suit.CLUBS, Rank.SIX));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.NINE));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.TEN));
        game.getDealerHand().addCard(new Card(Suit.HEARTS, Rank.EIGHT));

        GameResult result = game.determineResult();
        view.showRoundResult(result);
        String expected = "You win the round! ";

        assertEquals(expected, output.toString());
    }

    @Test
    void showDrawRoundResult() {
        BlackjackGame game = new BlackjackGame();
        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.EIGHT));
        game.getPlayerHand().addCard(new Card(Suit.CLUBS, Rank.SIX));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.SIX));

        GameResult result = game.determineResult();
        view.showRoundResult(result);
        String expected = "Draw! ";

        assertEquals(expected, output.toString());
    }

    @Test
    void showPlayerBlackjackWin() {
        BlackjackGame game = new BlackjackGame();
        game.getPlayerHand().addCard(new Card(Suit.SPADES, Rank.ACE));
        game.getPlayerHand().addCard(new Card(Suit.CLUBS, Rank.KING));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
        game.getDealerHand().addCard(new Card(Suit.DIAMONDS, Rank.SIX));

        GameResult result = game.determineResult();
        view.showBlackjackResult(result);
        String expected = "Blackjack! You win the round! ";

        assertEquals(expected, output.toString());
    }

    @Test
    void showDealerBlackjackWin() {
        BlackjackGame game = new BlackjackGame();
        game.getDealerHand().addCard(new Card(Suit.SPADES, Rank.ACE));
        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.KING));
        game.getPlayerHand().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
        game.getPlayerHand().addCard(new Card(Suit.DIAMONDS, Rank.SIX));

        GameResult result = game.determineResult();
        view.showBlackjackResult(result);
        String expected = "The dealer has blackjack and wins the round! ";

        assertEquals(expected, output.toString());
    }

    @Test
    void showBlackjackDraw() {
        BlackjackGame game = new BlackjackGame();
        game.getDealerHand().addCard(new Card(Suit.SPADES, Rank.ACE));
        game.getDealerHand().addCard(new Card(Suit.CLUBS, Rank.KING));
        game.getPlayerHand().addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        game.getPlayerHand().addCard(new Card(Suit.DIAMONDS, Rank.QUEEN));

        GameResult result = game.determineResult();
        view.showBlackjackResult(result);
        String expected = "Both have blackjack. Draw! ";

        assertEquals(expected, output.toString());
    }

    @Test
    void showPlayerLeadingScore() {
        int playerWins = 5;
        int dealerWins = 3;

        view.showScore(playerWins, dealerWins);

        String expected = "Score: 5:3 You are leading."
            + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showDealerLeadingScore() {
        int playerWins = 5;
        int dealerWins = 8;

        view.showScore(playerWins, dealerWins);

        String expected = "Score: 5:8 The dealer is leading."
            + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void showDrawScore() {
        int playerWins = 5;
        int dealerWins = 5;

        view.showScore(playerWins, dealerWins);

        String expected = "Score: 5:5 "
            + System.lineSeparator();

        assertEquals(expected, output.toString());
    }
}