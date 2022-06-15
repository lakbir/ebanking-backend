package org.sid.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestController {

    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomer();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return bankAccountService.searchCustomers(keyword);
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomerById(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return bankAccountService.getCustomerById(id);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers")
    public CustomerDTO putCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void putCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }


}
