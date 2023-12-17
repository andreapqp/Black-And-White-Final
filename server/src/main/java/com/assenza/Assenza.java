package com.assenza;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Document(collection = "assenze")
public class Assenza {

    @Id
    private String id;
    private Date data;
    private List<String> dipendenti;
  
    public Assenza(Date data, List<String> dipendenti) {
        this.data = data;
        this.dipendenti = dipendenti;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<String> getDipendenti() {
        return dipendenti;
    }

    public void setDipendenti(List<String> dipendenti) {
        this.dipendenti = dipendenti;
    }
}
