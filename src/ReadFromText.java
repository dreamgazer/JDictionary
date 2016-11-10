import java.io.*;
import java.util.*;
public class ReadFromText {
	private ArrayList<Item>  words;
	public  ReadFromText(){
		File file=new File("dictionary.txt");
		words=new ArrayList<Item> ();
		String temp=null;
		String temp2=null;
		try {
			Scanner scanner=new Scanner(file);
			temp=scanner.nextLine();
			while(scanner.hasNextLine()){
				Item item=new Item();
				temp=scanner.nextLine();
				Scanner linescanner=new Scanner(temp);
				while(linescanner.hasNext()){
					temp2=linescanner.next();					
					if(temp2.matches("[0-9]+")){
						item.setNumber(Integer.parseInt(temp2));
						continue;
						}
					
					if((temp2.matches("[a-zA-Z'-/\"\\?\\!]+"))&&item.getExplain()==null&&!temp2.matches("[a-zA-Z]+\\.")){
						if(item.getWord()!=null&&(words.get(words.size()-1)).getWord().split(" ")[0].equals(item.getWord().split(" ")[0])){
							item.setWord(item.getWord()+" "+temp2);
							continue;
						}
						else if(item.getWord()==null){
							item.setWord(temp2);
							continue;
						}


					}
					if(item.getWord()==null){
						item.setWord(temp2);
						continue;
					}
					if(temp2.matches("[a-zA-Z0-9,;':-]+")){
						if(item.getSymbol()!=null){
							item.setSymbol(item.getSymbol()+" "+temp2);
						}
						else{
							item.setSymbol(temp2);
						}
						continue;
					}
					if(item.getExplain()!=null){
						item.setExplain(item.getExplain()+temp2);
					}
					else{
						item.setExplain(temp2);
					}
			}			
				
				words.add(item);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	

	}//从文件中读取单词并区分单词、音标和翻译
	
	public ArrayList<Item> result(){
		return words;
	}
}
