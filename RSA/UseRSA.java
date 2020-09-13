package RSA;

import java.math.BigInteger;

public class UseRSA { 
	/**
	 * <pre>
	 def gen_key(p, q):
	    n = p * q
	    fy = (p - 1) * (q - 1)
	    e = 3889
	    # generate d
	    a = e
	    b = fy
	    r, x, y = ext_gcd(a, b)
	    print x
	    d = x
	    # 公钥    私钥
	    return (n, e), (n, d)
	    </pre>
	 * @param p
	 * @param q
	 * @return
	 */
	public BigInteger[] pubkey;
	public BigInteger[] selfkey;
	public UseRSA()
	{
		// 公钥私钥中用到的两个大质数p,q'''
		BigInteger p = new BigInteger("106697219132480173106064317148705638676529121742557567770857687729397446898790451577487723991083173010242416863238099716044775658681981821407922722052778958942891831033512463262741053961681512908218003840408526915629689432111480588966800949428079015682624591636010678691927285321708935076221951173426894836169") ;
		BigInteger q = new BigInteger("144819424465842307806353672547344125290716753535239658417883828941232509622838692761917211806963011168822281666033695157426515864265527046213326145174398018859056439431422867957079149967592078894410082695714160599647180947207504108618794637872261572262805565517756922288320779308895819726074229154002310375209") ;
	
		// 生成公钥私钥'''
		// pubkey, selfkey = gen_key(p, q)
		BigInteger[][] keys = genKey(p, q) ;
		pubkey  = keys[0] ;
		selfkey = keys[1] ;
	}
	
    
	public BigInteger[][] genKey(BigInteger p, BigInteger q){
		BigInteger n = p.multiply(q) ;
		BigInteger fy = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)) ;
		BigInteger e = new BigInteger("3889") ;
		// generate d
		BigInteger a = e ;
		BigInteger b = fy ;
		BigInteger[] rxy = new GCD().extGcd(a, b) ;
		BigInteger r = rxy[0] ;
		BigInteger x = rxy[1] ;
		BigInteger y = rxy[2] ;
		
		BigInteger d = x ;
		// 公钥  私钥
		return new BigInteger[][]{{n , e}, {n , d}} ;
	}
	//快速幂算法
	public BigInteger expMode(BigInteger base, BigInteger exponent, BigInteger n) {
		BigInteger res=new BigInteger("1");
		while(exponent.compareTo(BigInteger.ZERO)>0) {
			if(exponent.and(BigInteger.ONE).equals(BigInteger.ONE)) {
				res=res.multiply(base).remainder(n);
			}
			base=base.multiply(base).remainder(n);
			exponent=exponent.shiftRight(1);
		}
		return res;
	}
	
	
	/**
	 * 加密
	 * @param m 被加密的信息转化成为大整数m
	 * @param pubkey 公钥
	 * @return
	 */
	public String encrypt(String M, String N,String E){
		String BM=StrToBinstr(M);
		System.out.println(BM);
	    BigInteger m = new BigInteger(BM);	//会消去开头的0
	   
	    System.out.println("被加密信息：" + m);
		
		BigInteger n = new BigInteger(N) ;
		BigInteger e = new BigInteger(E) ;
	    
		BigInteger c = expMode(m, e, n) ;
		String C=c.toString();
	    return C ;
	}
	
	/**
	 * 解密
	 * @param c 
	 * @param selfkey 私钥
	 * @return
	 */
	public String decrypt(String C, String N,String D){
		System.out.println(C);
		BigInteger c=new BigInteger(C);
		BigInteger n = new BigInteger(N) ;
		BigInteger d = new BigInteger(D) ;
		
		BigInteger m = expMode(c, d, n) ;
		int l=m.toString().length();
	    String M="";
	    while(l%8>0)
		 {
			 ++l;
			 M+="0";
		 }		//还原BigInteger消去的头部0
	    M+=m.toString();		
	    System.out.println(M);
	    M=BinstrToStr(M);
		return M ;
	}
	
	//将字符串转换成二进制字符串
		public static String StrToBinstr(String str){
			char[] strChar=str.toCharArray();
			String temp="";
			for(int i=0;i<strChar.length;i++){
				 String ch=Integer.toBinaryString(strChar[i]);
				 int l=ch.length();
				 while(l<8)
				 {
					 ++l;
					 temp+="0";
				 }
				 temp+=ch;
			}
			return temp;
				 
		}
		
	//将二进制字符串转换成字符串
		public static String BinstrToStr(String str)
		{
			String s="";
			for(int k = 0; k < str.length()/8; k++){
				int a = 0;
	        	int num = 128;
	        	for(int j = 8*k; j < (k+1)*8; j++){
	        		String t = "";
	        		t += str.charAt(j);
	        		a += Integer.parseInt(t)*(num);		//二进制 值*2^（n-1）
	        		num /= 2;
	        	}
	        	//System.out.println(a);
	        	char c = (char) a;
	        	s+= c;
			}
	        	return s;
		}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
	
		UseRSA rsa = new UseRSA() ;
		String M="11111111ssssssss";
		
	    // 信息加密'''
	    String c = rsa.encrypt(M, rsa.pubkey[0].toString(),rsa.pubkey[1].toString()) ;
	    System.out.println("密文：" + c);
	    // 信息解密'''
	    String d = rsa.decrypt(c, rsa.selfkey[0].toString(),rsa.selfkey[1].toString()) ;
	    System.out.println("被解密后信息：" + d);
	    
	    System.out.println("public:" + rsa.pubkey[1]);
	    System.out.println("private:" + rsa.selfkey[1]);

	}

}
