package com.example.springDemoWithRest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.springDemoWithRest.Model.Album;
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Album[] findByAccount_id(long id);
    
}
