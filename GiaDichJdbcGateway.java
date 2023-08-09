package Persistence;

import Domain.GiaoDich;
import Domain.GiaoDichDat;
import Domain.GiaoDichNha;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaDichJdbcGateway implements GiaDichGateway {
    private Connection connection;

    public GiaDichJdbcGateway() {
        // Initialize the database connection here (replace dbUrl, username, and
        // password with your SQL Server credentials)
        String dbUrl = "jdbc:sqlserver://localhost;databaseName=GiaoDich";
        String username = "sa";
        String password = "12345";
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveGiaoDich(GiaoDich giaoDich) {
        String sql = "INSERT INTO GiaoDich (maGiaoDich, ngayGiaoDich, loaiGiaoDich, soTien) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, giaoDich.getMaGiaoDich());
            statement.setDate(2, new Date(giaoDich.getNgayGiaoDich().getTime()));
            statement.setString(3, giaoDich.getClass().getSimpleName()); // Lưu tên lớp con vào cột loaiGiaoDich
            statement.setDouble(4, giaoDich.tinhThanhTien());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGiaoDich(String maGiaoDich, GiaoDich giaoDich) {
        String sql = "UPDATE GiaoDich SET ngayGiaoDich = ?, loaiGiaoDich = ?, soTien = ? WHERE maGiaoDich = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new Date(giaoDich.getNgayGiaoDich().getTime()));
            statement.setString(2, giaoDich.getClass().getSimpleName()); // Lưu tên lớp con vào cột loaiGiaoDich
            statement.setDouble(3, giaoDich.tinhThanhTien());
            statement.setString(4, maGiaoDich);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGiaoDich(String maGiaoDich) {
        String sql = "DELETE FROM GiaoDich WHERE maGiaoDich = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, maGiaoDich);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GiaoDich getGiaoDichByMa(String maGiaoDich) {
        String sql = "SELECT * FROM GiaoDich WHERE maGiaoDich = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, maGiaoDich);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String loaiGiaoDich = resultSet.getString("loaiGiaoDich");
                if ("GiaoDichDat".equalsIgnoreCase(loaiGiaoDich)) {
                    String ma = resultSet.getString("maGiaoDich");
                    java.util.Date ngay = resultSet.getDate("ngayGiaoDich");
                    double donGia = resultSet.getDouble("donGia");
                    double dienTich = resultSet.getDouble("dienTich");
                    String loaiDat = resultSet.getString("loaiDat");
                    return new GiaoDichDat(ma, ngay, donGia, dienTich, loaiDat);
                } else if ("GiaoDichNha".equalsIgnoreCase(loaiGiaoDich)) {
                    String ma = resultSet.getString("maGiaoDich");
                    java.util.Date ngay = resultSet.getDate("ngayGiaoDich");
                    double donGia = resultSet.getDouble("donGia");
                    double dienTich = resultSet.getDouble("dienTich");
                    String loaiNha = resultSet.getString("loaiNha");
                    return new GiaoDichNha(ma, ngay, donGia, dienTich, loaiNha);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<GiaoDich> getAllGiaoDich() {
        List<GiaoDich> giaoDichList = new ArrayList<>();
        String sql = "SELECT * FROM GiaoDich";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String loaiGiaoDich = resultSet.getString("loaiGiaoDich");
                if ("GiaoDichDat".equalsIgnoreCase(loaiGiaoDich)) {
                    String ma = resultSet.getString("maGiaoDich");
                    java.util.Date ngay = resultSet.getDate("ngayGiaoDich");
                    double donGia = resultSet.getDouble("donGia");
                    double dienTich = resultSet.getDouble("dienTich");
                    String loaiDat = resultSet.getString("loaiDat");
                    giaoDichList.add(new GiaoDichDat(ma, ngay, donGia, dienTich, loaiDat));
                } else if ("GiaoDichNha".equalsIgnoreCase(loaiGiaoDich)) {
                    String ma = resultSet.getString("maGiaoDich");
                    java.util.Date ngay = resultSet.getDate("ngayGiaoDich");
                    double donGia = resultSet.getDouble("donGia");
                    double dienTich = resultSet.getDouble("dienTich");
                    String loaiNha = resultSet.getString("loaiNha");
                    giaoDichList.add(new GiaoDichNha(ma, ngay, donGia, dienTich, loaiNha));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return giaoDichList;
    }
}
