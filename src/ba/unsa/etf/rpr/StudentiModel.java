package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentiModel {
    private static int i=1;
    private static ObservableList<String> studenti= FXCollections.observableArrayList();
    public ObservableList<String> getStudenti() {
        return studenti;
    }

    public void setStudenti(ObservableList<String> studenti) {
        this.studenti = studenti;
    }

    public static void dodajStudenta(String zadnji){
        if(i==15) studenti.add("Student"+zadnji);
        else studenti.add("Student"+i);
        i++;
    }
    public static void dodajStudentaSaImenom(String student){
        studenti.add(student);
        i++;
    }
    public static ObservableList<String> skratiDo(int x){
        ObservableList<String> result=FXCollections.observableArrayList();
        int y=0;
        for(String st:studenti){
            result.add(st);
            y++;
            if(y==x) break;
        }
        return result;
    }
    public static ObservableList<String> prosiriDo(int x, String zadnji){
        if(x<studenti.size()) return skratiDo(x);
        for(int m=i;m<=x;m++){
            if(studenti.size()==x) break;
            dodajStudenta(zadnji);
        }
        return studenti;
    }
}
