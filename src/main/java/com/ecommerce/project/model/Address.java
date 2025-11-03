package com.ecommerce.project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @NotBlank
    @Size(min = 5, message = " Street must have atleast 5 chracter ")
    private String street;
    @NotBlank
    @Size(min = 5, message = " Building name have atleast 5 chracter ")
    private String buildingName;
    @NotBlank
    @Size(min = 4, message = " City name have atleast 4 chracter ")
    private String city;
    @NotBlank
    @Size(min = 2, message = " State name have atleast 2 chracter ")
    private String state;
    @NotBlank
    @Size(min = 2, message = " Country name have atleast 2 chracter ")
    private String country;
    @NotBlank
    @Size(min = 6, message = " Pincode  have atleast 6 chracter ")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(Long addressId, String street, String buildingName, String city, String state, String country, String pincode) {
        this.addressId = addressId;
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
}
