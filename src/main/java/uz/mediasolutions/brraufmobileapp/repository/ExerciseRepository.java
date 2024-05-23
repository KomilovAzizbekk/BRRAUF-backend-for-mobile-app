package uz.mediasolutions.brraufmobileapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.brraufmobileapp.entity.Exercise;
import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Page<Exercise> findByTrainingCenter(TrainingCenter trainingCenter, Pageable pageable);

    @Query(value = "SELECT e.*\n" +
            "FROM exercises e\n" +
            "WHERE (:exercise_type IS NULL OR e.exercise_type_id = :exercise_type)\n" +
            "  AND (:training_center IS NULL OR e.training_center_id = :training_center)", nativeQuery = true)
    Page<Exercise> findByFilter(
            @Param("exercise_type") Long exerciseTypeId,
            @Param("training_center") Long trainingCenterId,
            Pageable pageable);

    int countAllByStudentId(Long studentId);

    Page<Exercise> findAllByStudentId(Long studentId, Pageable pageable);
}
