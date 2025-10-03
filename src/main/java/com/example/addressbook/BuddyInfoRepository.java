package com.example.addressbook;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

// CrudRepository is a generic interface that will be AUTO IMPLEMENTED by Spring at runtime into a Bean called buddyInfoRepository
// CRUD refers Create, Read, Update, Delete. CrudRepository gives us various default methods.
public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long> {
    // Can define custom query methods just by declaring their signature
    // Spring Data JPA will implement them automatically
    List<BuddyInfo> findByName(String name);
}