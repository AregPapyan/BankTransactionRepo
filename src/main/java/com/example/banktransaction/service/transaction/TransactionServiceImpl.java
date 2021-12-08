package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionAdminModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.converter.TransactionConverter;
import com.example.banktransaction.exception.APIRequestException;
import com.example.banktransaction.exception.ActivationException;
import com.example.banktransaction.exception.AuthorityException;
import com.example.banktransaction.exception.StatusException;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.Account;
import com.example.banktransaction.persistence.account.AccountRepository;
import com.example.banktransaction.persistence.transaction.Transaction;
import com.example.banktransaction.persistence.transaction.TransactionRepository;

import com.example.banktransaction.persistence.transaction.TransactionType;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;


    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionConverter transactionConverter, AccountRepository accountRepository, RestTemplateBuilder restTemplateBuilder) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
        this.accountRepository = accountRepository;
        this.restTemplate = restTemplateBuilder.build();;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionAdminModel> getAll(){
        List<Transaction> all = transactionRepository.findAll();
        return transactionConverter.transactionsToAdminModels(all);
    }
    @Override
    @Transactional(readOnly = true)
    public List<TransactionUserResponseModel> getAllByUserId(Long id) {
        List<Transaction> allByUserId = transactionRepository.getAllByUserId(id);
        return transactionConverter.transactionsToResponses(allByUserId);
    }

    @Override
    @Transactional
    public TransactionUserResponseModel add(TransactionUserRequestModel request, Long userId){

        if(accountRepository.getAccountByNumber(request.getFrom()).getUser().getId()!=userId){
            throw new AuthorityException("You can use only your accounts");
        }
        else if(accountRepository.getAccountByNumber(request.getFrom()).getStatus()!=Status.ACCEPTED
                || accountRepository.getAccountByNumber(request.getTo()).getStatus()!=Status.ACCEPTED
        || !accountRepository.getAccountByNumber(request.getFrom()).isActive()
                || !accountRepository.getAccountByNumber(request.getTo()).isActive()){
            throw new StatusException("You can use only active accepted accounts");
        }else if(request.getType().equals(TransactionType.EXCHANGE) && request.getFrom().equals(request.getTo())){
            throw new APIRequestException("Choose different accounts as sender and receiver");
        }else if(!request.getType().equals(TransactionType.EXCHANGE) && !request.getFrom().equals(request.getTo())){
            throw new APIRequestException("Choose the same account as sender and receiver");
        }else if(request.getType().equals(TransactionType.WITHDRAWAL) || request.getType().equals(TransactionType.EXCHANGE)){
            List<Transaction> allByUserId = transactionRepository.getAllByUserId(userId);
            List<Account> curAccount = new ArrayList<>();
            curAccount.add(accountRepository.getAccountByNumber(request.getFrom()));
            if(getBalance(allByUserId,curAccount).get(0)-request.getAmount()<0){
                throw new APIRequestException("Not enough balance");
            }
        }
        Transaction adding = transactionConverter.requestToTransaction(request);
        adding.setFrom(accountRepository.getAccountByNumber(request.getFrom()));
        adding.setTo(accountRepository.getAccountByNumber(request.getTo()));
        if(request.getType().equals(TransactionType.EXCHANGE)){
            String grailsUrl = "http://localhost:8080/exchange/exchange?fromCur="
                    +
                    adding.getFrom().getCurrency().name()
                    +
                    "&toCur="
                    +
                    adding.getTo().getCurrency().name()
                    +
                    "&amount="
                    +
                    request.getAmount();
            String toAmountString = restTemplate.getForObject(grailsUrl, String.class);
            adding.setToAmount(Double.valueOf(toAmountString));
        }
        adding.setActive(true);
        Date now = new Date();
        adding.setDateCreated(now);
        adding.setLastUpdated(now);
        adding.setStatus(Status.PENDING);
        return transactionConverter.transactionToResponse(transactionRepository.save(adding));
    }
    @Override
    @Transactional
    public TransactionAdminModel accept(Long id){
        Transaction byId = transactionRepository.getById(id);
        byId.setStatus(Status.ACCEPTED);
        byId.setLastUpdated(new Date());
        return transactionConverter.transactionToAdminModel(transactionRepository.save(byId));
    }
    @Override
    @Transactional
    public TransactionAdminModel reject(Long id){
        Transaction byId = transactionRepository.getById(id);
        byId.setStatus(Status.REJECTED);
        byId.setLastUpdated(new Date());
        return transactionConverter.transactionToAdminModel(transactionRepository.save(byId));
    }

    @Override
    @Transactional
    public TransactionUserResponseModel update(Long id, TransactionUserRequestModel request, Long userId)  {
        if(userId == transactionRepository.getById(id).getFrom().getUser().getId()){
            if(accountRepository.getAccountByNumber(request.getFrom()).getStatus()!=Status.ACCEPTED
                    || accountRepository.getAccountByNumber(request.getTo()).getStatus()!=Status.ACCEPTED
                    || !accountRepository.getAccountByNumber(request.getFrom()).isActive()
                    || !accountRepository.getAccountByNumber(request.getTo()).isActive()){
                throw new StatusException("You can use only active accepted accounts");
            }else if(request.getType().equals(TransactionType.EXCHANGE) && request.getFrom().equals(request.getTo())){
                throw new APIRequestException("Choose different accounts as sender and receiver");
            }else if(!request.getType().equals(TransactionType.EXCHANGE) && !request.getFrom().equals(request.getTo())){
                throw new APIRequestException("Choose the same account as sender and receiver");
            }else if(request.getType().equals(TransactionType.WITHDRAWAL) || request.getType().equals(TransactionType.EXCHANGE)){
                List<Transaction> allByUserId = transactionRepository.getAllByUserId(userId);
                List<Account> curAccount = new ArrayList<>();
                curAccount.add(accountRepository.getAccountByNumber(request.getFrom()));
                if(getBalance(allByUserId,curAccount).get(0)-request.getAmount()<0){
                    throw new APIRequestException("Not enough balance");
                }
            }
            Transaction transaction = transactionRepository.getById(id);
            if(transaction.getStatus()==Status.PENDING){
                if(accountRepository.getAccountByNumber(request.getTo()).getStatus() == Status.ACCEPTED){
                    transaction.setAmount(request.getAmount());
                    transaction.setType(request.getType());
                    transaction.setTo(accountRepository.getAccountByNumber(request.getTo()));
                    Date now = new Date();
                    transaction.setLastUpdated(now);
                    return transactionConverter.transactionToResponse(transactionRepository.save(transaction));
                }else{
                    throw new StatusException("You can use only accepted accounts");
                }

            }
            else{
                throw new StatusException("You can update only  transactions with PENDING status");
            }
        }
        else{
            throw new AuthorityException("You can update only your transactions");
        }
    }

    @Override
    public TransactionUserResponseModel deActivate(Long id, Long userId)  {
        Transaction transaction = transactionRepository.getById(id);
        if(!transaction.isActive()){
            throw new ActivationException("Already inactive");
        }
        if(transaction.getStatus().equals(Status.ACCEPTED) || transaction.getStatus().equals(Status.REJECTED)){
            throw new StatusException("You can deActive only  transactions with PENDING status");
        }
        if(transaction.getFrom().getUser().getId() != userId){
            throw new AuthorityException("You can delete only your transactions");
        }else{
            transaction.setActive(false);
            transaction.setLastUpdated(new Date());
        }
        return transactionConverter.transactionToResponse(transactionRepository.save(transaction));
    }

    @Override
    public TransactionUserResponseModel activate(Long id, Long userId) {
        Transaction transaction = transactionRepository.getById(id);
        if(transaction.isActive()){
            throw new ActivationException("Already Active");
        }
        if(transaction.getFrom().getUser().getId() != userId){
            throw new AuthorityException("You can activate only your transactions");
        }else{
            transaction.setActive(true);
            transaction.setLastUpdated(new Date());
        }
        return transactionConverter.transactionToResponse(transactionRepository.save(transaction));
    }
    //Balance
    @Override
    public List<Double> balance(Long id) {

        List<Account> accounts = accountRepository.getAllByUserId(id);
        List<Transaction> transactions = transactionRepository.getAllByUserId(id);


        return getBalance(transactions, accounts);
    }


    public List<Double> getBalance(List<Transaction> transactions, List<Account> accounts) {
        List<Double> balances = new ArrayList<>();
        for (Account account : accounts) {
            double balance = 0.0;
            List<Transaction> addingByAccount = transactionRepository.getAddingByAccount(account.getNumber());
            for (Transaction tr : addingByAccount) {
                if (!tr.getStatus().equals(Status.ACCEPTED)) {
                    continue;
                }
                if (tr.getType().equals(TransactionType.DEPOSIT)) {
                    balance += tr.getAmount();
                } else {
                    balance += tr.getToAmount();
                }
            }
            for (Transaction transaction : transactions) {
                if (!transaction.getStatus().equals(Status.ACCEPTED)) {
                    continue;
                }
                if (transaction.getType().equals(TransactionType.DEPOSIT)) {
                    continue;
                }
                if (account.getNumber().equals(transaction.getFrom().getNumber())) {
                    balance -= transaction.getAmount();
                }
//                else {
//                    if (transaction.getType().equals(TransactionType.EXCHANGE)) {
//                        balance += transaction.getToAmount();
//                    }
//                }
            }
            balances.add(balance);
        }


        return balances;
    }
}
