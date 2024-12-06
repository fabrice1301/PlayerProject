package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TAlbum;
import eafc.peruwelz.playerproject.repos.TAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final TAlbumRepository albumRepository;

    @Autowired
    public AlbumService(TAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<TAlbum> findAllAlbumService(){
        return albumRepository.findAll();
    }

    public void saveAlbumService(TAlbum album){
        albumRepository.save(album);
    }

}
