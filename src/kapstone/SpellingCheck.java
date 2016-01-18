package kapstone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.*;

class SpellingCheck {
							
	
	public static void main(String[] args)
	{
		String s11="I want to create database gray";
		StringWordTokenizer t1= new StringWordTokenizer(s11);
		File dict = new File("dict/english.0");
		SpellChecker s1;
		try {
			s1 = new SpellChecker(new SpellDictionaryHashMap(dict));
			int s2= s1.checkSpelling(t1);
			System.out.println("s:"+s2);
		} 
		catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		catch (IOException e) {
			
			e.printStackTrace();
		}
		

		System.out.println("aashna");
		
		
	}
							


	
	
	
	
	
}
