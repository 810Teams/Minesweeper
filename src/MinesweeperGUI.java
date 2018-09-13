/**
 * MinesweeperGUI class
 * by Teerapat Kraisrisirikul
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class MinesweeperGUI extends MinesweeperCore {
    private final String MINE_IMAGE = "resources/mine.png";
    private final String FLAG_IMAGE = "resources/flag.png";
    private boolean isFlagMode;
    private int gameStatus;
    private JFrame gameFrame;
    private JPanel boardPanel, menuZone, menuBox, menu;
    private JTextField messageBox;
    private JButton[][] boardCell;
    private JButton flagButton, restartButton, exitButton;

    public MinesweeperGUI(int rows, int columns, int mines) {
        // Constructor
        super(rows, columns, mines);
        isFlagMode = false;
        gameStatus = 0;
        boardPanel = new JPanel(new GridLayout(rows, columns));
        menuZone = new JPanel(new GridLayout(2, 1));
        menuBox = new JPanel();
        menu = new JPanel(new GridLayout(1, 3));
        messageBox = new JTextField("Welcome to Minesweeper!");
        boardCell = new JButton[rows][columns];
        flagButton = new JButton("Flag Mode: OFF");
        restartButton = new JButton("Restart");
        exitButton = new JButton("Exit");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boardCell[i][j] = new JButton();
                boardCell[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                boardCell[i][j].addActionListener(new ButtonListener());
                boardPanel.add(boardCell[i][j]);
            }
        }

        gameFrame = new JFrame("Minesweeper");

        flagButton.setForeground(new Color(180, 17, 2));
        flagButton.setFont(new Font("Arial", Font.PLAIN, 14));
        restartButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));

        flagButton.addActionListener(new ButtonListener());
        restartButton.addActionListener(new ButtonListener());
        exitButton.addActionListener(new ButtonListener());

        messageBox.setFont(new Font("Arial", Font.PLAIN, 18));
        messageBox.setEditable(false);
        messageBox.setHorizontalAlignment(JTextField.CENTER);
        messageBox.setForeground(new Color(13, 141, 215));

        menu.add(flagButton);
        menu.add(restartButton);
        menu.add(exitButton);
        menuBox.add(menu, BorderLayout.CENTER);

        menuZone.add(messageBox);
        menuZone.add(menuBox);

        gameFrame.add(boardPanel);
        gameFrame.add(menuZone, BorderLayout.SOUTH);
    }

    public void run() {
        // Object Method: Run the game
        gameFrame.setSize(30 * columns, 30 * rows + 100);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    private void clickCell(int i, int j) {
        // Object Method: Click cell
        if (isRevealed(i, j))
            return; // DEBUG: Duplicated cell revealing
        revealCell(i, j);
        if (boardData[i][j] == 0) {
            clickCell(max(i - 1, 0), max(j - 1, 0));
            clickCell(max(i - 1, 0), j);
            clickCell(max(i - 1, 0), min(j + 1, columns - 1));
            clickCell(i, min(j + 1, columns - 1));
            clickCell(min(i + 1, rows - 1), min(j + 1, columns - 1));
            clickCell(min(i + 1, rows - 1), j);
            clickCell(min(i + 1, rows - 1), max(j - 1, 0));
            clickCell(i, max(j - 1, 0));
        }
    }

    private void revealCell(int i, int j) {
        // Object Method: Reveal cell
        boardCell[i][j].setEnabled(false);

        if (boardCell[i][j].getIcon() != null) {
            flags++;
            boardCell[i][j].setIcon(null);
            updateFlagsMessage();
        }

        if (boardData[i][j] == 0) {
            boardCell[i][j].setText(" ");
        } else if (boardData[i][j] == -1) {
            try {
                Image img = ImageIO.read(getClass().getResource(MINE_IMAGE));
                boardCell[i][j].setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            boardCell[i][j].setText(Integer.toString(boardData[i][j]));
        }
    }

    private void flagCell(int i, int j) {
        // Object Method: Flag cell
        if (boardCell[i][j].getIcon() == null && flags > 0) {
            flags--;
            try {
                Image img = ImageIO.read(getClass().getResource(FLAG_IMAGE));
                boardCell[i][j].setIcon(new ImageIcon(img));
                boardCell[i][j].setEnabled(true);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (boardCell[i][j].getIcon() != null) {
            flags++;
            boardCell[i][j].setIcon(null);
        }
    }

    private boolean isRevealed(int i, int j) {
        // Object Method: Check if the cell is revealed
        if (!boardCell[i][j].isEnabled())
            return true;
        return false;
    }

    private void updateGameStatus() {
        // Object Method: Update game status
        // 0 : In Progress
        // 1 : Won
        // 2 : Lose

        // Case I: Mine revealed
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (isRevealed(i, j) && boardCell[i][j].getIcon() != null) {
                    gameStatus = 2;
                    return;
                }
            }
        }

        // Case II: At least one empty cell still exists
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!isRevealed(i, j) && boardCell[i][j].getIcon() == null) {
                    gameStatus = 0;
                    return;
                }
            }
        }

        // Case III: All cell are not empty & No bomb discovered
        gameStatus = 1;
    }

    private void updateFlagButton() {
        if (isFlagMode) {
            flagButton.setText("Flag Mode: ON");
            flagButton.setForeground(new Color(23, 199, 35));
        } else {
            flagButton.setText("Flag Mode: OFF");
            flagButton.setForeground(new Color(180, 17, 2));
        }
    }

    private void resetMessage() {
        // Object Method: Reset message
        messageBox.setText("Welcome to Minesweeper!");
        messageBox.setForeground(new Color(13, 141, 215));
    }

    private void updateFlagsMessage() {
        // Object Method: Update remaining flags message
        messageBox.setText("Flags Remaining: " + flags);
        messageBox.setForeground(new Color(13, 141, 215));
    }

    private void updateGameStatusMessage() {
        // Object Method: Update remaining flags message
        if (gameStatus == 1) {
            messageBox.setText("You win!");
            messageBox.setForeground(new Color(23, 199, 35));
        } else if (gameStatus == 2) {
            messageBox.setText("You lose!");
            messageBox.setForeground(new Color(180, 17, 2));
        }
    }

    private void restart() {
        // Object Method: Restart
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boardCell[i][j].setText("");
                boardCell[i][j].setIcon(null);
                boardCell[i][j].setEnabled(true);
            }
        }
        isFlagMode = false;
        updateFlagButton();
        resetFlags();
        resetMessage();
        randomBoardData();
        updateGameStatus();
    }

    private void exit() {
        // Object Method: Exit
        System.exit(0);
    }

    public void debugGUI() {
        // Object Method: Debug
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                revealCell(i, j);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (e.getSource() == boardCell[i][j] && gameStatus == 0) {
                        if (isFlagMode)
                            flagCell(i, j);
                        else
                            clickCell(i, j);

                        updateFlagsMessage();
                        updateGameStatus();
                        updateGameStatusMessage();
                    }
                }
            }

            if (e.getSource() == flagButton) {
                isFlagMode = !isFlagMode;
                updateFlagButton();
            }

            if (e.getSource() == restartButton)
                restart();

            if (e.getSource() == exitButton)
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?") == 0)
                    exit();
        }
    }
}
