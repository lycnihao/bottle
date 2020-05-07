package run.bottle.app.model.enums;

public enum  ExpiredTypeEnums {
  DAY(1),
  WEEK(7),
  MONTH(30);

  private Integer code;

  ExpiredTypeEnums(int code) {
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }
}
