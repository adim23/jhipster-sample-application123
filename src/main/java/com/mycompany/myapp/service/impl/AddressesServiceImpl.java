package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Addresses;
import com.mycompany.myapp.repository.AddressesRepository;
import com.mycompany.myapp.service.AddressesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Addresses}.
 */
@Service
@Transactional
public class AddressesServiceImpl implements AddressesService {

    private final Logger log = LoggerFactory.getLogger(AddressesServiceImpl.class);

    private final AddressesRepository addressesRepository;

    public AddressesServiceImpl(AddressesRepository addressesRepository) {
        this.addressesRepository = addressesRepository;
    }

    @Override
    public Addresses save(Addresses addresses) {
        log.debug("Request to save Addresses : {}", addresses);
        return addressesRepository.save(addresses);
    }

    @Override
    public Optional<Addresses> partialUpdate(Addresses addresses) {
        log.debug("Request to partially update Addresses : {}", addresses);

        return addressesRepository
            .findById(addresses.getId())
            .map(existingAddresses -> {
                if (addresses.getAddress() != null) {
                    existingAddresses.setAddress(addresses.getAddress());
                }
                if (addresses.getAddressNo() != null) {
                    existingAddresses.setAddressNo(addresses.getAddressNo());
                }
                if (addresses.getZipCode() != null) {
                    existingAddresses.setZipCode(addresses.getZipCode());
                }
                if (addresses.getProsfLetter() != null) {
                    existingAddresses.setProsfLetter(addresses.getProsfLetter());
                }
                if (addresses.getNameLetter() != null) {
                    existingAddresses.setNameLetter(addresses.getNameLetter());
                }
                if (addresses.getLetterClose() != null) {
                    existingAddresses.setLetterClose(addresses.getLetterClose());
                }
                if (addresses.getFirstLabel() != null) {
                    existingAddresses.setFirstLabel(addresses.getFirstLabel());
                }
                if (addresses.getSecondLabel() != null) {
                    existingAddresses.setSecondLabel(addresses.getSecondLabel());
                }
                if (addresses.getThirdLabel() != null) {
                    existingAddresses.setThirdLabel(addresses.getThirdLabel());
                }
                if (addresses.getFourthLabel() != null) {
                    existingAddresses.setFourthLabel(addresses.getFourthLabel());
                }
                if (addresses.getFifthLabel() != null) {
                    existingAddresses.setFifthLabel(addresses.getFifthLabel());
                }
                if (addresses.getFavourite() != null) {
                    existingAddresses.setFavourite(addresses.getFavourite());
                }

                return existingAddresses;
            })
            .map(addressesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Addresses> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Addresses> findOne(Long id) {
        log.debug("Request to get Addresses : {}", id);
        return addressesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Addresses : {}", id);
        addressesRepository.deleteById(id);
    }
}
