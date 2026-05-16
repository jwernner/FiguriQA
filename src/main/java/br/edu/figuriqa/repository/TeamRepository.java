package br.edu.figuriqa.repository;

import br.edu.figuriqa.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
