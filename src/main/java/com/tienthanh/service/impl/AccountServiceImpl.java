package com.tienthanh.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienthanh.domain.Account;
import com.tienthanh.domain.security.AccountRole;
import com.tienthanh.repository.AccountRepository;
import com.tienthanh.repository.RoleRepository;
import com.tienthanh.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return (List<Account>) accountRepository.findAll();
	}

	@Override
	public void createAccount(Account account, Set<AccountRole> accountRoles) {
		// TODO Auto-generated method stub
		Account localAccount = accountRepository.findByUsername(account.getUsername());
		if (localAccount != null) {
			System.out.println("Account is Exist! Nothing work be done!");
		} else {
			for (AccountRole accountRole : accountRoles) {
				roleRepository.save(accountRole.getRole());
			}
			account.getAccountRoles().addAll(accountRoles);
			accountRepository.save(account);
		}
		return;
	}

	@Override
	public Account findById(Long id) {
		// TODO Auto-generated method stub
		return accountRepository.findById(id).get();
	}

	@Override
	public Account save(Account account) {
		// TODO Auto-generated method stub
		return accountRepository.save(account);
	}

}
