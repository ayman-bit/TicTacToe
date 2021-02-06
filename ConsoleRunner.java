import java.util.Scanner;

public class ConsoleRunner {

    /**
     * Should the human player be the X?  Note that X always
     * goes first.
     */
    private boolean playerIsX;

    private Game game;
    
    // Use to process text input from the user.
    private Scanner scanner = new Scanner(System.in);

    private boolean challenging;

    /**
     * Constructor
     */
    public ConsoleRunner() {
        this.gameStart();
        this.game = new Game(this.playerIsX, this.challenging);
        printBoard();
    }

    /**
     * Enter the main control loop which returns only at the end of the game
     * when one party has won or there has been a draw.
     */
    public void mainLoop() {
        while(this.game.getStatus() == GameStatus.IN_PROGRESS) {
            if (playerIsX)
                getAndMakePlayerMove();
            else
                getAndMakeAIMove();

            if (this.game.getStatus() != GameStatus.IN_PROGRESS)
                break;
            printBoard();

            if (playerIsX)
                getAndMakeAIMove();
            else
                getAndMakePlayerMove();

            if (this.game.getStatus() != GameStatus.IN_PROGRESS)
                break;
            printBoard();
        }
        printBoard();
        GameOver();
    }

    private void printBoard(){
        System.out.print(this.game.getBoard().toString());
    }

    private void GameOver(){
        String GameResults = "";
        if(this.game.getStatus()==GameStatus.X_WON && playerIsX)
            GameResults = "Congratulations You WON!";
        else if(this.game.getStatus()==GameStatus.X_WON && !playerIsX)
            GameResults = "AI WON!";
        else if(this.game.getStatus()==GameStatus.O_WON && !playerIsX)
            GameResults = "Congratulations You WON!";
        else if(this.game.getStatus()==GameStatus.O_WON && playerIsX)
            GameResults = "AI WON!";
        else if(this.game.getStatus()==GameStatus.DRAW)
            GameResults = "Game Was a DRAW!! Good Work!! ";
        System.out.print("#####################\n");
        System.out.print(GameResults + "\n");
        System.out.print("#####################\n");
    }

    private void getAndMakePlayerMove(){
        boolean validMove = false;
        int i = 0;
        int j = 0;
        while(!validMove) {
            char playerPiece = this.playerIsX ? 'X' : 'O';
            System.out.print("Where would you like to place your " + playerPiece + " piece?\n");

            while(true){
                System.out.print("'X' Coordinate first.\n");
                try{
                    i = this.scanner.nextInt();
                    //valid input is 0-2
                    if((0 <= i)&(i < 3)){
                        break;
                    }else{
                        System.out.print("Not Valid 'X'! Between 0 and 2 please! \n");
                    }
                }catch(Exception e){
                    System.out.print("Not Valid 'X'! Integer please! \n");
                    scanner.next(); // pull out invalid keystroke from scanner to allow for new input
                }
            }

            while(true){
                System.out.print("'Y' Coordinate second.\n");
                try{
                    j = this.scanner.nextInt();
                    //valid input is 0-2
                    if((0 <= j)&(j < 3)){
                        break;
                    }else{
                        System.out.print("Not Valid 'Y'! Between 0 and 2 please! \n");
                    }
                }catch(Exception e){
                    System.out.print("Not Valid 'Y'! Integer please! \n");
                    scanner.next(); // pull out invalid keystroke from scanner to allow for new input
                }
            }

            validMove = this.game.placePlayerPiece(i, j);
            if(!validMove)
                System.out.print("This is not a valid position.\n");
        }
    }

    private void getAndMakeAIMove(){
        this.game.aiPlacePiece();
        System.out.print("AI Move Made \n");
    }

    public void gameStart() {
        boolean valid = false;
        while (!valid) {
            System.out.println("Do you want to be X? (Y/N)");
            String response = this.scanner.next();
            if (response.matches("Y") || response.matches("y")) {
                this.playerIsX = true;
                valid = true;
            } else if (response.matches("N") || response.matches("n")) {
                this.playerIsX = false;
                valid = true;
            } else {
                System.out.println("Invalid Entry!");
            }
        }
        valid  = false;
        while(!valid) {
            System.out.println("Do you want a challenge? (Y/N)");
            String response = this.scanner.next();
            if (response.matches("Y") || response.matches("y")) {
                this.challenging = true;
                valid = true;
            } else if (response.matches("N") || response.matches("n")) {
                this.challenging = false;
                valid = true;
            } else {
                System.out.println("Not a valid selection, please try again.");
            }
        }
    }
}