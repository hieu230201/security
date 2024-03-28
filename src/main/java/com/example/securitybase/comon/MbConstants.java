package com.example.securitybase.comon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
    Lưu ý: KHÔNG định nghĩa các type cùng kiểu
    Dùng Enum để thay thế!
    @author: hieunt
 */
public class MbConstants {
    private MbConstants() {
    }

    public static final String CLIENT_MESSAGE_ID = "clientMessageId";
    public static final String TRANSACTION_ID = "transactionId";
    public static final String UU_ID = "uuid";
    public static final String STATUS_CODE = "statusCode";
    public static final String START_TIME = "startTime";
    public static final String HEADERS = "headers";
    public static final String PATH = "path";
    public static final String BODY = "body";
    public static final String PARAMS = "params";
    public static final String DATA = "data";
    public static final String MESSAGE = "message";
    public static final String DURATION = "duration";
    public static final String FULL_RESPONSE = "full_response";
    public static final String FULLSTACK = "fullstack";
    public static final String ERROR_DETAILS = "errorDetails";
    public static final String ERROR_DETAILS_MS016 = "ERROR_DETAILS_MS016";
    public static final String MESSAGE_CODE = "messageCode";
    public static final String RECORD_ID = "recordId";
    public static final String BRANCH_CODE = "branchCode";
    public static final String MESSAGE_DETAIL = "message_detail";


    public class CusType {
        private CusType() {
        }

        public static final String KHCN = "CN";
        public static final String KHDN = "DN";
        public static final String KHDNTPLUS = "DN_TPLUS";
    }

    public static class MappingCmv {
        private MappingCmv() {
        }

        public static final Integer DA_MAPPING = 1;
        public static final Integer CHUA_MAPPING = 0;
    }

    public class KiemNgan {
        private KiemNgan() {
        }

        public static final int CO_QUA_KIEM_NGAN = 1;
        public static final int KHONG_QUA_KIEM_NGAN = 0;
    }

    public class BpmStatus {
        private BpmStatus() {
        }

        public static final String HOAN_THANH_KIEM_SOAT = "900";
    }

    public class StatusCN {
        private StatusCN() {
        }

        public static final int HACH_TOAN = 76;
        public static final int KS_HACH_TOAN = 77;
        public static final int UPLOAD_HS = 74;
        public static final int KS_HO_SO = 75;
        public static final int APP_NOVA_LAND = 224;
        // luồng xuất
        public static final int RM_BCDX_HO_SO = 262;
        public static final int HACH_TOAN_BCKSHS_XUAT = 263;
        public static final int KS_BCKSHS_XUAT = 265;

        // luồng mượn
        public static final int RM_MUON_NHAP_LIEU = 280;
        public static final int HACH_TOAN_BCKSHS_MUON = 282;
        public static final int KS_BCKSHS_MUON = 283;
    }

    public class StatusDN {
        private StatusDN() {
        }

        // luồng T
        public static final int HACH_TOAN = 82;
        public static final int KS_HACH_TOAN = 83;
        // luồng bổ sung
        public static final int UPLOAD_HS = 30;
        public static final int KS_HO_SO = 31;
        // luồng T+
        public static final int HACH_TOAN_T_PLUS = 86;
        public static final int KS_T_PLUS = 87;
        // luồng xuất
        public static final int HACH_TOAN_BCKSHS_XUAT = 352;
        public static final int KS_BCKSHS_XUAT = 353;
        public static final String HOAN_THANH = "900";

        // luồng mượn
        public static final int RM_MUON_NHAP_LIEU = 397;
        public static final int HACH_TOAN_BCKSHS_MUON = 399;
        public static final int KS_BCKSHS_MUON = 400;

        // luồng gia hạn
        public static final int RM_GHM_NHAP_LIEU = 393;
        public static final int HACH_TOAN_BCKSHS_GHM = 395;
        public static final int KS_BCKSHS_GHM = 396;
        public static final int HUY_PA = 901;

    }

    public class StatusCo {
        private StatusCo() {
        }

        public static final long HOLD = 0L;   //bản ghi mới đc tạo ra
        public static final long INAU = 1L;   //Bản ghi validate thành công
        public static final long AUTH = 2L;   //BẢN ghi được duyệt AUTH
        public static final long HIS = 3L;    //Bản ghi mới được duyêt (AUTH) / bản ghi cũ chuyển từ AUTH -> HIS
        public static final long OUTBANK = 4L;    //Bản ghi được xuất kho hoàn toàn
        public static final long REVERT_CO = 22L;    //Bản ghi bị xóa trên T24
    }

