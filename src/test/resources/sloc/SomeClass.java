package de.baumato.loc;

/**
 * SomeClass has coordinates.
 *
 * @author author
 */
public class SomeClass {

  // field comment
  private int x;

  /** multi-line comment on field */
  private int y;

  /**
   * Constructor comment
   *
   * @param x
   * @param y
   */
  public SomeClass(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  /** mutli line comment on method */
  public void add(int x, int y) {
    // single line comment
    this.x += x;
    this.y += y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
