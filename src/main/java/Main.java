import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.kohsuke.args4j.CmdLineParser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wei yuyaung
 * @date 2020.02.25 22:06
 */
public class Main {
    public static void main(String[] args) throws Exception {
        //当前项目下路径
        File tempFile = new File("");
        String localOutputPath = tempFile.getCanonicalPath() + "\\src\\main\\java";


        //获取输入的路径
        String jsonPath = null;
        String destPath = null;
        String packageNameVO = null;
        String packageNameDTO = null;
        Inputs inputs = new Inputs();
        CmdLineParser parser = new CmdLineParser(inputs);
        try {
            parser.parseArgument(args);
            jsonPath = inputs.getJsonPath();
            destPath = inputs.getDestPath();
            packageNameVO = inputs.getPackageNameVO();
            packageNameDTO = inputs.getPackageNameDTO();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        //list所有path目录下的文件名
        List<String> methodNames = listDirectories(jsonPath);
        //for循环访问每个子目录
        for (String s :
                methodNames) {
            File file = new File(jsonPath + "/" + s);
            File[] jsonSchemas = file.listFiles();
            //将request.json 改名为 methodVO response改为 methodDTO
            //生成类文件到指定位置
            for (File f :
                    jsonSchemas) {
                if (f.getName().equals("request.json")) {
                    String className = upperFirst(s.toLowerCase()) + "VO";
                    generateClasses(jsonPath + "/" + s + "/" + f.getName(), packageNameVO, className, localOutputPath);
                } else if (f.getName().equals("response.json")) {
                    String className = upperFirst(s.toLowerCase()) + "DTO";
                    generateClasses(jsonPath + "/" + s + "/" + f.getName(), packageNameDTO, className, localOutputPath);
                } else {
                    throw new Exception("error json file name");
                }
            }
        }

    }

    private static boolean generateClasses(String filePath, String packageName, String className, String destPath) throws MalformedURLException {
        JCodeModel codeModel = new JCodeModel();

        //URL source = Main.class.getResource(filePath);
        URL source = new URL("file:/" + filePath);

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() { // set config option by overriding method
                return true;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(), new SchemaStore()), new SchemaGenerator());
        try {
            mapper.generate(codeModel, className, packageName, source);
            codeModel.build(new File(destPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static List<String> listDirectories(String path) {
        List<String> methodNames = new ArrayList<>();
        File file = new File(path);
        File[] fs = file.listFiles();
        for (File files :
                fs) {
            if (files.isDirectory()) {
                methodNames.add(files.getName());
            }
        }
        return methodNames;
    }

    /**
     * description: 首字母大写
     *
     * @return:
     * @author: Wei Yuyang
     * @time: 2020.02.24
     */
    private static String upperFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