    public class StatusCoT24 {
        private StatusCoT24() {
        }

        public static final String AUTH_T24 = "AUTH";   //BẢN ghi được duyệt AUTH
    }

    public class TypePA {
        private TypePA() {
        }

        public static final String NHAP = "1";
        public static final String BO_SUNG_TAI_SAN = "2";
        public static final String T_PLUS = "3";
        public static final String MUON = "4";
        public static final String TRA = "5";
        public static final String XUAT = "6";
        public static final String HOAN_THANH_KIEM_NGAN = "7";
        public static final String GIA_HAN_MUON = "9";
    }

    public static List<String> LuongPAKhongQuaKiemNgan = Arrays.asList(TypePA.BO_SUNG_TAI_SAN, TypePA.GIA_HAN_MUON);

    public class LuongHachToan {
        private LuongHachToan() {
        }

        public static final String THONG_THUONG = "0";
        public static final String T_PLUS = "1";
    }

    public class HinhThucNhanTaiSan {
        private HinhThucNhanTaiSan() {
        }

        public static final String THONG_THUONG = "1";
        public static final String NHAN_BO_SUNG = "2";
        public static final String NHAN_GUI_GIU = "3";
        public static final String CHUYEN_GIU_GIU = "4";
        public static final String HOP_DONG_THE_CHAP_KHUNG = "5";
    }

    public class TaiSanHinhThanhTuPhuongAn {
        private TaiSanHinhThanhTuPhuongAn() {
        }

        public static final String CO = "1";
        public static final String KHONG = "2";
    }

    public class UserType {
        private UserType() {
        }

        public static final String RM = "1";
        public static final String NOT_RM = "0";
    }

    public class GanCoVaoPhuongAn {
        private GanCoVaoPhuongAn() {
        }

        public static final String CO = "1";
        public static final String KHONG = "0";
    }

    public static final List<String> stLoaiTaiSanGtcgKhacMb = Arrays.asList("800", "901", "904", "907", "908", "909", "701", "702", "703", "704", "602", "604", "751", "753", "755", "910", "912", "752", "754", "756", "911");

    public class TrangThaiBi {
        private TrangThaiBi() {
        }

        public static final String FULL = "1";   // Trạng thái đầy
        public static final String EMPTY = "2";   // Trạng thái trống
    }

    public class LoaiTaiSan {
        private LoaiTaiSan() {
        }

        public static final String BOSUNG = "28598";   // Loại tài sản bổ sung
    }

    public class DateAppConfig {
        private DateAppConfig() {
        }

        public static final String DATE_FORMAT = "yyyy-MM-dd";
        public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
        public static final String DATE_FORMAT_DD_MM_YYYY_2 = "dd-MM-yyyy";
        public static final String DATE_FORMAT_DD_MM_YYYY_3 = "dd/MM/yyyy HH:mm:ss";
    }

    public class ContentDisposition {
        private ContentDisposition() {
        }

        public static final String ATTACHMENT_FILENAME = "attachment;filename=";
    }

    public class MaSanPham {
        private MaSanPham() {
        }

        public static final String MASANPHAM_28590 = "28590";
        public static final String MASANPHAM_28591 = "28591";
        public static final String MASANPHAM_28592 = "28592";
        public static final String MASANPHAM_28593 = "28593";
        public static final String MASANPHAM_28594 = "28594";
        public static final String MASANPHAM_28595 = "28595";
        public static final String MASANPHAM_28596 = "28596";
        public static final String MASANPHAM_28597 = "28597";
        public static final String MASANPHAM_28598 = "28598";
        public static final String MASANPHAM_28512 = "28512";
    }

    public class TypeService {
        private TypeService() {
        }

        public static final String DINH_GIA_LAI_T14 = "DINH_GIA_LAI_T24";
        public static final String UPDATE_LTV_CO_QUA_HAN = "UPDATE_LTV_CO_QUA_HAN";
    }

    public class TRANGTHAIHOSO {
        private TRANGTHAIHOSO() {
        }

        public static final String NHAP = "1";
        public static final String NO = "2";
        public static final String MUON = "3";
        public static final String XUAT = "4";
        public static final String CHOXUAT = "5";

    }

    public class NguonGocTSBD {
        private NguonGocTSBD() {
        }

        public static final String TAI_SAN_KHACH_HANG = "1";
        public static final String TAI_SAN_BAO_LANH = "2";
    }

    public class PERMISSION{
        public static final String USER_VIEW = "USER#VIEW";					//user thấp nhất, all role có permission này
        public static final String USER_MODIFY = "USER#MODIFY"; 			//user tại mb có quyền sửa

