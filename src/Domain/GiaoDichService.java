package Domain;

import java.util.List;

public interface GiaoDichService {
    void addGiaoDich(GiaoDich giaoDich);

    void removeGiaoDich(String maGiaoDich);

    void editGiaoDich(String maGiaoDich, GiaoDich giaoDich);

    GiaoDich getGiaoDichByMa(String maGiaoDich);

    List<GiaoDich> getAllGiaoDich();
}