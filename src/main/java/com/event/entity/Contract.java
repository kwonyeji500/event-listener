package com.event.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "tb_contract_bas")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Contract create(User user) {
        return Contract.builder().user(user).build();
    }

}
