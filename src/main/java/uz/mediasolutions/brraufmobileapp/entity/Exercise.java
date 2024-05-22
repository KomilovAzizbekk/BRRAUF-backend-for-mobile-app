package uz.mediasolutions.brraufmobileapp.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.brraufmobileapp.entity.template.AbsLong;

import javax.persistence.*;

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
@Table(name = "exercises")
public class Exercise extends AbsLong {

    @ManyToOne(fetch = FetchType.LAZY)
    private ExerciseType exerciseType;

    @Column(name = "spent_time")
    private long spentTime;

    @Column(name = "sequence")
    private int sequence;

    @Column(name = "error")
    private int error;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingCenter trainingCenter;

}
