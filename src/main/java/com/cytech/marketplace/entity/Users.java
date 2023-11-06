package com.cytech.marketplace.entity;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigInteger;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "\"Users\"", schema = "public")
public class Users {
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Basic
    @Column(name = "loyalty_points")
    private BigInteger loyaltyPoints;
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id")
    private UUID id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public BigInteger getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(BigInteger loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
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

        Users users = (Users) o;

        if (!Objects.equals(id, users.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isAdmin != null ? isAdmin.hashCode() : 0);
        result = 31 * result + (loyaltyPoints != null ? loyaltyPoints.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
