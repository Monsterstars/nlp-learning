package com.monster.HanLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @auther: Monster
 * @date: 2019/11/9
 * @description:
 */
public class readFile {

    /**
     * 读取训练数据
     * @param dataFilePath
     * @return
     * @throws IOException
     */
    public static String getTrainingData(String dataFilePath) throws IOException {
        File file = new File(dataFilePath);
        String text = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                text = text + tempString;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
