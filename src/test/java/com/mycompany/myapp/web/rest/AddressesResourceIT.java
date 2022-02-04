package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Addresses;
import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.domain.Companies;
import com.mycompany.myapp.domain.ContactTypes;
import com.mycompany.myapp.domain.Countries;
import com.mycompany.myapp.domain.Regions;
import com.mycompany.myapp.repository.AddressesRepository;
import com.mycompany.myapp.service.criteria.AddressesCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AddressesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressesResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_NO = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROSF_LETTER = "AAAAAAAAAA";
    private static final String UPDATED_PROSF_LETTER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_LETTER = "AAAAAAAAAA";
    private static final String UPDATED_NAME_LETTER = "BBBBBBBBBB";

    private static final String DEFAULT_LETTER_CLOSE = "AAAAAAAAAA";
    private static final String UPDATED_LETTER_CLOSE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_FOURTH_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_FOURTH_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_FIFTH_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_FIFTH_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FAVOURITE = false;
    private static final Boolean UPDATED_FAVOURITE = true;

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressesRepository addressesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressesMockMvc;

    private Addresses addresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createEntity(EntityManager em) {
        Addresses addresses = new Addresses()
            .address(DEFAULT_ADDRESS)
            .addressNo(DEFAULT_ADDRESS_NO)
            .zipCode(DEFAULT_ZIP_CODE)
            .prosfLetter(DEFAULT_PROSF_LETTER)
            .nameLetter(DEFAULT_NAME_LETTER)
            .letterClose(DEFAULT_LETTER_CLOSE)
            .firstLabel(DEFAULT_FIRST_LABEL)
            .secondLabel(DEFAULT_SECOND_LABEL)
            .thirdLabel(DEFAULT_THIRD_LABEL)
            .fourthLabel(DEFAULT_FOURTH_LABEL)
            .fifthLabel(DEFAULT_FIFTH_LABEL)
            .favourite(DEFAULT_FAVOURITE);
        return addresses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createUpdatedEntity(EntityManager em) {
        Addresses addresses = new Addresses()
            .address(UPDATED_ADDRESS)
            .addressNo(UPDATED_ADDRESS_NO)
            .zipCode(UPDATED_ZIP_CODE)
            .prosfLetter(UPDATED_PROSF_LETTER)
            .nameLetter(UPDATED_NAME_LETTER)
            .letterClose(UPDATED_LETTER_CLOSE)
            .firstLabel(UPDATED_FIRST_LABEL)
            .secondLabel(UPDATED_SECOND_LABEL)
            .thirdLabel(UPDATED_THIRD_LABEL)
            .fourthLabel(UPDATED_FOURTH_LABEL)
            .fifthLabel(UPDATED_FIFTH_LABEL)
            .favourite(UPDATED_FAVOURITE);
        return addresses;
    }

    @BeforeEach
    public void initTest() {
        addresses = createEntity(em);
    }

    @Test
    @Transactional
    void createAddresses() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();
        // Create the Addresses
        restAddressesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addresses)))
            .andExpect(status().isCreated());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate + 1);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAddresses.getAddressNo()).isEqualTo(DEFAULT_ADDRESS_NO);
        assertThat(testAddresses.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testAddresses.getProsfLetter()).isEqualTo(DEFAULT_PROSF_LETTER);
        assertThat(testAddresses.getNameLetter()).isEqualTo(DEFAULT_NAME_LETTER);
        assertThat(testAddresses.getLetterClose()).isEqualTo(DEFAULT_LETTER_CLOSE);
        assertThat(testAddresses.getFirstLabel()).isEqualTo(DEFAULT_FIRST_LABEL);
        assertThat(testAddresses.getSecondLabel()).isEqualTo(DEFAULT_SECOND_LABEL);
        assertThat(testAddresses.getThirdLabel()).isEqualTo(DEFAULT_THIRD_LABEL);
        assertThat(testAddresses.getFourthLabel()).isEqualTo(DEFAULT_FOURTH_LABEL);
        assertThat(testAddresses.getFifthLabel()).isEqualTo(DEFAULT_FIFTH_LABEL);
        assertThat(testAddresses.getFavourite()).isEqualTo(DEFAULT_FAVOURITE);
    }

    @Test
    @Transactional
    void createAddressesWithExistingId() throws Exception {
        // Create the Addresses with an existing ID
        addresses.setId(1L);

        int databaseSizeBeforeCreate = addressesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addresses)))
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].addressNo").value(hasItem(DEFAULT_ADDRESS_NO)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].prosfLetter").value(hasItem(DEFAULT_PROSF_LETTER)))
            .andExpect(jsonPath("$.[*].nameLetter").value(hasItem(DEFAULT_NAME_LETTER)))
            .andExpect(jsonPath("$.[*].letterClose").value(hasItem(DEFAULT_LETTER_CLOSE)))
            .andExpect(jsonPath("$.[*].firstLabel").value(hasItem(DEFAULT_FIRST_LABEL)))
            .andExpect(jsonPath("$.[*].secondLabel").value(hasItem(DEFAULT_SECOND_LABEL)))
            .andExpect(jsonPath("$.[*].thirdLabel").value(hasItem(DEFAULT_THIRD_LABEL)))
            .andExpect(jsonPath("$.[*].fourthLabel").value(hasItem(DEFAULT_FOURTH_LABEL)))
            .andExpect(jsonPath("$.[*].fifthLabel").value(hasItem(DEFAULT_FIFTH_LABEL)))
            .andExpect(jsonPath("$.[*].favourite").value(hasItem(DEFAULT_FAVOURITE.booleanValue())));
    }

    @Test
    @Transactional
    void getAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get the addresses
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL_ID, addresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addresses.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.addressNo").value(DEFAULT_ADDRESS_NO))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.prosfLetter").value(DEFAULT_PROSF_LETTER))
            .andExpect(jsonPath("$.nameLetter").value(DEFAULT_NAME_LETTER))
            .andExpect(jsonPath("$.letterClose").value(DEFAULT_LETTER_CLOSE))
            .andExpect(jsonPath("$.firstLabel").value(DEFAULT_FIRST_LABEL))
            .andExpect(jsonPath("$.secondLabel").value(DEFAULT_SECOND_LABEL))
            .andExpect(jsonPath("$.thirdLabel").value(DEFAULT_THIRD_LABEL))
            .andExpect(jsonPath("$.fourthLabel").value(DEFAULT_FOURTH_LABEL))
            .andExpect(jsonPath("$.fifthLabel").value(DEFAULT_FIFTH_LABEL))
            .andExpect(jsonPath("$.favourite").value(DEFAULT_FAVOURITE.booleanValue()));
    }

    @Test
    @Transactional
    void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        Long id = addresses.getId();

        defaultAddressesShouldBeFound("id.equals=" + id);
        defaultAddressesShouldNotBeFound("id.notEquals=" + id);

        defaultAddressesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address equals to DEFAULT_ADDRESS
        defaultAddressesShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the addressesList where address equals to UPDATED_ADDRESS
        defaultAddressesShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address not equals to DEFAULT_ADDRESS
        defaultAddressesShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the addressesList where address not equals to UPDATED_ADDRESS
        defaultAddressesShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultAddressesShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the addressesList where address equals to UPDATED_ADDRESS
        defaultAddressesShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address is not null
        defaultAddressesShouldBeFound("address.specified=true");

        // Get all the addressesList where address is null
        defaultAddressesShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAddressContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address contains DEFAULT_ADDRESS
        defaultAddressesShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the addressesList where address contains UPDATED_ADDRESS
        defaultAddressesShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address does not contain DEFAULT_ADDRESS
        defaultAddressesShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the addressesList where address does not contain UPDATED_ADDRESS
        defaultAddressesShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressNoIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressNo equals to DEFAULT_ADDRESS_NO
        defaultAddressesShouldBeFound("addressNo.equals=" + DEFAULT_ADDRESS_NO);

        // Get all the addressesList where addressNo equals to UPDATED_ADDRESS_NO
        defaultAddressesShouldNotBeFound("addressNo.equals=" + UPDATED_ADDRESS_NO);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressNo not equals to DEFAULT_ADDRESS_NO
        defaultAddressesShouldNotBeFound("addressNo.notEquals=" + DEFAULT_ADDRESS_NO);

        // Get all the addressesList where addressNo not equals to UPDATED_ADDRESS_NO
        defaultAddressesShouldBeFound("addressNo.notEquals=" + UPDATED_ADDRESS_NO);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressNoIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressNo in DEFAULT_ADDRESS_NO or UPDATED_ADDRESS_NO
        defaultAddressesShouldBeFound("addressNo.in=" + DEFAULT_ADDRESS_NO + "," + UPDATED_ADDRESS_NO);

        // Get all the addressesList where addressNo equals to UPDATED_ADDRESS_NO
        defaultAddressesShouldNotBeFound("addressNo.in=" + UPDATED_ADDRESS_NO);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressNo is not null
        defaultAddressesShouldBeFound("addressNo.specified=true");

        // Get all the addressesList where addressNo is null
        defaultAddressesShouldNotBeFound("addressNo.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAddressNoContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressNo contains DEFAULT_ADDRESS_NO
        defaultAddressesShouldBeFound("addressNo.contains=" + DEFAULT_ADDRESS_NO);

        // Get all the addressesList where addressNo contains UPDATED_ADDRESS_NO
        defaultAddressesShouldNotBeFound("addressNo.contains=" + UPDATED_ADDRESS_NO);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressNoNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressNo does not contain DEFAULT_ADDRESS_NO
        defaultAddressesShouldNotBeFound("addressNo.doesNotContain=" + DEFAULT_ADDRESS_NO);

        // Get all the addressesList where addressNo does not contain UPDATED_ADDRESS_NO
        defaultAddressesShouldBeFound("addressNo.doesNotContain=" + UPDATED_ADDRESS_NO);
    }

    @Test
    @Transactional
    void getAllAddressesByZipCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where zipCode equals to DEFAULT_ZIP_CODE
        defaultAddressesShouldBeFound("zipCode.equals=" + DEFAULT_ZIP_CODE);

        // Get all the addressesList where zipCode equals to UPDATED_ZIP_CODE
        defaultAddressesShouldNotBeFound("zipCode.equals=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllAddressesByZipCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where zipCode not equals to DEFAULT_ZIP_CODE
        defaultAddressesShouldNotBeFound("zipCode.notEquals=" + DEFAULT_ZIP_CODE);

        // Get all the addressesList where zipCode not equals to UPDATED_ZIP_CODE
        defaultAddressesShouldBeFound("zipCode.notEquals=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllAddressesByZipCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where zipCode in DEFAULT_ZIP_CODE or UPDATED_ZIP_CODE
        defaultAddressesShouldBeFound("zipCode.in=" + DEFAULT_ZIP_CODE + "," + UPDATED_ZIP_CODE);

        // Get all the addressesList where zipCode equals to UPDATED_ZIP_CODE
        defaultAddressesShouldNotBeFound("zipCode.in=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllAddressesByZipCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where zipCode is not null
        defaultAddressesShouldBeFound("zipCode.specified=true");

        // Get all the addressesList where zipCode is null
        defaultAddressesShouldNotBeFound("zipCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByZipCodeContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where zipCode contains DEFAULT_ZIP_CODE
        defaultAddressesShouldBeFound("zipCode.contains=" + DEFAULT_ZIP_CODE);

        // Get all the addressesList where zipCode contains UPDATED_ZIP_CODE
        defaultAddressesShouldNotBeFound("zipCode.contains=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllAddressesByZipCodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where zipCode does not contain DEFAULT_ZIP_CODE
        defaultAddressesShouldNotBeFound("zipCode.doesNotContain=" + DEFAULT_ZIP_CODE);

        // Get all the addressesList where zipCode does not contain UPDATED_ZIP_CODE
        defaultAddressesShouldBeFound("zipCode.doesNotContain=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllAddressesByProsfLetterIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where prosfLetter equals to DEFAULT_PROSF_LETTER
        defaultAddressesShouldBeFound("prosfLetter.equals=" + DEFAULT_PROSF_LETTER);

        // Get all the addressesList where prosfLetter equals to UPDATED_PROSF_LETTER
        defaultAddressesShouldNotBeFound("prosfLetter.equals=" + UPDATED_PROSF_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByProsfLetterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where prosfLetter not equals to DEFAULT_PROSF_LETTER
        defaultAddressesShouldNotBeFound("prosfLetter.notEquals=" + DEFAULT_PROSF_LETTER);

        // Get all the addressesList where prosfLetter not equals to UPDATED_PROSF_LETTER
        defaultAddressesShouldBeFound("prosfLetter.notEquals=" + UPDATED_PROSF_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByProsfLetterIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where prosfLetter in DEFAULT_PROSF_LETTER or UPDATED_PROSF_LETTER
        defaultAddressesShouldBeFound("prosfLetter.in=" + DEFAULT_PROSF_LETTER + "," + UPDATED_PROSF_LETTER);

        // Get all the addressesList where prosfLetter equals to UPDATED_PROSF_LETTER
        defaultAddressesShouldNotBeFound("prosfLetter.in=" + UPDATED_PROSF_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByProsfLetterIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where prosfLetter is not null
        defaultAddressesShouldBeFound("prosfLetter.specified=true");

        // Get all the addressesList where prosfLetter is null
        defaultAddressesShouldNotBeFound("prosfLetter.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByProsfLetterContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where prosfLetter contains DEFAULT_PROSF_LETTER
        defaultAddressesShouldBeFound("prosfLetter.contains=" + DEFAULT_PROSF_LETTER);

        // Get all the addressesList where prosfLetter contains UPDATED_PROSF_LETTER
        defaultAddressesShouldNotBeFound("prosfLetter.contains=" + UPDATED_PROSF_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByProsfLetterNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where prosfLetter does not contain DEFAULT_PROSF_LETTER
        defaultAddressesShouldNotBeFound("prosfLetter.doesNotContain=" + DEFAULT_PROSF_LETTER);

        // Get all the addressesList where prosfLetter does not contain UPDATED_PROSF_LETTER
        defaultAddressesShouldBeFound("prosfLetter.doesNotContain=" + UPDATED_PROSF_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByNameLetterIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where nameLetter equals to DEFAULT_NAME_LETTER
        defaultAddressesShouldBeFound("nameLetter.equals=" + DEFAULT_NAME_LETTER);

        // Get all the addressesList where nameLetter equals to UPDATED_NAME_LETTER
        defaultAddressesShouldNotBeFound("nameLetter.equals=" + UPDATED_NAME_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByNameLetterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where nameLetter not equals to DEFAULT_NAME_LETTER
        defaultAddressesShouldNotBeFound("nameLetter.notEquals=" + DEFAULT_NAME_LETTER);

        // Get all the addressesList where nameLetter not equals to UPDATED_NAME_LETTER
        defaultAddressesShouldBeFound("nameLetter.notEquals=" + UPDATED_NAME_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByNameLetterIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where nameLetter in DEFAULT_NAME_LETTER or UPDATED_NAME_LETTER
        defaultAddressesShouldBeFound("nameLetter.in=" + DEFAULT_NAME_LETTER + "," + UPDATED_NAME_LETTER);

        // Get all the addressesList where nameLetter equals to UPDATED_NAME_LETTER
        defaultAddressesShouldNotBeFound("nameLetter.in=" + UPDATED_NAME_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByNameLetterIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where nameLetter is not null
        defaultAddressesShouldBeFound("nameLetter.specified=true");

        // Get all the addressesList where nameLetter is null
        defaultAddressesShouldNotBeFound("nameLetter.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByNameLetterContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where nameLetter contains DEFAULT_NAME_LETTER
        defaultAddressesShouldBeFound("nameLetter.contains=" + DEFAULT_NAME_LETTER);

        // Get all the addressesList where nameLetter contains UPDATED_NAME_LETTER
        defaultAddressesShouldNotBeFound("nameLetter.contains=" + UPDATED_NAME_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByNameLetterNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where nameLetter does not contain DEFAULT_NAME_LETTER
        defaultAddressesShouldNotBeFound("nameLetter.doesNotContain=" + DEFAULT_NAME_LETTER);

        // Get all the addressesList where nameLetter does not contain UPDATED_NAME_LETTER
        defaultAddressesShouldBeFound("nameLetter.doesNotContain=" + UPDATED_NAME_LETTER);
    }

    @Test
    @Transactional
    void getAllAddressesByLetterCloseIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where letterClose equals to DEFAULT_LETTER_CLOSE
        defaultAddressesShouldBeFound("letterClose.equals=" + DEFAULT_LETTER_CLOSE);

        // Get all the addressesList where letterClose equals to UPDATED_LETTER_CLOSE
        defaultAddressesShouldNotBeFound("letterClose.equals=" + UPDATED_LETTER_CLOSE);
    }

    @Test
    @Transactional
    void getAllAddressesByLetterCloseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where letterClose not equals to DEFAULT_LETTER_CLOSE
        defaultAddressesShouldNotBeFound("letterClose.notEquals=" + DEFAULT_LETTER_CLOSE);

        // Get all the addressesList where letterClose not equals to UPDATED_LETTER_CLOSE
        defaultAddressesShouldBeFound("letterClose.notEquals=" + UPDATED_LETTER_CLOSE);
    }

    @Test
    @Transactional
    void getAllAddressesByLetterCloseIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where letterClose in DEFAULT_LETTER_CLOSE or UPDATED_LETTER_CLOSE
        defaultAddressesShouldBeFound("letterClose.in=" + DEFAULT_LETTER_CLOSE + "," + UPDATED_LETTER_CLOSE);

        // Get all the addressesList where letterClose equals to UPDATED_LETTER_CLOSE
        defaultAddressesShouldNotBeFound("letterClose.in=" + UPDATED_LETTER_CLOSE);
    }

    @Test
    @Transactional
    void getAllAddressesByLetterCloseIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where letterClose is not null
        defaultAddressesShouldBeFound("letterClose.specified=true");

        // Get all the addressesList where letterClose is null
        defaultAddressesShouldNotBeFound("letterClose.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLetterCloseContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where letterClose contains DEFAULT_LETTER_CLOSE
        defaultAddressesShouldBeFound("letterClose.contains=" + DEFAULT_LETTER_CLOSE);

        // Get all the addressesList where letterClose contains UPDATED_LETTER_CLOSE
        defaultAddressesShouldNotBeFound("letterClose.contains=" + UPDATED_LETTER_CLOSE);
    }

    @Test
    @Transactional
    void getAllAddressesByLetterCloseNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where letterClose does not contain DEFAULT_LETTER_CLOSE
        defaultAddressesShouldNotBeFound("letterClose.doesNotContain=" + DEFAULT_LETTER_CLOSE);

        // Get all the addressesList where letterClose does not contain UPDATED_LETTER_CLOSE
        defaultAddressesShouldBeFound("letterClose.doesNotContain=" + UPDATED_LETTER_CLOSE);
    }

    @Test
    @Transactional
    void getAllAddressesByFirstLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where firstLabel equals to DEFAULT_FIRST_LABEL
        defaultAddressesShouldBeFound("firstLabel.equals=" + DEFAULT_FIRST_LABEL);

        // Get all the addressesList where firstLabel equals to UPDATED_FIRST_LABEL
        defaultAddressesShouldNotBeFound("firstLabel.equals=" + UPDATED_FIRST_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFirstLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where firstLabel not equals to DEFAULT_FIRST_LABEL
        defaultAddressesShouldNotBeFound("firstLabel.notEquals=" + DEFAULT_FIRST_LABEL);

        // Get all the addressesList where firstLabel not equals to UPDATED_FIRST_LABEL
        defaultAddressesShouldBeFound("firstLabel.notEquals=" + UPDATED_FIRST_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFirstLabelIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where firstLabel in DEFAULT_FIRST_LABEL or UPDATED_FIRST_LABEL
        defaultAddressesShouldBeFound("firstLabel.in=" + DEFAULT_FIRST_LABEL + "," + UPDATED_FIRST_LABEL);

        // Get all the addressesList where firstLabel equals to UPDATED_FIRST_LABEL
        defaultAddressesShouldNotBeFound("firstLabel.in=" + UPDATED_FIRST_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFirstLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where firstLabel is not null
        defaultAddressesShouldBeFound("firstLabel.specified=true");

        // Get all the addressesList where firstLabel is null
        defaultAddressesShouldNotBeFound("firstLabel.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFirstLabelContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where firstLabel contains DEFAULT_FIRST_LABEL
        defaultAddressesShouldBeFound("firstLabel.contains=" + DEFAULT_FIRST_LABEL);

        // Get all the addressesList where firstLabel contains UPDATED_FIRST_LABEL
        defaultAddressesShouldNotBeFound("firstLabel.contains=" + UPDATED_FIRST_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFirstLabelNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where firstLabel does not contain DEFAULT_FIRST_LABEL
        defaultAddressesShouldNotBeFound("firstLabel.doesNotContain=" + DEFAULT_FIRST_LABEL);

        // Get all the addressesList where firstLabel does not contain UPDATED_FIRST_LABEL
        defaultAddressesShouldBeFound("firstLabel.doesNotContain=" + UPDATED_FIRST_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesBySecondLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where secondLabel equals to DEFAULT_SECOND_LABEL
        defaultAddressesShouldBeFound("secondLabel.equals=" + DEFAULT_SECOND_LABEL);

        // Get all the addressesList where secondLabel equals to UPDATED_SECOND_LABEL
        defaultAddressesShouldNotBeFound("secondLabel.equals=" + UPDATED_SECOND_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesBySecondLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where secondLabel not equals to DEFAULT_SECOND_LABEL
        defaultAddressesShouldNotBeFound("secondLabel.notEquals=" + DEFAULT_SECOND_LABEL);

        // Get all the addressesList where secondLabel not equals to UPDATED_SECOND_LABEL
        defaultAddressesShouldBeFound("secondLabel.notEquals=" + UPDATED_SECOND_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesBySecondLabelIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where secondLabel in DEFAULT_SECOND_LABEL or UPDATED_SECOND_LABEL
        defaultAddressesShouldBeFound("secondLabel.in=" + DEFAULT_SECOND_LABEL + "," + UPDATED_SECOND_LABEL);

        // Get all the addressesList where secondLabel equals to UPDATED_SECOND_LABEL
        defaultAddressesShouldNotBeFound("secondLabel.in=" + UPDATED_SECOND_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesBySecondLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where secondLabel is not null
        defaultAddressesShouldBeFound("secondLabel.specified=true");

        // Get all the addressesList where secondLabel is null
        defaultAddressesShouldNotBeFound("secondLabel.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesBySecondLabelContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where secondLabel contains DEFAULT_SECOND_LABEL
        defaultAddressesShouldBeFound("secondLabel.contains=" + DEFAULT_SECOND_LABEL);

        // Get all the addressesList where secondLabel contains UPDATED_SECOND_LABEL
        defaultAddressesShouldNotBeFound("secondLabel.contains=" + UPDATED_SECOND_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesBySecondLabelNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where secondLabel does not contain DEFAULT_SECOND_LABEL
        defaultAddressesShouldNotBeFound("secondLabel.doesNotContain=" + DEFAULT_SECOND_LABEL);

        // Get all the addressesList where secondLabel does not contain UPDATED_SECOND_LABEL
        defaultAddressesShouldBeFound("secondLabel.doesNotContain=" + UPDATED_SECOND_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByThirdLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where thirdLabel equals to DEFAULT_THIRD_LABEL
        defaultAddressesShouldBeFound("thirdLabel.equals=" + DEFAULT_THIRD_LABEL);

        // Get all the addressesList where thirdLabel equals to UPDATED_THIRD_LABEL
        defaultAddressesShouldNotBeFound("thirdLabel.equals=" + UPDATED_THIRD_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByThirdLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where thirdLabel not equals to DEFAULT_THIRD_LABEL
        defaultAddressesShouldNotBeFound("thirdLabel.notEquals=" + DEFAULT_THIRD_LABEL);

        // Get all the addressesList where thirdLabel not equals to UPDATED_THIRD_LABEL
        defaultAddressesShouldBeFound("thirdLabel.notEquals=" + UPDATED_THIRD_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByThirdLabelIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where thirdLabel in DEFAULT_THIRD_LABEL or UPDATED_THIRD_LABEL
        defaultAddressesShouldBeFound("thirdLabel.in=" + DEFAULT_THIRD_LABEL + "," + UPDATED_THIRD_LABEL);

        // Get all the addressesList where thirdLabel equals to UPDATED_THIRD_LABEL
        defaultAddressesShouldNotBeFound("thirdLabel.in=" + UPDATED_THIRD_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByThirdLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where thirdLabel is not null
        defaultAddressesShouldBeFound("thirdLabel.specified=true");

        // Get all the addressesList where thirdLabel is null
        defaultAddressesShouldNotBeFound("thirdLabel.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByThirdLabelContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where thirdLabel contains DEFAULT_THIRD_LABEL
        defaultAddressesShouldBeFound("thirdLabel.contains=" + DEFAULT_THIRD_LABEL);

        // Get all the addressesList where thirdLabel contains UPDATED_THIRD_LABEL
        defaultAddressesShouldNotBeFound("thirdLabel.contains=" + UPDATED_THIRD_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByThirdLabelNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where thirdLabel does not contain DEFAULT_THIRD_LABEL
        defaultAddressesShouldNotBeFound("thirdLabel.doesNotContain=" + DEFAULT_THIRD_LABEL);

        // Get all the addressesList where thirdLabel does not contain UPDATED_THIRD_LABEL
        defaultAddressesShouldBeFound("thirdLabel.doesNotContain=" + UPDATED_THIRD_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFourthLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fourthLabel equals to DEFAULT_FOURTH_LABEL
        defaultAddressesShouldBeFound("fourthLabel.equals=" + DEFAULT_FOURTH_LABEL);

        // Get all the addressesList where fourthLabel equals to UPDATED_FOURTH_LABEL
        defaultAddressesShouldNotBeFound("fourthLabel.equals=" + UPDATED_FOURTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFourthLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fourthLabel not equals to DEFAULT_FOURTH_LABEL
        defaultAddressesShouldNotBeFound("fourthLabel.notEquals=" + DEFAULT_FOURTH_LABEL);

        // Get all the addressesList where fourthLabel not equals to UPDATED_FOURTH_LABEL
        defaultAddressesShouldBeFound("fourthLabel.notEquals=" + UPDATED_FOURTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFourthLabelIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fourthLabel in DEFAULT_FOURTH_LABEL or UPDATED_FOURTH_LABEL
        defaultAddressesShouldBeFound("fourthLabel.in=" + DEFAULT_FOURTH_LABEL + "," + UPDATED_FOURTH_LABEL);

        // Get all the addressesList where fourthLabel equals to UPDATED_FOURTH_LABEL
        defaultAddressesShouldNotBeFound("fourthLabel.in=" + UPDATED_FOURTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFourthLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fourthLabel is not null
        defaultAddressesShouldBeFound("fourthLabel.specified=true");

        // Get all the addressesList where fourthLabel is null
        defaultAddressesShouldNotBeFound("fourthLabel.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFourthLabelContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fourthLabel contains DEFAULT_FOURTH_LABEL
        defaultAddressesShouldBeFound("fourthLabel.contains=" + DEFAULT_FOURTH_LABEL);

        // Get all the addressesList where fourthLabel contains UPDATED_FOURTH_LABEL
        defaultAddressesShouldNotBeFound("fourthLabel.contains=" + UPDATED_FOURTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFourthLabelNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fourthLabel does not contain DEFAULT_FOURTH_LABEL
        defaultAddressesShouldNotBeFound("fourthLabel.doesNotContain=" + DEFAULT_FOURTH_LABEL);

        // Get all the addressesList where fourthLabel does not contain UPDATED_FOURTH_LABEL
        defaultAddressesShouldBeFound("fourthLabel.doesNotContain=" + UPDATED_FOURTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFifthLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fifthLabel equals to DEFAULT_FIFTH_LABEL
        defaultAddressesShouldBeFound("fifthLabel.equals=" + DEFAULT_FIFTH_LABEL);

        // Get all the addressesList where fifthLabel equals to UPDATED_FIFTH_LABEL
        defaultAddressesShouldNotBeFound("fifthLabel.equals=" + UPDATED_FIFTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFifthLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fifthLabel not equals to DEFAULT_FIFTH_LABEL
        defaultAddressesShouldNotBeFound("fifthLabel.notEquals=" + DEFAULT_FIFTH_LABEL);

        // Get all the addressesList where fifthLabel not equals to UPDATED_FIFTH_LABEL
        defaultAddressesShouldBeFound("fifthLabel.notEquals=" + UPDATED_FIFTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFifthLabelIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fifthLabel in DEFAULT_FIFTH_LABEL or UPDATED_FIFTH_LABEL
        defaultAddressesShouldBeFound("fifthLabel.in=" + DEFAULT_FIFTH_LABEL + "," + UPDATED_FIFTH_LABEL);

        // Get all the addressesList where fifthLabel equals to UPDATED_FIFTH_LABEL
        defaultAddressesShouldNotBeFound("fifthLabel.in=" + UPDATED_FIFTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFifthLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fifthLabel is not null
        defaultAddressesShouldBeFound("fifthLabel.specified=true");

        // Get all the addressesList where fifthLabel is null
        defaultAddressesShouldNotBeFound("fifthLabel.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFifthLabelContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fifthLabel contains DEFAULT_FIFTH_LABEL
        defaultAddressesShouldBeFound("fifthLabel.contains=" + DEFAULT_FIFTH_LABEL);

        // Get all the addressesList where fifthLabel contains UPDATED_FIFTH_LABEL
        defaultAddressesShouldNotBeFound("fifthLabel.contains=" + UPDATED_FIFTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFifthLabelNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where fifthLabel does not contain DEFAULT_FIFTH_LABEL
        defaultAddressesShouldNotBeFound("fifthLabel.doesNotContain=" + DEFAULT_FIFTH_LABEL);

        // Get all the addressesList where fifthLabel does not contain UPDATED_FIFTH_LABEL
        defaultAddressesShouldBeFound("fifthLabel.doesNotContain=" + UPDATED_FIFTH_LABEL);
    }

    @Test
    @Transactional
    void getAllAddressesByFavouriteIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where favourite equals to DEFAULT_FAVOURITE
        defaultAddressesShouldBeFound("favourite.equals=" + DEFAULT_FAVOURITE);

        // Get all the addressesList where favourite equals to UPDATED_FAVOURITE
        defaultAddressesShouldNotBeFound("favourite.equals=" + UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void getAllAddressesByFavouriteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where favourite not equals to DEFAULT_FAVOURITE
        defaultAddressesShouldNotBeFound("favourite.notEquals=" + DEFAULT_FAVOURITE);

        // Get all the addressesList where favourite not equals to UPDATED_FAVOURITE
        defaultAddressesShouldBeFound("favourite.notEquals=" + UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void getAllAddressesByFavouriteIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where favourite in DEFAULT_FAVOURITE or UPDATED_FAVOURITE
        defaultAddressesShouldBeFound("favourite.in=" + DEFAULT_FAVOURITE + "," + UPDATED_FAVOURITE);

        // Get all the addressesList where favourite equals to UPDATED_FAVOURITE
        defaultAddressesShouldNotBeFound("favourite.in=" + UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void getAllAddressesByFavouriteIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where favourite is not null
        defaultAddressesShouldBeFound("favourite.specified=true");

        // Get all the addressesList where favourite is null
        defaultAddressesShouldNotBeFound("favourite.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        Countries country;
        if (TestUtil.findAll(em, Countries.class).isEmpty()) {
            country = CountriesResourceIT.createEntity(em);
            em.persist(country);
            em.flush();
        } else {
            country = TestUtil.findAll(em, Countries.class).get(0);
        }
        em.persist(country);
        em.flush();
        addresses.setCountry(country);
        addressesRepository.saveAndFlush(addresses);
        Long countryId = country.getId();

        // Get all the addressesList where country equals to countryId
        defaultAddressesShouldBeFound("countryId.equals=" + countryId);

        // Get all the addressesList where country equals to (countryId + 1)
        defaultAddressesShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByKindIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        ContactTypes kind;
        if (TestUtil.findAll(em, ContactTypes.class).isEmpty()) {
            kind = ContactTypesResourceIT.createEntity(em);
            em.persist(kind);
            em.flush();
        } else {
            kind = TestUtil.findAll(em, ContactTypes.class).get(0);
        }
        em.persist(kind);
        em.flush();
        addresses.setKind(kind);
        addressesRepository.saveAndFlush(addresses);
        Long kindId = kind.getId();

        // Get all the addressesList where kind equals to kindId
        defaultAddressesShouldBeFound("kindId.equals=" + kindId);

        // Get all the addressesList where kind equals to (kindId + 1)
        defaultAddressesShouldNotBeFound("kindId.equals=" + (kindId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        Regions region;
        if (TestUtil.findAll(em, Regions.class).isEmpty()) {
            region = RegionsResourceIT.createEntity(em);
            em.persist(region);
            em.flush();
        } else {
            region = TestUtil.findAll(em, Regions.class).get(0);
        }
        em.persist(region);
        em.flush();
        addresses.setRegion(region);
        addressesRepository.saveAndFlush(addresses);
        Long regionId = region.getId();

        // Get all the addressesList where region equals to regionId
        defaultAddressesShouldBeFound("regionId.equals=" + regionId);

        // Get all the addressesList where region equals to (regionId + 1)
        defaultAddressesShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        Companies company;
        if (TestUtil.findAll(em, Companies.class).isEmpty()) {
            company = CompaniesResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Companies.class).get(0);
        }
        em.persist(company);
        em.flush();
        addresses.setCompany(company);
        addressesRepository.saveAndFlush(addresses);
        Long companyId = company.getId();

        // Get all the addressesList where company equals to companyId
        defaultAddressesShouldBeFound("companyId.equals=" + companyId);

        // Get all the addressesList where company equals to (companyId + 1)
        defaultAddressesShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByCitizenIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        Citizens citizen;
        if (TestUtil.findAll(em, Citizens.class).isEmpty()) {
            citizen = CitizensResourceIT.createEntity(em);
            em.persist(citizen);
            em.flush();
        } else {
            citizen = TestUtil.findAll(em, Citizens.class).get(0);
        }
        em.persist(citizen);
        em.flush();
        addresses.setCitizen(citizen);
        addressesRepository.saveAndFlush(addresses);
        Long citizenId = citizen.getId();

        // Get all the addressesList where citizen equals to citizenId
        defaultAddressesShouldBeFound("citizenId.equals=" + citizenId);

        // Get all the addressesList where citizen equals to (citizenId + 1)
        defaultAddressesShouldNotBeFound("citizenId.equals=" + (citizenId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressesShouldBeFound(String filter) throws Exception {
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].addressNo").value(hasItem(DEFAULT_ADDRESS_NO)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].prosfLetter").value(hasItem(DEFAULT_PROSF_LETTER)))
            .andExpect(jsonPath("$.[*].nameLetter").value(hasItem(DEFAULT_NAME_LETTER)))
            .andExpect(jsonPath("$.[*].letterClose").value(hasItem(DEFAULT_LETTER_CLOSE)))
            .andExpect(jsonPath("$.[*].firstLabel").value(hasItem(DEFAULT_FIRST_LABEL)))
            .andExpect(jsonPath("$.[*].secondLabel").value(hasItem(DEFAULT_SECOND_LABEL)))
            .andExpect(jsonPath("$.[*].thirdLabel").value(hasItem(DEFAULT_THIRD_LABEL)))
            .andExpect(jsonPath("$.[*].fourthLabel").value(hasItem(DEFAULT_FOURTH_LABEL)))
            .andExpect(jsonPath("$.[*].fifthLabel").value(hasItem(DEFAULT_FIFTH_LABEL)))
            .andExpect(jsonPath("$.[*].favourite").value(hasItem(DEFAULT_FAVOURITE.booleanValue())));

        // Check, that the count call also returns 1
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressesShouldNotBeFound(String filter) throws Exception {
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAddresses() throws Exception {
        // Get the addresses
        restAddressesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses
        Addresses updatedAddresses = addressesRepository.findById(addresses.getId()).get();
        // Disconnect from session so that the updates on updatedAddresses are not directly saved in db
        em.detach(updatedAddresses);
        updatedAddresses
            .address(UPDATED_ADDRESS)
            .addressNo(UPDATED_ADDRESS_NO)
            .zipCode(UPDATED_ZIP_CODE)
            .prosfLetter(UPDATED_PROSF_LETTER)
            .nameLetter(UPDATED_NAME_LETTER)
            .letterClose(UPDATED_LETTER_CLOSE)
            .firstLabel(UPDATED_FIRST_LABEL)
            .secondLabel(UPDATED_SECOND_LABEL)
            .thirdLabel(UPDATED_THIRD_LABEL)
            .fourthLabel(UPDATED_FOURTH_LABEL)
            .fifthLabel(UPDATED_FIFTH_LABEL)
            .favourite(UPDATED_FAVOURITE);

        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAddresses.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAddresses))
            )
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAddresses.getAddressNo()).isEqualTo(UPDATED_ADDRESS_NO);
        assertThat(testAddresses.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testAddresses.getProsfLetter()).isEqualTo(UPDATED_PROSF_LETTER);
        assertThat(testAddresses.getNameLetter()).isEqualTo(UPDATED_NAME_LETTER);
        assertThat(testAddresses.getLetterClose()).isEqualTo(UPDATED_LETTER_CLOSE);
        assertThat(testAddresses.getFirstLabel()).isEqualTo(UPDATED_FIRST_LABEL);
        assertThat(testAddresses.getSecondLabel()).isEqualTo(UPDATED_SECOND_LABEL);
        assertThat(testAddresses.getThirdLabel()).isEqualTo(UPDATED_THIRD_LABEL);
        assertThat(testAddresses.getFourthLabel()).isEqualTo(UPDATED_FOURTH_LABEL);
        assertThat(testAddresses.getFifthLabel()).isEqualTo(UPDATED_FIFTH_LABEL);
        assertThat(testAddresses.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void putNonExistingAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addresses.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addresses)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressesWithPatch() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses using partial update
        Addresses partialUpdatedAddresses = new Addresses();
        partialUpdatedAddresses.setId(addresses.getId());

        partialUpdatedAddresses
            .address(UPDATED_ADDRESS)
            .zipCode(UPDATED_ZIP_CODE)
            .letterClose(UPDATED_LETTER_CLOSE)
            .firstLabel(UPDATED_FIRST_LABEL)
            .fourthLabel(UPDATED_FOURTH_LABEL)
            .fifthLabel(UPDATED_FIFTH_LABEL);

        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddresses))
            )
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAddresses.getAddressNo()).isEqualTo(DEFAULT_ADDRESS_NO);
        assertThat(testAddresses.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testAddresses.getProsfLetter()).isEqualTo(DEFAULT_PROSF_LETTER);
        assertThat(testAddresses.getNameLetter()).isEqualTo(DEFAULT_NAME_LETTER);
        assertThat(testAddresses.getLetterClose()).isEqualTo(UPDATED_LETTER_CLOSE);
        assertThat(testAddresses.getFirstLabel()).isEqualTo(UPDATED_FIRST_LABEL);
        assertThat(testAddresses.getSecondLabel()).isEqualTo(DEFAULT_SECOND_LABEL);
        assertThat(testAddresses.getThirdLabel()).isEqualTo(DEFAULT_THIRD_LABEL);
        assertThat(testAddresses.getFourthLabel()).isEqualTo(UPDATED_FOURTH_LABEL);
        assertThat(testAddresses.getFifthLabel()).isEqualTo(UPDATED_FIFTH_LABEL);
        assertThat(testAddresses.getFavourite()).isEqualTo(DEFAULT_FAVOURITE);
    }

    @Test
    @Transactional
    void fullUpdateAddressesWithPatch() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses using partial update
        Addresses partialUpdatedAddresses = new Addresses();
        partialUpdatedAddresses.setId(addresses.getId());

        partialUpdatedAddresses
            .address(UPDATED_ADDRESS)
            .addressNo(UPDATED_ADDRESS_NO)
            .zipCode(UPDATED_ZIP_CODE)
            .prosfLetter(UPDATED_PROSF_LETTER)
            .nameLetter(UPDATED_NAME_LETTER)
            .letterClose(UPDATED_LETTER_CLOSE)
            .firstLabel(UPDATED_FIRST_LABEL)
            .secondLabel(UPDATED_SECOND_LABEL)
            .thirdLabel(UPDATED_THIRD_LABEL)
            .fourthLabel(UPDATED_FOURTH_LABEL)
            .fifthLabel(UPDATED_FIFTH_LABEL)
            .favourite(UPDATED_FAVOURITE);

        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddresses))
            )
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAddresses.getAddressNo()).isEqualTo(UPDATED_ADDRESS_NO);
        assertThat(testAddresses.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testAddresses.getProsfLetter()).isEqualTo(UPDATED_PROSF_LETTER);
        assertThat(testAddresses.getNameLetter()).isEqualTo(UPDATED_NAME_LETTER);
        assertThat(testAddresses.getLetterClose()).isEqualTo(UPDATED_LETTER_CLOSE);
        assertThat(testAddresses.getFirstLabel()).isEqualTo(UPDATED_FIRST_LABEL);
        assertThat(testAddresses.getSecondLabel()).isEqualTo(UPDATED_SECOND_LABEL);
        assertThat(testAddresses.getThirdLabel()).isEqualTo(UPDATED_THIRD_LABEL);
        assertThat(testAddresses.getFourthLabel()).isEqualTo(UPDATED_FOURTH_LABEL);
        assertThat(testAddresses.getFifthLabel()).isEqualTo(UPDATED_FIFTH_LABEL);
        assertThat(testAddresses.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void patchNonExistingAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeDelete = addressesRepository.findAll().size();

        // Delete the addresses
        restAddressesMockMvc
            .perform(delete(ENTITY_API_URL_ID, addresses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
