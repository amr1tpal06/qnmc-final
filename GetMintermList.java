package qnmc;

import java.util.Set;
import java.util.TreeSet;

public class GetMintermList {
	static Set<String> MintermList;
	private static GetMintermList instance;

	public void setMinList(String x){

		MintermList.add(x);
	}

	public static Set<String> getMin(){
		return MintermList;
	}

	public static GetMintermList getInstance() {
		if (instance == null) {
			instance = new GetMintermList();
		}
		return instance;
	}

	private GetMintermList() {
		MintermList = new TreeSet<>();
	}
	
}





