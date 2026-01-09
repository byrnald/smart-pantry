package com.byrnald.smart_pantry.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byrnald.smart_pantry.model.PantryItem;


@Repository // this annotation tells spring that this is a repository, basically just interacts with the database.
public interface PantryRepository extends JpaRepository<PantryItem, Long> {
    List<PantryItem> findByExpirationDateBetween(LocalDate start, LocalDate end);
    List<PantryItem> findByQuantityLessThan(int threshold);
    //we need to teach our database on how to find items based on their quantity
    
}
// this interface extends JpaRepository so it provides basic CRUD operations for the PantryItem entity.
// CRUD is just Creation Read Update Delete.

