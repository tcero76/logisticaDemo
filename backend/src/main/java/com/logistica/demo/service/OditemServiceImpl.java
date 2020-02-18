package com.logistica.demo.service;

import com.logistica.demo.model.Oditem;
import com.logistica.demo.repository.OditemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OditemServiceImpl implements OditemService {

    @Autowired
    public OditemRepo oditemRepo;

    @Override
    @Transactional
    public void listSave(List<Oditem> oditems) {
        oditems.stream()
                .forEach(odi -> oditemRepo.save(odi));
    }
}
