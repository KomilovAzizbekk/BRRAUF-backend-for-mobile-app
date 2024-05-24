package uz.mediasolutions.brraufmobileapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.brraufmobileapp.entity.Scores;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Scores, Long> {

    @Query(value = "SELECT s.*\n" +
            "FROM scores s\n" +
            "         JOIN public.exercise_results_scores ers on s.id = ers.scores_id\n" +
            "         JOIN public.exercise_results er on er.id = ers.exercise_result_id\n" +
            "WHERE er.student_id = :student_id\n" +
            "  AND er.exercise_id = :exercise_id", nativeQuery = true)
    List<Scores> findAllByStudentIdAndExerciseId(
            @Param("student_id") Long studentId,
            @Param("exercise_id") Long exerciseId);
}
