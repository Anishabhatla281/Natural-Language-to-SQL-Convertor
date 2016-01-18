package kapstone;


import java.io.IOException;

import java.util.Arrays;
import java.util.List;

public class delimitspace {
	List<String> delimit(String input) throws IOException
	{   
		if(input!=null)
		{
		if(input==null || input.equals(""))
    	{System.out.println("Enter valid input"); 
    	return null;    	
    	}
		List<String> tokens=Arrays.asList(input.split(" +"));
	    return tokens;
		}
		else
		return null;
	}

}
