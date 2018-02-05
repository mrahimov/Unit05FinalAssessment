package productions.darthplagueis.giphyview.model;

/**
 * Created by murodjon.rahimov on 2/4/18.
 */

public class ModelMeta {
  private int status;
  private String msg;
  private String response_id;

  public ModelMeta(int status, String msg, String response_id) {
    this.status = status;
    this.msg = msg;
    this.response_id = response_id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getResponse_id() {
    return response_id;
  }

  public void setResponse_id(String response_id) {
    this.response_id = response_id;
  }
}