package uz.mediasolutions.brraufmobileapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.brraufmobileapp.entity.ExerciseResult;

public interface ExerciseResultRepository extends JpaRepository<ExerciseResult, Long> {

    @Query(value = "select er.*\n" +
            "from exercise_results er\n" +
            "         join public.students s on s.id = er.student_id\n" +
            "         join public.training_center tc on tc.id = s.training_center_id\n" +
            "where tc.id = :trainingCenterId", nativeQuery = true)
    Page<ExerciseResult> findAllByTrainingCenterId(
            @Param("trainingCenterId") Long trainingCenterId,
            Pageable pageable);

    Page<ExerciseResult> findAllByStudentId(Long studentId, Pageable pageable);

}