        public static final String MB_VIEW = "MB#VIEW"; 					//MB chỉ mb view
        public static final String MB_MODIFY = "MB#MODIFY"; 				//MB chỉ mb modify
        public static final String MBCV_MODIFY = "MBCV#MODIFY"; 			//MB chỉ mb chuyen vien modify
        public static final String MB_APPROVAL = "MB#APPROVAL";				//MB APPROVAL

        public static final String QLTS_MODIFY = "QLTS#MODIFY"; 			//phòng QLTĐ mới đc modify
        public static final String QLTS_APPROVAL = "QLTS#APPROVAL"; 		//phòng QLTĐ sếp KS mới đc approval


        public static final String AMC_MODIFY = "AMC#MODIFY"; 				//chỉ nv chi nhánh AMC đc modify
        public static final String AMCCV_MODIFY = "AMCCV#MODIFY"; 			//chỉ nv chi nhánh AMC đc modify
        public static final String AMC_APPROVAL = "AMC#APPROVAL";			//chỉ sếp chi nhánh AMC đc approval
        public static final String AMC_VIEW = "AMC#VIEW";					//chỉ nv chi nhánh amc đc view

        public static final String ADMIN_MODIFY = "ADMIN#MODIFY"; 			//chỉ admin đc modify
    }

    public class LoaiTaiSanVanKien {
        private LoaiTaiSanVanKien() {
        }

        public static final String BDS = "1";
        public static final String PTVT_DUONG_BO = "2";
        public static final String GTCG = "3";
        public static final String MMTB = "4";
        public static final String QTS = "5";
        public static final String PTVT_THUY_NOI_DIA = "6";
        public static final String HANG_HOA_QDN = "7";
        public static final String QUYEN_DU_AN = "8";
    }

    public class MucDichCamCo {
        private MucDichCamCo() {
        }

        public static final String DU_NO_TIN_DUNG = "1";
        public static final String DU_NO_BAO_LANH = "2";
    }

    public class TrangThaiTichHopT24 {
        private TrangThaiTichHopT24() {
        }

        public static final int CHUA_TICH_HOP = 0; // 0 hoac null
        public static final int DA_TICH_HOP = 1;
    }

    public class QuyDinhBaoHiem {
        private QuyDinhBaoHiem() {
        }

        public static final String BAT_BUOC_THEO_PHE_DUYET = "1";
        public static final String BAT_BUOC_THEO_QUY_DINH = "2";
        public static final String KHONG_BAT_BUOC = "3";
    }

    public static final List<String> loaiTaiSanDatVaNhaTuNhan = Arrays.asList("101", "102", "104", "105", "114", "115");
    public static final List<String> loaiTaiSanBatDongSan = Arrays.asList("201", "202", "203", "204", "205", "206", "207");
    public static final List<String> loaiTaiSanBatDongSanNhaChungCu = Collections.singletonList("103");
    public static final List<String> loaiTaiSanQuyenTsHinhThanhTuMuaBanNhaO = Arrays.asList("420", "421", "422", "423", "424", "425");
    public static final List<String> loaiTaiSanMayMocThietBi = Arrays.asList("214", "215", "216");
    public static final List<String> loaiTaiSanPhuongTienVanTai = Arrays.asList("301", "302", "303", "304", "305", "306", "307",  "308",  "309",  "310",  "311",  "312",  "313", "360", "361", "362", "363", "380", "385");
    public static final List<String> loaiTaiSanHangHoa405401402 = Arrays.asList("405", "402", "401");
    public static final List<String> loaiTaiSanHangHoa403404 = Arrays.asList("403", "404");
    public static final List<String> loaiTaiSanQuyenTaiSan = Arrays.asList("410", "500", "510", "520");
    public static final List<String> loaiTaiSanCoPhieuTraiPhieu = Arrays.asList("800", "904", "908", "701", "703", "602", "751", "753", "755", "910", "912", "901", "907", "909", "702", "704", "604", "752", "754", "756", "911", "801", "902", "903", "780", "607", "606");
    public static final List<String> loaiTaiSanTienGuiNganHang = Arrays.asList("601", "603", "605", "610");
    public static final List<String> loaiTaiSanDau402404 = Arrays.asList("402", "404");
    public static final List<String> loaiTaiSanHangHoaQuyenDoiNo403 = Arrays.asList("403");
    public static final List<String> loaiTaiSanHangHoa405401402404 = Arrays.asList("405", "402", "401", "404");
    public static final List<String> loaiTaiSanChungCu = Arrays.asList("103", "206", "207", "420", "423");


