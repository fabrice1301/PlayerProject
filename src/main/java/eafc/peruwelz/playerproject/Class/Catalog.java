package eafc.peruwelz.playerproject.Class;

import eafc.peruwelz.playerproject.ctrl.CatalogController;
import eafc.peruwelz.playerproject.domain.TTrack;
import eafc.peruwelz.playerproject.service.TrackService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;


@Component
public class Catalog {

    private ObservableList<TTrack> dataCatalogTable;
    private CatalogController catalogController;
    private final TrackService trackService;

    @Autowired
    public Catalog(TrackService trackService){
        this.dataCatalogTable=FXCollections.observableArrayList();
        this.trackService=trackService;

        List<TTrack> trackList = trackService.findAllTrackService();
        this.dataCatalogTable.addAll(trackList);
    }



    public void setCatalogController(CatalogController catalogController){
        this.catalogController=catalogController;
    }

    public void addTrack(TTrack track) {
        dataCatalogTable.add(track);
    }

    public void modifyTrack(int index,TTrack track){
        dataCatalogTable.set(index,track);
    }

    public int getIndex(TTrack track){
        return dataCatalogTable.indexOf(track);
    }

    public void activeTrack(TTrack track){
        for(TTrack t:dataCatalogTable){
            if (t.equals(track)){
                t.setTrackWaiting(false);
                break;
            }
        }
    }

    public void sortCatalog()
    {
        FXCollections.sort(this.dataCatalogTable, Comparator.comparing(TTrack::getTrackTitle));
    }


    public void setDataCatalogTable(List<TTrack> list){
        this.dataCatalogTable.setAll(list.stream().sorted(Comparator.comparing(TTrack::getTrackTitle)).toList());
        //catalogController.refreshTableView();
    }

    public ObservableList<TTrack> getDataCatalogTable(){
        return this.dataCatalogTable;
    }

    public void removeTrack(TTrack track){
        this.dataCatalogTable.remove(track);
    }

    public TTrack getTrackByIndex(int index){
        return dataCatalogTable.get(index);
    }


    public void initTrackWaiting(){
        for(TTrack track:this.dataCatalogTable){
            track.setTrackWaiting(false);
            this.trackService.saveTrackService(track);

        }
    }


}
