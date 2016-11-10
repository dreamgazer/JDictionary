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

	}//��ʼ������
	
	private JPanel northPanel(){
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout());
		JButton search=new JButton("����");
		search.setBounds(4,50,3,4);
		search.addActionListener(new SearchListener());
		SearchText=new JTextField(40);
		SearchText.getDocument().addDocumentListener(new KeyWordListener());
		p1.add(SearchText);	
		p1.add(search);	
		add(p1,BorderLayout.NORTH);
		return p1;
	}//��ʼ��������������ť
	
	private JPanel westPanel(){
		JPanel pm=new JPanel(new BorderLayout());
		Vag=new JLabel(" ");
		Vag.setFont(new Font("��Բ",Font.BOLD,13));
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
	}//��ʼ�������
	
	private JPanel eastPanel(){
		JPanel pe=new JPanel(new BorderLayout());
		explains=new JTextPane();
		explains.setFont(new Font("����",Font.BOLD,16));
		explains.setText(" ");
		explains.setPreferredSize(new Dimension(400,70));
		explains.setText("�����������������Ҫ��ѯ�ĵ��ʡ�");
		pe.add(explains,BorderLayout.EAST);
		return pe;
	}//��ʼ���������
	
	private void inputUpdateJList(String searchString) throws BadLocationException{
		if(searchString.charAt(0)<0){
			explains.setText("û���ҵ�"+"\""+searchString+"\""+"��Ӣ���������������������������롣");
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
	}//����ʱ���������
	
	private void updateJList(String searchString) throws BadLocationException{
		if(searchString.charAt(0)<0){
			explains.setText("û���ҵ�"+"\""+searchString+"\""+"��Ӣ���������������������������롣");
			return;
		}
		results=(new AccurateSearch(searchString)).SearchResult();
		Vag.setText("���������");
		if(results.size()==0||!((Item)results.get(0)).getWord().equals(searchString)){
			results=(new VagueSearch(searchString)).SearchResult();
			Vag.setText("��Ҫ�ҵ��ǲ��ǣ�");
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
			explains.setText("û���ҵ�"+"\""+searchString+"\""+"��Ӣ����������");
		}
		else{
			explains.setText("û���ҵ�"+"\""+searchString+"\""+"��Ӣ���������������������������롣");
		}
	}//����������ť���������
	
 	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				updateJList(SearchText.getText());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}//������ť����
	
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
					explains.setText("�����������������Ҫ��ѯ�ĵ��ʡ�");
				}
				else{
					inputUpdateJList(SearchText.getText());					
				}

			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}//��������
	
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
		
	}//�����������
	

}


