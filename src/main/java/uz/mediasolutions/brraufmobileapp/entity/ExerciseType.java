package uz.mediasolutions.brraufmobileapp.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.brraufmobileapp.entity.template.AbsLong;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "exercise_type")
public class ExerciseType extends AbsLong {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "exerciseType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises;

}
