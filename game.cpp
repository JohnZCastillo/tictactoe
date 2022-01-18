using namespace std;
#include <stdio.h>
#include <string>
#include <iostream>


static string board[9];
static string winner;

//for checking board
static int config[24] = {1,2,3,4,5,6,7,8,9,1,4,7,2,5,8,3,6,9,1,5,9,3,5,7};

//players
static string players[2] = {"X","O"};


//user input
int input(){
    int input;
    printf("Your turn: ");
    scanf("%d", &input);
    return input;
}

//check if board{index] is empty
bool empty(int index){
    return (board[index-1]).length() == 0;
}

//insert to board return false if unsucessful
bool insert(string player, int index){
    if(!empty(index))return false;
    board[index-1] = player;
    return true;
}

//check if board is full
bool boardFull(){
    for(int i = 1; i<= 9; i++){
        if(empty(i)) return false;
    }
    return true;
}


//print board 
void print(string data []){
    cout << "\n -- Board --\n";
    for (int i = 0; i < 9; i+=3) {
        for (int j = i; j <i+3; j++) {
             cout << ((data[j].size() == 0) ? " |" : data[j]+"|");
        }
    cout << "\n";
    }
}

//check if variables match player
bool match(string player, int target,int a,int b,int c){
    int count = 0;
    count = player == board[a-1] ? ++count : count;
    count = player == board[b-1] ? ++count : count;
    count = player == board[c-1] ? ++count : count;
    return target == count;
}

//check winner
bool hasWinner(){
    for (string player : players) {
        for(int i = 0; i < 24; i+=3){
            if(match(player, 3, config[i], config[i+1], config[i+2])){
                winner = player;
                return true;
            } 
        } 
    }
    return false;
}

//generate random number
int random(int max){
    return (int) rand() % max;
}

//check kung my empty sa array na gin pass
bool hasNullGroup(int data [4]){
    for(int i = 0; i<4;i++){
        if(empty(data[i])) return true;
    }
    return false;
}

   
//guess cross or side 
int guess(int data []){
    int index = random(4);
    while(!empty(data[index])){
        index = random(4);
    }
    return data[index];
}

//computer instruction
 int getComputer(){
        // pririoty pick basi sa first move (0 = side | 1 = cross)
    int code = 0;
    int side [4] = {1,3,7,9};
    int cross [4] = {2,4,6,8};

        //priority center
        if(empty(5)){
            code = 1;
            return 5;
        }
        
        //find possible bingo self before opponent
        for (int player = 1; player >= 0; player--) {
            for(int i = 0; i < 24; i+=3){
                if(match(players[player], 2, config[i], config[i+1], config[i+2])){
                    if(empty(config[i])) return config[i];
                    if(empty(config[i+1])) return config[i+1];
                    if(empty(config[i+2])) return config[i+2];
                }
            }
        }
     
        //check kung una cross or side ang itara basi sa code

        if(code == 0){
            if(hasNullGroup(cross)) return guess(cross);
            if(hasNullGroup(side)) return guess(side);
        }
        
        if(hasNullGroup(side)) return guess(side);
        if(hasNullGroup(cross)) return guess(cross);
        
        //error
        return -1;
    }

//start
int main(){
 
    //index for user input
  string numbering[9] = {"1","2","3","4","5","6","7","8","9"};

    //greetings    
        cout <<"Welcome to tictactoe Game\n";
        cout <<"Your token: X\n";
        cout <<"Computer token: O\n";

         //display index of board for user input
        cout << "\nBox number below";
        print(numbering);
        
    
        cout << "\n- Game Start -\n";


        //run until no winner or board is not full    
         while(!boardFull() && !hasWinner()){

             //ask user until valid
            while (!insert(players[0],input()));
            print(board);

            //prevent sa computer mag tira pag may gana 
            if(boardFull() || hasWinner()) break;

            //computer's turn
            insert(players[1], getComputer());
            print(board);
         }

        //declaration
        if(boardFull()) cout << "Draw";
        if(hasWinner()) cout << "Winner | "+winner;
    //end
}