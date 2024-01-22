package io.github.nascimentofilipe.calendarapi;

import io.github.nascimentofilipe.calendarapi.model.entity.Contact;
import io.github.nascimentofilipe.calendarapi.model.entity.model.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CalendarApiApplication {

//    @Bean
//    public CommandLineRunner commandLineRunner(
//            @Autowired ContactRepository contactRepository) {
//        return args -> {
//            Contact contact = new Contact();
//            contact.setName("Fulano");
//            contact.setEmail("fulano@email.com");
//            contact.setFavorite(false);
//            contactRepository.save(contact);
//        };
//    }

    public static void main(String[] args) {
        SpringApplication.run(CalendarApiApplication.class, args);
    }

}
