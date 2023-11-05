package com.cytech.marketplace.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "\"Articles\"", schema = "public")
public class Articles {
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Column(name = "stock")
    private BigInteger stock;
    @Basic
    @Column(name = "image")
    private String image;
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id")
    private UUID id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigInteger getStock() {
        return stock;
    }

    public void setStock(BigInteger stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Articles articles = (Articles) o;

        if (!Objects.equals(name, articles.name)) return false;
        if (!Objects.equals(price, articles.price)) return false;
        if (!Objects.equals(stock, articles.stock)) return false;
        if (!Objects.equals(image, articles.image)) return false;
        if (!Objects.equals(id, articles.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
