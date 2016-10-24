package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by rdw1995 on 10/24/16.
 */
@Controller
public class PurchaseController {
    @Autowired
    CustomerRepo customers;
    @Autowired
    PurchaseRepo purchases;


    @PostConstruct
    public void init () throws FileNotFoundException {
        if(customers.count() == 0){
            File f = new File("customers.csv");
            Scanner fileScanner = new Scanner(f);
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] columns = line.split("\\,");
                Customer customer = new Customer(columns[0], columns[1]);
                customers.save(customer);
            }
        }
        if(purchases.count() == 0){
            File f = new File("purchases.csv");
            Scanner fileScanner = new Scanner(f);
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] columns = line.split("\\,");
                Purchase purchase = new Purchase(columns[1], columns[2],Integer.valueOf(columns[3]),
                        columns[4], customers.findOne(Integer.valueOf(columns[0])));
                purchases.save(purchase);
            }
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home (Model model, String category){
        Iterable<Purchase> plist;
        Iterable<Customer> clist = customers.findAll();

        if(category != null){
            plist = purchases.findByCategory(category);
        }
        else {
            plist = purchases.findAll();
        }

        model.addAttribute("purchases", plist);
        model.addAttribute("categories", category);
        return "home";
    }

}
