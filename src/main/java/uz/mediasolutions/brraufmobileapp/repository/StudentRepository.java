package uz.mediasolutions.brraufmobileapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.brraufmobileapp.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
