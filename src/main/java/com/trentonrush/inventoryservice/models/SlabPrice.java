package com.trentonrush.inventoryservice.models;

import com.trentonrush.inventoryservice.exceptions.InvalidInputException;
import com.trentonrush.inventoryservice.models.dtos.SlabDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.Objects;

import static com.trentonrush.inventoryservice.utils.InventoryConstants.*;

@Document(collection = "slab_prices")
public class SlabPrice {

    @Id
    private String id;
    private String color;
    private String type;
    private BigDecimal amountPerSqFt;
    private Currency currency;
    private Instant creationDate;
    private Instant modificationDate;

    public SlabPrice() {
    }

    public SlabPrice(BigDecimal amountPerSqFt, Currency currency) {
        if (amountPerSqFt.scale() > currency.getDefaultFractionDigits()) {
            throw new InvalidInputException("Scale of amount exceeds currency's default fraction digits.");
        }
        this.amountPerSqFt = amountPerSqFt;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmountPerSqFt() {
        return amountPerSqFt;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setAmountPerSqFtAndCurrency(BigDecimal amountPerSqFt, Currency currency) {
        if (amountPerSqFt.scale() > currency.getDefaultFractionDigits()) {
            throw new InvalidInputException("Scale of amount exceeds currency's default fraction digits.");
        }
        this.amountPerSqFt = amountPerSqFt;
        this.currency = currency;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
     * Build SlabPrice from DTO
     * @param slabDTO object containing price details
     * @return new SlabPrice
     */
    public static SlabPrice buildSlabPrice(SlabDTO slabDTO) {
        SlabPrice slabPrice = new SlabPrice();
        slabPrice.amountPerSqFt = new BigDecimal(slabDTO.getSqftPrice());
        slabPrice.currency = Currency.getInstance(USD_CURRENCY_CODE);
        if (slabPrice.amountPerSqFt.scale() > slabPrice.currency.getDefaultFractionDigits()) {
            throw new InvalidInputException("Scale of amount exceeds currency's default fraction digits.");
        }
        slabPrice.color = slabDTO.getColor();
        slabPrice.type = slabDTO.getType();
        return slabPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlabPrice slabPrice = (SlabPrice) o;
        return amountPerSqFt.equals(slabPrice.amountPerSqFt) && currency.equals(slabPrice.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountPerSqFt, currency);
    }

    @Override
    public String toString() {
        return "SlabPrice{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", amountPerSqFt=" + amountPerSqFt +
                " " + currency.getSymbol() +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
