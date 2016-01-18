package kapstone;
import java.io.IOException;
import org.xeustechnologies.googleapi.spelling.*;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TagText {
	
String tagging(String sample) throws IOException, ClassNotFoundException
{
	
MaxentTagger tagger = new MaxentTagger("taggers/wsj-0-18-left3words.tagger");

String tagged = tagger.tagString(sample);
System.out.println(tagged);
return tagged;

}

}
