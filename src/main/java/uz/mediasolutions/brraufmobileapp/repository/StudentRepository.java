package uz.mediasolutions.brraufmobileapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.brraufmobileapp.entity.Student;
import uz.mediasolutions.brraufmobileapp.entity.User;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT s.*\n" +
            "FROM students s\n" +
            "WHERE s.fullname ILIKE '%' || :search || '%'\n" +
            "  AND (:training_center IS NULL OR s.training_center_id = :training_center)", nativeQuery = true)
    Page<Student> findBySearchAndFilter(@Param("search") String search,
                                        @Param("training_center") Long trainingCenterId,
                                        Pageable pageable);

    Page<Student> findAllByOrderByFullNameAsc(Pageable pageable);

    Page<Student> findAllByTrainingCenterIdOrderByFullNameAsc(Long trainingCenterId, Pageable pageable);

    Student findByUser(User user);
}
