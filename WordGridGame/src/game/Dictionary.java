package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
	private static Node root; 
	private final char alphabet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	public Dictionary() {}
	
	/**
	 * @param s String used to know if you want to add
	 * 			a dictionary from a txt file or if you want
	 * 			to store words manually
	 */
	public Dictionary(String s) 
	{
		if (!s.equalsIgnoreCase("null"))
		{
			try 
			{
				root = new Node();
				System.out.println("Reading and saving dictionary, please be patient...");
				File text = new File(s); 
				Scanner fileReader = new Scanner(text, "UTF-8");
				while (fileReader.hasNextLine()) 
				{ 
					String data = fileReader.nextLine();
					String[] dataArray = data.split("\\s+");
					String word = dataArray[0]; 
					String number = dataArray[1]; 
					int score = Integer.parseInt(number);
					addWord(word, score);
				}
				fileReader.close();
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("File not found");
				e.printStackTrace();
			}
			System.out.println("Reading complete");
		}
		else 
		{
			root = new Node();
			Scanner ask = new Scanner(System.in);
			String answer = "";
			while(!answer.equalsIgnoreCase("no"))
			{
				System.out.println("Do you want to add any word?");
				answer = ask.nextLine();
				if(answer.equalsIgnoreCase("yes"))
				{
					Scanner askScore = new Scanner(System.in);
					Scanner askWord = new Scanner(System.in);
					System.out.println("Introduce the word");
					String word = askWord.nextLine();
					System.out.println("Introduce the score");
					String n = askScore.nextLine();
					int score = Integer.parseInt(n);
					addWord(word,score);
					askScore.close();
					askWord.close();
				}
			}
			ask.close();
    		}
    	}
	
       /**
         * Constructor of the nodes
	 */
	private static class Node 
    	{
        	@SuppressWarnings("unused")
		private char character;
        	private int score;
        	private Node[] next = new Node[27];
        	Node()
        	{	 
            		for (int i = 0; i < 27; i++) 
            		next[i] = null; 
        	}
		public void setCharacter(char character) 
		{
	    		this.character = character;
		} 
    	}
	
	
    	/**
     	 * This method adds a word to the trie letter by letter
     	 * calling to the private recursive method
     	 * 
     	 * @param word String of the word added
     	 * @param score int with the score of the word
     	 */
    	public void addWord(String word, int score) 
    	{
        	Node head = root;
        	head = addWord(head, word, score, 0);
    	}

    	/**
     	 * Recursive method to add a word letter by letter through 
     	 * the nodes of the trie.
     	 * 
     	 * @param n node imported from addWord recursively
     	 * @param word String of the word added
     	 * @param score int with the score of the word
     	 * @param i counter used to locate the letter of the node
     	 * @return n last node with the last letter
     	 */
	private Node addWord(Node n, String word, int score, int i) 
	{
        	if (n == null) 
        	{
        		n = new Node();
        	}
        	if (i == word.length() - 1)
        	{
        		n.score = score;
        	}
        	if (i == word.length()) 
        	{
            		n.score = score;
            		return n;
        	}
        	char letter = word.charAt(i);
        	n.setCharacter(letter);
        	int indexOfTwo = new String(alphabet).indexOf(letter);
        	n.next[indexOfTwo] = addWord(n.next[indexOfTwo], word, score, i+1);
        	return n;
    	}
    
    	/**
     	 * Recursive search of the word letter by letter through 
     	 * the nodes of the trie calling the private recursive method.
     	 * 
     	 * @param word the word searched
     	 * @return the score of the word, minus one 
     	 * 		   if it is not in the dictionary
     	 */
	public int checkWord(String word) 
    	{
    		Node head = root;
        	head = checkWord(root, word, 0);
        	if(head != null && head.score > 0)
        	{
        		System.out.println("The score of the word is " + head.score);
        		return head.score;
        	}
		return -1;
    	}
    
    	/**
     	 * Recursive search of the word letter by letter through 
     	 * the nodes of the trie.
     	 * 
     	 * @param n node imported from checkWord recursively
     	 * @param word the word searched
     	 * @param i counter used to locate the letter of the node
     	 * @return call to return to the first function 
     	 */
	private Node checkWord(Node n, String word, int i) 
    	{
        	if (n == null)
        	{
        		return null;
        	}
        	if (i == word.length()) 
        	{
        		return n;
        	}
        	char letter = word.charAt(i); 
        	int indexOfTwo = new String(alphabet).indexOf(letter);
        	return checkWord(n.next[indexOfTwo], word, i+1);
    	}
}
