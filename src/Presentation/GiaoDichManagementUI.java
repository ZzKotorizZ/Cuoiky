package Presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Domain.GiaoDich;
import Domain.GiaoDichDat;
import Domain.GiaoDichNha;
import Domain.GiaoDichService;


public class GiaoDichManagementUI extends JFrame {

    private final GiaoDichService giaoDichService;
    private final DefaultTableModel tableModel;
    private final JTable giaoDichTable;
    private final JComboBox<String> loaiGiaoDichComboBox;
    private final JTextField maGiaoDichField, ngayGiaoDichField, donGiaField, dienTichField, thongTinKhacField;
    private final JButton addButton, removeButton, editButton;

    public GiaoDichManagementUI(GiaoDichService giaoDichService) {
        this.giaoDichService = giaoDichService;

        // Initialize components
        String[] columnNames = { "Mã GD", "Ngày GD", "Đơn giá", "Diện tích", "Thành tiền", "Thông tin khác" };
        tableModel = new DefaultTableModel(columnNames, 0);
        giaoDichTable = new JTable(tableModel);

        loaiGiaoDichComboBox = new JComboBox<>();
        loaiGiaoDichComboBox.addItem("Giao dịch đất");
        loaiGiaoDichComboBox.addItem("Giao dịch nhà");

        maGiaoDichField = new JTextField(10);
        ngayGiaoDichField = new JTextField(10);
        donGiaField = new JTextField(10);
        dienTichField = new JTextField(10);
        thongTinKhacField = new JTextField(20);

        addButton = new JButton("Thêm");
        removeButton = new JButton("Xóa");
        editButton = new JButton("Sửa");

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGiaoDich();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeGiaoDich();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editGiaoDich();
            }
        });

        // Table selection action
        giaoDichTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedGiaoDichInfo();
            }
        });

        // Layout setup
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(new JLabel("Loại giao dịch:"), gbc);
        gbc.gridx++;
        inputPanel.add(loaiGiaoDichComboBox, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Mã giao dịch:"), gbc);
        gbc.gridx++;
        inputPanel.add(maGiaoDichField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Ngày giao dịch (dd/MM/yyyy):"), gbc);
        gbc.gridx++;
        inputPanel.add(ngayGiaoDichField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx++;
        inputPanel.add(donGiaField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Diện tích:"), gbc);
        gbc.gridx++;
        inputPanel.add(dienTichField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Thông tin khác:"), gbc);
        gbc.gridx++;
        inputPanel.add(thongTinKhacField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(giaoDichTable), BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH); // Thêm phần nhập thông tin vào mainPanel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.setTitle("Quản lý giao dịch nhà đất");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 400);
        this.add(mainPanel);
        this.setVisible(true);

        // Load existing data from service and populate the table
        refreshGiaoDichTable();
    }

    private void addGiaoDich() {
        String loaiGiaoDich = (String) loaiGiaoDichComboBox.getSelectedItem();
        String maGiaoDich = maGiaoDichField.getText();
        String ngayGiaoDichStr = ngayGiaoDichField.getText();
        String donGiaStr = donGiaField.getText();
        String dienTichStr = dienTichField.getText();
        String thongTinKhac = thongTinKhacField.getText();

        if (maGiaoDich.isEmpty() || ngayGiaoDichStr.isEmpty() || donGiaStr.isEmpty() || dienTichStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin giao dịch.");
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date ngayGiaoDich = dateFormat.parse(ngayGiaoDichStr);
            double donGia = Double.parseDouble(donGiaStr);
            double dienTich = Double.parseDouble(dienTichStr);

            GiaoDich giaoDich;
            if (loaiGiaoDich.equals("Giao dịch đất")) {
                // Thêm phần nhập thông tin giao dịch đất tại đây (nếu có)
                giaoDich = new GiaoDichDat(maGiaoDich, ngayGiaoDich, donGia, dienTich, thongTinKhac);
            } else {
                // Thêm phần nhập thông tin giao dịch nhà tại đây (nếu có)
                giaoDich = new GiaoDichNha(maGiaoDich, ngayGiaoDich, donGia, dienTich, thongTinKhac);
            }

            giaoDichService.addGiaoDich(giaoDich);

            // Clear input fields
            maGiaoDichField.setText("");
            ngayGiaoDichField.setText("");
            donGiaField.setText("");
            dienTichField.setText("");
            thongTinKhacField.setText("");

            // Refresh the table
            refreshGiaoDichTable();
        } catch (ParseException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nhập sai định dạng dữ liệu.");
        }
    }

    private void removeGiaoDich() {
        int selectedRow = giaoDichTable.getSelectedRow();
        if (selectedRow != -1) {
            String maGiaoDich = (String) giaoDichTable.getValueAt(selectedRow, 0);
            giaoDichService.removeGiaoDich(maGiaoDich);
            refreshGiaoDichTable();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giao dịch để xóa.");
        }
    }

    private void editGiaoDich() {
        int selectedRow = giaoDichTable.getSelectedRow();
        if (selectedRow != -1) {
            String loaiGiaoDich = (String) loaiGiaoDichComboBox.getSelectedItem();
            String maGiaoDich = maGiaoDichField.getText();
            String ngayGiaoDichStr = ngayGiaoDichField.getText();
            String donGiaStr = donGiaField.getText();
            String dienTichStr = dienTichField.getText();
            String thongTinKhac = thongTinKhacField.getText();

            if (maGiaoDich.isEmpty() || ngayGiaoDichStr.isEmpty() || donGiaStr.isEmpty() || dienTichStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin giao dịch.");
                return;
            }

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date ngayGiaoDich = dateFormat.parse(ngayGiaoDichStr);
                double donGia = Double.parseDouble(donGiaStr);
                double dienTich = Double.parseDouble(dienTichStr);

                GiaoDich giaoDich;
                if (loaiGiaoDich.equals("Giao dịch đất")) {
                    giaoDich = new GiaoDichDat(maGiaoDich, ngayGiaoDich, donGia, dienTich, thongTinKhac);
                } else {
                    giaoDich = new GiaoDichNha(maGiaoDich, ngayGiaoDich, donGia, dienTich, thongTinKhac);
                }

                giaoDichService.editGiaoDich(maGiaoDich, giaoDich);

                // Refresh the table
                refreshGiaoDichTable();
            } catch (ParseException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng dữ liệu.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giao dịch để sửa.");
        }
    }

    private void showSelectedGiaoDichInfo() {
        int selectedRow = giaoDichTable.getSelectedRow();
        if (selectedRow != -1) {
            String maGiaoDich = (String) giaoDichTable.getValueAt(selectedRow, 0);
            GiaoDich giaoDich = giaoDichService.getGiaoDichByMa(maGiaoDich);
            if (giaoDich != null) {
                populateInputFields(giaoDich);
            }
        }
    }

    private void populateInputFields(GiaoDich giaoDich) {
        loaiGiaoDichComboBox.setSelectedItem(giaoDich instanceof GiaoDichDat ? "Giao dịch đất" : "Giao dịch nhà");
        maGiaoDichField.setText(giaoDich.getMaGiaoDich());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ngayGiaoDichField.setText(dateFormat.format(giaoDich.getNgayGiaoDich()));
        donGiaField.setText(String.valueOf(giaoDich.getDonGia()));
        dienTichField.setText(String.valueOf(giaoDich.getDienTich()));
        thongTinKhacField.setText(giaoDich.getThongTinKhac());

    }

    private void refreshGiaoDichTable() {
        // Clear existing data in the table
        tableModel.setRowCount(0);

        // Retrieve all giao dịch from the service and populate the table
        List<GiaoDich> giaoDichList = giaoDichService.getAllGiaoDich();
        for (GiaoDich giaoDich : giaoDichList) {
            Object[] rowData = {
                    giaoDich.getMaGiaoDich(),
                    formatDate(giaoDich.getNgayGiaoDich()),
                    giaoDich.getDonGia(),
                    giaoDich.getDienTich(),
                    giaoDich.tinhThanhTien(),
                    giaoDich.getThongTinKhac()
            };
            tableModel.addRow(rowData);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

}
