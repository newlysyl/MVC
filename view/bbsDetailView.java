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

public class bbsDetailView extends JFrame implements ActionListener {

	
	JTextField idTextfield;				// id
	JTextField wDateTextfield;			// 작성일
	JTextField titleTextfield;			// 제목
	JTextField readCountTextfield;		// 조회수
	
	JTextArea contentArea;
	
	
	public bbsDetailView(int seq) {
		super("글 상세 조회");
		
		BbsDAO dao = BbsDAO.getInstance();
		dao.readCount(seq);
		
		BbsDTO dto = dao.getBBS(seq);
		
		setLayout(null);
		
		System.out.println("c = " + dto.toString());
		
		JLabel writerLabel = new JLabel("작성자");
		writerLabel.setBounds(10, 10, 60, 15);
		add(writerLabel);
		
		// id
		idTextfield = new JTextField(dto.getId());
		idTextfield.setBounds(120, 10, 200, 20);
		idTextfield.setEditable(false);
		add(idTextfield);
		
		JLabel writedLabel = new JLabel("작성일");
		writedLabel.setBounds(10, 40, 60, 15);
		add(writedLabel);
		
		// 작성일
		wDateTextfield = new JTextField(dto.getWdate());
		wDateTextfield.setBounds(120, 40, 200, 20);
		wDateTextfield.setEditable(false);
		add(wDateTextfield);
		
		JLabel readLabel = new JLabel("조회수");
		readLabel.setBounds(10, 70, 60, 15);
		add(readLabel);
		
		// 조회수
		readCountTextfield = new JTextField(dto.getReadCount() + "");
//		readCountTextfield = new JTextField();
//		readCountTextfield.setText(dto.getReadCount() + "");
		readCountTextfield.setBounds(120, 70, 200, 20);
		readCountTextfield.setEditable(false);
		add(readCountTextfield);
		
		JLabel titleLabel = new JLabel("제목");
		titleLabel.setBounds(10, 100, 60, 15);
		add(titleLabel);
		
		// 제목
		titleTextfield = new JTextField(dto.getTitle());
		titleTextfield.setBounds(120, 100, 300, 20);
		titleTextfield.setEditable(false);
		add(titleTextfield);
		
		JLabel contentLabel = new JLabel("내용");
		contentLabel.setBounds(10, 130, 60, 15);
		add(contentLabel);
		
		// 내용
		contentArea = new JTextArea(dto.getContent());
		contentArea.setEditable(false);
		contentArea.setLineWrap(true);	// 자동 줄 바꿈
		
		JScrollPane scrPane = new JScrollPane(contentArea);
		scrPane.setPreferredSize(new Dimension(200, 120));
		scrPane.setBounds(10, 160, 460, 300);
		add(scrPane);
		 
		// 글 목록 버튼
		JButton bbsBt = new JButton("목록");
		bbsBt.setBounds(10, 480, 100, 20);
		add(bbsBt);
		
		// 삭제 버튼
		JButton deleteBt = new JButton("삭제");
		deleteBt.setBounds(260, 480, 100, 20);
		add(deleteBt);
		
		setBounds(100, 100, 500, 600);
		setVisible(true);
		
		// 수정 버튼
		JButton updateBt = null;
		updateBt = new JButton("수정");
		updateBt.setBounds(150, 480, 100, 20);
		add(updateBt);
		
		// 같은 id일 때 수정버튼 비활성화
		if(!dto.getId().equals(MemberDAO.getInstance().getLoginId())) {
			updateBt.setEnabled(false);
			deleteBt.setEnabled(false);
		}
		
		updateBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new bbsUpdateView(seq);
				dispose();
			}
		});
		
		
		deleteBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BbsDAO dao = BbsDAO.getInstance();
				if(dao.deleteBbs(seq)) {
					JOptionPane.showMessageDialog(null, "글을 삭제하였습니다");
				}else {
					JOptionPane.showMessageDialog(null, "글이 삭제되지 않았습니다");
				}
				new bbsListView();	
				dispose();
				
			}
		});
		
		bbsBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new bbsListView();
				dispose();
			}
		});
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		

	}

}
