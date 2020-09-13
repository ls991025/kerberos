package TGS;

import DES.UseDES;

public class Ticket {
	private String key;
	private String src_id;
	private String ad;
	private String des_id;
	private String timeStamp;
	private String lifeTime;

	private final static int ID_LENGTH = 8;
	private final static int TIMESTAMP_LENGTH = 13;
	private final static int LIFETIME_LENGTH = 4;
	private final static int KEY_LENGTH = 8;
	private final static int AD_LENGTH = 12;
	
	private String ticketDecryption(String ticket){
		//as和tgs共同维护的密钥
		String keyg="aaaaaaaa";
		System.out.println(UseDES.decryMessage(ticket, keyg));
		return UseDES.decryMessage(ticket, keyg);
	}
	
	public void divTicketTGS(String TTGS){
		
		//解密并拆分ticket
		String ttgs = ticketDecryption(TTGS);

		String ss = "";
		ss = ttgs.substring(0, KEY_LENGTH);//8 Kc,tgs
		ttgs = ttgs.substring(KEY_LENGTH, ttgs.length());
		setKey(ss);
		
		ss = ttgs.substring(0, ID_LENGTH);//8 IDc
		ttgs = ttgs.substring(ID_LENGTH, ttgs.length());
		setSrc_id(ss);
		
		
		ss = ttgs.substring(0, AD_LENGTH);//12 ADc
		ttgs = ttgs.substring(AD_LENGTH, ttgs.length());
		setAd(ss);
		
		ss = ttgs.substring(0, ID_LENGTH);//8 IDtgs
		ttgs = ttgs.substring(ID_LENGTH, ttgs.length());
		setDes_id(ss);
		System.out.println(ss+"\n");
		
		ss = ttgs.substring(0, TIMESTAMP_LENGTH);//13 TS
		ttgs = ttgs.substring(TIMESTAMP_LENGTH, ttgs.length());
		setTimeStamp(ss);
		
		ss = ttgs.substring(0, LIFETIME_LENGTH);//4 Lifetime
		ttgs = ttgs.substring(LIFETIME_LENGTH, ttgs.length());
		setLifeTime(ss);

	}


	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
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


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}

	public String getLifeTime() {
		return lifeTime;
	}


	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}

}
