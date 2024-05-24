package uz.mediasolutions.brraufmobileapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.brraufmobileapp.entity.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query(value = "SELECT e.*\n" +
            "FROM exercises e\n" +
            "WHERE (:exercise_type IS NULL OR e.exercise_type_id = :exercise_type)", nativeQuery = true)
    Page<Exercise> findByFilter(
            @Param("exercise_type") Long exerciseTypeId,
            Pageable pageable);

    @Query(value = "SELECT e.*\n" +
            "FROM exercises e\n" +
            "         JOIN public.exercises_students es on e.id = es.exercise_id\n" +
            "         JOIN public.students s on s.id = es.students_id\n" +
            "WHERE s.id = :student_id", nativeQuery = true)
    Page<Exercise> findAllByStudentId(
            @Param("student_id") long studentId,
            Pageable pageable);
}
