import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGameGUI extends JFrame {
    private JTextField[][] puzzleGrid;
    private JButton checkButton, newGameButton;

    private int[][] solution = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    private int[][] puzzle = new int[9][9];

    public SudokuGameGUI() {
        setTitle("Sudoku Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(9, 9));
        puzzleGrid = new JTextField[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                puzzleGrid[row][col] = new JTextField(1);
                puzzleGrid[row][col].setFont(new Font("Arial", Font.PLAIN, 24));
                puzzleGrid[row][col].setHorizontalAlignment(JTextField.CENTER);
                gridPanel.add(puzzleGrid[row][col]);
            }
        }

        JPanel buttonPanel = new JPanel();
        checkButton = new JButton("Check");
        newGameButton = new JButton("New Game");

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSudokuSolved()) {
                    JOptionPane.showMessageDialog(null, "Congratulations! Sudoku is solved.");
                } else {
                    JOptionPane.showMessageDialog(null, "Sudoku is not yet solved.");
                }
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateNewGame();
            }
        });

        buttonPanel.add(checkButton);
        buttonPanel.add(newGameButton);

        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        generateNewGame();
    }

    private boolean isSudokuSolved() {
        int[][] userSolution = new int[9][9];
        try {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    String cellValue = puzzleGrid[row][col].getText().trim();
                    if (!cellValue.isEmpty()) {
                        int num = Integer.parseInt(cellValue);
                        userSolution[row][col] = num;
                    } else {
                        return false; // An empty cell indicates an incomplete puzzle
                    }
                }
            }

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (userSolution[row][col] != solution[row][col]) {
                        return false;
                    }
                }
            }
            return true;
        } catch (NumberFormatException e) {
            return false; // Handle non-integer input
        }
    }

    private void generateNewGame() {
        // Initialize the puzzle with the solution
        for (int row = 0; row < 9; row++) {
            System.arraycopy(solution[row], 0, puzzle[row], 0, 9);
        }

        // Remove some numbers randomly to create blanks
        int blanksToRemove = 40; // Adjust the number of blanks as needed
        while (blanksToRemove > 0) {
            int row = (int) (Math.random() * 9);
            int col = (int) (Math.random() * 9);
            if (puzzle[row][col] != 0) {
                puzzle[row][col] = 0;
                puzzleGrid[row][col].setText("");
                puzzleGrid[row][col].setEditable(true);
                blanksToRemove--;
            }
        }

        // Set the remaining numbers as non-editable
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (puzzle[row][col] != 0) {
                    puzzleGrid[row][col].setText(String.valueOf(puzzle[row][col]));
                    puzzleGrid[row][col].setEditable(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SudokuGameGUI sudokuGame = new SudokuGameGUI();
                sudokuGame.setVisible(true);
            }
        });
    }
}
