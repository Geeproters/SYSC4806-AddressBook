package com.example.addressbook;

import org.springframework.data.repository.CrudRepository;

public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {
    // This interface inherits all CRUD (Create, Read, Update, Delete) methods
    // save(), findById(), findAll(), count(), delete(), deleteById(), etc
    // No need to write any implementation
}