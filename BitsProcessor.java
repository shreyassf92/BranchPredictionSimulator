
public class BitsProcessor {
	
	public static int getIndex(String address, int M) {
		
		String addrBinary = Integer.toBinaryString(Integer.parseInt(address, 16));
		
		//Make address length equal to 24 bits
  		if(addrBinary.length() != 24) {
  			StringBuilder tempString = new StringBuilder("");
  			for (int i = 0; i < (24 - addrBinary.length()); i++) {
  				tempString.append("0");
  			}
  			addrBinary = tempString.append(addrBinary).toString();
  		}
		
		addrBinary = addrBinary.substring(24-M-2, 22);
		
		return Integer.parseInt(addrBinary,2);  
	}
	
	public static int getIndex(String address, String gbhRegister, int M, int n) {
		
		String addrBinary = Integer.toBinaryString(Integer.parseInt(address, 16));
		
		String mBits = "";
		String nBits = "";
		String xoredBits = "";
		
		int pcNBitValue = 0;
		int gbhRegValue = 0;
		int xorValue = 0;
		
		//Make address length equal to 24 bits
  		if(addrBinary.length() != 24) {
  			StringBuilder tempString = new StringBuilder("");
  			for (int i = 0; i < (24 - addrBinary.length()); i++) {
  				tempString.append("0");
  			}
  			addrBinary = tempString.append(addrBinary).toString();
  		}
		
		mBits = addrBinary.substring(24-M-2, 22);
		nBits = mBits.substring(M-n);
		
		pcNBitValue = Integer.parseInt(nBits,2);  
		gbhRegValue = Integer.parseInt(gbhRegister,2);  
		
	    xorValue = pcNBitValue^gbhRegValue;
	    
	    xoredBits = Integer.toBinaryString(xorValue);
	    
	    //Make address length equal to n bits
  		if(xoredBits.length() != n) {
  			StringBuilder tempString = new StringBuilder("");
  			for (int i = 0; i < (n - xoredBits.length()); i++) {
  				tempString.append("0");
  			}
  			xoredBits = tempString.append(xoredBits).toString();
  		}
  		
  		mBits = mBits.substring(0, M-n);
  		
  		mBits = mBits+xoredBits;
  		
  		return Integer.parseInt(mBits,2);  
  		
	}
}
