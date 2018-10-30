package de.baumato.loc.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CharsetTryingFileReader {

  private static final List<Charset> CHARSETS_TO_TRY =
      Arrays.asList(
          StandardCharsets.UTF_8,
          StandardCharsets.ISO_8859_1,
          StandardCharsets.UTF_16LE,
          StandardCharsets.UTF_16BE);
  private final Path path;

  public CharsetTryingFileReader(Path p) {
    this.path = p;
  }

  public String read() throws IOException {
    byte[] fileContent = Files.readAllBytes(path);
    return decode(fileContent);
  }

  private String decode(byte[] fileContent) throws IOException {
    IOException error = new IOException("Unable to determine charset of file '" + path + "'.");
    for (Charset charset : CHARSETS_TO_TRY) {
      try {
        return decode(fileContent, charset);
      } catch (CharacterCodingException e) {
        error.addSuppressed(e);
      }
    }
    throw error;
  }

  private static String decode(byte[] fileContent, Charset charsetToTry)
      throws CharacterCodingException {
    CharsetDecoder decoder = charsetToTry.newDecoder().onMalformedInput(CodingErrorAction.REPORT);
    CharBuffer decoded = decoder.decode(ByteBuffer.wrap(fileContent));
    return decoded.toString();
  }
}
