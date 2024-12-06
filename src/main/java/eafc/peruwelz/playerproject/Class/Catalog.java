package eafc.peruwelz.playerproject.Class;

import eafc.peruwelz.playerproject.domain.TTrack;
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


}
