package com.example.addressbook;

import org.springframework.data.repository.CrudRepository;

public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {
    // Inherits standard CRUD methods: save, findById, findAll, delete, etc.
}
