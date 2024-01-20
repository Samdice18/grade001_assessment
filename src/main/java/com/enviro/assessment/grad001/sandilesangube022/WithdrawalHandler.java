package com.enviro.assessment.grad001.sandilesangube022;

import com.enviro.assessment.grad001.sandilesangube022.withdrawal.Withdrawal;
import com.enviro.assessment.grad001.sandilesangube022.withdrawal.WithdrawalData;
import com.enviro.assessment.grad001.sandilesangube022.product.Product;
import com.enviro.assessment.grad001.sandilesangube022.product.ProductsData;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/withdrawals")
public class WithdrawalHandler {
    private static final String TOPIC_NAME = "WITHDRAWAL_NOTICE";
    private static final String NTFY_URL = "https://ntfy.sh/" + TOPIC_NAME;
    @Autowired
    private WithdrawalData withdrawalData;

    @Autowired
    private ProductsData productsData;

    @GetMapping
    public List<Withdrawal> getAllWithdrawals(){
        return withdrawalData.findAll();
    }

    @PostMapping
    public ResponseEntity<String> performWithdrawal(@RequestBody Withdrawal withdrawal) {
        /*
        how to withdraw:
        curl -X POST -H "Content-Type: application/json" -d '{"productId": 1, "withdrawalAmount": 500.0, "bankName":
        "capitec", "accountNumber": "12345678"}' http://localhost:8080/withdrawals
         */
        Long productId = withdrawal.getProductId();

        Optional<Product> product = productsData.findById(productId);

        if (product.isPresent()) {
            return handleValidProduct(product.get(), withdrawal);
        }

        return ResponseEntity.ok("Failed to complete withdrawal as the product doesn't exist\n");
    }

    private ResponseEntity<String> handleValidProduct(Product product, Withdrawal withdrawal) {
        BigDecimal withdrawalAmount = withdrawal.getWithdrawalAmount();
        String productName = product.getName();
        BigDecimal balance = product.getBalance();

        if (isWithdrawalAmountWithinLimit(balance, withdrawalAmount)) {
            return processSuccessfulWithdrawal(product, withdrawal);
        }

        return processFailedWithdrawal(productName, product.getId());
    }

    private ResponseEntity<String> processSuccessfulWithdrawal(Product product, Withdrawal withdrawal) {
        BigDecimal withdrawalAmount = withdrawal.getWithdrawalAmount();
        BigDecimal newAmount = product.getBalance().subtract(withdrawalAmount);
        product.changeBalance(newAmount);
        String bankName = withdrawal.getBankName();
        String accountNumber = withdrawal.getAccountNumber();
        withdrawal.setDate(LocalDate.now());
        String message = buildSuccessfulWithdrawalMessage(product.getName(), product.getId(),
                product.getBalance(), withdrawalAmount, newAmount, bankName, accountNumber);
        sendAlertToNtfy(message);
        productsData.save(product);
        withdrawalData.save(withdrawal);
        return ResponseEntity.ok("Withdrawal was successful\n");
    }

    private ResponseEntity<String> processFailedWithdrawal(String productName, Long productId) {
        String message = buildFailedWithdrawalMessage(productName, productId);
        sendAlertToNtfy(message);
        return ResponseEntity.ok("Failed to complete withdrawal\n");
    }

    public boolean isWithdrawalAmountWithinLimit(BigDecimal balance, BigDecimal withdrawalAmount){
        BigDecimal maximumAmountAllowed = balance.multiply(BigDecimal.valueOf(0.9));

        return withdrawalAmount.compareTo(maximumAmountAllowed) <= 0;
    }

    private static String buildSuccessfulWithdrawalMessage(String productName, Long productID, BigDecimal previousBalance
            , BigDecimal amount, BigDecimal newBalance, String accountName, String accountNumber){
        return "This is to inform you of your recent withdrawal from the investment product,\"" +
                productName + "\", with the product ID \""+productID+"\". Here are the details:\n" +
                "\n" +
                "PREVIOUS BALANCE: " + previousBalance + "\n"+
                "WITHDRAWN AMOUNT: " + amount + "\n"+
                "NEW BALANCE: " + newBalance + "\n"+
                "\n" +
                "The withdrawn amount has been successfully deposited int the bank account" +
                "specified below:\n" +
                "\n" +
                "BANK ACCOUNT NAME: " + accountName + "\n"+
                "ACCOUNT NUMBER: " + accountNumber + "\n"+
                "DATE OF TRANSACTION: " + LocalDate.now() + "\n"+
                "\n" +
                "If you have any questions or require further clarification, please feel free" +
                "to reach out.";
    }

    private static String buildFailedWithdrawalMessage(String productName, Long productID){
        return "We regret to inform you that the withdrawal attempt from the investment product," +
                productName + "(ID: "+productID+") has encountered an issue. Unfortunately, the" +
                "transaction could not be processed successfully.";
    }

    private static void sendAlertToNtfy(String message) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpPost postRequest = new HttpPost(NTFY_URL);

            StringEntity entity = new StringEntity(message);
            postRequest.setEntity(entity);

            postRequest.setHeader("Content-Type", "text/plain");

            httpClient.execute(postRequest);

        } catch (Exception ignored) {}
    }
}
