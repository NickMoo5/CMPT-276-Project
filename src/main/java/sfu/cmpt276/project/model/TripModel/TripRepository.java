package sfu.cmpt276.project.model.TripModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByUid(int uid);
    List<Trip> findByUserUid(int userUid);
}
