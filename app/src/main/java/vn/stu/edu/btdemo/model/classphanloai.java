package vn.stu.edu.btdemo.model;

public class classphanloai {
    public classphanloai(String idPL, String tenPL) {
        this.idPL = idPL;
        this.tenPL = tenPL;
    }
public classphanloai(){}


    private String idPL;
    private String tenPL;
    public String getIdPL() {
        return idPL;
    }

    public void setIdPL(String idPL) {
        this.idPL = idPL;
    }

    public String getTenPL() {
        return tenPL;
    }

    public void setTenPL(String tenPL) {
        this.tenPL = tenPL;
    }


    @Override
    public String toString() {
        return "classphanloai{" +
                "idPL='" + idPL + '\'' +
                ", tenPL='" + tenPL + '\'' +
                '}';
    }
}
