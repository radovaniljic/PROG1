class Dog {

	private final String name;
	private final String breed;
	private final int weight;
	private User owner;
	private int age;

	public Dog(String name, String breed, int age, int weight) {
		this.name = name;
		this.breed = breed;
		this.age = age;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public String getBreed() {
		return breed;
	}

	public int getAge() {
		return age;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public boolean hasOwner() {
		return owner != null;
	}
	
	public int getWeight() {
		return weight;
	}

	public double getTailLength() {
		if (breed.equalsIgnoreCase("Tax") || breed.equalsIgnoreCase("Dachshund")) {
			return 3.7;
		} else {
			return age * weight / 10;
		}
	}

	public void increaseAge() {
		++age;
	}

	public void addOwner(User u) {
		owner = u;
	}

	public void removeOwner() {
		owner = null;
	}

	@Override
	public String toString() {
		String formattedName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		String output = String.format("* %s (%s, %d years, %d kilo, %.1f cm tail)", formattedName, breed, age, weight, getTailLength());
		
		return owner == null ? output : output + ", owned by " + owner;
	}

}