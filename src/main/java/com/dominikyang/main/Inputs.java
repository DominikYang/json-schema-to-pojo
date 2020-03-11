package com.dominikyang.main;

import org.kohsuke.args4j.Option;

/**
 * @author Wei yuyaung
 * @date 2020.02.25 21:57
 */
public class Inputs {
    //json文件在宿主机的位置
    @Option(name = "-srcPath", required = true, usage = "json文件在宿主机的位置")
    private String jsonPath;
    //生成的文件输出到宿主机的位置
    @Option(name = "-outPath", required = true, usage = "生成的文件输出到宿主机的位置")
    private String destPath;
    //生成的包名
    @Option(name = "-packageNameVO", required = true, usage = "生成的文件输出到宿主机的位置")
    private String packageNameVO;

    @Option(name = "-packageNameDTO", required = true, usage = "生成的文件输出到宿主机的位置")
    private String packageNameDTO;

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    public String getPackageNameVO() {
        return packageNameVO;
    }

    public void setPackageNameVO(String packageNameVO) {
        this.packageNameVO = packageNameVO;
    }

    public String getPackageNameDTO() {
        return packageNameDTO;
    }

    public void setPackageNameDTO(String packageNameDTO) {
        this.packageNameDTO = packageNameDTO;
    }
}
