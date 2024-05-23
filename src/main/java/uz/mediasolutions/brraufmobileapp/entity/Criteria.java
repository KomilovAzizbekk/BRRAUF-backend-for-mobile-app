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
@Table(name = "criterias")
public class Criteria extends AbsLong {

    @Column(name = "name")
    private String name;

    @Column(name = "score")
    private long score;

}
