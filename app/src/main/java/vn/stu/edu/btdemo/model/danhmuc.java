package vn.stu.edu.btdemo.model;

public class danhmuc {
    @Override
    public String toString() {
        return "danhmuc{" +
                "id='" + id + '\'' +
                ", tenloai='" + tenloai + '\'' +
                '}';
    }

    public danhmuc(String id, String tenloai) {
        this.id = id;
        this.tenloai = tenloai;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    private String tenloai;


}
