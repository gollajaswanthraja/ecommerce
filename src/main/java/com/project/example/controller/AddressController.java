package com.project.example.controller;

import com.project.example.model.Address;
import com.project.example.model.User;
import com.project.example.payload.AddressDTO;
import com.project.example.service.AddressService;
import com.project.example.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
        AddressDTO addressDTO1 = addressService.createAddress(addressDTO,user);
        return new ResponseEntity<>(addressDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses(){
        List<AddressDTO> addressDTOList = addressService.getAddresses();
        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/users/address")
    public ResponseEntity<AddressDTO> getUserAddress(){
        User user = authUtil.loggedInUser();
        AddressDTO addressDTO = addressService.getUserAddress(user);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,
                                                    @RequestBody Address addresss){
        AddressDTO addressDTO = addressService.updateAddress(addressId,addresss);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> deleteAddress(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }
}
