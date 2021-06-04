package com.tienthanh.service;

import java.util.List;
import java.util.Set;

import com.tienthanh.domain.Account;
import com.tienthanh.domain.security.AccountRole;

public interface AccountService {
	List<Account> findAll();

	Account findById(Long id);

	Account save(Account account);

	void createAccount(Account account, Set<AccountRole> accountRoles);
}
