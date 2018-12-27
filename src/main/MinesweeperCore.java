/**
 * `MinesweeperCore` Abstract Class
 * by Teerapat Kraisrisirikul
 */

package main;

public abstract class MinesweeperCore {
    protected int rows, columns, mines, flags, gameStatus;
    protected int[][] boardData;
    protected boolean flagMode;

    public MinesweeperCore(int rows, int columns, int mines) {
        setRows(rows);
        setColumns(columns);
        setMines(mines);
        resetFlags();
        createBoardData();
        randomBoardData();
        gameStatus = 0;
        flagMode = false;
    }

    protected boolean setRows(int rows) {
        // Object Method: Set Rows
        if (rows > 0) {
            this.rows = rows;
            return true;
        }
        return false;
    }

    protected boolean setColumns(int columns) {
        // Object Method: Set Columns
        if (columns > 0) {
            this.columns = columns;
            return true;
        }
        return false;
    }

    protected boolean setMines(int mines) {
        // Object Method: Set Mines
        if (mines > 0 && mines <= columns * rows / 4) {
            this.mines = mines;
            return true;
        }
        return false;
    }

    protected void resetFlags() {
        // Object Method: Reset Flags
        this.flags = this.mines;
    }

    protected void createBoardData() {
        // Object Method: Create Board Data
        if (setColumns(this.columns) && setRows(this.rows) && setMines(this.mines)) {
            boardData = new int[rows][columns];
        } else {
            System.exit(0);
        }

    }

    protected void randomBoardData() {
        // Object Method: Random Board Data
        int minesLeft = this.mines;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boardData[i][j] = 0;
            }
        }

        while (minesLeft > 0) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if ((int) (Math.random() * columns * rows) <= mines && minesLeft > 0 && boardData[i][j] != -1) {
                        minesLeft--;
                        boardData[i][j] = -1;
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (boardData[i][j] != -1) {
                    boardData[i][j] = countAround(i, j);
                }
            }
        }
    }

    protected int countAround(int i, int j) {
        int minesAround = 0;

        if (isValidCell(i - 1, j - 1))
            if (boardData[i - 1][j - 1] == -1)
                minesAround++; // NW
        if (isValidCell(i - 1, j))
            if (boardData[i - 1][j] == -1)
                minesAround++; // N
        if (isValidCell(i - 1, j + 1))
            if (boardData[i - 1][j + 1] == -1)
                minesAround++; // NE
        if (isValidCell(i, j + 1))
            if (boardData[i][j + 1] == -1)
                minesAround++; // E
        if (isValidCell(i + 1, j + 1))
            if (boardData[i + 1][j + 1] == -1)
                minesAround++; // SE
        if (isValidCell(i + 1, j))
            if (boardData[i + 1][j] == -1)
                minesAround++; // S
        if (isValidCell(i + 1, j - 1))
            if (boardData[i + 1][j - 1] == -1)
                minesAround++; // SW
        if (isValidCell(i, j - 1))
            if (boardData[i][j - 1] == -1)
                minesAround++; // W

        return minesAround;
    }

    protected boolean isValidCell(int i, int j) {
        return i >= 0 && j >= 0 && i < rows && j < columns;
    }
}
