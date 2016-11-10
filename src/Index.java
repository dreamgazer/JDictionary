import java.util.ArrayList;

public class Index {
	private ArrayList <List>indexList;
	private ArrayList <Item>words; 
	
	Index(ArrayList<Item>words){
		indexList=new ArrayList<List>();
		this.words=words;
		build();

	}//构造函数初始化索引
	
	class List{
		private char ch;
		private ArrayList<Integer> numbers;
		
		public List(char ch){
			this.ch=ch;
			numbers=new ArrayList<Integer>();
		}
		
		public char getch(){
			return ch;
		}
		
		public ArrayList<Integer>getNumbers(){
			return numbers;
		}
	}//索引中的每个字母结点
	
	public int findIndex(String str,int low,int high){
		int mid=0;
		while(low<=high){
			mid=(low+high)/2;
			if(indexList.get(mid).getch()==str.charAt(0)){
				return mid;
			}
			else if(((List)indexList.get(mid)).ch>str.charAt(0)){
				high=mid-1;
			}
			else{
				low=mid+1;
			}
		}
		if(low>=0)
			return low;
		else{
			return 0;
		}
	}//二分查找字母结点
	
	public void build(){
		int temp=0;
		for(int i=0;i<words.size()-1;i++){
			temp=findIndex(words.get(i).getWord(),0,indexList.size()-1);
			if(temp<indexList.size()&&indexList.size()!=0&&words.get(i).getWord().charAt(0)==indexList.get(temp).getch()){
				indexList.get(temp).getNumbers().add(words.get(i).getNumber());
			}	
			else{
				indexList.add(new List(' '));
				for(int j=indexList.size()-1;j>temp;j--){
					indexList.set(j,indexList.get(j-1));
				}
				indexList.set(temp,new List(words.get(i).getWord().charAt(0)));
				indexList.get(temp).getNumbers().add(words.get(i).getNumber());
			}
		}
		
		Integer Temp;
		boolean isInOrder;
		for(int n=0;n<indexList.size()-1;n++){		
			for(int i=0;i<indexList.get(n).getNumbers().size()-1;i++){
				isInOrder=true;
				for(int j=0;j<indexList.get(n).getNumbers().size()-2;j++){
					if(words.get(indexList.get(n).getNumbers().get(j).intValue()).getWord().compareTo(words.get(indexList.get(n).getNumbers().get(j+1).intValue()).getWord())>0){
						Temp=indexList.get(n).getNumbers().get(j);
						indexList.get(n).getNumbers().set(j,indexList.get(n).getNumbers().get(j));
						indexList.get(n).getNumbers().set(j+1,Temp);
						isInOrder=false;
					}
				}
				if(isInOrder){
					break;
				}
			}
		}		
	}//建立索引
	
	public int findIndex(String Keyword){
		int temp=findIndex(Keyword,0,indexList.size()-1);
		if(temp<0||temp>indexList.size()-1){
			return 0;
		}
		int low=0,high=indexList.get(temp).getNumbers().size()-1;
		while(low<=high){
			int mid=(low+high)/2;
			if(Keyword.compareTo(words.get(indexList.get(temp).getNumbers().get(mid).intValue()).getWord())>0){
				low=mid+1;
				continue;
			}
			if(Keyword.compareTo(words.get(indexList.get(temp).getNumbers().get(mid).intValue()).getWord())<0){
				high=mid-1;
				continue;
			}
			if(Keyword.compareTo(words.get(indexList.get(temp).getNumbers().get(mid).intValue()).getWord())==0){
				return indexList.get(temp).getNumbers().get(mid).intValue();
			}
		}
		return -1;
	}//查找索引
	
	public int findFirst(String Keyword){
		int temp=findIndex(Keyword,0,indexList.size()-1);
		if(temp<0||temp>indexList.size()-1){
			return 0;
		}
		int low=0,mid=0,high=indexList.get(temp).getNumbers().size()-1;
		while(low<=high){
			mid=(low+high)/2;
			if(Keyword.compareTo(words.get(indexList.get(temp).getNumbers().get(mid).intValue()).getWord())>0){
				low=mid+1;
				continue;
			}
			if(Keyword.compareTo(words.get(indexList.get(temp).getNumbers().get(mid).intValue()).getWord())<0){
				high=mid-1;
				continue;
			}
			if(Keyword.compareTo(words.get(indexList.get(temp).getNumbers().get(mid).intValue()).getWord())==0){
				return indexList.get(temp).getNumbers().get(mid).intValue();
			}
		}
		return indexList.get(temp).getNumbers().get(low).intValue();
	}//查找字母前几位与被查找单词相同的单词
	

}//索引类
	


