/**
 * Represents the logic of the game in terms of detecting wins or draws.  Also
 * places new pieces for the human player or AI.
 *
 * @author Ayman Abu Awad
 */

public class Game {
    private Board board = new Board();
    private GameStatus status;
    private AI ai;
    private boolean playerIsX;

    /**
     * Construct a new Game according to the given parameters.
     */
    public Game(boolean playerIsX, boolean challenging) {
        this.playerIsX=playerIsX;
        this.status = GameStatus.IN_PROGRESS;
        if(challenging == false){
            this.ai = new DumbAI(!playerIsX);
        }
        else{
            this.ai = new SmartAI(!playerIsX);
            }
    }

    /**
     * Return a copy of the board's current contents.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Get the game's status.
     */
    public GameStatus getStatus() {
        return this.status;
    }

    public void updateGameStatus(int x, int y, char piece){

        GameStatus PieceStatus = piece=='X'?GameStatus.X_WON:GameStatus.O_WON;

        //Check the horizontal win scenario
        for(int i=0;i<3;i++){
            if(this.board.get(x,i)!=piece)
                break;
            if(i==2) {
                this.status = PieceStatus;
                return;
            }
        }

        //Check the vertical win scenario
        for(int i=0;i<3;i++){
            if(this.board.get(i,y)!=piece)
                break;
            if(i==2) {
                this.status = PieceStatus;
                return;
            }
        }

        //Check the diagonal win scenario
        if(x==y){
            for(int i=0;i<3;i++){
                if(this.board.get(i,i)!=piece)
                    break;
                if(i==2) {
                    this.status = PieceStatus;
                    return;
                }
            }
        }

        //Check the anti-diagonal (reverse diagonal) win scenario
        if(x+y==2){
            for(int i=0;i<3;i++){
                if(this.board.get(i,2-i)!=piece)
                    break;
                if(i==2) {
                    this.status = PieceStatus;
                    return;
                }
            }
        }

        //Check if board is full and no win
        if(this.board.isFull()){
            this.status = GameStatus.DRAW;
            return;
        }
    }
    
    /**
     * Place a piece for the player on the board.
     * @param i i-coordinate of desired position.
     * @param j j-coordinate of desired position
     * @return true only if the coordinates of the desired position are in
     * range and the corresponding cell is empty.
     *
     * @precondition status == IN_PROGRESS
     *
     */
    public boolean placePlayerPiece(int i, int j) {
        
        if((this.status == GameStatus.IN_PROGRESS) && ((i >= 0 && i <= 2)&&(j >= 0 && j <= 2)) && this.board.get(i,j)==' '){
            this.board = new Board(this.board, new Move(i,j, this.playerIsX?'X':'O'));
            updateGameStatus(i,j, this.playerIsX?'X':'O');
            return true;
        }else{
            return false;
        }

    }

    /**
     * @precondition status == IN_PROGRESS
     */
    public void aiPlacePiece() {
        Move move = this.ai.chooseMove(this.board);
        if((this.status == GameStatus.IN_PROGRESS)){
            this.board = new Board(this.board, move);
            updateGameStatus(move.getI(),move.getJ(),move.getPiece());
        }
    }
}
