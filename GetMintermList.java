package qnmc;

import java.util.Set;
import java.util.TreeSet;

public class GetMintermList {
	static Set<String> MintermList=new TreeSet<>();

	public void setMinList(String x){
	
		MintermList.add(x);
		
	}


public static Set<String> getMin(){
	return MintermList;
}
	
}





