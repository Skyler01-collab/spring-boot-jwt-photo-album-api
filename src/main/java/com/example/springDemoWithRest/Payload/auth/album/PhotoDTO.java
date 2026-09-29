package com.example.springDemoWithRest.Payload.auth.album;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor 
@Setter 
@Getter 
public class PhotoDTO {
    
    private long id;

    private String name;

    private String description;

  //  private String originalFileName;

    private String fileName;

    private String download_link;
}
