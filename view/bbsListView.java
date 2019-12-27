package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.BbsDTO;
import javabean.BbsDAO;
import javabean.MemberDAO;

public class bbsListView extends JFrame implements MouseListener, ActionListener {

	private JTable jtable;
	private JScrollPane jscrPane;
	private JButton writeBt;
	private JButton logoutBt;
	private JComboBox<String> choiceList; // 제목, 내용, 작성자 콤보박스
	private JTextField selectField; // 검색어 입력하는 텍스트 필드
	private JButton selectBt; // 검색 버튼

	String columnNames[] = { "번호", "제목", "작성자" };

	Object rowData[][];

	DefaultTableModel model; // 테이블 넓이, 폭 설정 (view 에 필요)

	List<BbsDTO> list = null; // DAO 에서 생성되므로 굳이 여기서 생성 ㄴ

	public bbsListView() {
		super("게시판");

		setLayout(null);

		JLabel label = new JLabel();
		label.setBounds(10, 10, 120, 15);
		add(label);

		// DAO를 통해서 List 가져오기
		BbsDAO dao = BbsDAO.getInstance();
		list = dao.getBbsList();

		// 테이블의 갯수 즉 JTable의 row를 생성
		rowData = new Object[list.size()][3];

		// list 에서 테이블로 데이터 삽입하기
		for (int i = 0; i < list.size(); i++) {
			BbsDTO dto = list.get(i);

			rowData[i][0] = i + 1;			// 글의 번호
			if(dto.getDel() == 1) {		// 글제목
				rowData[i][1] = "사용자의 요청에 의해 삭제된 글입니다.";
			}else {
				rowData[i][1] = dto.getTitle();
			}			
			rowData[i][2] = dto.getId();	// 작성자
		}

		// 테이블 관련
		// 테이블 폭을 설정하기 위한 모델
		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);

		// 테이블 생성
		jtable = new JTable(model);
		jtable.addMouseListener(this);

		// column의 폭을 설정
		jtable.getColumnModel().getColumn(0).setMaxWidth(50); // 번호가 들어갈 폭을 설정
		jtable.getColumnModel().getColumn(1).setMaxWidth(500); // 제목이 들어갈 폭을 설정
		jtable.getColumnModel().getColumn(2).setMaxWidth(200); // 작성자가 들어갈 폭을 설정

		// 테이블 column의 글맞춤 (왼쪽, 중간, 오른쪽)
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER); // 중간

		// '번호'와 '작성자' 의 컬럼은 글의 중간맞춤이 된다.
		jtable.getColumn("번호").setCellRenderer(celAlignCenter);
		jtable.getColumn("작성자").setCellRenderer(celAlignCenter);

		jscrPane = new JScrollPane(jtable);
		jscrPane.setBounds(10, 50, 600, 300);
		add(jscrPane);

		writeBt = new JButton("글쓰기");
		writeBt.setBounds(10, 380, 100, 20);
		writeBt.addActionListener(this);
		add(writeBt);

		// 검색기능 콤보박스
		String selects[] = { "선택", "제목", "내용", "작성자" };
		choiceList = new JComboBox<String>(selects); // 선택, 제목, 내용, 작성자가 selects에 들어감
		choiceList.setBounds(150, 380, 80, 20);
		add(choiceList);

		// 검색어 치는 필드
		selectField = new JTextField();
		selectField.setBounds(250, 380, 150, 20);
		add(selectField);

		// 검색버튼
		selectBt = new JButton("Search");
		selectBt.addActionListener(this);
		selectBt.setBounds(420, 380, 100, 20);
		add(selectBt);

		// 오른쪽상단 로그아웃버튼
		logoutBt = new JButton("로그아웃");
		logoutBt.setBounds(510, 10, 100, 20);
		logoutBt.addActionListener(this);
		add(logoutBt);

		setBackground(new Color(0, 0, 128));
		setBounds(100, 100, 640, 480);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int rowNum = jtable.getSelectedRow();

		if (list.get(rowNum).getDel() == 1) {
			JOptionPane.showMessageDialog(null, "요청에 의해 삭제된 글입니다.");
			return;
		}

		JOptionPane.showMessageDialog(null, "선택한 글 정보:" + list.get(rowNum).toString());

		new bbsDetailView(list.get(rowNum).getSeq());
		this.dispose();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == logoutBt) {
			MemberDAO dao = MemberDAO.getInstance();
			dao.setLoginId("");

			this.dispose();
			new loginView();

		} else if (obj == writeBt) {
			this.dispose();
			new bbsAddView();
		} else if (obj == selectBt) {
		//	JOptionPane.showMessageDialog(null, "Search button"); 			확인용 메시지

			// 콤보박스에서 무엇을 선택했는지 (제목 or 내용 or 작성자)
			String selectedItem = (String) choiceList.getSelectedItem();
		//	JOptionPane.showMessageDialog(null, "Search button");

			// DB에 접근하여 검색된 글 데이터 가져오기
			BbsDAO dao = BbsDAO.getInstance();
			list = dao.getSelectList(selectedItem, selectField.getText()); // 검색할 항목, 검색어

			// 확인
			if (list.size() == 0 || selectField.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "검색한 단어로 데이터를 찾지 못했습니다.");
				// 원본 list 호출
				list = dao.getBbsList();
			}

			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
			}

			// 테이블의 갯수 즉 JTable의 row를 생성
			rowData = new Object[list.size()][3];

			// list 에서 테이블로 데이터 삽입하기
			for (int i = 0; i < list.size(); i++) {
				BbsDTO dto = list.get(i);

				rowData[i][0] = i + 1; // sequence number 가 아니고, 글 번호임 (음..? 글번호= 시퀀스..? 뭐..?)
				if (dto.getDel() == 1) {
					rowData[i][1] = "사용자의 요청에 의해 삭제된 글입니다.";
				} else {
					rowData[i][1] = dto.getTitle();
				}
				rowData[i][2] = dto.getId(); // 작성자
			}
			model.setDataVector(rowData, columnNames);

			// column의 폭을 설정
			jtable.getColumnModel().getColumn(0).setMaxWidth(50); // 번호
			jtable.getColumnModel().getColumn(1).setMaxWidth(500); // 제목
			jtable.getColumnModel().getColumn(2).setMaxWidth(200); // 작성자

			// 테이블의 column의 글의 맞춤(왼쪽, 중간, 오른쪽)
			DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
			celAlignCenter.setHorizontalAlignment(JLabel.CENTER); // 중간

			// '번호'와 '작성자'의 컬럼은 글의 중간 맞춤이 된다
			jtable.getColumn("번호").setCellRenderer(celAlignCenter);
			jtable.getColumn("작성자").setCellRenderer(celAlignCenter);

			jtable.setModel(model);
		}

	}

}
