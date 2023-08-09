import Domain.GiaoDichService;
import Domain.GiaoDichServiceImpl;
import Presentation.GiaoDichManagementUI;

public class GiaoDichTestDrive {
    public static void main(String[] args) {
        GiaoDichService giaoDichService = new GiaoDichServiceImpl();
        GiaoDichManagementUI ui = new GiaoDichManagementUI(giaoDichService);
    }
}
