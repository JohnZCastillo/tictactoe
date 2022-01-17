import java.util.Scanner;

public class Tictactoe{

    static Scanner input;
    static String [] board;
    static String winner;
    static String player1;
    static String player2;

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
        for(int i = 0; i < config.length; i+=3){
            if(pair(player1, 3, config[i], config[i+1], config[i+2])){
                winner = player1;
                return true;
            } 

            if(pair(player2, 3, config[i], config[i+1], config[i+2])){
                winner = player2;
                return true;
            }
        }
        return false;
    }

    public static int getRandom(int max){
    	int temp =  (int) (Math.random()*max);
    	System.out.println("random called: "+temp);
        return temp;
    }

    public static boolean hasNullGroup(int [] indexes){
        for (int index : indexes) {
            if(isNull(index)) return true;
        }
        return false;
    }

    //computer guess algo
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

        //priority center
        if(isNull(5)){
            code = 1;
            return 5;
        }
        

        //find possible bingo
        for(int i = 0; i < config.length; i+=3){
            //check self
            if(pair(player2, 2, config[i], config[i+1], config[i+2])){
                if(isNull(config[i])) return config[i];
                if(isNull(config[i+1])) return config[i+1];
                if(isNull(config[i+2])) return config[i+2];
            }
            //check opponent
            if(pair(player1, 2, config[i], config[i+1], config[i+2])){
                if(isNull(config[i])) return config[i];
                if(isNull(config[i+1])) return config[i+1];
                if(isNull(config[i+2])) return config[i+2];
            }
        }

       
        int [] side = {1,3,7,9};
        int [] cross = {2,4,6,8};


        if(code == 0){
            if(hasNullGroup(cross)) return guess(cross);
            if(hasNullGroup(side)) return guess(side);
        }else{
            if(hasNullGroup(side)) return guess(side);
            if(hasNullGroup(cross)) return guess(cross);
        }

        //error
        return -1;
    }

    public static void main(String[] args) {
        
        //player token
        player1 = "X";
        player2 = "O";
        
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

            
            while (!insertToBoard(player1,getUserInput()));
            displayBoard(board);

            //prevent sa computer mag tira pag may gana 
            if(isBoardFull() || hasWinner()) break;

            insertToBoard(player2,getComputer());
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
