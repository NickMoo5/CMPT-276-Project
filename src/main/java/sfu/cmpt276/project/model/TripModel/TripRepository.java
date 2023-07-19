package sfu.cmpt276.project.model.TripModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByUid(int uid);
    List<Trip> findByUserUid(int userUid);
}
