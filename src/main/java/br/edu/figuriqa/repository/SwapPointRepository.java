package br.edu.figuriqa.repository;

import br.edu.figuriqa.model.SwapPoint;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SwapPointRepository extends JpaRepository<SwapPoint, Long> {
    List<SwapPoint> findByCityContainingIgnoreCase(String city);
}
