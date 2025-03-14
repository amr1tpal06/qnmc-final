package qnmc;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class GetMintermList {
	static Set<String> MintermList;
	private static GetMintermList instance;
	private final Set<Observer> observers = new HashSet<>();

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

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		String result = QuineProcessor.applyQuineMcCluskey(MintermList);
		for (Observer observer : observers) {
			observer.update(result);
		}
	}
}





