package my.oktmo;

public class Place extends GroupsAndPlaces{
  /*public long getCode() {
    return code;
  }

  public void setCode(long code) {
    this.code = code;
  }*/

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String status;

  @Override
  public String toString(){
    return code + " " + status + " " + name;

  }
}
