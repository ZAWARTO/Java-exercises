/**
 * MIT License
 * Copyright (c) 2021 ZAWARTO
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE.
**/
package game;
import java.util.Scanner;
/**
    * This is the main class, starts the game in a loop and calls the necessary
    * methods from <i>Dictionary<i> and <i>WordGrid<i> to build the board and load
    * the dictionary into the trie structure that is the <i>Dictionary<i> constructor.
    * 
    * <p>Starts asking which files you want to use as a dictionary or if you want to load
    * any word and its score, then you choose the desired rows and columns of the desired 
    * game board, the game time will be affected by the size of the table in a directly 
    * proportional way and this invokes the constructor method that fills the table with 
    * the letters of the Spanish alphabet in a random but related way with the percentage 
    * of appearance of these letters in Spanish, later the game starts, it shows you the 
    * rules of the game, if you have found a correct word then add its score to your time 
    * bonus unless the time has expired and add your score and number of words found.</p>
    *
    * @author  ZAWARTO
    * @version 0.2
    * @see     Dictionary#checkWord()
    * @see     WordGrid#findWord()
    * @see     WordGrid#toString()
*/
public class PlayWordGrid 
{
	static boolean GAME = true, RULES = true, FIRST = true;
	static long START_TIME = 0, FINISH_TIME = 0;
	static int TIME = 0, TIME_LIMIT = 0, BONUS = 0, SCORE_SUM = 0, WORDS_SUM = 0;
	public static void main(String[] args) 
	{
		Dictionary d = null;
		Scanner ask = new Scanner(System.in);
		System.out.print("Write file if you want to load a dictionary from a file write INPUT,\nif you want to load words manually write STORE"
						+", \nif you want to use the default diccionary then press ENTER");
		String load = ask.nextLine();
		System.out.println();
		if (load.equalsIgnoreCase("file")) {
			System.out.print("Introduce the path of the txt file");
			String filename = ask.nextLine();
			d = new Dictionary(filename);
		}
		else if (load.equalsIgnoreCase("store")){
			d = new Dictionary("null");
		}
		else
		{
			d = new Dictionary("files/diccionario.txt");
		}
		System.out.println();
		System.out.println();
		System.out.println("How many rows will the board have?");
		String rows = ask.nextLine();
		System.out.println("And how many columns?");
		String columns = ask.nextLine();
		int r = Integer.parseInt(rows);
		int c = Integer.parseInt(columns);
		TIME_LIMIT = (int) ((7000*(c + r)) / 1000);
		WordGrid wg = new WordGrid(r,c);
		while(GAME)
		{
			while(RULES)
			{
				System.out.println("THE GAME HAS STARTED!");
				System.out.println("If you want to left the game you only have to write this in the console: you have win this time machine");
				try {
					Thread.sleep(2000);
					System.out.println("You will have " + TIME_LIMIT + " seconds until the game finishes");
					Thread.sleep(2000);
					System.out.println();
					System.out.println("This is the game board");
					System.out.println();
					System.out.println(wg.toString());
					System.out.println();
					System.out.println("GAME STARTS IN 5 SECONDS");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				RULES = false;
			}			
			System.out.println();
			START_TIME = System.currentTimeMillis();
			System.out.println("So what word have you find?");
			String input = ask.nextLine().toUpperCase();
			if(input.equalsIgnoreCase("you have won this time machine"))
			{
				break;
			}
			System.out.println();
			BONUS = d.checkWord(input);
			if(BONUS == -1)
			{
				System.out.println("This word doesn't exists in the diccionary");
			}
			else
			{
				if(wg.findWord(input))
				{
					FINISH_TIME = System.currentTimeMillis();
					if(((int) ((FINISH_TIME - START_TIME)/1000))+TIME < TIME_LIMIT)
					{
						TIME_LIMIT = TIME_LIMIT + BONUS;
					}
					System.out.println("Congratulations!!! The word was found in the board");
					System.out.println("Updated board");
					System.out.println();
					System.out.println(wg.toString());
					SCORE_SUM = SCORE_SUM + BONUS;
					WORDS_SUM = WORDS_SUM + 1;
				}
				else
				{
					System.out.println("...but not found in the board");
				}
			}
			FINISH_TIME = System.currentTimeMillis();
	        TIME = TIME + (int) ((FINISH_TIME - START_TIME)/1000);
	        if(TIME_LIMIT - TIME <= 0)
			{
				break;
			}
	        System.out.println(TIME +" segundos" + ",you have only" + (int)(TIME_LIMIT-TIME) + "seconds left");
		}
		ask.close();
		System.out.println();
		System.out.println("Game finished");
		System.out.println("Score: " + SCORE_SUM);
		System.out.println("Words found: " + WORDS_SUM);
	}
}