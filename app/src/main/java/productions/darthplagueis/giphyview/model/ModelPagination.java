package productions.darthplagueis.giphyview.model;

/**
 * Created by murodjon.rahimov on 2/4/18.
 */

public class ModelPagination {
  private int total_count;
  private int count;
  private int offset;

  public ModelPagination(int total_count, int count, int offset) {
    this.total_count = total_count;
    this.count = count;
    this.offset = offset;
  }

  public int getTotal_count() {
    return total_count;
  }

  public void setTotal_count(int total_count) {
    this.total_count = total_count;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }
}