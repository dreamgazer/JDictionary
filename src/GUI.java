import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;

public class GUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private  JTextField SearchText;
	private  JTextPane explains;
	private  JList<String> showResults;
	private  JLabel Vag;
	String[] str;
	
	private ArrayList<Item> results;
	
	public GUI() {
		
	}

	public void init(){
		add(northPanel(),BorderLayout.NORTH);	
		add(westPanel(),BorderLayout.WEST);
        add(eastPanel(),BorderLayout.EAST);
		setTitle("Dictionary");
		setSize(600,450);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

	}//初始化界面
	
	private JPanel northPanel(){
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout());
		JButton search=new JButton("搜索");
		search.setBounds(4,50,3,4);
		search.addActionListener(new SearchListener());
		SearchText=new JTextField(40);
		SearchText.getDocument().addDocumentListener(new KeyWordListener());
		p1.add(SearchText);	
		p1.add(search);	
		add(p1,BorderLayout.NORTH);
		return p1;
	}//初始化输入框和搜索按钮
	
	private JPanel westPanel(){
		JPanel pm=new JPanel(new BorderLayout());
		Vag=new JLabel(" ");
		Vag.setFont(new Font("幼圆",Font.BOLD,13));
		pm.add(Vag,BorderLayout.NORTH);	
		JScrollPane p2=new JScrollPane();
		p2.setLayout(new ScrollPaneLayout());
        p2.setPreferredSize(new Dimension(160,70));
		String[] str={" "};
		showResults=new JList<String>();
		showResults.setVisibleRowCount(2);
		showResults.setListData(str);
		showResults.addListSelectionListener(new ResultsListener());
		p2.setViewportView(showResults);
		pm.add(p2,BorderLayout.CENTER);
		return pm;
	}//初始化联想框
	
	private JPanel eastPanel(){
		JPanel pe=new JPanel(new BorderLayout());
		explains=new JTextPane();
		explains.setFont(new Font("楷体",Font.BOLD,16));
		explains.setText(" ");
		explains.setPreferredSize(new Dimension(400,70));
		explains.setText("请在输入框内输入您要查询的单词。");
		pe.add(explains,BorderLayout.EAST);
		return pe;
	}//初始化结果窗口
	
	private void inputUpdateJList(String searchString) throws BadLocationException{
		if(searchString.charAt(0)<0){
			explains.setText("没有找到"+"\""+searchString+"\""+"的英汉互译结果或相近结果，请重新输入。");
			return;
		}
		results=(new AssociateSearch(searchString)).SearchResult();
		if(results.size()!=0){
			str=new String[results.size()];
		}
		else{
			String[] temp={" "};
			Vag.setText(" ");
			str=temp.clone();
		}
		for(int i=0;i<results.size();i++){
			str[i]=((Item)results.get(i)).getWord();
		}
		showResults.setListData(str);
		if(results.size()>0){
			if(results.get(0).getExplain()==null){
				explains.setText(((Item)results.get(0)).getWord());
			}
		    else if(((Item)results.get(0)).getSymbol()==null){

				explains.setText(((Item)results.get(0)).getWord()+"\n"+((Item)results.get(0)).getExplain());
			}
			else{
				explains.setText(((Item)results.get(0)).getWord()+"\n["+((Item)results.get(0)).getSymbol()+"]\n"+((Item)results.get(0)).getExplain());
			}
		}
		else{
			explains.setText(" ");
		}
	}//输入时更新联想框
	
	private void updateJList(String searchString) throws BadLocationException{
		if(searchString.charAt(0)<0){
			explains.setText("没有找到"+"\""+searchString+"\""+"的英汉互译结果或相近结果，请重新输入。");
			return;
		}
		results=(new AccurateSearch(searchString)).SearchResult();
		Vag.setText("搜索结果：");
		if(results.size()==0||!((Item)results.get(0)).getWord().equals(searchString)){
			results=(new VagueSearch(searchString)).SearchResult();
			Vag.setText("您要找的是不是：");
		}
		if(results.size()==0){
			String[] temp={" "};
			Vag.setText(" ");
			str=temp.clone();
		}
		else{
			str=new String[results.size()];
		}
		for(int i=0;i<results.size();i++){
			str[i]=((Item)results.get(i)).getWord();
		}
		showResults.setListData(str);
		if(results.size()>0&&((Item)results.get(0)).getWord().equals(searchString)){
			if(results.get(0).getExplain()==null){
				explains.setText(((Item)results.get(0)).getWord());
			}
			else if(((Item)results.get(0)).getSymbol()==null){
				explains.setText(((Item)results.get(0)).getWord()+"\n"+((Item)results.get(0)).getExplain());
			}
			else{
				explains.setText(((Item)results.get(0)).getWord()+"\n["+((Item)results.get(0)).getSymbol()+"]\n"+((Item)results.get(0)).getExplain());
			}
		}
		else if(results.size()>0&&((Item)results.get(0)).getMistake()!=0){
			explains.setText("没有找到"+"\""+searchString+"\""+"的英汉互译结果。");
		}
		else{
			explains.setText("没有找到"+"\""+searchString+"\""+"的英汉互译结果或相近结果，请重新输入。");
		}
	}//按下搜索按钮更新联想框
	
 	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				updateJList(SearchText.getText());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}//搜索按钮监听
	
	class KeyWordListener implements DocumentListener{
		@Override
		public void changedUpdate(DocumentEvent e) {
			try {
				inputUpdateJList(SearchText.getText());
				Vag.setText(" ");
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			try {
				inputUpdateJList(SearchText.getText());
				Vag.setText(" ");
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			try {
				if(SearchText.getText().length()==0){
					updateJList(" ");
					explains.setText("请在输入框内输入您要查询的单词。");
				}
				else{
					inputUpdateJList(SearchText.getText());					
				}

			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}//输入框监听
	
	class ResultsListener implements ListSelectionListener{
		private int  index=0;
		public void valueChanged(ListSelectionEvent e){
			index=((JList)e.getSource()).getSelectedIndex();
			if(results.size()>0&&index<results.size()){
				if(index<0){
					index=0;
				}
				if(results.get(0).getExplain()==null){
					explains.setText(((Item)results.get(0)).getWord());
				}
				else if(((Item)results.get(index)).getSymbol()==null){
		        	explains.setText(((Item)results.get(index)).getWord()+"\n"+((Item)results.get(index)).getExplain());
		        }
		        else{
		        	explains.setText(((Item)results.get(index)).getWord()+"\n["+((Item)results.get(index)).getSymbol()+"]\n"+((Item)results.get(index)).getExplain());
		        }
			}
		}
		
	}//翻译结果框监听
	

}


