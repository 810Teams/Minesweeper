/**
 * MinesweeperLauncher class
 * by Teerapat Kraisrisirikul
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MinesweeperLauncher {
    private int rows, columns, mines;
    private MinesweeperGUI game;
    private JFrame frame;
    private JPanel settingsPanel, menuPanel;
    private JLabel rowsLabel, columnsLabel, minesLabel;
    private JTextField rowsField, columnsField, minesField;
    private JButton startButton, exitButton;

    public MinesweeperLauncher() {
        // Constructor
        frame = new JFrame("Minesweeper Launcher");
        settingsPanel = new JPanel(new GridLayout(3, 2));
        menuPanel = new JPanel(new GridLayout(1, 2));

        rowsLabel = new JLabel("Rows:");
        columnsLabel = new JLabel("Columns:");
        minesLabel = new JLabel("Mines:");

        rowsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        columnsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        minesLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        rowsField = new JTextField();
        columnsField = new JTextField();
        minesField = new JTextField();

        rowsField.setFont(new Font("Arial", Font.PLAIN, 14));
        columnsField.setFont(new Font("Arial", Font.PLAIN, 14));
        minesField.setFont(new Font("Arial", Font.PLAIN, 14));

        startButton = new JButton("Start");
        exitButton = new JButton("Exit");

        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));

        startButton.addActionListener(new ButtonListener());
        exitButton.addActionListener(new ButtonListener());

        settingsPanel.add(rowsLabel);
        settingsPanel.add(rowsField);
        settingsPanel.add(columnsLabel);
        settingsPanel.add(columnsField);
        settingsPanel.add(minesLabel);
        settingsPanel.add(minesField);

        menuPanel.add(startButton);
        menuPanel.add(exitButton);

        frame.add(settingsPanel);
        frame.add(menuPanel, BorderLayout.SOUTH);
    }

    public void run() {
        // Object Method: Run launcher
        frame.setSize(275, 175);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void start() {
        // Object Method: Start game
        game = new MinesweeperGUI(rows, columns, mines);
        game.run();
        frame.setVisible(false);
    }

    private boolean checkValid() {
        // Object Method: Check valid
        try {
            rows = Integer.parseInt(rowsField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid row amount format.");
            return false;
        }

        try {
            columns = Integer.parseInt(columnsField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid column amount format.");
            return false;
        }

        try {
            mines = Integer.parseInt(minesField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid mine amount format.");
            return false;
        }

        if (rows < 10) {
            JOptionPane.showMessageDialog(null, "Row amount must be at least 10.");
            return false;
        } else if (columns < 10) {
            JOptionPane.showMessageDialog(null, "Column amount must be at least 10.");
            return false;
        } else if (mines < 1) {
            JOptionPane.showMessageDialog(null, "Mine amount must be at least 1.");
            return false;
        } else if (mines > (int) (rows * columns / 5)) {
            JOptionPane.showMessageDialog(null, "Mine amount must not cover over 20% (" + (int) (rows * columns / 5) + ") of the board.");
            return false;
        }
        return true;
    }

    private void exit() {
        // Object Method: Exit launcher
        System.exit(0);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton)
                if (checkValid())
                    start();

            if (e.getSource() == exitButton)
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?") == 0)
                    exit();
        }
    }
}
