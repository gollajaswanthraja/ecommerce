package com.project.example.service;

import com.project.example.model.Address;
import com.project.example.model.User;
import com.project.example.payload.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressById(Long addressId);

    AddressDTO getUserAddress(User user);

    AddressDTO updateAddress(Long addressId,Address addresss);

    AddressDTO deleteAddress(Long addressId);
}
