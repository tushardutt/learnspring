package com.practice.learnspring.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="users")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "uname")
    private String uname;
    @Column(name = "department")
    private String department;
    @Column(name = "email_id")
    private String email;
}
