package V;

public class DataPacket {
	// 各个字段的长度
	private final int VERSION_LENGTH = 4;
	private final int HEAD_LENGTH = 16;
	private final int ID_LENGTH = 8;
	private final int TIMESTAMP_LENGTH = 13;
	private final int LIFETIME_LENGTH = 4;
	private final int TICKET_LENGTH = 56;
	private final int KEY_LENGTH = 8;
	private final int AUTHENTICATOR_LENGTH = 40;
	

	// 包头具有的字段，按照定义的顺序拼接
	private String version;
	//协议版本号，长度为4为
	private int type;
	//数据包的类型，当为0时表示认证数据包，当为1时表示服务数据包

	// 数据部分具有的字段，按照定义的顺寻拼接
	private String src_id = new String();
	// 源ID。
	private String des_id = new String();
	// 目的ID。
	private String timeStamp = new String();
	// 时间戳。
	private String lifeTime = new String();
	// 生命周期
	private Ticket ticket = new Ticket();
	// 票据
	private String key = new String();
	// 对称密钥
	private Authen authenticator = new Authen();
	// authenticator
	private String head = new String();
	private String message = new String();
	//服务数据包的数据

	private void parsePacket(String msg){
		head = msg.substring(0, HEAD_LENGTH);
		message = msg.substring(HEAD_LENGTH, msg.length());
	}
	
	//拆分解密之后的数据包
	public void parseMessage(String msg, DataPacket dataPacket) {
		parsePacket(msg);
		String data = message;
		//当为服务数据包时，不用拆分
		if(head.charAt(4) == '1'){
			message = data;
			return;
		}
		
		// 如果数据包中包含源ID，解析出源ID。
		if (head.charAt(5) == '1') {
			src_id = data.substring(0, ID_LENGTH);
			data = data.substring(ID_LENGTH, data.length());
		}
		// 如果数据包中包含目的ID，解析出目的ID。
		if (head.charAt(6) == '1') {
			des_id = data.substring(0, ID_LENGTH);
			data = data.substring(ID_LENGTH, data.length());
		}
		// 如果数据包中包含时间戳，解析出时间戳。
		if (head.charAt(7) == '1') {
			timeStamp = data.substring(0, TIMESTAMP_LENGTH);
			data = data.substring(TIMESTAMP_LENGTH, data.length());
		}
		// 如果数据包中包含生命周期，解析出生命周期。
		if (head.charAt(8) == '1') {
			lifeTime = data.substring(0, LIFETIME_LENGTH);
			data = data.substring(LIFETIME_LENGTH, data.length());
		}
		// 如果数据包中包含包含ticket，解析出ticket。
		if (head.charAt(9) == '1') {
			ticket.divTicketTGS(data.substring(0,TICKET_LENGTH));
			data = data.substring(TICKET_LENGTH,data.length());
		}
		// 如果数据包中包含密钥，解析出密钥
		if (head.charAt(10) == '1') {
			key = data.substring(0, KEY_LENGTH);
			data = data.substring(KEY_LENGTH, data.length());
		}
		// 如果数据包中包含authenticator，解析出authenticator。
		if (head.charAt(11) == '1') {
			authenticator.divTicketTGS(data.substring(0, AUTHENTICATOR_LENGTH),dataPacket);
			data = data.substring(AUTHENTICATOR_LENGTH, data.length());
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

	public Authen getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(Authen authenticator) {
		this.authenticator = authenticator;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
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

