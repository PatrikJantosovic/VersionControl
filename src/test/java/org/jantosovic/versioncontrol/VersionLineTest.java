package org.jantosovic.versioncontrol;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.jantosovic.versioncontrol.api.VersionLine;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class VersionLineTest {

  static Stream<String> PackageVersionLineTest() {
    return Stream.of("21.02.2020  2.0  jantosovic       [XXXX67X0-0X0X0X0] THIS is Also Task"
        , "17.03.2021 25.0  jantosovic       [XXXX67X0-0X0X0X1] THIS is another Task"
        , "12.03.2021 133.0  jantosovic      [XXXX67X0-0X0X0X2] THIS is not Task"
        , "31.08.2020 128.12 jantosovic      [XXXX67X0-0X0X0X3] THIS is yey Task"
    );
  }

  @ParameterizedTest
  @MethodSource
  public void PackageVersionLineTest(String line){
    var versionLine = new VersionLine(line, 0);
    var constructedVersionLine = versionLine.ConstructVersionLine();
    assertThat(constructedVersionLine)
        .isEqualTo(line);
  }

  static Stream<String> ViewVersionLineTest() {
    return Stream.of("19.02.2015  5  jantosovic       [XXXX67X0-0X0X0X2] task"
    );
  }

  @ParameterizedTest
  @MethodSource
  public void ViewVersionLineTest(String line){
    var versionLine = new VersionLine(line, 0);
    var constructedVersionLine = versionLine.ConstructVersionLine();
    assertThat(constructedVersionLine)
        .isEqualTo(line);
  }

}
