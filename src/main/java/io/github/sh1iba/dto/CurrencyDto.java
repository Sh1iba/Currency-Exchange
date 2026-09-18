package io.github.sh1iba.dto;

public class CurrencyDto {
    private int id;
    private String code;
    private String fullName;
    private String sign;

    public CurrencyDto(int id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public CurrencyDto(String code, String fullName, String sign) {
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "CurrencyDto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
