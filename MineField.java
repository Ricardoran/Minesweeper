// Name: Haoran Zhang
// USC NetID: hzhang07
// CS 455 PA3
// Fall 2019

import java.util.Random;
/** 
   MineField
      class with locations of mines for a game.
      This class is mutable, because we sometimes need to change it once it's created.
      mutators: populateMineField, resetEmpty
      includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {

    private Random generator;  // generate mines with random locations
    private boolean[][] mineArea;   // represent area in this game, contains all info about mine and non-mine data
    private int numberRows;
    private int numberCols;
    private int numberMines;

    public static boolean STATUS_MINE = true;
    public static boolean STATUS_NO_MINE = false;


   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in the array
      such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
      this minefield will corresponds to the number of 'true' values in mineData.
    * @param mineData  the data for the mines; must have at least one row and one col.
    */
   public MineField(boolean[][] mineData) {

       this.numberRows = mineData.length;
       this.numberCols = mineData[0].length;
       /**
        * Specify the mineArea[][], which is a defensive copy of mineData[][]
        */
       mineArea = new boolean[numberRows][numberCols];
       mineArea = defensiveCopy(mineData);

       /**
        * Count the number of mines in this area
        */
       numberMines = countMineNumbers(numRows(), numCols());
   }


   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a MineField, 
      numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {

       /**
        * Specify the mineArea[][] which may have different numRows and numCols
        */
       numberRows = numRows;
       numberCols = numCols;
       numberMines = numMines;

       mineArea = new boolean[numberRows][numberCols];
   }
   

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
      ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col)
    */
   public void populateMineField(int row, int col) {

       /**
        * Reset the game board before populating the field
        */
       resetEmpty();

       /**
        * Generate the number of [numMines] mines on the board (row * col) in a random position
        */
       int rowLoc = 0;
       int colLoc = 0;
       int mine_counter = 0;

       generator = new Random();

       while (mine_counter < numMines()){
           rowLoc = generator.nextInt(numRows());
           colLoc = generator.nextInt(numCols());

           /**
            * If this location already has mines, or the mine will be put at the position
            * once you uncover the first position, do not put the mine here (row,col)
            * re-generate mine locations
            */
           if((rowLoc == row && colLoc == col) || hasMine(rowLoc, colLoc)){
               continue;
           } else{
               mineArea[rowLoc][colLoc] = STATUS_MINE;
               mine_counter ++;
           }

       }
   }


   /**
      Reset the minefield to all empty squares. This does not affect numMines(), numRows() or numCols()
      Thus, after this call, the actual number of mines in the minefield does not match numMines().  
      Note: This is the state the minefield is in at the beginning of a game.
    */
   public void resetEmpty() {
       /**
        * Reset the game by clearing whole mines on the game board.
        * Put each position into STATUS_NO_MINE(false)
        */
       for(int i = 0; i < numRows(); i++){
           for(int j=0; j < numCols(); j++){
               mineArea[i][j] = STATUS_NO_MINE;
           }
       }
   }

   
  /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   public int numAdjacentMines(int row, int col) {

       /**
        * Count the adjacent number of mines of a specific location (row, col) in eight direction
        */
       int countMine = 0;

       // top
       if (inRange(row, col - 1) && hasMine(row, col - 1)) {
           countMine++;
       }
       // down
       if (inRange(row, col + 1) && hasMine(row,col + 1)) {
           countMine++;
       }
       // left
       if (inRange(row - 1, col) && hasMine(row - 1, col)){
           countMine++;
       }
       // right
       if(inRange(row + 1, col) && hasMine(row + 1, col)){
           countMine++;
       }
       // left up
       if(inRange(row - 1, col - 1) && hasMine(row - 1, col - 1)){
           countMine++;
       }
       // left down
       if(inRange(row - 1, col + 1) && hasMine(row - 1, col + 1)){
           countMine++;
       }
       // right up
       if(inRange(row + 1, col - 1) && hasMine(row + 1, col - 1)){
           countMine++;
       }
       // right down
       if(inRange(row + 1, col + 1) && hasMine(row + 1, col + 1)){
           countMine++;
       }

       /**
        * return the number of adjacent mines around one location (row, col).
        */
      return countMine;
   }
   
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   public boolean inRange(int row, int col) {

       /**
        * check if any of the selected location is within the range [0, numRows - 1][0, numCol - 1]
        * ensure that no location is out of the border set.
        */
       if(row >= 0 && row < numRows() && col >= 0 && col < numCols()){
           return true;
       } else{
           return false;
       }

   }
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */  
   public int numRows() {
      return numberRows;
   }
   
   
   /**
      Returns the number of columns in the field.
      @return number of columns in the field
   */    
   public int numCols() {
      return numberCols;
   }
   
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */    
   public boolean hasMine(int row, int col) {

       if(mineArea[row][col] == STATUS_MINE){
           return STATUS_MINE;
       } else {
           return STATUS_NO_MINE;
       }
   }
   
   
   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
      some of the time this value does not match the actual number of mines currently on the field.  See doc for that
      constructor, resetEmpty, and populateMineField for more details.
    * @return
    */
   public int numMines() {
      return numberMines;
   }

   // <put private methods here>


    /**
     * Count the number of mines of the 1-arg constructor input case (MineSweeperFixed case)
     * @param row row of the location to start counting mines
     * @param col col of the location to start counting mines
     * @return
     */
    public int countMineNumbers(int row, int col){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(mineArea[i][j] == STATUS_MINE){
                    numberMines ++;
                }
            }
        }
        return numberMines;
    }


    /**
     * Make a defensive copy of the input 2D array, this method is used only ine the
     * 1-arg constructor input case (MineSweeperFixed case)
     * @param mineData the input 2D array (fixed)
     * @return
     */
    public boolean[][] defensiveCopy(boolean mineData[][]){
        for(int i =0; i < numberRows; i++){
            for(int j = 0; j < numberCols; j++){
                if(mineData[i][j] == STATUS_MINE){
                    mineArea[i][j] = STATUS_MINE;
                }
                else if(mineData[i][j] == STATUS_NO_MINE){
                    mineArea[i][j] = STATUS_NO_MINE;
                }
            }
        }
        return mineArea;

    }
}

