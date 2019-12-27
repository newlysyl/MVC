package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dto.MemberDTO;
import javabean.MemberDAO;

public class loginView extends JFrame implements ActionListener {

	private JTextField idTextF;
	private JPasswordField pwTextF;
	
	private JButton logBt;
	private JButton accountBt;
	
	public loginView() {
		super("로그인");
		setLayout(null);
		
		JLabel loginLabel = new JLabel();
		loginLabel.setBounds(100, 10, 120, 15);
		add(loginLabel);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(31, 60, 67, 15);
		add(idLabel);
		
		idTextF = new JTextField(10);
		idTextF.setBounds(100, 60, 150, 20);
		add(idTextF);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setBounds(31, 104, 67, 15);
		add(passLabel);
		
		pwTextF = new JPasswordField();
		pwTextF.setBounds(100, 104, 150, 20);
		add(pwTextF);
				
		logBt = new JButton("로그인");
		logBt.setBounds(31, 150, 100, 40);
		logBt.addActionListener(this);
		add(logBt);
		
		accountBt = new JButton("회원가입");
		accountBt.setBounds(150, 150, 100, 40);
		accountBt.addActionListener(this);
		add(accountBt);
		
		setBounds(100, 100, 300, 280);
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {				
				System.exit(0);				
			}			
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		JButton btn = (JButton)e.getSource();
		MemberDAO dao = MemberDAO.getInstance();
	if(btn == logBt) {
		MemberDTO mem = dao.login(idTextF.getText(), pwTextF.getText());
		if(mem == null) {
			JOptionPane.showMessageDialog(null, "아이디 혹은 패스워드가 맞지 않습니다.");
		}else {
			JOptionPane.showMessageDialog(null, mem.getId() + "님 환영합니다");
			this.dispose();
			
			// login 한 id 를 저장 -> Session(Web)
			dao.setLoginId(mem.getId());
			
			new bbsListView();
		}
		
	}
		else if(btn == accountBt){ // 회원가입
			new accountView();
			this.dispose();
		}
	}

}
