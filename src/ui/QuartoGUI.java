package ui;

import core.Game;
import core.exceptions.InvalidPiecePlacementException;
import core.pieces.Piece;
import core.pieces.Pieces;

import javax.swing.*;
import java.awt.*;

/**
 * The QuartoGUI class represents the graphical user interface (GUI) for the Quarto game.
 * It extends the JFrame class.
 */
public class QuartoGUI extends JFrame {
    /**
     * Dimensions of the GUI components
     */
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 900;
    public static final int SIDE_BUTTON_SIZE = 100;
    public static final int BOARD_BUTTON_SIZE = 200;

    /**
     * Label format for displaying player instructions
     */
    private static final String label = "%s, choose piece for %s";

    /**
     * Game instance
     */
    private final Game game;

    private int player1Wins = 0;
    private int player2Wins = 0;

    /**
     * Array to store the buttons representing the game board
     */
    private PieceShapeButton[][] boardButtons;

    /**
     * Selected game piece and its corresponding button
     */
    private Piece selectedPiece;
    private PieceShapeButton selectedPieceShapeButton;

    /**
     * JLabel for displaying game information
     */
    private final JLabel infoLabel = new JLabel();
    private final JLabel winCountLabel = new JLabel();


    /**
     * JPanels for organizing GUI components
     */
    private final JPanel infoPanel;
    private final JPanel boardPanel;
    private final JPanel pieceWrapperPanel;
    private final JPanel controlPanel;

    /**
     * Constructs a new QuartoGUI instance, initializing the GUI components and setting up the game.
     */
    public QuartoGUI() {
        showInitialPopup();

        boardButtons = new PieceShapeButton[4][4];
        setTitle("Quarto Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        infoLabel.setForeground(Color.WHITE);

        game = new Game();

        infoPanel = constructInfoPanel();
        boardPanel = constructBoardButtonsPanel();
        pieceWrapperPanel = constructPiecesPanel();
        controlPanel = constructControlPanel();

        add(boardPanel, BorderLayout.CENTER);
        add(pieceWrapperPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);

        setLocationRelativeTo(null);
        setVisible(false); 

        String firstInfo = String.format(label + " to start the game!", Game.Turn.PLAYER_2, Game.Turn.PLAYER_1);
        updateLabel(firstInfo);
        updateWinCountLabel();
    }

    /**
     * Displays the initial pop-up window that starts the game
     */
    private void showInitialPopup() {
        JFrame startFrame = new JFrame("QUARTO");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(400, 100);
        startFrame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton startGameButton = new JButton("Start Game");
        JButton rulesButton = new JButton("View Rules");

        rulesButton.addActionListener((e) -> showRules());
        startGameButton.addActionListener((e) -> {
            startFrame.dispose(); 
            setVisible(true); 
        });

        buttonPanel.add(startGameButton);
        buttonPanel.add(rulesButton);

        startFrame.add(buttonPanel, BorderLayout.CENTER);

        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
    }

    /**
     * Method for constructing the panel containing the pieces for selection
     * @return wrapperPanel
     */
    private JPanel constructPiecesPanel() {
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));

        JPanel piecesPanel = new JPanel();
        piecesPanel.setLayout(new GridLayout(8, 2));

        wrapperPanel.add(piecesPanel);
        for (Piece piece : Pieces.getAllPieces()) {
            PieceShapeButton pieceButton = getPieceShapeButton(piece);
            piecesPanel.add(pieceButton);
        }

