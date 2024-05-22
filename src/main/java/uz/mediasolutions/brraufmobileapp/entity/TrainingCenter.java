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
@Table(name = "training_center")
public class TrainingCenter extends AbsLong {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "info", columnDefinition = "text")
    private String info;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "trainingCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    @OneToMany(mappedBy = "trainingCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises;

}
