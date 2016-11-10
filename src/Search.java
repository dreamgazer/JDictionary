import java.util.*;
public abstract class Search {
	protected ArrayList<Item> resultWords;
	protected String keyWord;
	protected Index wordsIndex;
	
	public Search(){
		resultWords=new ArrayList<Item>();
		wordsIndex=Dictionary.wordsIndex;
	}
	
	public abstract ArrayList<Item> SearchResult();
	

}//所有搜索方法的基类
