package com.example.springDemoWithRest.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springDemoWithRest.Repository.AlbumRepository;
import com.example.springDemoWithRest.Model.Album;
@Service 
public class AlbumService {
    @Autowired 
    private  AlbumRepository albumRepository;

    public Album save(Album album) {
        return albumRepository.save(album);
    }

    public Album[] findByAccount_id(long id) {
        // TODO Auto-generated method stub
        return albumRepository.findByAccount_id(id);
      //throw new UnsupportedOperationException("Unimplemented method 'findByAccount_id'");
    }

    public Optional<Album> findById(long album_id) {
        // TODO Auto-generated method stub
        return albumRepository.findById(album_id);
    }

    public void delete(Album album) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

   

}
