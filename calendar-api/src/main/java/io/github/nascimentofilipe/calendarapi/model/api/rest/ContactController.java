package io.github.nascimentofilipe.calendarapi.model.api.rest;

import io.github.nascimentofilipe.calendarapi.model.entity.Contact;
import io.github.nascimentofilipe.calendarapi.model.entity.model.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contact save(@RequestBody Contact contact) {
        return repository.save(contact);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @GetMapping
    public List<Contact> list() {
        return repository.findAll();
    }

    @PatchMapping("{id}/favorite")
    public void toFavorite(@PathVariable Integer id, @RequestBody Boolean favorite) {
        Optional<Contact> contact = repository.findById(id);
        contact.ifPresent(c -> {
            c.setFavorite(favorite);
            repository.save(c);
        });
    }
}
