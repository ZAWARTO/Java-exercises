package game;

public class WordGrid 
{
	private Character board[][];
	private final char ALPHABET_FREQ[] = {'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'C', 'C', 'C', 'C', 'D', 'D', 'D', 'D', 'D', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'F', 'G', 'H', 'I', 'I', 'I', 'I', 'I', 'I', 'J', 'K', 'L', 'L', 'L', 'L', 'L', 'M', 'M', 'M', 'N', 'N', 'N', 'N', 'N', 'N', 'Ñ', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'P', 'P', 'Q', 'R', 'R', 'R', 'R', 'R', 'R', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'T', 'T', 'T', 'T', 'U', 'U', 'U', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    /**
	 * Constructor of the game board
	 * 
	 * @param rows int of rows of the desired board table
	 * @param columns int of columns of the desired board table
	 * @see WordGrid#boardMaker()
	 */
	public WordGrid(int rows, int columns) 
	{
		board = new Character[rows][columns];
		boardMaker();
	}

	/**
	 * Method that builds the game board
	 */
	private void boardMaker() 
	{
		int alphaPos;
		for (int i = 0; i < board.length; i++) 
		{
			for (int j = 0; j < board[0].length; j++)
			{
		    	alphaPos = (int) (Math.random()*ALPHABET_FREQ.length+0);
		    	board[i][j] = ALPHABET_FREQ[alphaPos];
		    }
		}
	}
	
    /**
     * Method used to find a word in the board
     * 
     * @param word the word searched
     * @return true if its in the board or false if not
     */
    public boolean findWord(String word) 
    {
    	 for(int i = 0; i < board.length; i++)
    	 {
             for(int j = 0; j < board[0].length; j++)
             {
                 if(board[i][j] == word.charAt(0) && isInBoard(board, i, j, word, 0))
                 {
                     return true;
                 }
             }
    	 }
         return false;
    } 
    
    /**
     * This function search the word in the game board letter by letter 
     * diagonally, horizontally or vertically
     * 
     * @param board2 the game board
     * @param i value of row iterator
     * @param j value of column iterator
     * @param word the word to search in the board
     * @param index iterator of the word number of letters
     * @return boolean true if it's found on the table, false if not
     */
    private boolean isInBoard(Character[][] board2, int i, int j, String word, int index) {
        if(index == word.length()) 
        {
        	return true;
        }
        if(i < 0 || j < 0 || i >= board2.length || j >= board2[0].length) 
        {
        	return false;
        }
        if(word.charAt(index) != board2[i][j]) 
        {
        	return false;
        }
        char aux = board2[i][j];
        board2[i][j] = '●';
        if(isInBoard(board2, i + 1, j, word, index + 1) ||
        	isInBoard(board2, i - 1, j, word, index + 1) ||
        	isInBoard(board2, i, j + 1, word, index + 1) ||
        	isInBoard(board2, i, j - 1, word, index + 1)||
        	isInBoard(board2, i - 1, j - 1, word, index + 1)||
			isInBoard(board2, i - 1, j + 1, word, index + 1)||
			isInBoard(board2, i + 1, j - 1, word, index + 1)||
			isInBoard(board2, i + 1, j + 1, word, index + 1))
        {
            return true;
        }
        board2[i][j] = aux;
        return false;
    }
	
	/**
	 * This method overrides the toString method to 
	 * show the game board as a matrix with borders
	 *@since 0.2
	 *@Override
	 */
	public String toString() 
	{
		String matrix = "";
		for (int i = 0; i < board.length; i++) 
		{
			for (int k = 0; k < board[0].length; k++)
			{	
				matrix = matrix + "+---";
			}
			matrix = matrix + "+";
			matrix = matrix + "\n" + "| ";
			for (int j = 0; j < board[0].length; j++)
			{			
					matrix = matrix + board[i][j] + " | ";
			}
			matrix = matrix + System.lineSeparator();
		}
		for (int k = 0; k < board[0].length; k++)
		{	
			matrix = matrix + "+---";
		}
		matrix = matrix + "+";
		return matrix;
	}
}