package org.apache.ch2;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * IO Test
 */

class Serialize implements Serializable {
  private static final long serialVersionID = -32498989348938493L;
  public int num = 2022;
}

public class App {
  public static void main(String[] args) {

    try {
      FileOutputStream fos = new FileOutputStream("/tmp/file");
      ObjectOutputStream oss = new ObjectOutputStream(fos);
      Serialize s = new Serialize();
      oss.writeObject(s);
      oss.flush();
      oss.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      StringBuffer str = new StringBuffer();
      char[] buf = new char[1024];
      FileReader f = new FileReader("/tmp/file");
      while (f.read(buf) > 0) {
        str.append(buf);
      }
      System.out.println(str.toString());
      f.close();
    } catch (IOException e) {
      System.out.println(e.toString());
    }

  }
}
