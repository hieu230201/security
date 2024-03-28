package com.example.securitybase.comon;

import com.example.securitybase.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Cấu hình erorcode - error message - httpStatus code
 *
 * @author hieunt
 */
public enum ErrorCode implements BaseErrorCode {
    // auth
    USER_NOT_ACCEPT(1, "user.not.accept", HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_REQUIRED(2, "refresh.token.required", HttpStatus.UNPROCESSABLE_ENTITY),
    REFRESH_TOKEN_NOT_EXIST(3, "refresh.token.not.exist", HttpStatus.UNPROCESSABLE_ENTITY),
    REFRESH_TOKEN_EXPIRED(4, "refresh.token.expired", HttpStatus.UNPROCESSABLE_ENTITY),
    GRANT_TYPE_NOT_SUPPORTED(5, "grant.type.not.supported", HttpStatus.UNPROCESSABLE_ENTITY),
    USER_LOCKED(6, "user.locked", HttpStatus.UNPROCESSABLE_ENTITY),
    USER_NOT_FOUND(7, "username.not.found", HttpStatus.UNPROCESSABLE_ENTITY),
    TOKEN_INVALID(8, "token.invalid", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(9, "token.expired", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(10, "access.denied", HttpStatus.FORBIDDEN),
    USER_PASS_INVALID(11, "user.pass.invalid", HttpStatus.BAD_REQUEST),
    MAIL_TEMPLATE_NOT_FOUND(12, "mail.template.not.found", HttpStatus.NOT_FOUND),
    SERVER_ERROR(13, "server.error", HttpStatus.UNPROCESSABLE_ENTITY),
    WRONG_PASSWORD(1011, "wrong.password", HttpStatus.UNAUTHORIZED),
    CHECK_AUTHEN(14, "check.authen", HttpStatus.UNAUTHORIZED),
    CHECK_STATUS(19, "Phương án kiểm kê đã hoàn thành", HttpStatus.BAD_REQUEST),
    CHECK_PHUONG_AN(19, "Không tồn tại phương án nào", HttpStatus.BAD_REQUEST),
    CHECK_USER(20, "User không có quyền thực hiện ở đây", HttpStatus.BAD_REQUEST),
    IT02_ERROR(4005, "Mã bì kho không tồn tại trong chi nhánh", HttpStatus.BAD_REQUEST),
    NOT_FOUND_DECISION(21, "Không tìm thấy phương án", HttpStatus.BAD_REQUEST),
    VALIDATE_FAIL(22, "validate fail", HttpStatus.BAD_REQUEST),

    PROCESS_NOT_EXISTED(1005, "process.not.exist", HttpStatus.NOT_FOUND),
    NO_RIGHTS_EXECUTE(1006, "no.rights.execute", HttpStatus.METHOD_NOT_ALLOWED),
    HTTP_STATUS_CODE_EXCEPTION(1007, "", HttpStatus.BAD_REQUEST),
    UNPROCESSABLE_ENTITY(1008, "", HttpStatus.UNPROCESSABLE_ENTITY),
    PROCESS_VALIDATE_BCDG(1009, "", HttpStatus.NOT_FOUND),
    PROCESS_VALIDATE_TY_LE_DIEU_CHINH_DIEN_TICH(1010, "Tỷ lệ điều chỉnh diện tích không thỏa mãn", HttpStatus.CONFLICT),
    PROCESS_VALIDATE_TY_LE_DIEU_CHINH(1011, "Tỷ lệ điều chỉnh diện tích không thỏa mãn", HttpStatus.CONFLICT),

    CALL_API_ERROR(2000, "", HttpStatus.BAD_REQUEST),
    CALL_API_HTTP_ERROR(2001, "", HttpStatus.BAD_REQUEST),

    FILE_UPLOAD_NOT_FOUND(2002, "Không tìm thấy file", HttpStatus.NOT_FOUND),

    COMMON_REQUIRE_FIELD(2003, "Vui lòng điền các trường bắt buộc", HttpStatus.CONFLICT),
    COMMON_DUPLICATE(2004, "Thông tin bản ghi đã tồn tại", HttpStatus.CONFLICT),

    SYS_GROUP_KEY_DUPLICATE(3001, "Mã đơn vị đã tồn tại", HttpStatus.CONFLICT),

    GUI_PHE_DUYET_PDN_NULL(4000, "", HttpStatus.BAD_REQUEST),
    GUI_PHE_DUYET_PDN_VALIDATE_ERROR(4001, "", HttpStatus.BAD_REQUEST),
    KHONG_TON_TAI_CUM_CMV(4004, "Không tồn tại cụm CMV!", HttpStatus.BAD_REQUEST),
    CUM_CMV_KHONG_CO_MA_TAI_SAN(4005, "Mã tài sản CMV không được để trống!", HttpStatus.BAD_REQUEST),
    VALIDATE_FAIL_REJECT(4006, "Vui lòng nhập lý do từ chối!", HttpStatus.BAD_REQUEST),
    NOT_MAPPING_USER(4009, "User hạch toán chưa được mapping user T24 tại CMV", HttpStatus.BAD_REQUEST),
    NOT_MAPPING_USER_KS(4010, "User kiểm soát hạch toán chưa được mapping user T24 tại CMV", HttpStatus.BAD_REQUEST),
    NOT_CREATE_MD(23, "", HttpStatus.BAD_REQUEST),
    NOT_TAT_TOAN_MD(24, "", HttpStatus.BAD_REQUEST),
    NOT_MAPPING_CPTP(4011, "Có lỗi khi mapping thông tin cổ phiếu trái phiếu", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_ECM_FAILED(11028, "Upload file lên ECM không thành công vui lòng liên hệ IT hỗ trợ", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_Validate(11029, "", HttpStatus.BAD_REQUEST),
    TRANSACTION_CHECK(500, "Hệ thống đang trong quá trình xử lý revert hoặc tích hơp. Không thể revert hoặc tích hợp !", HttpStatus.BAD_REQUEST),
    BLOCK_FUNCTION(500, "Chức năng đang được bảo trì, vui lòng thử lại sau!", HttpStatus.BAD_REQUEST),
    ERROR_FILE_SIZE(400, "File phải nhỏ hơn 20Mb!", HttpStatus.BAD_REQUEST),
    ERROR_FILE_TYPE(400, "File sai định dạng!", HttpStatus.BAD_REQUEST);


    private final int code;

    private final String message;

    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}