package org.sid.ebankingbackend;

import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.entities.AccountOperation;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.sid.ebankingbackend.enums.AccountStatus;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BankAccountService bankAccountService){
		return args -> {
			Stream.of("Lakbir", "Mounir", "Saif").forEach(name -> {
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name.toLowerCase()+"@gmail.com");
				customer.setPhone("+21268954715");
				bankAccountService.saveCustomer(customer);
			});
			bankAccountService.listCustomer().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*90000, 5000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*120000, 5.5, customer.getId());

				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});
		};
	}
	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							AccountOperationRepository operationRepository,
							BankAccountRepository accountRepository){
		return args -> {
			Stream.of("Lakbir", "Mounir", "Saif").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name.toLowerCase()+"@gmail.com");
				customer.setPhone("+21268954715");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(customer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*90000);
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCreateAt(new Date());
				currentAccount.setCustomer(customer);
				currentAccount.setOverDraft(5000);
				accountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*98500);
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCreateAt(new Date());
				savingAccount.setCustomer(customer);
				savingAccount.setInterestRate(5.5);
				accountRepository.save(savingAccount);
			});

			accountRepository.findAll().forEach(bankAccount -> {
				for (int i=0; i<10; i++){
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setAmount(Math.random()*12000);
					accountOperation.setOperationDate(new Date());
					accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(bankAccount);
					operationRepository.save(accountOperation);
				}
			});
		};
	}
}
