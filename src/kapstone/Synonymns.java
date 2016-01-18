package kapstone;

import java.util.Arrays;
import java.util.List;

public class Synonymns {
	static List<String>[] data = new List[5];
	static List<String> create=Arrays.asList("create","build","develop","make","produce","form","originate");
	static List<String> drop=Arrays.asList("drop","erase","remove","annul","cancel","obliterate","delete","destroy","demolish");
	static List<String> insert=Arrays.asList("insert","add","append","enter","include","put","initialize","fill","introduce");
	static List<String> delete=Arrays.asList("delete","erase","cancel","obliterate","drop","destroy","demolish");
	static List<String> select=Arrays.asList("select","choose","find","want","opt","determine","prefer");
	
	static List<String>[] nounData = new List[2];
	static List<String> database=Arrays.asList("database","db");
	static List<String> table=Arrays.asList("table","records","logs");	
	
	public static String compare(List<String> verbs)	
	{
	data[0] = create;
	data[1]=insert;
	data[2]=delete;
	data[3]=select;
	data[4]=drop;
	int i=0;
	int final_k=-1;
	
		
		for(int k=0;k<data.length;k++)
	    for(int j=0;j<data[k].size();j++)
	    	for(i=0;i<verbs.size();i++)
	    	{			
				if(verbs.get(i).toLowerCase().equals(data[k].get(j)))
					{final_k=k;
					switch(final_k)
					{
					case 0: return "create"; 
					case 1: return "insert";
					case 2: return "delete";
					case 3: return "select";	
					case 4: return "drop";
					default: return null;
					}
					}
				
			}
	

		return null;
	} 
	public static String compareNouns(List<String> nouns)	
	{
	nounData[0] = database;
	nounData[1]=table;
	int i=0;
	int final_k=-1;
		
		
		for(int k=0;k<nounData.length;k++)
	    for(int j=0;j<nounData[k].size();j++)
	    	for(i=0;i<nouns.size();i++)
	    	{
				
				if(nouns.get(i).toLowerCase().equals(nounData[k].get(j)))
					{final_k=k;
					switch(final_k)
					{
					case 0: return "database"; 
					case 1: return "table" + ";"+nouns.get(i).toLowerCase();
					default: return null;
					}
					}
							
			}
	

		return null;
	} 
	}


