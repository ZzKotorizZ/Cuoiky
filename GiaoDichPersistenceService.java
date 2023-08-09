package Persistence;

import Domain.*;

import java.util.List;

public interface GiaoDichPersistenceService {
    void saveGiaoDich(GiaoDich giaoDich);

    void deleteGiaoDich(String maGiaoDich);

    void updateGiaoDich(String maGiaoDich, GiaoDich giaoDich);

    GiaoDich getGiaoDichByMa(String maGiaoDich);

    List<GiaoDich> getAllGiaoDich();
}
