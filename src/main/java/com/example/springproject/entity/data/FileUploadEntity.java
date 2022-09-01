package com.example.springproject.entity.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String uuid;

    private String contentType;

    private String name;

    private String originalFilename;

//    private String writer;
    private Long board_seq;
}
