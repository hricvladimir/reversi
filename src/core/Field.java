package core;

public class Field {

    private int size;
    private int freeTiles;
    private Tile[][] tiles;
    private Player player1;
    private Player player2;
    private Player playerOnTurn;
    private GameState state = GameState.PLAYING;

    public Field(Player player1, Player player2) { // default size
        if(player1 == player2) return;
        this.size = 8;
        initializeField(size, player1, player2);
    }

    public Field(int size, Player player1, Player player2) { // custom size
        if(player1 == player2 || size <= 4) return;
        this.size = size;
        initializeField(size, player1, player2);
    }

    private void initializeField(int size, Player player1, Player player2) {

        this.player1 = player1;
        this.player2 = player2;
        createField();
        playerOnTurn = player1;
        updateScore();

        stoneInitialization(player1, size/2-1, size/2-1);
        stoneInitialization(player1, size/2, size/2);
        stoneInitialization(player2, size/2, size/2-1);
        stoneInitialization(player2, size/2-1, size/2);
    }

    public void createField() {
        // creates field and fills it with empty Tiles
        this.tiles = new Tile[size][size];
        createTiles(); // fills Field with Tiles
        this.freeTiles = size*size -4; // update free tiles
    }

    private void createTiles() {
        for(int row = 0; row < size; row++)
            for(int col = 0; col < size; col++) {
                this.tiles[row][col] = new Tile(row, col);
            }
    }

    private void stoneInitialization(Player player, int row, int col) {
        Stone stone = new Stone(player);
        this.tiles[row][col].occupyTile(stone);
    }

    public boolean addStoneToField(Player player, int row, int col) {
        if(state == GameState.FINISHED || tiles[row][col].getTileState() == TileState.OCCUPIED) return false;

        markStones(row, col);
        if(!changeColors()) return false;

        changeTurn();
        stoneInitialization(player, row, col);
        freeTiles -= 1;
        if(freeTiles == 0) state = GameState.FINISHED;
        updateScore();
        return true;
    }

    private void updateScore() {
        player1.setScore(0);
        player2.setScore(0);
        for(Tile[] tileRow : tiles)
            for(Tile tile : tileRow) {
                if(tile.getTileState() == TileState.OCCUPIED) {
                    tile.getStone().getPlayer().incrementScore();
                }
            }
    }

    public boolean changeColors() {
        // returns false if no colors changed

        boolean didColorsChange = false;

        for(Tile[] tileRow : tiles)
            for(Tile tile : tileRow) {
                if(tile.getStone() != null && tile.getStone().shouldChange()) {
                    tile.getStone().setPlayer(getPlayerOnTurn());
                    tile.getStone().setShouldChange(false);
                    didColorsChange = true;
                }
            }
        return didColorsChange;
    }

    public void changeTurn() {
        if(playerOnTurn == player1) playerOnTurn = player2;
        else playerOnTurn = player1;
    }

    public void markStones(int row, int col) {
        horizontalCheck(row, col);
        verticalCheck(row, col);
        diagonalCheck(row, col);
    }

    public boolean isMovePossible() {
        for(int row = 0; row < size; row++)
            for(int col = 0; col < size; col++) {
                horizontalCheck(row, col);
                verticalCheck(row, col);
                diagonalCheck(row, col);

                if(checkIfColorsChange()) return true;
            }
        return false;
    }

    public boolean checkIfColorsChange() {
        boolean didChange = false;
        for(int row = 0; row < size; row++)
            for(int col = 0; col < size; col++) {
                if(tiles[row][col].getTileState() != TileState.FREE)
                    if(tiles[row][col].getStone().shouldChange()) {
                        tiles[row][col].getStone().setShouldChange(false);
                        didChange = true;
                    }
            }
        return didChange;
    }

