package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     *
     * @param customerDto
     */
    public void createAccount(CustomerDto customerDto);
}
