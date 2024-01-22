package io.github.nascimentofilipe.calendarapi.model.entity.model.repository;

import io.github.nascimentofilipe.calendarapi.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