    private void horizontalCheck(int row, int col) {
        // left
        for(int i = col; i >= 0; i--) {
            if (tiles[row][i].getTileState() == TileState.FREE && i != col) break;

            if (i != col && tiles[row][i].getStone().getPlayer() == getPlayerOnTurn()) {
                markStonesInRow(i, col, row);
                break;
            }
        }

        for(int i = col; i < size; i++) {
            if (tiles[row][i].getTileState() == TileState.FREE && i != col) break;

            if (i != col && tiles[row][i].getStone().getPlayer() == getPlayerOnTurn()) {
                markStonesInRow(col, i, row);
                break;
            }
        }
    }

    private void verticalCheck(int row, int col) {

        // left
        for(int i = row; i >= 0; i--) {
            if (tiles[i][col].getTileState() == TileState.FREE && i != row) break;

            if (i != row && tiles[i][col].getStone().getPlayer() == getPlayerOnTurn()) {
                markStonesInCol(i, row, col);
                break;
            }
        }

        // right
        for(int i = row; i < size; i++) {
            if(tiles[i][col].getTileState() == TileState.FREE && i != row) break;

            if (i != row && tiles[i][col].getStone().getPlayer() == getPlayerOnTurn()) {
                markStonesInCol(row,i, col);
                break;
            }
        }
    }

    private void diagonalCheck(int row, int col) {
        int rowChecking = row;
        int colChecking = col;

        // up left
        while(rowChecking != 0 || colChecking != 0) {
            if (determineMarking(row, col, rowChecking, colChecking)) break;
            rowChecking--;
            colChecking--;
        }

        rowChecking = row;
        colChecking = col;

        // up right
        while(rowChecking != 0 || colChecking != size) {
            if (determineMarking(row, col, rowChecking, colChecking)) break;
            rowChecking--;
            colChecking++;
        }

        rowChecking = row;
        colChecking = col;

        // down left
        while(rowChecking != size || colChecking != 0) {
            if (determineMarking(row, col, rowChecking, colChecking)) break;
            rowChecking++;
            colChecking--;
        }

        rowChecking = row;
        colChecking = col;

        // down right
        while(rowChecking != size || colChecking != size) {
            if (determineMarking(row, col, rowChecking, colChecking)) break;
            rowChecking++;
            colChecking++;
        }
    }

    private boolean determineMarking(int row, int col, int rowChecking, int colChecking) {
        if(row < 0 || col < 0 || rowChecking < 0 || colChecking < 0 || row >= size || col >= size || rowChecking >= size || colChecking >= size) return true;

        if(tiles[rowChecking][colChecking].getTileState() == TileState.OCCUPIED)
            if(Math.abs(row - rowChecking) == 1 && tiles[rowChecking][colChecking].getStone().getPlayer() == getPlayerOnTurn()) return true;

        if (tiles[rowChecking][colChecking].getTileState() == TileState.FREE && rowChecking != row && colChecking != col)
            return true;

        if (rowChecking != row && colChecking != col && tiles[rowChecking][colChecking].getStone().getPlayer() == getPlayerOnTurn()) {
            markStonesDiagonally(rowChecking, row, colChecking, col);
        }
        return false;
    }

    public void markStonesInRow(int col1, int col2, int row) {

        for(int i = col1 +1; i < col2; i++) {
            tiles[row][i].getStone().setShouldChange(true);
        }
    }

    public void markStonesInCol(int row1, int row2, int col) {
        for(int i = row1 +1; i < row2; i++) {
            tiles[i][col].getStone().setShouldChange(true);
        }
    }

    public void markStonesDiagonally(int row1, int row2, int col1, int col2) {
        while(row1 != row2 && col1 != col2) {
            tiles[row1][col1].getStone().setShouldChange(true);
            if(row1 > row2) {
                if(col1 > col2) {
                    col1--;
                }
                else col1++;
                row1--;
            } else {
                if(col1 > col2) {
                    col1--;
                }
                else col1++;
                row1++;
            }
        }
    }

    public boolean isFieldFull() {
        return this.freeTiles == 0;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Player getPlayerOnTurn() {
        return playerOnTurn;
    }

    public GameState getState() {
        return state;
    }

    public int getSize() {
        return size;
    }

    public int getFreeTiles() {
        return freeTiles;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
