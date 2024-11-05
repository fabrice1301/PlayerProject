package eafc.peruwelz.PlayerProject.Class;

import eafc.peruwelz.PlayerProject.domain.TTrack;
import eafc.peruwelz.PlayerProject.service.TrackService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Catalog {

    private ObservableList<TTrack> dataCatalogTable;

    public ObservableList<TTrack> reloadCatalogTableView(List<TTrack> listTrack){
        this.dataCatalogTable = FXCollections.observableArrayList(
                listTrack.stream().sorted(Comparator.comparing(TTrack::getTrackTitle)).collect(Collectors.toList())
        );
        return this.dataCatalogTable;
    }

    public void addTrack(TTrack track) {
        dataCatalogTable.add(track);
    }

    public void modifyTrack(int index,TTrack track){ dataCatalogTable.set(index,track);}

    public int getIndex(TTrack track){
        return dataCatalogTable.indexOf(track);
    }
}
