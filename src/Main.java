import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;

class Main {

	private static int auctionCounter;

	private Scanner input;
	private ArrayList<Dog> dogList;
	private ArrayList<User> userList;
	private ArrayList<Auction> auctionList;

	private String[] commands;

	public Main() {
		auctionCounter = 1;

		input = new Scanner(System.in);
		dogList = new ArrayList<>();
		userList = new ArrayList<>();
		auctionList = new ArrayList<>();

		commands = new String[] { "Register new dog", "Increase age", "List dogs", "Remove dog", "Register new user",
				"List users", "Remove user", "Start auction", "List auctions", "List bids", "Make bid", "Close auction",
				"Exit" };
	}

	public void initialize() {
		System.out.print("Welcome\n\nThe available commands are: \n");

		for (int i = 0; i < commands.length; i++) {
			System.out.println("* " + commands[i]);
		}
	}

	private void handleCommand(String command) {
		switch (command.toLowerCase()) {
		case "register new dog":
			registerDogName();
			break;
		case "increase age":
			increaseAge();
			break;
		case "list dogs":
			listDogs();
			break;
		case "remove dog":
			removeDog();
			break;
		case "register new user":
			handleRegisterUserInput();
			break;
		case "list users":
			listUsers();
			break;
		case "remove user":
			removeUser();
			break;
		case "start auction":
			handleStartAuctionInput();
			break;
		case "list auctions":
			listAuctions();
			break;
		case "list bids":
			handleListBidsInput();
			break;
		case "make bid":
			handleMakeBidUserInput();
			break;
		case "close auction":
			handleCloseAuctionInput();
			break;
		case "exit":
			closeDown();
			break;
		default:
			System.out.println("ERROR: unknown command");
			break;
		}
	}
	
	public void registerDogName() {
		System.out.print("Name> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
			registerDogName();
		} else {
			name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
			registerDogBreed(name);
		}
	}

	public void registerDogBreed(String name) {
		System.out.print("Breed> ");
		String breed = input.nextLine().trim();

		if (breed.isBlank()) {
			System.out.println("ERROR: the dog must have a breed!");
			registerDogBreed(name);
		} else {
			breed = breed.substring(0, 1).toUpperCase() + breed.substring(1).toLowerCase();
			registerDogAge(name, breed);
		}
	}

	public void registerDogAge(String name, String breed) {
		System.out.print("Age> ");
		int age = input.nextInt();

		if (age < 1) {
			System.out.println("ERROR: the age must be 1 or higher");
			registerDogAge(name, breed);
		} else {
			registerDogWeight(name, breed, age);
		}
	}

	public void registerDogWeight(String name, String breed, int age) {
		System.out.print("Weight> ");
		int weight = input.nextInt();
		input.nextLine().trim();

		if (weight < 1) {
			System.out.println("ERROR: the weight must be 1 or higher");
			registerDogWeight(name, breed, age);
		} else {
			registerDog(name, breed, age, weight);
		}

	}

	public void registerDog(String name, String breed, int age, int weight) {
		Dog dog = new Dog(name, breed, age, weight);
		dogList.add(dog);
		System.out.println(name + " added to the register");
	}

	public void increaseAge() {
		if (dogList.isEmpty()) {
			System.out.println("ERROR: no dogs in register");
		} else {
			handleIncreaseAgeInput();
		}
	}

	public void handleIncreaseAgeInput() {
		System.out.print("Enter the name of the dog> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
			handleIncreaseAgeInput();
		} else {
			increaseAge(name);
		}
	}

	public void increaseAge(String name) {
		Dog dog = getDogByName(name);
		if (dog == null) {
			System.out.println("ERROR: no such dog");
		} else {
			dog.increaseAge();
			System.out.println(dog.getName() + " is now one year older");
		}
	}

	public void listDogs() {
		if (dogList.isEmpty()) {
			System.out.println("ERROR: no dogs in register");
		} else {
			handleListDogsInput();
		}
	}

	public void handleListDogsInput() {
		System.out.print("Smallest tail length to display> ");
		double tailLength = input.nextDouble();
		input.nextLine().trim();

		listDogs(tailLength);
	}

