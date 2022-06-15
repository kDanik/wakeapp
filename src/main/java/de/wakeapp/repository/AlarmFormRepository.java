package de.wakeapp.repository;

import de.wakeapp.model.AlarmForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmFormRepository extends JpaRepository<AlarmForm, Integer> {
}
