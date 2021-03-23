import java.util.ArrayList;
import java.util.Comparator;

class Auction {

	private final Dog dog;
	private final int number;
	private final ArrayList<Bid> bids;
	private Bid maxBid;

	public Auction(Dog dog, int number) {
		this.dog = dog;
		this.number = number;
		this.bids = new ArrayList<>();
		this.maxBid = new Bid(null, 0);
	}

	public Dog getDog() {
		return dog;
	}

	public ArrayList<Bid> getbidList() {
		return new ArrayList<Bid>(bids);
	}

	public Bid getMaxBid() {
		return maxBid;
	}

	public void addBid(Bid b) {
		bids.add(b);
		updateMaxBid(b, b.getAmount());
	}

	public void updateBid(Bid b, int amount) {
		b.setAmount(amount);
		updateMaxBid(b, amount);
	}
	
	private void updateMaxBid(Bid b, int amount) {
		if (amount > maxBid.getAmount()) {
			maxBid = b;
		}
		sortbidList();
	}

	public void removeBid(User u) {
		Bid bid = null;
		for (Bid b : bids) {
			if (b.getUser() == u) {
				bid = b;
			}
		}
		if (bid != null) {
			bids.remove(bid);
		}
	}

	public void sortbidList() {
		bids.sort(Comparator.comparing(Bid::getAmount).reversed());
	}
	
	private ArrayList<Bid> getTopThreeBids() {
		ArrayList<Bid> temp = new ArrayList<>();

		int size = bids.size() <= 3 ? bids.size() : 3;

		for (int i = 0; i < size; i++) {
			temp.add(bids.get(i));
		}
		
		return temp;
	}

	public String toString() {
		String formattedDogName = dog.getName().substring(0, 1).toUpperCase()
				+ dog.getName().substring(1).toLowerCase();
		
		return "Auction #" + number + ": " + formattedDogName + ". Top bids: " + getTopThreeBids();
	}

}