    public class BpmExportOptions {
        private BpmExportOptions() {
        }

        public static final String CO_XUAT_TOAN_BO = "1";
        public static final String CO_XUAT_1_PHAN = "2";
        public static final String XUAT_HO_SO = "1";
        public static final String KHONG_XUAT_HO_SO = "2";

    }

    public class BCDX {
        private BCDX() {
        }

        public static final String BCDX = "1";
        public static final String KHONG_QUA_BCDX = "0";

    }

    public class ErrorCode {
        private ErrorCode() {
        }

        public static final String DAY_DU = "00";
        public static final String KHONG_GTSH_TAI_SAN = "01.10";
        public static final String CHUNG_BI = "04.1";
        public static final String THUA_HO_SO = "05.1";
        public static final String PHIEU_MUON_TS_XUAT_KHO = "06.1";
        public static final String SO_DO_CHUA_DANG_KY = "01.4";
        public static final String KHAC = "07.1";
        public static final String GIAY_TO_SO_HUU = "01.1";
        public static final String GIAY_TO_SO_HUU_GOC = "01.2";
        public static final String LOAI_HO_SO_GIAY_TO_SO_HUU_GOC = "01.6";
        public static final String GIAY_TO_SO_HUU_THEO_QĐ = "01.3";
        public static final String DK_GDBD_GOC = "02.2";
        public static final String DK_GDBD = "02.1";
        public static final String HO_SO_VAN_KIEN_THE_CHAP = "01.5";
        public static final String HO_SO_VAN_KIEN_THE_CHAP_GOC = "01.7";
        public static final String LOAI_HO_SO_VAN_KIEN_THE_CHAP = "01.8";
        public static final String VAN_KIEN_THE_CHAP_THEO_QUY_DINH = "01.9";
        public static final String KHAC_THIEU_HO_SO = "03.1";
        public static final String KHAC_KHONG_DUNG_LOAI_HO_SO = "03.2";
        public static final String KHAC_HO_SO_SAI_QĐ = "03.3";
        public static final String CHUA_KIEM_KE = "IT01";
        public static final String THUA_BI = "IT02";

    }

    public class BcdxRm {
        private BcdxRm() {
        }

        public static final String BCDX_RM = "262";
        public static final String BCKS_HO_SO = "263";
        public static final String KS_BCDX_RM = "264";
        public static final String KS_BCKS_HO_SO = "265";
        public static final String HUB_NO_IS_DN = "0";
        public static final String HUB_IS_DN = "1";

    }

    public class TrangThaiXuatHs {
        private TrangThaiXuatHs() {
        }

        public static final int XUAT_HS = 1;
        public static final int KHONG_XUAT_HS = 0;
	}

    public class MailMbDomain {
        private MailMbDomain() {
        }

        public static final String MAIL_MB_DOMAIN = "@mbbank.com.vn";
    }

    public class NoiNhanHoSo {
        private NoiNhanHoSo() {
        }

        public static final String CHI_NHANH_NGG = "1";
        public static final String CHI_NHANH_QLKH = "2";
    }

    public class DonViDinhGia {
        private DonViDinhGia() {
        }
        public static final String DGL_TU_DONG_AMC = "7";
        public static final String DGL_TU_DONG = "6";
        public static final String QUA_HAN_AMC = "3";
    }

    public class TypeXuatPhieu {
        private TypeXuatPhieu() {
        }
        public static final String KIEM_SOAT = "2";
        public static final String KIEM_NGAN = "1";
    }

    public class RoleCmv {
        private RoleCmv() {
        }
        public static final String G_9_MBVH_KHOTS_CVHT = "G9_MBVH_KHOTS_CVHT";
        public static final String G9_KHOTS_TRUONGHUB = "G9_KHOTS_TRUONGHUB";
        public final static String G9_MB_THUB_GDDV = "G9_MB_THUB_GDDV";
        public final static String G9_MBVH_GDDV = "G9_MBVH_GDDV";
        public static final String G9_MB_CVQHKH = "G9_MB_CVQHKH";
        public static final String G9_MB_CVHT = "G9_MB_CVHT";
        public static final String G9_MB_CVHTLV3 = "G9_MB_CVHTLV3";
        public static final String G9_MB_CVHTHO = "G9_MB_CVHTHO";
        public static final String G9_MB_KIEMNGAN = "G9_MB_KIEMNGAN";
        public static final String G9_MB_ADMIN = "G9_MB_ADMIN";
    }

    public class TinhTrangTheoMb {
        private TinhTrangTheoMb() {
        }
        public static final String MOI = "1";
        public static final String DA_SU_DUNG = "2";
    }