        return wrapperPanel;
    }

    /**
     * Method for constructing the panel containing the game board buttons
     * @return boardPanel
     */
    private JPanel constructBoardButtonsPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(game.BOARD_SIZE, game.BOARD_SIZE));
        boardButtons = new PieceShapeButton[game.BOARD_SIZE][game.BOARD_SIZE]; // Initialize boardButtons array
        for (int i = 0; i < game.BOARD_SIZE; i++) {
            for (int j = 0; j < game.BOARD_SIZE; j++) {
                boardButtons[i][j] = getButton(boardButtons, i, j); // Update argument to include the boardButtons array
                boardPanel.add(boardButtons[i][j]);
            }
        }
        return boardPanel;
    }

    /**
     * Method for constructing the panel containing the game information label
     * @return bottomPanel
     */
    private JPanel constructInfoPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setForeground(Color.LIGHT_GRAY);
        bottomPanel.setBackground(Color.GRAY);

        bottomPanel.add(infoLabel);
        bottomPanel.add(winCountLabel);
        return bottomPanel;
    }

    /**
     * Method for handling button click events on the game board
     * @param buttons PieceShapeButton 2-dim array
     * @param i i - coordinate
     * @param j j - coordinate
     * @return button
     */
    private PieceShapeButton getButton(PieceShapeButton[][] buttons, int i, int j) {
        PieceShapeButton button = new PieceShapeButton();

        button.setPreferredSize(new Dimension(BOARD_BUTTON_SIZE, BOARD_BUTTON_SIZE));
        button.addActionListener((e) -> {
            try {
                // Ensure a piece is selected before placement
                if (selectedPiece == null) return;

                // Try to place the selected piece
                boolean placed = game.placePiece(i, j, selectedPiece);
                if (!placed) return;

                // Update game turn information
                Game.Turn turn = game.getTurn();
                switch (turn) {
                    case PLAYER_1 -> updateLabel(String.format(label, Game.Turn.PLAYER_2, Game.Turn.PLAYER_1));
                    case PLAYER_2 -> updateLabel(String.format(label, Game.Turn.PLAYER_1, Game.Turn.PLAYER_2));
                }

                // Mark the button with the placed piece
                buttons[i][j].setPiece(selectedPiece);
                buttons[i][j].repaint();
                selectedPieceShapeButton.disablePiece();

                // Clear the selected piece
                selectedPiece = null;
                selectedPieceShapeButton = null;

                // Check for win or draw
                if (game.checkWin()) {
                    showWinDialog();
                } else if (game.checkDraw()) {
                    showDrawDialog();
                }

            } catch (InvalidPiecePlacementException ex) {
                // Show a message dialog with the error information
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Placement Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return button;
    }

    /**
     * Method for displaying a dialog when a player wins
     */
    private void showWinDialog() {
        Game.Turn turn = game.getTurn();
        String winner = turn.toString();
        String message = String.format("""
                Congratulations! %s wins!
                Click Ok to start a new game!""", winner);
        String title = "Winner";

        switch (game.getTurn()) {
            case PLAYER_1 -> player2Wins++;
            case PLAYER_2 -> player1Wins++;
        }

        // Update the win count label after determining the winner
        updateWinCountLabel();
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
        restartGame();
    }

    /**
     * Method for displaying a dialog when the game ends in a draw
     */
    private void showDrawDialog() {
        JOptionPane.showMessageDialog(this, "It's a draw!", "Draw", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method for creating a PieceShapeButton for a given game piece
     * @param piece Piece class object
     * @return pieceButton
     */
    private PieceShapeButton getPieceShapeButton(Piece piece) {
        PieceShapeButton pieceButton = new PieceShapeButton(piece);
        pieceButton.setPreferredSize(new Dimension(SIDE_BUTTON_SIZE, SIDE_BUTTON_SIZE));
        pieceButton.addActionListener((e) -> {
            selectedPiece = piece;
            selectedPieceShapeButton = pieceButton;

            Game.Turn turn = game.getTurn();

            switch (turn) {
                case PLAYER_1 -> updateLabel(String.format("%s's turn. Piece to play: %s", Game.Turn.PLAYER_1, piece));
                case PLAYER_2 -> updateLabel(String.format("%s's turn. Piece to play: %s", Game.Turn.PLAYER_2, piece));
            }
        });
        return pieceButton;
    }

    /**
     * Method for constructing the panel containing the control button(s)
     * @return controlPanel
     */
    private JPanel constructControlPanel() {
        JPanel controlPanel = new JPanel();    
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener((e) -> restartGame());
        controlPanel.add(restartButton);
        return controlPanel;
    }

    /**
     * Method for resetting the game state
     */
    private void resetGame() {
        game.reset();
    }

    /**
     * Method for showing the game rules in different languages
     */
    private void showRules() {
        JFrame rulesFrame = new JFrame("Select Language:");

        JPanel controlPanel = new JPanel();

        JButton rulesButton1 = new JButton("English");
        JButton rulesButton2 = new JButton("Russian");
        JButton rulesButton3 = new JButton("Armenian");

        rulesButton1.addActionListener((e) -> showRulesEnglish());
        rulesButton2.addActionListener((e) -> showRulesRussian());
        rulesButton3.addActionListener((e) -> showRulesArmenian());

        controlPanel.add(rulesButton1);
        controlPanel.add(rulesButton2);
        controlPanel.add(rulesButton3);

        rulesFrame.add(controlPanel);
        rulesFrame.setSize(300, 150);
        rulesFrame.setLocationRelativeTo(null);
        rulesFrame.setVisible(true);
    }

    /**
     * Helper Methods for showRules() method
     */
    private void showRulesEnglish() {
        JOptionPane.showMessageDialog(this, """
                        Here are the basic rules:
                        1. Players take turns placing pieces on the board.
                        2. Once a piece is placed on the board, it cannot be moved or removed.
                        3. The player who places a piece chooses which piece the opponent will play next.
                        4. The game continues until a player forms a line of four pieces with a common attribute (e.g., four tall pieces, four dark pieces, etc.).
                        5. If the board is filled without any player achieving a winning line, the game ends in a draw.""", "Game Rules",
        JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRulesRussian() {
        JOptionPane.showMessageDialog(this, """
                        Вот основные правила:
                        1. Игроки по очереди выставляют фигуры на доску.
                        2. После того, как фигура помещена на доску, ее нельзя переместить или удалить.
                        3. Игрок, выставивший фигуру, выбирает, какой фигурой соперник будет играть следующей.
                        4. Игра продолжается до тех пор, пока игрок не выстроит линию из четырех фигур с общим признаком (например, четыре высокие фигуры, четыре темные фигуры и т.д.).
                        5. Если доска заполнена и ни один игрок не достиг выигрышной линии, игра заканчивается вничью.""", "Правила игры",
        JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRulesArmenian() {
        JOptionPane.showMessageDialog(this, """
                        Ահա հիմնական կանոնները.
                        1. Խաղացողները հերթով խաղաքարեր են դնում խաղատախտակի վրա:
                        2. Երբ կտորը տեղադրվում է տախտակի վրա, այն հնարավոր չէ տեղափոխել կամ հեռացնել:
                        3. Խաղը տեղադրած խաղացողն ընտրում է, թե որ խաղաքարը կխաղա հաջորդը հակառակորդը:
                        4. Խաղը շարունակվում է այնքան ժամանակ, մինչև խաղացողը կառուցի չորս կտորներից բաղկացած գիծ՝ ընդհանուր հատկանիշով (օրինակ՝ չորս բարձր, չորս մուգ խաղաքարեր և այլն):
                        5. Եթե խաղատախտակը լի է, և ոչ մի խաղացող չի հասել հաղթական գծին, խաղն ավարտվում է ոչ-ոքի:""", "Խաղի կանոններ",
        JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Method for restarting the game
     */
    private void restartGame() {
        resetGame();
        resetLeftSidePiecePanel();
        resetBoardButtons();
    }

    /**
     * Method for resetting the piece selection panel
     */
    private void resetLeftSidePiecePanel() {
        JPanel piecePanel = (JPanel) pieceWrapperPanel.getComponents()[0];
        Component[] components = piecePanel.getComponents();
        for (Component component : components) {
            if (component instanceof PieceShapeButton button) {
                button.enablePiece();
            }
        }
    }

    /**
     * Method for resetting the board buttons
     */
    private void resetBoardButtons() {
        Component[] components = boardPanel.getComponents();
        for (Component component : components) {
            if (component instanceof PieceShapeButton button) {
                button.setPiece(null);
                button.enablePiece();
                button.repaint();
            }
        }
    }

    /**
     * Method for count label setting
     */
    private void updateWinCountLabel() {
        winCountLabel.setText(String.format("%s Wins: %d | %s Wins: %d", Game.Turn.PLAYER_1, player1Wins, Game.Turn.PLAYER_2, player2Wins));
        winCountLabel.repaint();
    }

    /**
     * Method for updating the game information label
     * @param text String value
     */
    private void updateLabel(String text) {
        infoLabel.setText(text);
        infoLabel.repaint();
    }
}
