class Bid {

	private final User user;
	private int amount;

	public Bid(User user, int amount) {
		this.user = user;
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String toString() {
		String formattedName = user.getName().substring(0, 1).toUpperCase() + user.getName().substring(1).toLowerCase();
		return String.format("%s %d kr", formattedName, amount);
	}
}