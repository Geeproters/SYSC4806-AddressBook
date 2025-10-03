package com.example.addressbook;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long> {
    // Custom query method: implemented automatically by Spring Data JPA
    List<BuddyInfo> findByName(String name);
}
