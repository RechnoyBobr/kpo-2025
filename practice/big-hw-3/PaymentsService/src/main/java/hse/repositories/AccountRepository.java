package hse.repositories;


import hse.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account getAccountById(Integer id);
}
