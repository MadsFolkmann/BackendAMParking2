package org.parking.backendamparking;


import org.parking.backendamparking.Entity.*;
import org.parking.backendamparking.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;

@Component
public class InitData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final CarsRepository carsRepository;
    private final CasesRepository casesRepository;
    private final PAreaRepository pAreaRepository;
    private final RentalUnitRepository rentalUnitRepository;
    private final PasswordEncoder passwordEncoder;


    public InitData(UserRepository userRepository, ParkingRepository parkingRepository, CarsRepository carsRepository, CasesRepository casesRepository, PAreaRepository pAreaRepository, RentalUnitRepository rentalUnitRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
        this.carsRepository = carsRepository;
        this.casesRepository = casesRepository;
        this.pAreaRepository = pAreaRepository;
        this.rentalUnitRepository = rentalUnitRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Henter data fra databasen");


        RentalUnit unit1 = new RentalUnit(1000000001L);
        RentalUnit unit2 = new RentalUnit(1000000002L);
        RentalUnit unit3 = new RentalUnit(1000000003L);
        RentalUnit unit4 = new RentalUnit(1000000004L);
        RentalUnit unit5 = new RentalUnit(1000000005L);
        RentalUnit unit6 = new RentalUnit(1000000006L);
        RentalUnit unit7 = new RentalUnit(1000000007L);
        RentalUnit unit8 = new RentalUnit(1000000008L);
        RentalUnit unit9 = new RentalUnit(1000000009L);
        RentalUnit unit10 = new RentalUnit(1000000010L);
        RentalUnit unit11 = new RentalUnit(1000000011L);
        RentalUnit unit12 = new RentalUnit(1000000012L);
        RentalUnit unit13 = new RentalUnit(1000000013L);
        RentalUnit unit14 = new RentalUnit(1000000014L);
        RentalUnit unit15 = new RentalUnit(1000000015L);
        RentalUnit unit16 = new RentalUnit(1000000016L);
        RentalUnit unit17 = new RentalUnit(1000000017L);
        RentalUnit unit18 = new RentalUnit(1000000018L);
        RentalUnit unit19 = new RentalUnit(1000000019L);
        RentalUnit unit20 = new RentalUnit(1000000020L);


        /* Rental Units Admin */
        RentalUnit adminUnit = new RentalUnit(1L);


        rentalUnitRepository.save(unit1);
        rentalUnitRepository.save(unit2);
        rentalUnitRepository.save(unit3);
        rentalUnitRepository.save(unit4);
        rentalUnitRepository.save(unit5);
        rentalUnitRepository.save(unit6);
        rentalUnitRepository.save(unit7);
        rentalUnitRepository.save(unit8);
        rentalUnitRepository.save(unit9);
        rentalUnitRepository.save(unit10);
        rentalUnitRepository.save(unit11);
        rentalUnitRepository.save(unit12);
        rentalUnitRepository.save(unit13);
        rentalUnitRepository.save(unit14);
        rentalUnitRepository.save(unit15);
        rentalUnitRepository.save(unit16);
        rentalUnitRepository.save(unit17);
        rentalUnitRepository.save(unit18);
        rentalUnitRepository.save(unit19);
        rentalUnitRepository.save(unit20);


        /* Rental Units admin*/
        rentalUnitRepository.save(adminUnit);


        /* Users */
        User user1 = new User();
        user1.setRentalUnit(1L);
        user1.setEmail("abdi@gmail.com");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setPhoneNumber(22232323);
        user1.setFirstName("Abdi");
        user1.setLastName("Ox");
        user1.setAdress("Vej 1");
        user1.setCity("Rødovre");
        user1.setZipCode(2610);
        user1.setRole(Roles.USER);

        User user2 = new User();
        user2.setRentalUnit(2112323123L);
        user2.setEmail("mads@gmail.com");
        user2.setPassword(passwordEncoder.encode("password"));
        user2.setPhoneNumber(2222222);
        user2.setFirstName("Mads");
        user2.setLastName("Diaz");
        user2.setAdress("Vej 2");
        user2.setCity("Hvidovre");
        user2.setZipCode(2650);
        user2.setRole(Roles.USER);


        User user3 = new User();
        user3.setRentalUnit(3112323123L);
        user3.setEmail("hej@123.dk");
        user3.setPassword(passwordEncoder.encode("hej"));
        user3.setPhoneNumber(2222222);
        user3.setFirstName("Hej");
        user3.setLastName("Dig");
        user3.setAdress("Vej 3");
        user3.setCity("Hvidovre");
        user3.setZipCode(2650);
        user3.setRole(Roles.USER);


        /* Users */
        User user4 = new User();
        user4.setRentalUnit(4112323123L);
        user4.setEmail("dummy@gmail.com");
        user4.setPassword(passwordEncoder.encode("dummy"));
        user4.setPhoneNumber(2222222);
        user4.setFirstName("Dummy");
        user4.setLastName("User");
        user4.setAdress("Vej 4");
        user4.setCity("Hvidovre");
        user4.setZipCode(2650);
        user4.setRole(Roles.USER);



        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        User savedUser3 = userRepository.save(user3);
        User savedUser4 = userRepository.save(user4);


        /* Admin */
        User admin = new User();
        admin.setRentalUnit(1212323123L);
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setPhoneNumber(22232323);
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setAdress("Admin Vej 1");
        admin.setCity("Admin City");
        admin.setZipCode(2610);
        admin.setRole(Roles.ADMIN);

        User savedAdmin = userRepository.save(admin);




        /* P-Area */
        PArea pArea1 = new PArea();
        pArea1.setAreaName("A");
        pArea1.setCity("Rødovre");
        pArea1.setPostalCode(2610);
        pArea1.setDaysAllowedParking(3);
        PArea pArea1Saved = pAreaRepository.save(pArea1);

        PArea pArea2 = new PArea();
        pArea2.setAreaName("B");
        pArea2.setCity("Hvidovre");
        pArea2.setPostalCode(2650);
        pArea2.setDaysAllowedParking(5);
        PArea pArea2Saved = pAreaRepository.save(pArea2);


        PArea pArea3 = new PArea();
        pArea3.setAreaName("C");
        pArea3.setCity("Hvidovre");
        pArea3.setPostalCode(2650);
        pArea3.setDaysAllowedParking(7);
        PArea pArea3Saved = pAreaRepository.save(pArea3);

        /* Parkings */
        Parking parking1 = new Parking();
        parking1.setParea(pArea1Saved);
        parking1.setPlateNumber("123456L");
        parking1.setCarBrand("Toyota");
        parking1.setCarColor("Blue");
        parking1.setCarModel("102");
        parking1.setUser(savedUser1);
        parking1.setStartTime(LocalDateTime.parse("2023-10-01 10:00:00", formatter));
        parking1.setEndTime(LocalDateTime.parse("2023-10-01 12:00:00", formatter));

        Parking parking1Saved = parkingRepository.save(parking1);

        Parking parking2 = new Parking();
        parking2.setParea(pArea2Saved);
        parking2.setPlateNumber("654321L");
        parking2.setCarBrand("Mercedes");
        parking2.setCarColor("Blue");
        parking2.setCarModel("c3");
        parking2.setUser(savedUser2);
        parking2.setStartTime(LocalDateTime.parse("2023-10-01 11:00:00", formatter));
        parking2.setEndTime(LocalDateTime.parse("2023-10-01 13:00:00", formatter));
        Parking parking2Saved = parkingRepository.save(parking2);

        Parking parking3 = new Parking();
        parking3.setParea(pArea3Saved);
        parking3.setPlateNumber("789012L");
        parking3.setCarBrand("BMW");
        parking3.setCarColor("White");
        parking3.setCarModel("X3");
        parking3.setUser(savedUser3);
        parking3.setStartTime(LocalDateTime.parse("2023-10-01 12:00:00", formatter));
        parking3.setEndTime(LocalDateTime.parse("2023-10-01 14:00:00", formatter));
        Parking parking3Saved = parkingRepository.save(parking3);



        /* Cars */
        Cars cars1 = new Cars();
        cars1.setNumberPlate("123456L");
        cars1.setBrand("Toyota");
        cars1.setModel("Corolla");
        cars1.setColor("Red");
        cars1.setType("Sedan");
        cars1.setDescription("Toyota Corolla 2020 in red color");
        cars1.setUser(user1);
        Cars cars1Saved = carsRepository.save(cars1);

        Cars cars2 = new Cars();
        cars2.setNumberPlate("654321L");
        cars2.setBrand("Honda");
        cars2.setModel("Civic");
        cars2.setColor("Blue");
        cars2.setType("Sedan");
        cars2.setDescription("Honda Civic 2020 in blue color");
        cars2.setUser(user2);
        Cars cars2Saved = carsRepository.save(cars2);



        /* Cases */
         Cases cases1 = new Cases();
        cases1.setTime(LocalDate.from(LocalDateTime.parse("2023-10-01 10:00:00", formatter)));
        cases1.setDescription("Overholdt ikke tidsfristen for parkering");
        Cases cases1Saved = casesRepository.save(cases1);

        Cases cases2 = new Cases();
        cases2.setTime(LocalDate.from(LocalDateTime.parse("2023-10-01 11:00:00", formatter)));
        cases2.setDescription("Overholdt ikke tidsfristen for parkering");
        Cases cases2Saved = casesRepository.save(cases2);






    }




}