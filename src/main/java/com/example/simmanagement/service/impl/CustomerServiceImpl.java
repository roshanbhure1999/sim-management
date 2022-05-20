package com.example.simmanagement.service.impl;

import com.example.simmanagement.dto.CustomerDTO;
import com.example.simmanagement.dto.Mail;
import com.example.simmanagement.entity.Customer;
import com.example.simmanagement.entity.Sim;
import com.example.simmanagement.exception.SimException;
import com.example.simmanagement.repository.CustomerRepository;
import com.example.simmanagement.repository.SimRepository;
import com.example.simmanagement.service.CustomerService;
import com.example.simmanagement.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SimRepository simRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public Customer createCustomer(CustomerDTO customerDto) {
        Customer customer = null;
        if (Objects.nonNull(customerDto.getCustomerId())) {
            Optional<Customer> customerOptional = customerRepository.findById(customerDto.getCustomerId());
            if (!customerOptional.isPresent()) {
                log.error("No customer exist for customer id ::{}", customerDto.getCustomerId());
                throw new SimException("No  customer for customer id ::" + customerDto.getCustomerId(), HttpStatus.NOT_FOUND);
            }
            customer = customerOptional.get();
            if (!Objects.equals(customer.getAadharNumber(), customerDto.getAadharNumber())) {
                log.error("Not allowed to update Aadhar number");
                throw new SimException("Not allowed to update Aadhar number", HttpStatus.BAD_REQUEST);

            }

            if (!Objects.equals(customer.getFirstName(), customerDto.getFirstName())) {
                log.error("Not allowed to update First Name");
                throw new SimException("Not allowed to update First Name", HttpStatus.BAD_REQUEST);

            }
            if (!Objects.equals(customer.getLastName(), customerDto.getLastName())) {
                log.error("Not allowed to update Last Name");
                throw new SimException("Not allowed to update Last Name", HttpStatus.BAD_REQUEST);

            }
            copyDtoToDTO(customer, customerDto);

        } else {
            customer = new Customer();
            BeanUtils.copyProperties(customerDto, customer);
        }
        Customer result = null;
        try {
            result = customerRepository.save(customer);
        } catch (Exception e) {
            log.error("Error :: {}", e.getMessage());
            throw new SimException("Customer should have unique AadharNumber And Emsa.", HttpStatus.BAD_REQUEST);

        }
        return result;
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<CustomerDTO> allCustomer = customerRepository.findAll().stream().map(this::toCustomerDTO).collect(Collectors.toList());
        if (allCustomer.size() > 0) {
            return allCustomer;
        } else throw new SimException("", HttpStatus.NO_CONTENT);
    }

    @Override
    public CustomerDTO findById(Long id) {
        CustomerDTO result = customerRepository.findById(id).map(this::toCustomerDTO).orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new SimException("Customer does not exit", HttpStatus.NO_CONTENT);
        }
    }


    @Override
    public List<Sim> getSims(long id) {
        List<Sim> accounts = simRepository.findByCustomer(customerRepository.findById(id));
        if (accounts != null) {
            return accounts;
        } else {
            throw new SimException("Customer does not exit", HttpStatus.NO_CONTENT);
        }
    }


    @Override
    public String[] getMailAfterSevenDay() {
        List<Customer> customers = customerRepository.getCustomer();
        String[] array = new String[customers.size()];
        if (customers.size() >= 0) {
            for (int i = 0; i < customers.size(); i++) {
                array[i] = customers.get(i).getEmail();
            }
        }
        return array;
    }

    @Override
    public ByteArrayInputStream dailyExport() {
        List<Customer> customersListOneDayBeforeBirthday = customerRepository.getCustomersListOneDayBeforeBirthday();
        List<CustomerDTO> customerSimDTOS = new ArrayList<>();

        for (Customer customer : customersListOneDayBeforeBirthday) {
            List<String> mobileNums = new ArrayList<>();
            mobileNums.add(customer.getMobileNumber());

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(customer.getCustomerId());
            customerDTO.setAddress(customer.getAddress());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setDateOfBirth(customer.getDateOfBirth());
            customerDTO.setMobileNumber(customer.getMobileNumber());
            customerSimDTOS.add(customerDTO);

        }
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Daily Export");

            Row row = sheet.createRow(0);

            // Define header cell style
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Creating header cells
            Cell cell = row.createCell(0);
            cell.setCellValue("Customer Id");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("First Name");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Last Name");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Email");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Address");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Mobile Number");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(6);
            cell.setCellValue("Date Of Birth");
            cell.setCellStyle(headerCellStyle);

            // Creating data rows
            for (int i = 0; i < customerSimDTOS.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(customerSimDTOS.get(i).getCustomerId());
                dataRow.createCell(1).setCellValue(customerSimDTOS.get(i).getFirstName());
                dataRow.createCell(2).setCellValue(customerSimDTOS.get(i).getLastName());
                dataRow.createCell(3).setCellValue(customerSimDTOS.get(i).getEmail());
                dataRow.createCell(4).setCellValue(customerSimDTOS.get(i).getAddress());
                dataRow.createCell(5).setCellValue(customerSimDTOS.get(i).getMobileNumber());
                dataRow.createCell(6).setCellValue(customerSimDTOS.get(i).getDateOfBirth().toString());
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException ex) {
            log.error("Error during export Excel file", ex);
        }
        return null;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void mail() {
        Mail mail = new Mail();
        mail.setMailFrom("roshanbhure1999@gmail.com");
        mail.setMailTo(getMailAfterSevenDay());
        mail.setMailSubject("Birthday Wish");
        mail.setMailContent("Wish You Many Many Happy Returns Of The Day!\n\nBest Of Luck For Your Future");
        emailSenderService.sendEmail(mail);
    }


    private CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    private void copyDtoToDTO(Customer customer, CustomerDTO customerDTO) {
        BeanUtils.copyProperties(customerDTO, customer, "firstName,lastName,panNumber,aadharNumber");
    }


}