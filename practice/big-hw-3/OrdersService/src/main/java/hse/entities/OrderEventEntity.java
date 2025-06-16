package hse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "orders_tasks")
public class OrderEventEntity {

    @Id
    private Integer id;

    private Integer userId;

    private Double amount;

    @Column(name = "is_finished")
    private Boolean isFinished;

}
