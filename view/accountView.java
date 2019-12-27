package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dto.MemberDTO;
import javabean.MemberDAO;

public class accountView extends JFrame implements ActionListener{

	
	private JTextField idTextf;
	private JTextField pwTextf;
	private JTextField nameTextf;
	private JTextField emailTextf;
	
	private JButton accountBt;
	private JButton idBt;
	
	
	public accountView() {
		super("회원가입");
		setLayout(null);
		
		JLabel loginLabel = new JLabel();
		loginLabel.setBounds(100, 10, 120, 15);
		add(loginLabel);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(31, 60, 67, 15);
		add(idLabel);
		
		idTextf = new JTextField();
		idTextf.setBounds(100, 60, 150, 20);
		add(idTextf);
		idTextf.setColumns(10);
		
		idBt = new JButton("Check");
		idBt.addActionListener(this);
		idBt.setBounds(260, 60, 70, 20);
		add(idBt);
		
		JLabel pwLabel = new JLabel("Password");
		pwLabel.setBounds(31, 104, 67, 15);
		add(pwLabel);
		
		pwTextf = new JTextField();
		pwTextf.setBounds(100, 104, 150, 20);
		add(pwTextf);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBounds(31, 148, 67, 15);
		add(nameLabel);
		
		nameTextf = new JTextField();
		nameTextf.setBounds(100, 148, 150, 20);
		add(nameTextf);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(31, 192, 67, 15);
		add(emailLabel);
		
		emailTextf = new JTextField();
		emailTextf.setBounds(100, 192, 150, 20);
		add(emailTextf);
		
		accountBt = new JButton("회원가입");
		accountBt.addActionListener(this);
		accountBt.setBounds(31, 236, 280, 50);
		add(accountBt);
		
	
		setBounds(100, 100, 370, 370);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		JOptionPane.showMessageDialog(null, "버튼 클릭");
		
		JButton bt = (JButton)e.getSource();
		String btTitle = bt.getLabel();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		if(btTitle.equals("Check")) {
//			JOptionPane.showMessageDialog(null, "id 버튼");
			
			
			// 빈칸인지 확인
			if(idTextf.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "ID를 입력하세요.");
				return;
			}
			
			String id = idTextf.getText().trim();
			boolean b = dao.getId(id);
			
			// 형식에 맞지 않는 id를 입력한 경우
			if(b) {
				JOptionPane.showMessageDialog(null, "이 ID는 사용하실 수 없습니다.");
				idTextf.setText("");
			
			// 형식에 맞는 id를 입력한 경우 -> 사용 가능
			}else {
				JOptionPane.showMessageDialog(null, idTextf.getText() + "는(은) 사용가능 합니다.");
			}
			
		}else if(btTitle.equals("회원가입")) {
			if(idTextf.getText().equals("") ||
					pwTextf.getText().equals("") ||
					nameTextf.getText().equals("") ||
					emailTextf.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "모두 기입해 주세요.");
				return;
			}
			
			boolean b = dao.addMember(new MemberDTO(idTextf.getText(), pwTextf.getText(), nameTextf.getText(), emailTextf.getText(), 0));
			if(b) {
				JOptionPane.showMessageDialog(null, "성공적으로 가입되었습니다.");
				this.dispose();
				new loginView();
			}else {
				JOptionPane.showMessageDialog(null, "가입에 실패했습니다.");
			}
		}		
	}
}
