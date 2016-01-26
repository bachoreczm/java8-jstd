package jstester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class JsContentsUtil {

  private JsContentsUtil() {
  }

  /**
   * Reading a js file from the classpath.
   *
   * @param path
   *          file's path
   * @return a {@link JsFileProperties} which contains the js file's content
   * @throws IOException
   *           if an I/O error occurs
   */
  public static JsFileProperties readFile(String path) throws IOException {
    String fileName = "/" + path.replaceAll("\\.", "/") + ".js";
    InputStream is = JsTester.class.getResourceAsStream(fileName);
    InputStreamReader isReader = new InputStreamReader(is);
    BufferedReader reader = new BufferedReader(isReader);
    final StringBuilder sb = new StringBuilder();
    String line = reader.readLine();
    int lineNumber = 0;
    while (line != null) {
      sb.append(line + "\n");
      line = reader.readLine();
      ++lineNumber;
    }
    return new JsFileProperties(path, lineNumber, sb.toString());
  }

  /**
   * Reading a js files from the classpath.
   *
   * @param srcFiles
   *          files' paths
   * @return {@link JsFileProperties} array which contains the js files'
   *         contents
   * @throws IOException
   *           if an I/O error occurs
   */
  public static JsFileProperties[] readFiles(String[] srcFiles)
      throws IOException {
    JsFileProperties[] codes = new JsFileProperties[srcFiles.length];
    for (int i = 0; i < srcFiles.length; ++i) {
      codes[i] = readFile(srcFiles[i]);
    }
    return codes;
  }

  /**
   * @param codes
   *          the javascript codes
   * @return concatenating the given codes.
   */
  public static String computeUserCode(JsFileProperties[] codes) {
    StringBuilder userCode = new StringBuilder();
    for (int i = 0; i < codes.length; ++i) {
      if (i > 0) {
        userCode.append("\n");
      }
      userCode.append(codes[i].toString());
    }
    return userCode.toString();
  }
}
