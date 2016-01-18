package kapstone;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class check {
	public static void main(String[] args)
	{   Scanner keyboard = new Scanner(System.in);
		//System.out.println("Enter your regex");
		//^.* John ni .*$
	    //String s2=keyboard.nextLine();
	    //Pattern fn = Pattern.compile("q[^0-9]");
	   String word="word";
	    Pattern fn = Pattern.compile("\\b"+word+"\\b");
		String s1;
		System.out.println("Enter input string");
		s1=keyboard.nextLine();
		
			Matcher matcher = fn.matcher(s1);
			boolean found = false;
            while (matcher.find()) { 
                System.out.println("I found the text " + matcher.group()+
                   " starting at index " + matcher.start()+
                    " and ending at index " +matcher.end());
                found = true;
            }
            if(!found){
            	System.out.println("Doesn't match required conditions. Repeating: ");
            	}
            
			if(matcher.matches()){System.out.println("Yes!");} //exact match = matches() subsequence in input sequence = find()
			
            /*
            }
			if(!matcher.matches())
			{ System.out.println("Doesn't match required conditions. Repeating: ");
			  
			}
			else
			{System.out.println(matcher.group());
			System.out.println("Start index: "+matcher.start());
			System.out.println("End index: "+matcher.end());
				System.out.println("matching");
				return;
			}
*/
	}
}

