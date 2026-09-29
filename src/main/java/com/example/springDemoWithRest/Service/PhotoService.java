package com.example.springDemoWithRest.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springDemoWithRest.Model.Photo;
import com.example.springDemoWithRest.Repository.PhotoRepository;

@Service 
public class PhotoService {
    
    @Autowired 
    private PhotoRepository photoRepository;    


    public Photo save(Photo photo) {
        return photoRepository.save(photo);
    }
    public Optional<Photo> findById(Long id) {
        return photoRepository.findById(id);
    }
     public List<Photo> findByAlbum_id(long id){
        return photoRepository.findByAlbum_id(id);
    }
     public Photo[] findByAlbum_id() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAlbum_id'");
     }
     public void delete(Photo photo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
     }
}
