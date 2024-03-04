package Teacher;

import Student.AnswerConn;

import java.sql.*;

public class TestNewLabel {
    private AnswerConn answerConn = null;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;



    public TestNewLabel() throws SQLException {
        newTable("Jason");
    }

    public void newTable(String name) throws SQLException {
        answerConn = new AnswerConn();
        conn = answerConn.getConnection();
        stmt = conn.createStatement();
        String sql = "CREATE TABLE `studentanswer`.`"+name+"` (\n" +
                "  `id` INT NOT NULL,\n" +
                "  `type` INT NULL,\n" +
                "  `question` TEXT(100) NULL,\n" +
                "  `answer` TEXT(100) NULL,\n" +
                "  `grade` TEXT(100) NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8\n" +
                "COLLATE = utf8_bin";
        stmt.executeUpdate(sql);

    }

    public static void main(String[] args) throws SQLException {
        new TestNewLabel();
    }

}
