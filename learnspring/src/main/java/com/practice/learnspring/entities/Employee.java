package com.practice.learnspring.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="employee")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
   @Id
   @GeneratedValue
   @Column(name = "id")
   private Long id;
   @Column(name = "name")
   private String name;
   @Column(name = "dept")
   private String dept;
   @Column(name = "address")
   private String address;
   @Column(name = "email")
   private String email;
   @Column(name = "mobile")
   private Long mobile;
}
