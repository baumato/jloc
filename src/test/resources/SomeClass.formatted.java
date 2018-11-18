package de.baumato.jloc;

public class SomeClass {

  private int x;
  private int y;

  public SomeClass(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  /** mutli line comment */
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
