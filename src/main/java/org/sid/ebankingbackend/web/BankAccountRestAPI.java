package org.sid.ebankingbackend.web;

import org.sid.ebankingbackend.dtos.AccountHistoryDTO;
import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestAPI {

    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable(name = "accountId")String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> bankAccountList(){
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/history")
    public List<AccountOperationDTO> accountHistory(@PathVariable(name = "accountId") String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO accountHistoryPage(@PathVariable(name = "accountId") String accountId,
                                                @RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.accountHistoryPage(accountId, page, size);
    }
}
