import java.util.ArrayList;


public class Dictionary {
	public static ArrayList<Item> words;
	public static Index wordsIndex;
	public static void main(String[] args){
		words=(new ReadFromText().result());
		wordsIndex=new Index(words);
		GUI DictionaryGUI=new GUI();
		DictionaryGUI.init();
	}//main·½·¨
}