    public class RecordStatusT24 {
        private RecordStatusT24() {
        }
        public static final String REVERT = "REVE";    //Bản ghi bị xóa trên T24
        public static final String HOLD = "IHLD";    //Bản ghi bị hold trên T24
        public static final String INAU = "INAU";    //Bản ghi bị INAU trên T24
    }

    public class LimitConstants {
        private LimitConstants() {
        }
        public static final String SUCCESS = "SUCCESS";
        public static final String LIMIT_OTHER = "1";
    }

    public static final List<String> lstHangTonKho = Arrays.asList("401", "402","403", "404","405");
    public static final List<String> lstQuyenTaiSan = Arrays.asList("420", "421", "422", "423", "424", "425");
    public static final List<String> lstHopDongBaoHiem = Arrays.asList("500");
    public static final List<String> lstQuyenSoHuuTriTue = Arrays.asList("510");
    public static final List<String> lstQuyenThuHuongLC = Arrays.asList("520");
    public static final List<String> lstTienGuiNganHang = Arrays.asList("601", "603", "605", "610", "800", "904", "908", "701", "703", "602", "751", "753", "755", "910", "912", "901", "907", "909", "702", "704", "604", "752", "754", "756", "911", "801", "902", "903", "780", "607", "606");
    public static final String M12 = "M12";
    public static final String M06 = "M06";
    public static final String M03 = "M03";
    public static final List<String> lstIgnoreGtcg = Arrays.asList("601", "602", "603", "604", "605", "606", "607", "701", "703", "702", "704", "751",  "752",  "753", "755", "756", "800", "801", "901", "902", "903", "904", "907", "908", "909", "910", "911", "912");

    public class SoLanXuat {
        private SoLanXuat() {
        }
        public static final int KSV = 1;
        public static final int KN = 2;
        public static final int DA_XUAT_KN = 3;
    }
    public class LoaiPtgtKhdn {
        private LoaiPtgtKhdn() {
        }
        public static final String XE_O_TO = "1";
        public static final String XE_O_TO_TEXT = "Xe ô tô";
        public static final String XE_RO_ROMOC = "2";
        public static final String XE_RO_ROMOC_TEXT = "Xe rơ moóc";
        public static final String XE_SO_MI_ROMOC = "3";
        public static final String XE_SO_MI_ROMOC_TEXT = "Xe sơ mi Rơmoóc";
        public static final String MAY_DAO_BANH_XICH = "4";
        public static final String MAY_DAO_BANH_XICH_TEXT = "Máy đào bánh xích";
        public static final String MAY_DAO_BANH_LOP = "5";
        public static final String MAY_DAO_BANH_LOP_TEXT = "Máy đào bánh lốp";
        public static final String MAY_XUC = "6";
        public static final String MAY_XUC_TEXT = "Máy xúc";
    }

    public class TrangThaiXuatLuuHanhXe {
        private TrangThaiXuatLuuHanhXe() {
        }
        public static final String CHUA_HOAN_THANH = "0";
        public static final String HOAN_THANH = "2";

    }

    public class LoaiHinhDamBao {
        private LoaiHinhDamBao() {
        }
        public static final String CONG_TY = "1";
        public static final String CA_NHAN = "2";
        public static final String HO_KINH_DOANH = "3";
        public static final String DOANH_NGHIEP_TU_NHAN = "4";

    }

    public class TrangThaiTichHop {
        private TrangThaiTichHop() {
        }
        public static final String TICH_HOP_CO = "126";
        public static final String REVERT_CO = "127";
        public static final int REVERT_KHONG_THANH_CONG = 0;
        public static final String REVERT = "REVE";
    }

    public class TenHoSoPhapLy {
        private TenHoSoPhapLy() {
        }
        public static final String HD_MUA_BAN = "1";
    }

    public class HienTrangBanGiao {
        private HienTrangBanGiao() {
        }
        public static final String CHUA_BG = "1";
        public static final String DA_BG_CHUA_NOP_HS = "2";
        public static final String DA_BG_DA_NOP_HS = "3";
    }

    public class TienDoHoSo {
        private TienDoHoSo() {
        }
        public static final String BBBG_NHA = "1";
        public static final String CAP_GCN = "2";
        public static final String HDMB = "3";
        public static final String HT_GCN = "4";
        public static final String VBTT = "5";
    }

    public class SourceAppId {
        private SourceAppId() {
        }
        public static final String MAPPING_APP_LUU_DONG = "1";
        public static final String KHONG_MAPPING = "127";
    }
}