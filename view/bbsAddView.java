package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dto.BbsDTO;
import javabean.BbsDAO;
import javabean.MemberDAO;

public class bbsAddView extends JFrame implements ActionListener{

	
	JTextField writerText;
	JTextField titleText;
	JTextArea contentArea;
	
	JButton bt;
	JButton backBt;
	
	public bbsAddView() {
		super("새 글");
		setLayout(null);
		
		JLabel writerLabel = new JLabel("User = ");
		writerLabel.setBounds(10, 10, 120, 15);
		add(writerLabel);		
		
		MemberDAO dao = MemberDAO.getInstance();
				
		writerText = new JTextField(dao.getLoginId());
		writerText.setBounds(120, 10, 200, 20);
		writerText.setEditable(false);		
		add(writerText);
		
		JLabel titleLabel = new JLabel("제목");
		titleLabel.setBounds(10, 40, 120, 15);
		add(titleLabel);
		
		titleText = new JTextField();
		titleText.setBounds(120, 40, 350, 20);
		add(titleText);
		
		JLabel contentLabel = new JLabel("내용");
		contentLabel.setBounds(10, 70, 120, 15);
		add(contentLabel);
		
		contentArea = new JTextArea();	
		contentArea.setLineWrap(true);	
			
		JScrollPane scrPane = new JScrollPane(contentArea);
		scrPane.setPreferredSize(new Dimension(200, 120));
		scrPane.setBounds(10, 100, 460, 300);
		add(scrPane);
		
		bt = new JButton("post");
		bt.setBounds(100, 420, 100, 20);		
		add(bt);
		
		setBounds(100, 100, 500, 500);
		setVisible(true);
		
		backBt = new JButton("list");
		backBt.setBounds(10, 420, 100, 20);		
		add(backBt);
		
		
		bt.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("bt.addActionListener");
								
				String id = dao.getLoginId();
				String title = titleText.getText();
				String content = contentArea.getText();
				
				BbsDAO dao = BbsDAO.getInstance();
												
				BbsDTO dto = new BbsDTO(0, id, title, content, null, 0, 0);				
				boolean b = dao.writeBBS(dto);
				if(b){
					JOptionPane.showMessageDialog(null, "등록되었습니다.");
					new bbsListView();
					dispose();
				}else{
					JOptionPane.showMessageDialog(null, "내용을 입력해주세요.");
				}
				
			}
		});
		
		backBt.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new bbsListView();
			}
		});
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
