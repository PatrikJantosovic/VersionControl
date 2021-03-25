package org.jantosovic.versioncontrol;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import java.util.ArrayList;
import org.jantosovic.versioncontrol.api.SourceFile;
import org.junit.Test;

public class SourceFileTest {

  @Test
  public void CalculateMajorChangeTest() {
    var sourceFile = new SourceFile(Paths.get("C:\\Repo\\server\\xxx\\xxx_package_pg.pkb"));
    var sourceFileFail = new SourceFile(Paths.get("C:\\Repo\\server\\xxx\\xxx_packageyx_pg.pkb"));
    var allFiles =  new ArrayList<String>(4);
    allFiles.add("xxx\\xxx_package_pg.pks");
    allFiles.add("xxx_view_detail_dw.vw");
    allFiles.add("xxx\\xxx_packagexy_pg.pkb");
    allFiles.add("xxx\\xxx_packagexy_pg.pks");
    assertThat(sourceFile.CalculateMajorChange(allFiles))
        .isEqualTo(true);
    assertThat(sourceFileFail.CalculateMajorChange(allFiles))
        .isEqualTo(false);
  }

}
