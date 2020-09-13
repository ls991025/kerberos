package C;

import DES.UseDES;

public class DataPacket {
	// �����ֶεĳ���
	private final int VERSION_LENGTH = 4;
	private final int HEAD_LENGTH = 16;
	private final int ID_LENGTH = 8;
	private final int TIMESTAMP_LENGTH = 13;
	private final int LIFETIME_LENGTH = 4;
	private final int KEY_LENGTH = 8;
	private final int AUTHENTICATOR_LENGTH = 40;
	private final int TICKET_LENGTH = 56;

	// ��ͷ���е��ֶΣ����ն����˳��ƴ��
	private String version;
	//Э��汾�ţ�����Ϊ4Ϊ
	private int type;
	//���ݰ������ͣ���Ϊ0ʱ��ʾ��֤���ݰ�����Ϊ1ʱ��ʾ�������ݰ�
	
	// ���ݲ��־��е��ֶΣ����ն����˳Ѱƴ��
	private String src_id = new String();
	// ԴID��
	private String des_id = new String();
	// Ŀ��ID��
	private String timeStamp = new String();
	// ʱ�����
	private String lifeTime = new String();
	// ��������
	private String ticket = new String();
	// Ʊ��
	private String key = new String();
	// �Գ���Կ
	private String authenticator = new String();
	// authenticator
	private String head = new String();
	private String message = new String();
	//�������ݰ�������

	private String ASsig=new String();
	
	//���ͷ��
	private void parsePacket(String msg){
		head = msg.substring(0, HEAD_LENGTH);
		message = msg.substring(HEAD_LENGTH, msg.length());
	}
	
	//��ͷ������ܲ��ֽ���
	private void parse(String key){
		message = UseDES.decryMessage(message, key);
	}
	
	//�Լ��ܵ����ݰ����
	public void parseMessage1(String msg, String key) {
		parsePacket(msg);
		parse(key);
		String data = message;
		//��Ϊ�������ݰ�ʱ�����ò��
		if(head.charAt(4) == '1'){
			message = data;
			return;
		}
		
		// ������ݰ��а���ԴID��������ԴID��
		if (head.charAt(5) == '1') {
			src_id = data.substring(0, ID_LENGTH);
			data = data.substring(ID_LENGTH, data.length());
		}
		// ������ݰ��а���Ŀ��ID��������Ŀ��ID��
		if (head.charAt(6) == '1') {
			des_id = data.substring(0, ID_LENGTH);
			//System.out.println(des_id);
			data = data.substring(ID_LENGTH, data.length());
		}
		// ������ݰ��а���ʱ�����������ʱ�����
		if (head.charAt(7) == '1') {
			timeStamp = data.substring(0, TIMESTAMP_LENGTH);
			data = data.substring(TIMESTAMP_LENGTH, data.length());
		}
		// ������ݰ��а����������ڣ��������������ڡ�
		if (head.charAt(8) == '1') {
			lifeTime = data.substring(0, LIFETIME_LENGTH);
			data = data.substring(LIFETIME_LENGTH, data.length());
		}
		// ������ݰ��а�������authenticator��������authenticator��
		if (head.charAt(9) == '1') {
			ticket = data.substring(0,TICKET_LENGTH);
			data = data.substring(TICKET_LENGTH,data.length());
		}
		// ������ݰ��а�����Կ����������Կ
		if (head.charAt(10) == '1') {
			key = data.substring(0, KEY_LENGTH);
			data = data.substring(KEY_LENGTH, data.length());
		}
		// ������ݰ��а���Ʊ�ݣ�������Ʊ�ݡ�
		if (head.charAt(11) == '1') {
			authenticator = data.substring(0, AUTHENTICATOR_LENGTH);
			data = data.substring(AUTHENTICATOR_LENGTH, data.length());
		}
		if(head.charAt(12)=='1')
		{
			String s[]=data.split("\0");		//ÿ����һ��substring����ĩβ����һ��"\0",��ȥ��\0��
			ASsig=data.substring(0,s[0].length());		
		}
		
	}
	
	//�Բ������ܵ����ݰ����
	public void parseMessage(String msg) {
		parsePacket(msg);
		String data = message;
		//��Ϊ�������ݰ�ʱ�����ò��
		if(head.charAt(4) == '1'){
			message = data;
			return;
		}
		
		// ������ݰ��а���ԴID��������ԴID��
		if (head.charAt(5) == '1') {
			src_id = data.substring(0, ID_LENGTH);
			data = data.substring(ID_LENGTH, data.length());
		}
		// ������ݰ��а���Ŀ��ID��������Ŀ��ID��
		if (head.charAt(6) == '1') {
			des_id = data.substring(0, ID_LENGTH);
			//System.out.println(des_id);
			data = data.substring(ID_LENGTH, data.length());
		}
		// ������ݰ��а���ʱ�����������ʱ�����
		if (head.charAt(7) == '1') {
			timeStamp = data.substring(0, TIMESTAMP_LENGTH);
			data = data.substring(TIMESTAMP_LENGTH, data.length());
		}
		// ������ݰ��а����������ڣ��������������ڡ�
		if (head.charAt(8) == '1') {
			lifeTime = data.substring(0, LIFETIME_LENGTH);
			data = data.substring(LIFETIME_LENGTH, data.length());
		}
		// ������ݰ��а�������authenticator��������authenticator��
		if (head.charAt(9) == '1') {
			ticket = data.substring(0,TICKET_LENGTH);
			data = data.substring(TICKET_LENGTH,data.length());
		}
		// ������ݰ��а�����Կ����������Կ
		if (head.charAt(10) == '1') {
			key = data.substring(0, KEY_LENGTH);
			data = data.substring(KEY_LENGTH, data.length());
		}
		// ������ݰ��а���Ʊ�ݣ�������Ʊ�ݡ�
		if (head.charAt(11) == '1') {
			authenticator = data.substring(0, AUTHENTICATOR_LENGTH);
			data = data.substring(AUTHENTICATOR_LENGTH, data.length());
		}
		if(head.charAt(12)=='1')
		{
			//System.out.println(data.length());
			String s[]=data.split("\0");		//ÿ����һ��substring����ĩβ����һ��"\0",��ȥ��\0��
			ASsig=data.substring(0,s[0].length());
			//System.out.println(s[0].length());
		}
		
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getVERSION_LENGTH() {
		return VERSION_LENGTH;
	}

	public String getSrc_id() {
		return src_id;
	}

	public void setSrc_id(String src_id) {
		this.src_id = src_id;
	}

	public String getDes_id() {
		return des_id;
	}

	public void setDes_id(String des_id) {
		this.des_id = des_id;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(String authenticator) {
		this.authenticator = authenticator;
	}

	public String getASsig() {
		return ASsig;
	}
	public int getTICKET_LENGTH() {
		return TICKET_LENGTH;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public int getHEAD_LENGTH() {
		return HEAD_LENGTH;
	}

	public int getID_LENGTH() {
		return ID_LENGTH;
	}

	public int getTIMESTAMP_LENGTH() {
		return TIMESTAMP_LENGTH;
	}

	public int getLIFETIME_LENGTH() {
		return LIFETIME_LENGTH;
	}

	public int getKEY_LENGTH() {
		return KEY_LENGTH;
	}

	public int getAUTHENTICATOR_LENGTH() {
		return AUTHENTICATOR_LENGTH;
	}
}

