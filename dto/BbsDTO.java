package dto;


public class BbsDTO {
	
	private int seq;				// 글 번호
	private String id;				// 아이디
	private String title;		    // 글 제목
	private String content;  		// 글 내용
	private String wdate; 			// 작성일
	private int del;				// 삭제 여부
	private int readCount;			// 조회수
	
	
	public BbsDTO() {
	
	}


	public BbsDTO(int seq, String id, String title, String content, String wdate, int del, int readCount) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.wdate = wdate;
		this.del = del;
		this.readCount = readCount;
	}

	
	public BbsDTO(String id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getWdate() {
		return wdate;
	}


	public void setWdate(String wdate) {
		this.wdate = wdate;
	}


	public int getDel() {
		return del;
	}


	public void setDel(int del) {
		this.del = del;
	}


	public int getReadCount() {
		return readCount;
	}


	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}


	@Override
	public String toString() {
		return "BbsDTO [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", wdate=" + wdate
				+ ", del=" + del + ", readCount=" + readCount + "]";
	}
	
	
	
	
}
