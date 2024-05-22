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
@Table(name = "students")
public class Student extends AbsLong {

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "spent_time")
    private long spentTime;

    @Column(name = "sequence")
    private int sequence;

    @Column(name = "error")
    private int error;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingCenter trainingCenter;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises;



}
