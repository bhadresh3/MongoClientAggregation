package com.system27.mongo.Model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Person {
    private String id;
    private String name;
    private int salary;
    private int age;
    private List<String> likes = new ArrayList<>();
}