	public void listDogs(double tailLength) {
		boolean found = false;

		dogList.sort(Comparator.comparing(Dog::getTailLength).thenComparing(Dog::getName));
		for (Dog d : dogList) {
			if (d.getTailLength() >= tailLength) {
				System.out.println(d);
				found = true;
			}
			if (!found) {
				System.out.println("ERROR: no dogs with such large tails");
			}
		}

	}

	public void removeDog() {
		if (dogList.isEmpty()) {
			System.out.println("ERROR: no dogs in register");
		} else {
			handleRemoveDogInput();
		}
	}

	public void handleRemoveDogInput() {
		System.out.print("Enter the name of the dog> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
			handleRemoveDogInput();
		} else {
			removeDog(name);
		}
	}

	public void removeDog(String name) {
		Dog dog = getDogByName(name);
		if (dog == null) {
			System.out.println("ERROR: no such dog");
		} else {
			dogList.remove(dog);
			removeDogFromUser(dog);
			System.out.println(dog.getName() + " is removed from the register");
		}
	}

	public void removeDogFromUser(Dog d) {
		User u = d.getOwner();
		if (u != null) {
			u.removeDog(d);
		}
	}

	public void handleRegisterUserInput() {
		System.out.print("Name> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
			handleRegisterUserInput();
		} else {
			name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
			registerUser(name);
		}
	}

	public void registerUser(String name) {
		User user = new User(name);
		userList.add(user);

		System.out.println(name + " added to the register");
	}

	public void listUsers() {
		if (userList.isEmpty()) {
			System.out.println("ERROR: no users in register");
		} else {
			for (User u : userList) {
				System.out.println(u + " " + u.getNameOfOwnedDogs());
			}
		}
	}

	public void removeUser() {
		if (userList.isEmpty()) {
			System.out.println("ERROR: no users in register");
		} else {
			handleRemoveUserInput();
		}
	}

	public void handleRemoveUserInput() {
		System.out.print("Enter the name of the user> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
			handleRemoveUserInput();
		} else {
			removeUser(name);
		}
	}

	public void removeUser(String name) {
		User user = getUserByName(name);
		if (user == null) {
			System.out.println("ERROR: no such user");
		} else {
			userList.remove(user);
			user.removeSelf(auctionList);
			System.out.println(user.getName() + " is removed from the register");
		}
	}

	public void handleStartAuctionInput() {
		System.out.print("Enter the name of the dog> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
			handleStartAuctionInput();
		} else {
			startAuction(name);
		}
	}

	public void startAuction(String name) {
		Dog dog = getDogByName(name);
		if (dog == null) {
			System.out.println("ERROR: no such dog");
		} else {
			checkIfDogIsAlreadyForSale(dog);
		}
	}

	public void checkIfDogIsAlreadyForSale(Dog d) {
		boolean found = false;
		for (Auction a : auctionList) {
			if (a.getDog() == d) {
				found = true;
				System.out.println("ERROR: this dog is already up for auction");
			}
		}
		if (!found) {
			if (d.hasOwner()) {
				System.out.println("ERROR: this dog already has an owner");
			} else {
				startAuction(d);
			}
		}
	}

	public void startAuction(Dog d) {
		Auction a = new Auction(d, auctionCounter);
		auctionList.add(a);
		System.out.println(d.getName() + " has been been put up for auction in auction #" + auctionCounter);
		++auctionCounter;
	}

	public void listAuctions() {
		if (auctionList.isEmpty()) {
			System.out.println("ERROR: no auctions in progress");
		} else {
			for (Auction a : auctionList) {
				System.out.println(a);
			}
		}
	}

	public void handleMakeBidUserInput() {
		System.out.print("Enter the name of the user> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
			handleMakeBidUserInput();
		} else {
			User u = getUserByName(name);
			if (u == null) {
				System.out.println("ERROR: no such user");
			} else {
				handleMakeBidDogInput(u);
			}
		}
	}

	public void handleMakeBidDogInput(User u) {
		System.out.print("Enter the name of the dog> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
			handleMakeBidDogInput(u);
		} else {
			Dog d = getDogByName(name);
			if (u == null) {
				System.out.println("ERROR: no such dog");
			} else {
				Auction a = getAuctionByDog(d);
				if (a != null) {
					handleMakeBidAmountInput(u, d, a);
				} else {
					System.out.println("ERROR: this dog is not for sale");
				}
			}
		}
	}

