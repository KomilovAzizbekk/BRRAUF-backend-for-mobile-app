package uz.mediasolutions.brraufmobileapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.brraufmobileapp.entity.Student;
import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;
import uz.mediasolutions.brraufmobileapp.entity.User;

public interface TrainingCenterRepository extends JpaRepository<TrainingCenter, Long> {

    TrainingCenter findByUser(User user);

    TrainingCenter findByStudentsContains(Student student);

    Page<TrainingCenter> findAllByNameContainsIgnoreCaseOrderByName(String name, Pageable pageable);

    Page<TrainingCenter> findAllByOrderByNameAsc(Pageable pageable);

}
