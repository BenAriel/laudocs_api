package br.api.laudocs.laudocs_api.enums;

public enum Status {
    FILA("fila"),
    FINALIZADA("finalizada"),;

    private String status;

    Status(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}