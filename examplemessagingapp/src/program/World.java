package program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.*;

public class World {
	private Gson gson;
	private Scanner scan;
	private BufferedReader bufferedReader;
	
	private List<Guest> guests;
	private List<Company> companies;
	private List<Message> messages;
	
	private Guest selectedGuest;
	private Company selectedCompany;
	private Message selectedMessage; 
	
	private GuestClient guestClient;
	private MessageGenerator mg;
	
	public World() {
		this.scan = new Scanner(System.in);
		this.gson = new GsonBuilder().create();

		try {
			this.bufferedReader = new BufferedReader(new FileReader(new File("./src/resources/Guests.json")));
			this.guests = new ArrayList<Guest>(Arrays.asList(this.gson.fromJson(bufferedReader, Guest[].class)));
			
			this.bufferedReader = new BufferedReader(new FileReader(new File("./src/resources/Companies.json")));
			this.companies = new ArrayList<Company>(Arrays.asList(this.gson.fromJson(bufferedReader, Company[].class)));
			
			this.bufferedReader = new BufferedReader(new FileReader(new File("./src/resources/Messages.json")));
			this.messages = new ArrayList<Message>(Arrays.asList(this.gson.fromJson(bufferedReader, Message[].class)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void ExecuteProgram() {
		System.out.println("MESSAGE GENERATOR 5000");
		this.selectCompany();
		this.selectGuest();
		this.createGuestClient();
		this.selectMessage();
		this.generateMessage();
		System.out.println("=======================================");
		System.out.println("Here is your generated message: \n"+this.mg.generateMessage());
		
		this.scan.close();
	}
	
	private void generateMessage() {
		this.mg = new MessageGenerator(this.guestClient, this.selectedMessage.getMessage());
	}
	
	private void selectMessage() {
		int userSelection = -1;
		
		do {
			userSelection = -1;
			System.out.println("=======================================");
			System.out.println("[Select a message to send]");
			for (int x = 1; x <= this.messages.size(); x++) {
				System.out.printf("[%d] %s\n", 
						x, 
						this.messages.get(x-1).getMessage()
						);
			}
			
			System.out.printf("\n[0] Create your own message.");
			
			try {
				userSelection = this.scan.nextInt();
				this.scan.nextLine();
				if(userSelection < 0 || userSelection > this.messages.size()) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				userSelection = -1;
				this.scan.nextLine();
				System.out.println("[ERROR] Invalid selection, please try agian!");
			}
			
		}while(userSelection < 0 || userSelection > messages.size());
		
		if (userSelection == 0) {
			System.out.println("=======================================");
			System.out.println("Custom message selected.");
			System.out.println("Reminder, to usr guest information such as first name, type '@' infront of the desired information and surround it with curly braces!");
			System.out.println("Examples: {@firstName}, {@roomNumber}, {@companyName), {@timeSetting}, etc.");
			System.out.println("Please enter your message now: ");
			
			this.selectedMessage = new Message(00000,this.scan.nextLine());
		} else {
			this.selectedMessage = this.messages.get(userSelection-1);
		}
	}
	
	private void createGuestClient() {
		this.guestClient = new GuestClient.Builder()
					.forGuestFirstName(this.selectedGuest.getFirstName())
					.withLastName(this.selectedGuest.getLastName())
					.fromCompany(this.selectedCompany.getCompany())
					.stayingAtRoomNumber(this.selectedGuest.getReservation().getRoomNumber())
					.build();
	}
	
	private void selectGuest() {
		int userSelection = -1;
		
		do {
			userSelection = -1;
			
			System.out.println("=======================================");
			System.out.println("[Send which guest a message]");
			
			for (int x = 1; x <= this.guests.size(); x++) {
				System.out.printf("[%d] %s %s \t(Room: %d)\n", 
						x, 
						this.guests.get(x-1).getFirstName(), 
						this.guests.get(x-1).getLastName(),
						this.guests.get(x-1).getReservation().getRoomNumber()
						);
			}
			
			try {
				userSelection = this.scan.nextInt();
				this.scan.nextLine();
				if(userSelection <= 0 || userSelection > this.guests.size()) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				userSelection = -1;
				this.scan.nextLine();
				System.out.println("[ERROR] Invalid selection, please try agian!");
			}
		}while(userSelection <= 0 || userSelection > this.guests.size());
		
		this.selectedGuest = this.guests.get(userSelection-1);
	}
	
	private void selectCompany() {
		int userSelection = -1;
		
		do {
			userSelection = -1;
			System.out.println("=======================================");
			System.out.println("[Please select your Company]");
			
			for (int x = 1; x <= this.companies.size(); x++) {
				System.out.printf("[%d] %s\n", 
						x, 
						this.companies.get(x-1).getCompany()
						);
			}
			
			try {
				userSelection = this.scan.nextInt();
				this.scan.nextLine();
				if(userSelection <= 0 || userSelection > companies.size()) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				userSelection = -1;
				this.scan.nextLine();
				System.out.println("Invalid selection, please try agian!");
			}
		}while(userSelection <= 0 || userSelection > this.companies.size());
		
		this.selectedCompany = this.companies.get(userSelection-1);
	}

}