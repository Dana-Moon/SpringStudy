package com.example.springproject.entity.CustomDto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class CustomDtoExample {

    @Id
    private String input_id;
    private String input_writer;
    private String input_title;
}
