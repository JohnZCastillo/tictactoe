import java.util.Scanner;

public class Tictactoe{

    static Scanner input;
    static String [] board;
    static String winner;
    
    static String [] players;

    //for checking board 
    static final int [] config = {
        1,2,3,
        4,5,6,
        7,8,9,
        1,4,7,
        2,5,8,
        3,6,9,
        1,5,9,
        3,5,7,
    };

    
    public static boolean isNull(int index){
        return board[index-1] == null;
    }

    public static boolean insertToBoard(String player, int index){
        if(!isNull(index))return false;
        board[index-1] = player;
        return true;
    }

    public static int getUserInput(){
        System.out.print("Your Turn (box number): ");
        return input.nextInt();
    }

    public static boolean isBoardFull(){
        for(int i = 1; i<= 9; i++){
            if(isNull(i)) return false;
        }
        return true;
    }

    public static void displayBoard(String [] board){
        double count = 0;
        System.out.println("");
        for (String box : board) {
            if(box == null){
                System.out.print(" |");
            }else{
                System.out.print(box+"|");
            }
            count++;
            if(count % 3 == 0) System.out.println("");
            
        }
    }

    public static boolean pair(String player, int target,int a,int b,int c){
        int count = 0;
        count = player == board[a-1] ? ++count : count;
        count = player == board[b-1] ? ++count : count;
        count = player == board[c-1] ? ++count : count;
        return target == count;
    }


    public static boolean hasWinner(){
        for (String player : players) {
            for(int i = 0; i < config.length; i+=3){
                if(pair(player, 3, config[i], config[i+1], config[i+2])){
                    winner = player;
                    return true;
                } 
            } 
        }
        return false;
    }


    public static int getRandom(int max){
    	return (int) Math.random()*max;
    }

    public static boolean hasNullGroup(int [] indexes){
        for (int index : indexes) {
            if(isNull(index)) return true;
        }
        return false;
    }

    public static int guess(int [] data){
        int randomIndex = getRandom(data.length);
        while(!isNull(data[randomIndex])){
            randomIndex = getRandom(data.length);
        }
        return data[randomIndex];
    }

    public static int getComputer(){
        
        // pririoty pick basi sa first move (0 = side | 1 = cross)
        int code = 0;
        int [] side = {1,3,7,9};
        int [] cross = {2,4,6,8};

        //priority center
        if(isNull(5)){
            code = 1;
            return 5;
        }
        

        //find possible bingo self before opponent
        for (int player = players.length-1; player >= 0; player--) {
            for(int i = 0; i < config.length; i+=3){
                if(pair(players[player], 2, config[i], config[i+1], config[i+2])){
                    if(isNull(config[i])) return config[i];
                    if(isNull(config[i+1])) return config[i+1];
                    if(isNull(config[i+2])) return config[i+2];
                }
            }
        }
     
        if(code == 0){
            if(hasNullGroup(cross)) return guess(cross);
            if(hasNullGroup(side)) return guess(side);
        }
        
        if(hasNullGroup(side)) return guess(side);
        if(hasNullGroup(cross)) return guess(cross);
        

        //error
        return -1;
    }


    public static void main(String[] args) {
        
        //player token
        players = new String[2];
        players[0]= "X";
        players[1] = "O";
        
        //initialize board
        board = new String[9];
        
        //display purpose only
        String [] numbering = {"1","2","3","4","5","6","7","8","9"};

        //scanner
        input = new Scanner(System.in);

        System.out.println("Welcome to tictactoe Game");
        System.out.println("Your token: X");
        System.out.println("Computer token: O");
        
        //for display box number only  
        System.out.println("\nBox number below");
        displayBoard(numbering);
        
        System.out.println("\n- Game Start -\n");

         while(!isBoardFull() && !hasWinner()){

            
            while (!insertToBoard(players[0],getUserInput()));
            displayBoard(board);

            //prevent sa computer mag tira pag may gana 
            if(isBoardFull() || hasWinner()) break;

            insertToBoard(players[1],getComputer());
            displayBoard(board);
         }

         if(isBoardFull()){
            System.out.println("Draw");
         }

         if(hasWinner()){
            System.out.println("Winner | "+winner);
         }
        
    }
}
