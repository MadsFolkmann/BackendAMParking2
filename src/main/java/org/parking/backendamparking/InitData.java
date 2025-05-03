package org.parking.backendamparking;


import org.parking.backendamparking.Repository.ParkingRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.parking.backendamparking.Entity.Parking;
import org.parking.backendamparking.Entity.User;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;

@Component
public class InitData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;

    public InitData(UserRepository userRepository, ParkingRepository parkingRepository) {
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Henter data fra databasen");


      /* Users */
        User user1 = new User();
        user1.setLejemaal("323212311");
        user1.setEmail("abdi@gmail.com");
        user1.setPassword("123456");
        user1.setNumber("22232323");
        user1.setName("Abdi");

        User user2 = new User();
        user2.setLejemaal("1212323123");
        user2.setEmail("mads@gmail.com");
        user2.setPassword("123123123");
        user2.setNumber("2222222");
        user2.setName("Mads");

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        /* Parkings */
        Parking parking1 = new Parking();
        parking1.setPArea("A");
        parking1.setPlateNumber(123456L);
        parking1.setUser(savedUser1);
        parking1.setStartTime(LocalDateTime.parse("2023-10-01 10:00:00", formatter));
        parking1.setEndTime(LocalDateTime.parse("2023-10-01 12:00:00", formatter));

        Parking parking1Saved = parkingRepository.save(parking1);

        Parking parking2 = new Parking();
        parking2.setPArea("B");
        parking2.setPlateNumber(654321L);
        parking2.setUser(savedUser2);
        parking2.setStartTime(LocalDateTime.parse("2023-10-01 11:00:00", formatter));
        parking2.setEndTime(LocalDateTime.parse("2023-10-01 13:00:00", formatter));
        Parking parking2Saved = parkingRepository.save(parking2);


    }
}