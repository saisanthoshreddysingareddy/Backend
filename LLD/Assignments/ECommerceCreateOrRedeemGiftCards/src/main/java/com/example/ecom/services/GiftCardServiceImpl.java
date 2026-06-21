package com.example.ecom.services;

import com.example.ecom.exceptions.GiftCardDoesntExistException;
import com.example.ecom.exceptions.GiftCardExpiredException;
import com.example.ecom.models.GiftCard;
import com.example.ecom.models.LedgerEntry;
import com.example.ecom.models.TransactionType;
import com.example.ecom.repositories.GiftCardRepository;
import com.example.ecom.repositories.LedgerEntryRepository;
import com.example.ecom.utils.GiftCardUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCardServiceImpl implements GiftCardService{
    GiftCardRepository giftCardRepository;
    LedgerEntryRepository ledgerEntryRepository;

    // Constructor
    public GiftCardServiceImpl(GiftCardRepository giftCardRepository,
                               LedgerEntryRepository ledgerEntryRepository){
        this.giftCardRepository = giftCardRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    // Interface methods
    public GiftCard createGiftCard(double amount){

        // Create Gift Card with given amount
        GiftCard giftCard = new GiftCard();
        giftCard.setCreatedAt(new Date());
        giftCard.setExpiresAt(GiftCardUtils.getExpirationDate(new Date()));
        giftCard.setAmount(amount);
        giftCard.setGiftCardCode(GiftCardUtils.generateGiftCardCode());

        // Create initial Ledger entry
        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setTransactionType(TransactionType.CREDIT);
        ledgerEntry.setAmount(amount);
        ledgerEntry.setCreatedAt(new Date());

        // Add first ledger to Gift Card
        List<LedgerEntry> ledgerEntries = new ArrayList<>();
        ledgerEntries.add(ledgerEntry);
        giftCard.setLedger(ledgerEntries);

        return giftCardRepository.save(giftCard);
    }

    public GiftCard redeemGiftCard(int giftCardId, double amountToRedeem) throws GiftCardDoesntExistException, GiftCardExpiredException{
        // Check Gift Card Existence
        Optional<GiftCard> optionalGiftCard = giftCardRepository.findById(giftCardId);
        if(optionalGiftCard.isEmpty()){
            throw new GiftCardDoesntExistException("Gift card doesn't exist");
        }
        GiftCard giftCard = optionalGiftCard.get();

        // Check if gift card expired or not
        Date giftCardExpirationDate = giftCard.getExpiresAt();
        Date currentDate = new Date();
        if(giftCardExpirationDate.before(currentDate)){
            throw new GiftCardExpiredException("Gift card expired");
        }

        // Check enough amount to be redeemed
        double giftCardAmount = giftCard.getAmount();
        double actualDeductedAmount = 0L;
        double newBalance = 0L;
        if(amountToRedeem <= giftCardAmount){
            actualDeductedAmount = amountToRedeem;
            newBalance = giftCardAmount - amountToRedeem;
        }
        if(amountToRedeem > giftCardAmount){
            actualDeductedAmount = giftCardAmount;
            newBalance =0.0;
        }

        // Update gift card amount
        giftCard.setAmount(newBalance);

        // Create Ledger
        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setAmount(actualDeductedAmount);
        ledgerEntry.setTransactionType(TransactionType.DEBIT);
        ledgerEntry.setCreatedAt(new Date());

        // Add ledger to gift card
        if (giftCard.getLedger() == null) {
            giftCard.setLedger(new ArrayList<>());
        }
        giftCard.getLedger().add(ledgerEntry);

        return giftCardRepository.save(giftCard);
    }
}
