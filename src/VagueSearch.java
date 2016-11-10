import java.util.ArrayList;

public class VagueSearch extends Search{
	public VagueSearch(String keyWord){
		super.keyWord=keyWord;
	}

	@Override
	public ArrayList<Item> SearchResult() {
		int mis;
		Item temp;
		for(int i=0;i<Dictionary.words.size();i++){
			mis=similar(keyWord,i);
			if(mis!=1000){
				(Dictionary.words.get(i)).setMistake(mis);
				resultWords.add(Dictionary.words.get(i));
			}
		}
		for(int i=0;i<resultWords.size()-1;i++){
			for(int  j=0;j<resultWords.size()-1;j++){
				if((resultWords.get(j)).getMistake()>(resultWords.get(j+1)).getMistake()){
					
						temp=(resultWords.get(j+1));
						resultWords.set(j+1, resultWords.get(j));
						resultWords.set(j, temp);					
				}
			}
			
		}
		return resultWords;
	}//获得相似的结果并将结果按相似度排序
	
public int similar(String str,int number){
		
		if(str==null){
			return 1000;
		}
		StringBuffer str1=new StringBuffer(str);
		StringBuffer str2;
		
		int mistake=0,L=str.length();
		str2=new StringBuffer((Dictionary.words.get(number)).getWord());
		if(str2.length()==1){
			return 1000;
		}

		if(Math.abs(str1.length()-str2.length())>=2){
			return 1000;
		}
		for(int i=0;i<str1.length()&&i<str2.length();i++){
			if((L<5&&mistake>=2)||(L<8&&mistake>=3)||mistake>=4){
				return 1000;
			}
			if(str1.charAt(i)==str2.charAt(i)){
				continue;
			}
			else if((i+1<str1.length())&&(i+1<str2.length())&&str2.charAt(i+1)==str1.charAt(i+1)){
				mistake++;
				i++;
				continue;
				}
			else if((i+1<str1.length())&&str1.charAt(i+1)==str2.charAt(i)){
				str2.insert(i+1, '*');
				mistake++;
				i++;
				continue;
			}
			else if((i+1<str2.length())&&str2.charAt(i+1)==str1.charAt(i)){
				str1.insert(i+1, '*');
				mistake++;
				i++;
				continue;
			}			
			mistake++;
		}
		mistake+=Math.abs(str1.length()-str2.length());
		if((L<5&&mistake>=2)||(L<8&&mistake>=3)||mistake>=4){
							return 1000;
		}
		return mistake;
			
	}//获得两个单词的相似度

}//搜索可能的相似单词
