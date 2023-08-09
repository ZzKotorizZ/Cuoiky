package Persistence;

import Domain.GiaoDich;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GiaoDichPersistenceServiceImpl implements GiaoDichPersistenceService {
    private final String filePath;

    public GiaoDichPersistenceServiceImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveGiaoDich(GiaoDich giaoDich) {
        List<GiaoDich> giaoDichList = getAllGiaoDich();
        giaoDichList.add(giaoDich);
        saveGiaoDichListToFile(giaoDichList);
    }

    @Override
    public void deleteGiaoDich(String maGiaoDich) {
        List<GiaoDich> giaoDichList = getAllGiaoDich();
        giaoDichList.removeIf(giaoDich -> giaoDich.getMaGiaoDich().equals(maGiaoDich));
        saveGiaoDichListToFile(giaoDichList);
    }

    @Override
    public void updateGiaoDich(String maGiaoDich, GiaoDich giaoDich) {
        List<GiaoDich> giaoDichList = getAllGiaoDich();
        for (int i = 0; i < giaoDichList.size(); i++) {
            if (giaoDichList.get(i).getMaGiaoDich().equals(maGiaoDich)) {
                giaoDichList.set(i, giaoDich);
                break;
            }
        }
        saveGiaoDichListToFile(giaoDichList);
    }

    @Override
    public GiaoDich getGiaoDichByMa(String maGiaoDich) {
        List<GiaoDich> giaoDichList = getAllGiaoDich();
        for (GiaoDich giaoDich : giaoDichList) {
            if (giaoDich.getMaGiaoDich().equals(maGiaoDich)) {
                return giaoDich;
            }
        }
        return null;
    }

    @Override
    public List<GiaoDich> getAllGiaoDich() {
        List<GiaoDich> giaoDichList = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                GiaoDich giaoDich = (GiaoDich) inputStream.readObject();
                giaoDichList.add(giaoDich);
            }
        } catch (EOFException e) {
            // End of file, do nothing
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return giaoDichList;
    }

    private void saveGiaoDichListToFile(List<GiaoDich> giaoDichList) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (GiaoDich giaoDich : giaoDichList) {
                outputStream.writeObject(giaoDich);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
