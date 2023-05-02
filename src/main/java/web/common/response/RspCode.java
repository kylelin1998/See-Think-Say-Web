package web.common.response;

import lombok.Getter;

@Getter
public enum RspCode {

    SUCCESS(200, "success"),
    ERROR(201, "error"),
    QUERY_SUCCESS(200, "query_success"),
    QUERY_ERROR(201, "query_error"),
    VERIFICATION_FAILED(401, "verification_failed"),
    INTERFACE_CLOSED(401, "interface_closed"),
    NOT_FOUND(404, "not_found"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(405, "http_request_method_not_supported"),
    PARAMETER_ERROR(406, "parameter_error"),
    SUBMISSION_AGREEMENT_ERROR(406, "submission_agreement_error"),
    CONTENT_IS_TOO_LARGE(413, "content_is_too_large"),
    UNKNOWN(500, "unknown"),

    GENERATE_TOO_MANY(3015, "generate_too_many"),
    RESOURCES_NOT_EXIST(3016, "resources_not_exist"),
    ;

    private int code;
    private String message;

    RspCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
