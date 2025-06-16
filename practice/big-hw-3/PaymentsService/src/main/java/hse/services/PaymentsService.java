package hse.services;

import hse.entities.Account;
import hse.entities.PaymentTask;
import hse.dto.OrderEvent;
import hse.repositories.AccountRepository;
import hse.repositories.PaymentTasksRepository;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentsService {
    private final AccountRepository accountRepository;

    private final PaymentTasksRepository eventRepository;

    public boolean createAccount(Integer userId) {
        Account account = new Account();
        account.setId(userId);
        account.setBalance(0.0);
        if (accountRepository.existsById(userId)) {
            return false;
        }
        accountRepository.save(account);
        return true;
    }

    public boolean topUp(Integer id, Double amount) {
        Account account = accountRepository.findById(id).orElse(new Account());
        if (!Objects.equals(account.getId(), id)) {
            return false;
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        return true;
    }

    public void processPayment(OrderEvent event) {
        Account user = accountRepository.getAccountById(event.userId());
        PaymentTask task = new PaymentTask();
        task.setIsFinished(false);
        task.setId(event.orderId());
        task.setUserId(event.userId());
        if (user == null || user.getBalance() < event.amount()) {
            task.setStatus(false);
        } else {
            task.setStatus(true);
            user.setBalance(user.getBalance() - event.amount());
            accountRepository.save(user);
        }
        eventRepository.save(task);
    }

    public Double getBalance(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return account.get().getBalance();
        }
        return -1.0;
    }
}
