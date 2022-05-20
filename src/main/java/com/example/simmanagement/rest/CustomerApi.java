package com.example.simmanagement.rest;
import com.example.simmanagement.dto.CustomerDTO;
import com.example.simmanagement.entity.Sim;
import com.example.simmanagement.service.CustomerService;
import com.example.simmanagement.service.EmailSenderService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/customer")
@Validated
public class CustomerApi {

    @Autowired
    private CustomerService customerService;


    @Autowired
    private EmailSenderService mailService;

    @PutMapping
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping(path = "/id/{customer_Id}")
    public ResponseEntity<List<Sim>> getSims(@PathVariable long customer_Id) {
        return ResponseEntity.ok(customerService.getSims(customer_Id));
    }

    @GetMapping(path = "/mail")
    public void sendMail() {
        customerService.mail();
    }

    @GetMapping("/downloadExcelFile")
    public void downloadExcelFile(HttpServletResponse response) throws IOException {
        ByteArrayInputStream byteArrayInputStream = customerService.dailyExport();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=CustomerBirthdayList.xlsx");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }


}
