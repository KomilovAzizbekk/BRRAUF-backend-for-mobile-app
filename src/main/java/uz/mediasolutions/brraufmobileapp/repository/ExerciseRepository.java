package uz.mediasolutions.brraufmobileapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.brraufmobileapp.entity.Exercise;
import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Page<Exercise> findByTrainingCenter(TrainingCenter trainingCenter, Pageable pageable);

}