	public Auction getAuctionByDog(Dog d) {
		Auction auction = null;
		for (Auction a : auctionList) {
			if (a.getDog() == d) {
				auction = a;
			}
		}
		return auction;
	}

	public void handleMakeBidAmountInput(User u, Dog d, Auction a) {
		System.out.print("Amount to bid (min " + (a.getMaxBid().getAmount() + 1) + ")> ");
		int amount = input.nextInt();
		input.nextLine().trim();

		if (amount <= a.getMaxBid().getAmount()) {
			System.out.println("ERROR: you need to bid higher");
			handleMakeBidAmountInput(u, d, a);
		} else {
			makeBidAmount(u, d, a, amount);
		}
	}

	public void makeBidAmount(User u, Dog d, Auction a, int amount) {
		Bid bid = null;
		for (Bid b : a.getbidList()) {
			if (b.getUser() == u) {
				bid = b;
			}
		}
		makeBid(a, u, bid, amount);
	}

	public void makeBid(Auction a, User u, Bid b, int amount) {
		if (b != null) {
			a.updateBid(b, amount);
		} else {
			Bid bid = new Bid(u, amount);
			a.addBid(bid);
		}
		System.out.println("Bid made");
	}

	public void handleListBidsInput() {
		System.out.print("Enter the name of the dog> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
		} else {
			listBids(name);
		}
	}

	public void listBids(String name) {
		Dog d = getDogByName(name);
		Auction a = getAuctionByDog(d);

		if (a != null) {
			if (a.getbidList().isEmpty()) {
				System.out.println("No registered bids yet for this auction");
			} else {
				System.out.println("Here are the bids for this auction");
				a.getbidList().forEach(System.out::println);
			}
		} else {
			System.out.println("ERROR: this dog is not for sale");
		}
	}

	public void handleCloseAuctionInput() {
		System.out.print("Enter the name of the dog> ");
		String name = input.nextLine().trim();

		if (name.isBlank()) {
			System.out.println("ERROR: the name can't be empty");
			handleCloseAuctionInput();
		} else {
			closeAuction(name);
		}
	}

	public void closeAuction(String name) {
		Dog d = getDogByName(name);
		Auction a = getAuctionByDog(d);

		if (a != null) {
			if (a.getbidList().isEmpty()) {
				System.out.println("The auction is closed. No bids were made for " + d.getName() + ".");
				auctionList.remove(a);
			} else {
				closeAuctionUser(a, name);
			}
		} else {
			System.out.println("ERROR: this dog is not up for auction");
		}
	}

	public void closeAuctionUser(Auction a, String name) {
		User user = null;
		for (User u : userList) {
			a.sortbidList();
			if (u == a.getbidList().get(0).getUser()) {
				user = u;
			}
		}
		closeAuction(a, user, name);
	}

	public void closeAuction(Auction a, User user, String name) {
		Dog dog = getDogByName(name);

		if (dog != null && user != null) {
			auctionList.remove(a);
			dog.addOwner(user);
			user.addDog(dog);
		}
		Bid b = a.getMaxBid();
		String output = String.format("The auction is closed. The winning bid was %d kr and was made by %s.",
				b.getAmount(), b.getUser());
		System.out.println(output);
	}

	public User getUserByName(String name) {
		User user = null;
		for (User u : userList) {
			if (u.getName().equalsIgnoreCase(name)) {
				user = u;
			}
		}
		return user;
	}

	public Dog getDogByName(String name) {
		Dog dog = null;
		for (Dog d : dogList) {
			if (d.getName().equalsIgnoreCase(name)) {
				dog = d;
			}
		}
		return dog;
	}

	public void closeDown() {
		System.out.println("Goodbye!");
	}

	public void runCommandLoop() {
		String command;
		do {
			System.out.print("Command> ");
			command = input.nextLine().trim();
			handleCommand(command);
		} while (!command.equalsIgnoreCase("exit"));
	}

	public void runProgram() {
		initialize();
		runCommandLoop();
	}

	public static void main(String[] args) {
		new Main().runProgram();
	}
}
