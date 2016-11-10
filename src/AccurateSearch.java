import java.util.ArrayList;

public class AccurateSearch extends Search{

	public AccurateSearch(String keyWord){
		super.keyWord=keyWord;
	}
	@Override
	public ArrayList<Item> SearchResult() {
		int result=wordsIndex.findIndex(keyWord);
		if(result>=0){		
			resultWords.add(Dictionary.words.get(result));
		}			
		return resultWords;
	}//精确查找相同的单词
}
