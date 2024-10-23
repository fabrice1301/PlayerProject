package eafc.peruwelz.PlayerProject.Class;

import eafc.peruwelz.PlayerProject.domain.TTrack;
import eafc.peruwelz.PlayerProject.service.TrackService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Catalog {

    private TrackService trackService;
    private ObservableList<TTrack> dataCatalogTable;

    @Autowired
    public Catalog(TrackService trackService){
        this.trackService=trackService;
    }

    public ObservableList<TTrack> reloadCatalogTableView(){
        List<TTrack> trackList=this.trackService.findAllTrackService();
        this.dataCatalogTable = FXCollections.observableArrayList(trackList);
        return this.dataCatalogTable;
    }

    public void addTrack(TTrack track) {
        dataCatalogTable.add(track);
    }
}
