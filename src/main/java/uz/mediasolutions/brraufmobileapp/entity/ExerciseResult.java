package uz.mediasolutions.brraufmobileapp.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.w3c.dom.Text;
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
@Table(name = "exercise_results")
public class ExerciseResult extends AbsLong {

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exercise exercise;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Scores> scores;

}
