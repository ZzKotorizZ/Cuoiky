package Domain;

import java.util.Date;

public class GiaoDichDat extends GiaoDich {
    private String loaiDat;

    public GiaoDichDat(String maGiaoDich, Date ngayGiaoDich, double donGia, double dienTich, String thongTinKhac) {
        super(maGiaoDich, ngayGiaoDich, donGia, dienTich, thongTinKhac);
        this.loaiDat = thongTinKhac;
    }

    public String getLoaiDat() {
        return loaiDat;
    }

    public void setLoaiDat(String loaiDat) {
        this.loaiDat = loaiDat;
    }

    @Override
    public double tinhThanhTien() {
        if ("B".equalsIgnoreCase(loaiDat)) {
            return getDonGia() * getDienTich();
        } else if ("A".equalsIgnoreCase(loaiDat)) {
            return getDonGia() * getDienTich() * 1.5;
        } else {
            return 0;
        }
    }
}
