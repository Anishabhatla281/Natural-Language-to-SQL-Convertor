package kapstone;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class delimiter {
	static List<String> nouns=new ArrayList<String>();
	static List<String> verbs=new ArrayList<String>();
	static List<String> left=new ArrayList<String>();
	static List<String> names = new ArrayList<String>();
	static String original_input;
	public static List<String> inputAndDelimit() throws IOException{
		System.out.println("Enter input. Name of Database/Table/Attribute(s)/Column(s) should be within \", the first letter being $/_/alphabets. Spaces are not allowed within the name.");

		Scanner sc = new Scanner(System.in);

		String input=sc.nextLine();

		if(input==null || input.equals(""))
		{System.out.println("Enter valid input"); 
		return null;        	
		}
		String[] str={"[","]",",",".","{","}",":","(",")"};
		original_input=input;
		for(int i=0;i<str.length;i++)
			input=input.replace(str[i], " ");
		System.out.println(input);
		input=input.toLowerCase();
		//tokenizing on space basis
		delimitspace dspace=new delimitspace();
		List<String> tokens=dspace.delimit(input);
		return tokens;
	}
	public static String noProperNoun(List<String> tokens){
		int valid=0;    	
		String ch=null;
		if(tokens==null)
			return null;
		System.out.println(tokens.size());
		System.out.println("Tokens: "+tokens);
		Pattern namePattern=Pattern.compile("[\"]{1}[_A-Za-z$]{1}[$0-9A-Za-z_]{0,}[\"]{1}");
		for(int i=0;i<tokens.size();i++)
		{
			Matcher m1 = namePattern.matcher(tokens.get(i));
			if(tokens.get(i).startsWith("\""))
					{	if(m1.matches())
			{	names.add(tokens.get(i));			
			valid=1;
			}	
			else{
			return null;	
			}
					}
		}
		if(names.size()==0)
		{		
			System.out.println("Invalid or Missing Name Of Database/Table.");
			return null;
		}
		if(valid==1){
		try {
			tokens.removeAll(names);			
			}
			catch(UnsupportedOperationException e)
			{
				tokens=new ArrayList<String>(tokens);
				tokens.removeAll(names);
			}

			String input_without_names="";
			//cast list to string for tagging
			if(tokens.size()>=2)
				for(int i=0;i<tokens.size();i++)
				{ input_without_names+=tokens.get(i)+" ";
				}
			else 
			{ System.out.println("Invalid input. Length of the input should be atleast 3 words");
			return null;
			}			
			return input_without_names;
	}
	return null;
	}

	public static void categories(List<String> cat)
	{   String trimmed;
	for(int i=0;i<cat.size();i++)
	{
		int index=cat.get(i).indexOf("_");
		if(cat.get(i).charAt(index+1)=='V')
		{   trimmed=cat.get(i).substring(0, index);
		verbs.add(trimmed);
		}
		else if(cat.get(i).charAt(index+1)=='N')
		{   trimmed=cat.get(i).substring(0, index);
		nouns.add(trimmed);
		}
		else
		{trimmed=cat.get(i).substring(0, index);
		left.add(trimmed);

		}

	}			

	}

	public static void main(String[] args) throws IOException {
		String input_without_names=noProperNoun(inputAndDelimit());	
		delimitspace dspace=new delimitspace();
		if(input_without_names==null)		
		{
			System.out.println("Invalid Query. Enter valid name.");
			return ;
		}

		String tagged=null;		

		TagText tagText=new TagText();
		try {			
			tagged=tagText.tagging(input_without_names);
		} catch (ClassNotFoundException e) {

			System.out.println("Sorry! try again");
		}

		List<String> cat=dspace.delimit(tagged);
		categories(cat);

		Synonymns s= new Synonymns();
		String v=s.compare(verbs);

		if(v!=null && v.equals("create")){
			createQuery();
		}	
		else
		{
			System.out.println("Invalid Query. Please explain clearly the type of action to be performed.");
		}
	}

	public static void createQuery() throws IOException{
		Synonymns s= new Synonymns();
		List<String> create_query=new ArrayList<String>();
		create_query.add(s.compare(verbs));
		String n = s.compareNouns(nouns);		
		if(n!=null)
		{	
			if(names.size()>1&& n.equals("database"))
			{System.out.println("Invalid query. Only one name should be within \"");
			return ;
			}
			else if(names.size()==1 && n.startsWith("table"))
			{System.out.println("Invalid query. Please state the attributes clearly within \" ");
			return ;
			}
			else if(n.equals("database"))
				{create_query.add(n);
				create_query.addAll(names);
				}
			else 
				//calling the function for creating query "create table"
				createTable(n);
		}
		else
		{
			System.out.println("Invalid query");
			return;
		}

		if(!create_query.isEmpty())
			System.out.println("Query generated is: \n"+create_query);
		else
			System.out.println("Query could not be generated. Please try again!");
	}

	public static void createTable(String n) throws IOException
	{	
	String[] str={"[","]","{",",",".","}",":","(",")"};
	String input=original_input;
	for(int i=0;i<str.length;i++)
		input=input.replace(str[i], " ");
	input=input.toLowerCase();	
	String word=n.substring(n.indexOf(';')+1,n.length());	
	Pattern p2= Pattern.compile("[\"]{1}[^\\s]+?[\"]{1}");
	Pattern p8 = Pattern.compile("[\"]{1}[_A-Za-z$]{1}[$0-9A-Za-z_]{0,}[\"]{1}");
    Pattern p9= Pattern.compile(p8+" (being |as |(which (is|should be) )|that is |(having( the)?( datatype)? )|(whose( datatype| constraint(s)?)*( is| are) ))(alphanumeric|alpha|integer|int|( not)? null| unique)+?( as( the)?( datatype)?)?( and)?( constraint(s)?)?[\\s]?");
	Pattern p10=Pattern.compile("alphanumeric|alpha|alphabet(s)?");
	Pattern p11=Pattern.compile("integer|int|num|(no.)|number|numeric|numeral");
	Pattern p12=Pattern.compile("unique");
	Pattern p13=Pattern.compile("null|void|(non[\\s]?(-)?[\\s]?existent)|empty|zero");
	Pattern p14=Pattern.compile("(not null)|(non[\\s]?(-)?[\\s]?void)|existent|(non[\\s]?(-)?[\\s]?zero)");
		
	//Test Case 1	 
	Pattern p1= Pattern.compile("[^(attribute(')?(s)?)]+("+word+")?( where| with| for|whose)?( the| its)? name( of( the)? ("+word+"|table))?( is| as| being)? \"");
	String tableName=null;
	int end=0;
	Matcher m1 = p1.matcher(input);
	int start_index=-1,ch=0;
	if(m1.find())
	{ch=m1.end();
	start_index=m1.end()-1;
	m1=p2.matcher(input.substring(start_index-1));
	if(m1.find()){
		end=start_index+m1.end()-1;		
	tableName=m1.group();
	System.out.println("Tablename: "+tableName);
	}	
	}
	
	//Test Case 2
	Pattern p7 = Pattern.compile("( the)? name of( the)?( "+word+"| table)( being| is| as) \"");
	m1=p7.matcher(input);
	if(m1.find()){
		ch=m1.end();
		start_index=m1.end()-1;
		m1=p2.matcher(input.substring(start_index-1));
		if(m1.find()){
			end=start_index+m1.end()-1;		
		tableName=m1.group();
		System.out.println("Tablename: "+tableName);
		}	
		
	}
	
		
	//Test Case 3
	Pattern p4=Pattern.compile("\\b(and|where|with|for)? \"[^\\s]+?\"( being| is)( the| its)? name( of|for( the)? "+word+")?\\b");
	m1=p4.matcher(input);
	if(m1.find()){
		int start_index2=m1.group().indexOf('\"');
		int end_index2=m1.group().lastIndexOf('\"');
		tableName=m1.group().substring(start_index2,end_index2+1);
		System.out.println("Tablename2: "+tableName);
	}
	
	//Test Case 4
	int count=0,ch2=0;
	String sub="";
	List<String> attributes =new ArrayList<String>();
	List<String> datatypes =new ArrayList<String>();
	List<String> constraints =new ArrayList<String>();
	int a_size=0;
	Pattern p3= Pattern.compile("\\b(with|and|where|which)?( the| contains)?( name of( the)?)?[\\s]?attribute(s)?( being| are| is| are)? \"\\b");
	m1=p3.matcher(input);
	if(m1.find()){
		start_index=m1.end()-1;
		ch2=m1.end();
		m1=p9.matcher(input.substring(start_index-1));
		Matcher m2;
		Matcher m3;
		sub = input.substring(start_index-1);
		for( int i=0; i<sub.length(); i++ )  
			if( sub.charAt(i)=='\"')
				count++;
		
		if(ch<ch2) 
			a_size=count/2;
		
		else
			a_size=((count/2)-1);
		
		do{  
		m2=p8.matcher(input.substring(start_index-1));
		if(m2.find()){
		System.out.println("m2:" +m2.group());
		attributes.add(m2.group());
		System.out.println(attributes);
		end=start_index+m2.end()-1;		
		m1=p9.matcher(input.substring(start_index-1));
		if(m1.find()){		
			if(m1.group().startsWith(m2.group())){		//for "h3" "h4" "h5" as alpha example
			System.out.println("p9: "+m1.group());
			end=start_index+m1.end()-1;
			String strtemp=m1.group();	
			
		m3=p10.matcher(strtemp);
			if(m3.find()){
				System.out.println("ALPHA");
				end=start_index+m3.end()-1;
			}
		
			else{
				m3=p11.matcher(strtemp);
				if(m3.find()){
					System.out.println("int");
					end=start_index+m3.end()-1;
				}
				else{
					m3=p12.matcher(strtemp);
					if(m3.find()){
						System.out.println("unique");
						end=start_index+m3.end()-1;
					}
					else{
						m3=p13.matcher(strtemp);
						if(m3.find()){
							System.out.println("null");
							end=start_index+m3.end()-1;
						}
						else{
							m3=p14.matcher(strtemp);
							if(m3.find()){
								System.out.println("not null");
								end=start_index+m3.end()-1;
							} }
					}
				}
			}
				}
			}
		}	}
		start_index=end;
		//System.out.println(start_index);
		}while(attributes.size()!=a_size);
		
		System.out.println("The attributes are: ");
		System.out.println(attributes);	 
	
	}
	//Test Case 5
	Pattern p5=Pattern.compile(".+( "+word+"| table)( for)? [\"]{1}[_A-Za-z$]{1}[$0-9A-Za-z_]{0,}[\"]{1}( with| and| where| which|and)?( the| contains)?( name of( the)?)?([\\s]?attribute(s)?)?( being|are|is|their|are)? ");
	m1=p5.matcher(input);
	if(m1.find()){
		int start_index2=m1.group().indexOf('\"');
		int end_index2=m1.group().lastIndexOf('\"');
		tableName=m1.group().substring(start_index2,end_index2+1);
		System.out.println("Tablename3: "+tableName);
	}
	//Test Case 6
/*	Pattern p7 = Pattern.compile("( the)? name of the "+word+"( is)? [\"]{1}[_A-Za-z$]{1}[$0-9A-Za-z_]{0,}[\"]{1}");
	m1=p7.matcher(input);  
	if(m1.find()){
		int start_index2=m1.group().indexOf('\"');
		int end_index2=m1.group().lastIndexOf('\"');
		tableName=m1.group().substring(start_index2,end_index2+1);
		System.out.println("Tablename4: "+tableName);
	}*/
	//Test Case 6
	Pattern p6=Pattern.compile("(where|and|with|also)(( [\"]{1}[_A-Za-z$]{1}[$0-9A-Za-z_]{0,}[\"]{1}| and))+"+"( being| are)( the)?( name of the)? attribute(s)?");
	m1=p6.matcher(input);
	if(m1.find()){
		System.out.println( m1.group());
		String strtemp=m1.group();		
		m1=p2.matcher(strtemp);
		while(m1.find()){
			attributes.add(m1.group());
		}
		
	}
	System.out.println("Attributes are: "+attributes);
	/*int index_invertedcommas=0;
	int index_word=tokens.indexOf(word);
	for(int i=index_word;i<index_word+5;i++)
		if(tokens.get(index_word+i).startsWith("\""))
		{index_invertedcommas=index_word+i;
		valid=1;
		break;
		}
		Pattern p = Pattern.compile("");
		if(valid==1)
		{
	for(int i=index_word;i<index_invertedcommas;i++)
		if(tokens.get(i).equals("attributes")||tokens.get(i).equals("attribute")||tokens.get(i).equals("with")||tokens.get(i).equals("in")||tokens.get(i).equals("and")||tokens.get(i).equals("where")||tokens.get(i).equals("primary"))
			valid=0;
		}
	if(valid==1)
		tablename=tokens.get(index_invertedcommas);
	
	else
	{
		int index_name=tokens.indexOf("name");
		if((index_name!=0)&&(tokens.get(index_name-1).equals("attribute")||tokens.get(index_name-1).equals("attributes")))
				valid=0;
		else if((index_name!=0)&&(tokens.get(index_name-1).equals("word")))
			 valid=1;
		else 
		{
			for(int i=index_name;i<index_name+3;i++)
			{
				if(tokens.get(i).equals(word))
					;
			}
		}
			
	}*/
	
	}
}
