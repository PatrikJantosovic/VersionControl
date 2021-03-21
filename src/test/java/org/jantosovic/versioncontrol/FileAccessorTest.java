package org.jantosovic.versioncontrol;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import org.jantosovic.versioncontrol.api.FileAccessor;
import org.junit.Test;

public class FileAccessorTest {

  @Test
  public void ParseLatestVersionTest(){
    var source = ""
        + "CREATE OR REPLACE PACKAGE BODY XXX.XXX_Package_XX\n"
        + "IS\n"
        + "\n"
        + "FUNCTION mfw_GetVersion\n"
        + "  RETURN VARCHAR2\n"
        + "IS\n"
        + "/*\n"
        + "10.07.2001  1.0  ------           Task comment\n"
        + "15.07.2002  1.1  jantosovic       [XXXXXXX0-0X0X0X0] Another Task Comment\n"
        + "21.02.2020  2.0  jantosovic       [XXXX67X0-0X0X0X0] THIS is Also Task\n"
        + "*/\n"
        + "BEGIN\n"
        + "  RETURN '2.0.0.0.21-02-2020';\n"
        + "END;\n"
        + "\n"
        + "FUNCTION mfw_GetSomethingFromTable(\n"
        + "    p_Object_ID NUMBER)\n"
        + "  RETURN VARCHAR2\n"
        + "IS\n"
        + "  l_Name VARCHAR2(32000);\n"
        + "BEGIN\n"
        + "  IF (p_Object_ID IS NULL) THEN\n"
        + "    RETURN NULL;\n"
        + "  END IF\n"
        + "  SELECT\n"
        + "      name\n"
        + "    INTO\n"
        + "      l_Name\n"
        + "    FROM\n"
        + "      xxx_table_xx\n"
        + "    WHERE\n"
        + "      (object_id=p_Object_ID);\n"
        + "  IF (l_Name IS NULL) THEN\n"
        + "    l_Name:=g_DefaultName;\n"
        + "  END IF;\n"
        + "  RETURN l_Name;\n"
        + "END;\n";
    var fileAccessor = new FileAccessor(Path.of("X"));
    var parsed = fileAccessor.ParseLatestVersion(source, "XXX_Package_XX.pkb");
    assertThat(parsed)
        .isEqualTo("21.02.2020  2.0  jantosovic       [XXXX67X0-0X0X0X0] THIS is Also Task");

    var viewSource = ""
        + "CREATE OR REPLACE FORCE VIEW XXX.XXX_VIEW_DW AS\n"
        + "SELECT\n"
        + "/*\n"
        + "05.10.2010  1  jantosovic        [XXXXXXX0-0X0X0X0] generated\n"
        + "14.01.2011  2  jantosovic        [XXXXXXX0-0X0X0X0] attribute added\n"
        + "05.01.2021  3  jantosovic        [XXXXXXX0-0X0X0X0] attribute deleted\n"
        + "*/\n"
        + "    '3.0.05-01-2021' version\n"
        + "  , view.id id\n"
        + "FROM\n"
        + "    xxx_view_vw view\n"
        + "  ;";
    var viewParsed = fileAccessor.ParseLatestVersion(viewSource, "XXX_VIEW_DW.vw");
    assertThat(viewParsed)
        .isEqualTo("05.01.2021  3  jantosovic        [XXXXXXX0-0X0X0X0] attribute deleted")
        ;
  }

}
