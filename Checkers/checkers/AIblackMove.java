
/*
AIblackMove.java
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author damiandelsignore josephclark jennafranz
 */
public class AIblackMove {

    //This is the current state of the game
    CheckersGame currentGame;
    //This array contains the legal moves at this point in the game for black.
    CheckersMove legalMoves[];

    // The constructor.
    public AIblackMove(CheckersGame game, CheckersMove moves[]) {
        currentGame = game;
        legalMoves = moves;
    }

    // This is where your logic goes to make a move.
    public CheckersMove nextMove() {
        // Here are some simple ideas:
        // 1. Always pick the first move
        //return legalMoves[0];
        // 2. Pick a random move
        //return legalMoves[currentGame.generator.nextInt(legalMoves.length)];
        int best = -12312;
        int best_index = 0;
        
        for(int i = 0; i < legalMoves.length; i++) //for however many moves there are
        {
            CheckersData new_board = new CheckersData(currentGame.boardData);
            currentGame.simulateMove(new_board, legalMoves[i],
                        CheckersData.BLACK); 
            /* Make a new board with the current data and then the next move */
            //int val = evaluate(new_board);  //if we're using evaluate
            int val = minimax(new_board, CheckersData.BLACK, 7, true);
            if(val > best) {
                best = val;
                best_index = i; //changed from 1 to i
            }

        }
        return(legalMoves[best_index]);

                

        //Or you can create a copy of the current board like this:
        //CheckersData new_board = new CheckersData(currentGame.boardData);
        //You can then simulate a move on this new board like this:
        //currentGame.simulateMove(new_board, legalMoves[0],CheckersData.BLACK); 
        //After you simulate the move you can evaluate the state of the board
        //after the move and see how it looks.  You can evaluate all the 
        //currently legal moves using a loop and select the best one.
    }

    // One thing you will probably want to do is evaluate the current
    // goodness of the board.  This is a toy example, and probably isn't
    // very good, but you can tweak it in any way you want.  Not only is
    // number of pieces important, but board position could also be important.
    // Also, are kings more valuable than regular pieces?  How much?
    int evaluate(CheckersData board) {
        return board.numBlack()+ 2 * board.numBlackKing()
                - board.numRed() - 2 * board.numRedKing();
        //Kings are twice as "good" as regular pieces
    }
    
   /* This is our code */ 
    int minimax(CheckersData board, int color, int depth, boolean top)
            //we went 7 deep, see line 43
    {
      CheckersMove cur_legalMoves[]; 
            if (top == true)  // To fix a bug, to see if it was the first
                              // call to minimax
                {cur_legalMoves = legalMoves; } 
            else  
                { cur_legalMoves = board.getLegalMoves(color);}
        boolean isBlack  = (color == CheckersData.BLACK ? true : false);
        int best = (isBlack ? -234234 : 235235); //If true, - number, if false, + number
        int val, best_index = 0;
        if (cur_legalMoves == null)
            return best_index;
        for(int i = 0; i < cur_legalMoves.length; i++)       
        {
            CheckersData new_board = new CheckersData(board);
            currentGame.simulateMove(new_board, cur_legalMoves[i], 
                        color);
            if (depth == 0) val = evaluate(new_board); //bottom of tree
            else val = minimax(new_board,
                    (isBlack ? CheckersData.RED : CheckersData.BLACK),
                    depth - 1, false);
            //switching players, subtracting one from depth, going down one more
            //have numbers at all stages of the tree, start with getting the max,
            //then the min, going all the way up.  This is how the best move is
            //determined.
                    
            //if we found a move better than the previous, replace it
            if (isBlack) { 
                if(val > best) {
                 best = val;
                 best_index = i;
                }
            }
            else { //if it's reds turn
                if (val <= best) {
                    best = val; 
                    best_index = i;
                }
            }
              
        }
        return best;
    }
}
//end AIblackMove.java