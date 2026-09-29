package com.example.springDemoWithRest.Payload.auth.album;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor 
@NoArgsConstructor
public class AlbumViewDTO {
    
    private long id;
    
    @NotBlank 
    @Schema (description = "The name of the album", example = "My Album",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @NotBlank
    @Schema (description = "The description of the album", example = "This is my favorite album",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;
    private List<PhotoDTO> photos;

}
