package de.baumato.loc.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.base.Charsets;
import com.google.common.io.MoreFiles;

public class CharsetTryingFileReaderTest {

  @Rule public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void shouldReadUtf8File() throws Exception {
    File file = folder.newFile();
    String expected = "Hello\nWorld!\n0815\nxyz";
    writeString(file, expected, Charsets.UTF_8);
    String actual = new CharsetTryingFileReader(file.toPath()).read();
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void shouldReadIso88591File() throws Exception {
    File file = folder.newFile();
    String expected = "Hello\nWorld!\näöü\nxyz";
    writeString(file, expected, Charsets.ISO_8859_1);

    // ensure that reading with UTF-8 fails
    Throwable t = catchThrowable(() -> readString(file, Charsets.UTF_8));
    assertThat(t).isInstanceOf(MalformedInputException.class);

    // ensure that CharsetTryingFileReader usues the correct charset
    String actual = new CharsetTryingFileReader(file.toPath()).read();
    assertThat(actual).isEqualTo(expected);
  }

  private static void writeString(File file, String s, Charset c) throws IOException {
    // TODO replace in java 11 with Files.writeString
    MoreFiles.asCharSink(file.toPath(), c).write(s);
  }

  private static String readString(File file, Charset c) throws IOException {
    // TODO replace in java 11 with Files.readString
    return decode(Files.readAllBytes(file.toPath()), c);
  }

  private static String decode(byte[] fileContent, Charset charset)
      throws CharacterCodingException {
    CharsetDecoder decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT);
    CharBuffer decoded = decoder.decode(ByteBuffer.wrap(fileContent));
    return decoded.toString();
  }
}
