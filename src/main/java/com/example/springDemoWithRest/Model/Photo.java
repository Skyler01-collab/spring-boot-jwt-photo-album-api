package com.example.springDemoWithRest.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Setter 
@Getter 
@AllArgsConstructor 
@NoArgsConstructor 
public class Photo {
    @Id 
    @GeneratedValue (strategy= GenerationType.SEQUENCE)
    private long id;

    private String name;

    private String description;

    private String originalFileName;

    private String fileName;

    @ManyToOne 
    @JoinColumn(name="album_id", referencedColumnName = "id",nullable = false)
    private Album album;
}
