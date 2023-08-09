package Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiaoDichServiceImpl implements GiaoDichService {
    private final Map<String, GiaoDich> giaoDichMap;

    public GiaoDichServiceImpl() {
        giaoDichMap = new HashMap<>();
    }

    @Override
    public void addGiaoDich(GiaoDich giaoDich) {
        giaoDichMap.put(giaoDich.getMaGiaoDich(), giaoDich);
    }

    @Override
    public void removeGiaoDich(String maGiaoDich) {
        giaoDichMap.remove(maGiaoDich);
    }

    @Override
    public void editGiaoDich(String maGiaoDich, GiaoDich giaoDich) {
        giaoDichMap.put(maGiaoDich, giaoDich);
    }

    @Override
    public GiaoDich getGiaoDichByMa(String maGiaoDich) {
        return giaoDichMap.get(maGiaoDich);
    }

    @Override
    public List<GiaoDich> getAllGiaoDich() {
        return new ArrayList<>(giaoDichMap.values());
    }
}