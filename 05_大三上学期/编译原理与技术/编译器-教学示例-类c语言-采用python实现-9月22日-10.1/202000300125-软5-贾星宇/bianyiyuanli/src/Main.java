import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //读取文件
        String content = new String();
        File file = new File("D:\\dasanshangxueqi\\bianyiyuanli\\src\\main.txt");
        Reader reader = null;
        try {
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    content += (char) tempchar;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Error.text = content;
        //词法分析
        Lexer lexer = new Lexer(content, 0);
//        boolean ifERR = false;
//        while (true){
//            lexer.getTokenList().add(lexer.getNextToken());
//            if (lexer.getTokenList().get(lexer.getTokenList().size()-1).type.equals("TK_ERROR")){
//                ifERR = true;
//                break;
//            }
//            if (lexer.getTokenList().get(lexer.getTokenList().size()-1).type.equals("EOF"))break;
//        }
//
//        if (!ifERR){
//            for (Token token : lexer.getTokenList()){
//                System.out.println(token.type+"             "+token.value+" ,line: "+token.line+" ,column: "+token.column);
//            }
//        }

        //语法分析
        Parser parser = new Parser(lexer);
        List<AST_Node> ast_nodes = parser.program();
//        System.out.println("parse successfully!");

        //语义分析
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.semantic_analyze(ast_nodes);
//        System.out.println("semantic analyze successfully!");

        //代码生成
        Codegenerator codegenerator = new Codegenerator();
        codegenerator.code_generate(ast_nodes);


    }
}