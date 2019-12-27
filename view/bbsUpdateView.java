



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

public class bbsUpdateView extends JFrame{
	
	JTextField idTextfield;
	JTextField wDateTextfield;
	JTextField readCountTextfield;
	JTextField titleTextfield;
	
	JTextArea contentArea;
	
	public bbsUpdateView(int seq) {
		super("글 수정하기");
		
		BbsDAO dao = BbsDAO.getInstance();
		BbsDTO dto = dao.getBBS(seq);
		dao.readCount(seq);
	
		setLayout(null);
		
		System.out.println("c = " + dao.toString());
		
		JLabel writerLabel = new JLabel("작성자");
		writerLabel.setBounds(10, 10, 60, 15);
		add(writerLabel);
		
		idTextfield = new JTextField(dto.getId());
		idTextfield.setBounds(120, 10, 200, 20);
		idTextfield.setEditable(false);
		add(idTextfield);
		
		JLabel writedLabel = new JLabel("작성일");
		writedLabel.setBounds(10, 40, 60, 15);
		add(writedLabel);
		
		wDateTextfield = new JTextField(dto.getWdate());
		wDateTextfield.setBounds(120, 40, 200, 20);
		wDateTextfield.setEditable(false);
		add(wDateTextfield);
		
		JLabel readLabel = new JLabel("조회수");
		readLabel.setBounds(10, 70, 60, 15);
		add(readLabel);
		
		readCountTextfield = new JTextField(dto.getReadCount() + "");
		readCountTextfield.setBounds(120, 70, 200, 20);
		readCountTextfield.setEditable(false);
		add(readCountTextfield);
		
		JLabel titleLabel = new JLabel("제목");
		titleLabel.setBounds(10, 100, 60, 15);
		add(titleLabel);
		
		titleTextfield = new JTextField(dto.getTitle());
		titleLabel.setBounds(120, 100, 300, 20);
		add(titleTextfield);
		
		JLabel contentLabel = new JLabel("내용");
		contentLabel.setBounds(10, 130, 60, 15);
		add(contentLabel);
		
		contentArea = new JTextArea(dto.getContent());
		contentArea.setLineWrap(true);
		
		JScrollPane scrPane = new JScrollPane(contentArea);
		scrPane.setPreferredSize(new Dimension(200, 120));
		scrPane.setBounds(10, 160, 460, 300);
		add(scrPane);
		
		JButton bbsBt = new JButton("게시판 목록");
		bbsBt.setBounds(10, 480, 100, 20);
		add(bbsBt);
		
		setBounds(100, 100, 500, 600);
		setVisible(true);
		
		
		
		JButton updateBt = null;
		updateBt = new JButton("수정 완료");
		updateBt.setBounds(150, 480, 100, 20);
		add(updateBt);
		updateBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = titleTextfield.getText();
				String content = contentArea.getText();
				
				if(title.equals("") || content.equals("")) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 작성하십시오");
					return;
				}
				
				// 수정
				boolean b = dao.updateBbs(seq, title, content);
				if(b) {
					JOptionPane.showMessageDialog(null, "성공적으로 수정되었습니다.");
					new bbsListView();
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "수정하지 못했습니다.");
				}
			}
		});
		
		bbsBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new bbsListView();
				dispose();
			}
		});
	}
}
