package jestesmy.glodni.cateringi;


import jestesmy.glodni.cateringi.model.AccountType;
import jestesmy.glodni.cateringi.model.Accounts;
import jestesmy.glodni.cateringi.model.Messages;
import jestesmy.glodni.cateringi.model.UserData;
import jestesmy.glodni.cateringi.repositories.AccountTypeRepository;

import jestesmy.glodni.cateringi.repositories.AccountsRepository;
import jestesmy.glodni.cateringi.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CateringiApplication implements CommandLineRunner {
	@Autowired
	AccountTypeRepository accountTypeRepository;

	@Autowired
	AccountsRepository accountsRepository;

	@Autowired
	MessagesRepository messagesRepository;

	public static void main(String[] args) {
		SpringApplication.run(CateringiApplication.class, args);
	}

	@Override
	public void run(String... args){
//		Scanner scan = new Scanner(System.in);
//		System.out.print("Login: ");
//		String login = scan.next();
//		List<Accounts> accountsList =  accountsRepository.findAll();
//		for(Accounts ac:accountsList) {
//			if(ac.getAccountName().equals(login)){
//				System.out.print("Password: ");
//				String password = scan.next();
//				if(ac.getPassword().equals(password))
//					System.out.println("You are logged in");
//			}
//		}
		List<Accounts> accountsList =  accountsRepository.findAll();
		List<Messages> messagesList =  messagesRepository.findAll();

		for(int i = 0; i < accountsList.size(); i++){
			System.out.println(accountsList.get(i).getEmail());
		}
		for(int i = 0; i < messagesList.size(); i++){
			System.out.println(messagesList.get(i).getDescription());
		}
	}


}
