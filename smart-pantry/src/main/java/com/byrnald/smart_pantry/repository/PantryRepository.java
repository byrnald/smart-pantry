package com.byrnald.smart_pantry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byrnald.smart_pantry.model.PantryItem;

@Repository // this annotation tells spring that this is a repository, basically just interacts with the database.
public interface PantryRepository extends JpaRepository<PantryItem, Long> {
    
}
// this interface extends JpaRepository so it provides basic CRUD operations for the PantryItem entity.
// CRUD is just Creation Read Update Delete.