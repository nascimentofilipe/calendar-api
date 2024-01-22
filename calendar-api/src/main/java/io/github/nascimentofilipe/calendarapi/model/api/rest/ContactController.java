package io.github.nascimentofilipe.calendarapi.model.api.rest;

import io.github.nascimentofilipe.calendarapi.model.entity.Contact;
import io.github.nascimentofilipe.calendarapi.model.entity.model.repository.ContactRepository;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@CrossOrigin("*")
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
    public Page<Contact> list(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize

    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        return repository.findAll(pageRequest);
    }

    @PatchMapping("{id}/favorite")
    public void toFavorite(@PathVariable Integer id) {
        Optional<Contact> contact = repository.findById(id);
        contact.ifPresent(c -> {
            boolean favorite = c.getFavorite() == Boolean.TRUE;
            c.setFavorite(!favorite);
            repository.save(c);
        });
    }

    @PutMapping("{id}/picture")
    public byte[] addPicture(@PathVariable Integer id,
                             @RequestParam("picture") Part file) {

        Optional<Contact> contact = repository.findById(id);
        return contact.map(c -> {
            try {
                InputStream inputStream = file.getInputStream();
                byte[] bytes = new byte[(int) file.getSize()];
                IOUtils.readFully(inputStream, bytes);
                c.setPicture(bytes);

                repository.save(c);
                inputStream.close();

                return bytes;
            } catch (IOException e) {
                return null;
            }
        }).orElse(null);
    }
}
