import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssociateSearch extends Search{

	public AssociateSearch(String keyWord){
		super.keyWord=keyWord;
	}
	@Override
	public ArrayList<Item> SearchResult() {
		int result=wordsIndex.findFirst(keyWord);
		Pattern p=null;
		Matcher m=null;
		p=Pattern.compile(keyWord+".*");
		m=p.matcher((Dictionary.words.get(result)).getWord());

		if(m.matches()){					
			boolean matchundone=true;
			for(int i=result;matchundone&&i<Dictionary.words.size();i++){
				if((Dictionary.words.get(i)).getWord()==null)
				   continue;
				m=p.matcher((Dictionary.words.get(i)).getWord());
				if(m.matches()){	
	                 resultWords.add(Dictionary.words.get(i));
				}
				else{
					matchundone=false;
				}
			}	
		}		
		return resultWords;
	}//输入时提示搜索

}
