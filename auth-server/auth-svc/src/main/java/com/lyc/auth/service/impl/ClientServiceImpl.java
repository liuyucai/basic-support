package com.lyc.auth.service.impl;

import com.lyc.auth.dto.ClientDTO;
import com.lyc.auth.entity.Client;
import com.lyc.auth.repository.ClientRepository;
import com.lyc.auth.service.ClientService;
import com.lyc.simple.jpa.SimpleServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 21:27
 * @Description:
 */
@Service
@Log4j2
public class ClientServiceImpl extends SimpleServiceImpl<ClientDTO, Client,String, ClientRepository> implements  ClientService{

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client getByClientSecret(String clientSecret) {
        return clientRepository.getByClientSecret(clientSecret);
    }

    @Override
    public String getIdByClientSecret(String clientSecret) {
        return clientRepository.getIdByClientSecret(clientSecret);
    }
}
