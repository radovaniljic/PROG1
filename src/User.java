import java.util.ArrayList;

class User {
	
	private final String name;
	private final ArrayList<Dog> ownedDogs;

	public User(String name) {
		this.name = name;
		this.ownedDogs = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<String> getNameOfOwnedDogs() {
		ArrayList<String> temp = new ArrayList<>();
		for (Dog d : ownedDogs) {
			temp.add(d.getName());
		}
		return temp;
	}
	
	public void addDog(Dog d) {
		ownedDogs.add(d);		
	}
	
	public void removeDog(Dog d) {
		ownedDogs.remove(d);
	}
	
	public void removeSelf(ArrayList<Auction> auctions) {
		for (Dog d : ownedDogs) {
			d.removeOwner();
		}
		for (Auction a : auctions) {
			for (Bid b : a.getbidList()) {
				if (b.getUser() == this) {
					a.removeBid(this);
				}
			}
		}
	}
	

	public String toString() {
			return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		}		
}