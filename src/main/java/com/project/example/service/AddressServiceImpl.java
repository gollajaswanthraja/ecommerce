package com.project.example.service;

import com.project.example.exceptions.APIException;
import com.project.example.exceptions.ResourceNotFoundException;
import com.project.example.model.Address;
import com.project.example.model.User;
import com.project.example.payload.AddressDTO;
import com.project.example.repositories.AddressRepository;
import com.project.example.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {

        Address address = modelMapper.map(addressDTO, Address.class);

        List<Address> addresses = user.getAddresses();
        addresses.add(address);
        user.setAddresses(addresses);

        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress,AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        if(addresses.isEmpty()){
            throw new APIException("No addresses found");
        }
        List<AddressDTO> addressDTOList = addresses.stream()
                .map(address -> modelMapper.map(address,AddressDTO.class))
                .toList();
        return addressDTOList;
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));

        return modelMapper.map(address,AddressDTO.class);
    }

    @Override
    public AddressDTO getUserAddress(User user) {
        Address address = addressRepository.findAddressByUser(user.getUserId());
        if(address==null){
            throw new APIException("Address not found for user : "+user.getUserId());
        }
        return modelMapper.map(address,AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, Address addresss) {
        Address address1 = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","addressId",addressId));
        address1.setBuildingName(addresss.getBuildingName());
        address1.setStreet(addresss.getStreet());
        address1.setCity(addresss.getCity());
        address1.setState(addresss.getState());
        address1.setCountry(addresss.getCountry());
        address1.setPinCode(addresss.getPinCode());
        addressRepository.save(address1);
        return modelMapper.map(address1,AddressDTO.class);
    }

    @Override
    public AddressDTO deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","addressId",addressId));

        User user = address.getUser();
        user.getAddresses().removeIf(adresss ->adresss.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(address);
        return modelMapper.map(address,AddressDTO.class);
    }


}
