package org.parking.backendamparking;


import org.parking.backendamparking.Entity.Cars;
import org.parking.backendamparking.Entity.Cases;
import org.parking.backendamparking.Repository.CarsRepository;
import org.parking.backendamparking.Repository.CasesRepository;
import org.parking.backendamparking.Repository.ParkingRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.parking.backendamparking.Entity.Parking;
import org.parking.backendamparking.Entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;

@Component
public class InitData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final CarsRepository carsRepository;
    private final CasesRepository casesRepository;
    private final PasswordEncoder passwordEncoder;


    public InitData(UserRepository userRepository, ParkingRepository parkingRepository, CarsRepository carsRepository, CasesRepository casesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
        this.carsRepository = carsRepository;
        this.casesRepository = casesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Henter data fra databasen");


      /* Users */
        User user1 = new User();
        user1.setRentalUnit(1212323123L);
        user1.setEmail("abdi@gmail.com");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setPhoneNumber(22232323);
        user1.setFirstName("Abdi");
        user1.setLastName("Ox");
        user1.setAdress("Vej 1");
        user1.setCity("Rødovre");
        user1.setZipCode(2610);

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



        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        User savedUser3 = userRepository.save(user3);

        /* Parkings */
        Parking parking1 = new Parking();
        parking1.setPArea("A");
        parking1.setPlateNumber("123456L");
        parking1.setUser(savedUser1);
        parking1.setStartTime(LocalDateTime.parse("2023-10-01 10:00:00", formatter));
        parking1.setEndTime(LocalDateTime.parse("2023-10-01 12:00:00", formatter));

        Parking parking1Saved = parkingRepository.save(parking1);

        Parking parking2 = new Parking();
        parking2.setPArea("B");
        parking2.setPlateNumber("654321L");
        parking2.setUser(savedUser2);
        parking2.setStartTime(LocalDateTime.parse("2023-10-01 11:00:00", formatter));
        parking2.setEndTime(LocalDateTime.parse("2023-10-01 13:00:00", formatter));
        Parking parking2Saved = parkingRepository.save(parking2);

        Parking parking3 = new Parking();
        parking3.setPArea("C");
        parking3.setPlateNumber("789012L");
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
        cars1.setUser(user1);
        Cars cars1Saved = carsRepository.save(cars1);

        Cars cars2 = new Cars();
        cars2.setNumberPlate("654321L");
        cars2.setBrand("Honda");
        cars2.setModel("Civic");
        cars2.setColor("Blue");
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