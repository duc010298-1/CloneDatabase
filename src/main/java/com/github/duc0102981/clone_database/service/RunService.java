package com.github.duc0102981.clone_database.service;

import com.github.duc0102981.clone_database.entity.CustomerEntity;
import com.github.duc0102981.clone_database.entity.CustomerOldEntity;
import com.github.duc0102981.clone_database.repository.CustomerOldRepository;
import com.github.duc0102981.clone_database.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RunService {

    @Autowired
    public RunService(CustomerRepository customerRepository, CustomerOldRepository customerOldRepository) {
        List<CustomerOldEntity> customerOldEntities = customerOldRepository.findAll();
        System.out.println("Total old: " + customerOldEntities.size());
        List<CustomerEntity> customerEntities = convertOldCusToNewCus(customerOldEntities);
        customerRepository.saveAll(customerEntities);
        customerEntities = customerRepository.findAll();
        System.out.println("Total new: " + customerEntities.size());
    }

    private List<CustomerEntity> convertOldCusToNewCus(List<CustomerOldEntity> customerOldEntities) {
        List<CustomerEntity> customerEntities = new ArrayList<>();
        for (CustomerOldEntity c : customerOldEntities) {
            CustomerEntity cus = new CustomerEntity();
            cus.setName(standardizeString(c.getCustomerName()));
            cus.setNameSearch(standardizeSpaceString(c.getNameSearch()));
            cus.setYearOfBirth(c.getYob());
            cus.setAddress(standardizeString(c.getAddress()));
            cus.setAddressSearch(standardizeSpaceString(c.getAddressSearch()));
            cus.setDayVisit(c.getDayVisit());
            cus.setExpectedDateOfBirth(c.getExpectedDob());
            cus.setResult(c.getResult());
            cus.setNote(c.getNote());
            cus.setReport(c.getReport());
            customerEntities.add(cus);
        }
        return customerEntities;
    }

    private String standardizeSpaceString(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        return str;
    }

    private String standardizeString(String str) {
        str = standardizeSpaceString(str);
        str = str.toLowerCase();
        String temp[] = str.split(" ");
        str = "";
        for (int i = 0; i < temp.length; i++) {
            str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) {
                str += " ";
            }
        }
        return str;
    }
}
