package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthUtil authUtil;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);
        List<Address> addressesList = user.getAddresses();
        addressesList.add(address);
        user.setAddresses(addressesList);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddress() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(a->modelMapper.map(a,AddressDTO.class)).toList();

    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
         Address address = addressRepository.findById(addressId)
                 .orElseThrow(()->new ResourceNotFoundException("Address", "AdressID",addressId));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddress(User user) {
        List<Address> addresses = user.getAddresses();
        return addresses.stream().map(a->modelMapper.map(a,AddressDTO.class)).toList();

    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address addressFromDataBase = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address", "AdressID",addressId));
        addressFromDataBase.setCity(addressDTO.getCity());
        addressFromDataBase.setPincode(addressDTO.getPincode());
        addressFromDataBase.setState(addressDTO.getState());
        addressFromDataBase.setCountry(addressDTO.getCountry());
        addressFromDataBase.setStreet(addressDTO.getStreet());
        addressFromDataBase.setBuildingName(addressDTO.getBuildingName());

        Address updateAddress = addressRepository.save(addressFromDataBase);


        User user = addressFromDataBase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updateAddress);
        userRepo.save(user);
        return modelMapper.map(addressFromDataBase,AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressFromDataBase = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address", "AdressID",addressId));
        User user = addressFromDataBase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userRepo.save(user);
        addressRepository.delete(addressFromDataBase);
        return "Address deleted successfully with address id "+ addressId;
    }
}
