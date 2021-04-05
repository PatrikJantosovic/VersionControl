package org.jantosovic.versioncontrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.jantosovic.versioncontrol.api.FileAccessor;
import org.jantosovic.versioncontrol.api.VersionLine;
import org.junit.Test;

public class FileAccessorTest {

  String source = ""
      + "CREATE OR REPLACE PACKAGE BODY XXX.XXX_Package_XX\r\n"
      + "IS\r\n"
      + "\r\n"
      + "FUNCTION mfw_GetVersion\r\n"
      + "  RETURN VARCHAR2\r\n"
      + "IS\r\n"
      + "/*\r\n"
      + "10.07.2001  1.0  ------           Task comment\r\n"
      + "15.07.2002  1.1  jantosovic       [XXXXXXX0-0X0X0X0] Another Task Comment\r\n"
      + "21.02.2020  2.0  jantosovic       [XXXX67X0-0X0X0X0] THIS is Also Task\r\n"
      + "*/\r\n"
      + "BEGIN\r\n"
      + "  RETURN '2.0.0.0.21-02-2020';\r\n"
      + "END;\r\n"
      + "\r\n"
      + "FUNCTION mfw_GetSomethingFromTable(\r\n"
      + "    p_Object_ID NUMBER)\r\n"
      + "  RETURN VARCHAR2\r\n"
      + "IS\r\n"
      + "  l_Name VARCHAR2(32000);\r\n"
      + "BEGIN\r\n"
      + "  IF (p_Object_ID IS NULL) THEN\r\n"
      + "    RETURN NULL;\r\n"
      + "  END IF\r\n"
      + "  SELECT\r\n"
      + "      name\r\n"
      + "    INTO\r\n"
      + "      l_Name\r\n"
      + "    FROM\r\n"
      + "      xxx_table_xx\r\n"
      + "    WHERE\r\n"
      + "      (object_id=p_Object_ID);\r\n"
      + "  IF (l_Name IS NULL) THEN\r\n"
      + "    l_Name:=g_DefaultName;\r\n"
      + "  END IF;\r\n"
      + "  RETURN l_Name;\r\n"
      + "END;\r\n";

  String viewSource = ""
      + "CREATE OR REPLACE FORCE VIEW XXX.XXX_VIEW_DW AS\r\n"
      + "SELECT\r\n"
      + "/*\r\n"
      + "05.10.2010  1  jantosovic        [XXXXXXX0-0X0X0X0] generated\r\n"
      + "14.01.2011  2  jantosovic        [XXXXXXX0-0X0X0X0] attribute added\r\n"
      + "05.01.2021  3  jantosovic        [XXXXXXX0-0X0X0X0] attribute deleted\r\n"
      + "*/\r\n"
      + "    '3.0.05-01-2021' version\r\n"
      + "  , view.id id\r\n"
      + "FROM\r\n"
      + "    xxx_view_vw view\r\n"
      + "  ;";

  @Test
  public void ParseLatestVersionTest(){
    var fileAccessor = new FileAccessor();
    var parsed = fileAccessor.ParseLatestVersion(source, "XXX_Package_XX.pkb");
    assertThat(parsed)
        .isEqualTo("21.02.2020  2.0  jantosovic       [XXXX67X0-0X0X0X0] THIS is Also Task");

    var viewParsed = fileAccessor.ParseLatestVersion(viewSource, "XXX_VIEW_DW.vw");
    assertThat(viewParsed)
        .isEqualTo("05.01.2021  3  jantosovic        [XXXXXXX0-0X0X0X0] attribute deleted")
        ;
  }

  @Test
  public void EditVersionInfoViewTest() {
    var versionInfo = "    '3.0.05-01-2021' version";
    var versionLine = new VersionLine("05.04.2021  4  jantosovic        [XXXXXXX0-0X0X0X0] attribute deleted", 0);
    var currentVersionInfo = versionLine.EditVersionInfo(versionInfo);
    assertThat(currentVersionInfo)
        .isEqualTo("    '4.0.05-04-2021' version");
  }

  @Test
  public void EditVersionInfoPackageTest() {
    var versionInfo = "  RETURN '2.0.0.0.21-02-2020';";
    var versionLine = new VersionLine("05.04.2021  2.1  jantosovic       [XXXX67X0-0X0X0X0] THIS is Also Task", 0);
    var currentVersionInfo = versionLine.EditVersionInfo(versionInfo);
    assertThat(currentVersionInfo)
        .isEqualTo("  RETURN '2.1.0.0.05-04-2021';");
  }

}
