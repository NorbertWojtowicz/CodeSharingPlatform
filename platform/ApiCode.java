package platform;

import java.time.LocalDateTime;

public class ApiCode implements Comparable<ApiCode> {
    public String code;
    public LocalDateTime dateTimeOfPublication = LocalDateTime.now();

    public ApiCode() {}

    public ApiCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void refreshDateTime() {
        dateTimeOfPublication = LocalDateTime.now();
    }

    public String getFormattedDateTime() {
        return dateTimeOfPublication.toLocalDate() + " "
                + dateTimeOfPublication.toLocalTime().withNano(0);
    }

    @Override
    public int compareTo(ApiCode apiCode) {
        return getFormattedDateTime().compareTo(apiCode.getFormattedDateTime()) * -1;
    }
}
