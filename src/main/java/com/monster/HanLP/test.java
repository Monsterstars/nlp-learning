package com.monster.HanLP;

import org.apache.log4j.*;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @auther: Monster
 * @date: 2019/11/9
 * @description:
 */
public class test {

    public static void main(String[] args) throws Exception{
        final String text = readFile.getTrainingData("./data/text.txt");
        List<Term> termList;

        termList = HanLP.segment(text);
        System.out.println("标准分词");
        System.out.println(termList);

        termList = IndexTokenizer.segment(text);
        System.out.println("索引分词");
        for (Term term : termList){
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }

        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        termList = segment.seg(text);
        System.out.println("中国人名识别");
        System.out.println(getName(termList));

        Segment segment1 = HanLP.newSegment().enableTranslatedNameRecognize(true);
        termList = segment1.seg(text);
        System.out.println("音译识别名字");
        System.out.println(getTranslatedName(termList));

        Segment areasegment = HanLP.newSegment().enablePlaceRecognize(true);
        termList = areasegment.seg(text);
        System.out.println("地名识别");
        System.out.println(getPlace(termList));

        //创建一个词语解析器,类似于分词
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        //这边要注意,引用了中文的解析器
        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());

        //拿到文档里面分出的词,和词频,建立一个集合存储起来
        List<WordFrequency> wordFrequencies = frequencyAnalyzer.load("./data/text.txt");


        Dimension dimension = new Dimension(600, 600);

        //设置图片相关的属性,这边是大小和形状,更多的形状属性,可以从CollisionMode源码里面查找
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);

        //这边要注意意思,是设置中文字体的,如果不设置,得到的将会是乱码,
        //这是官方给出的代码没有写的,我这边拓展写一下,字体,大小可以设置
        //具体可以参照Font源码
        java.awt.Font font = new java.awt.Font("STSong-Light", 2, 16);
        wordCloud.setKumoFont(new KumoFont(font));
        wordCloud.setBackgroundColor(new Color(255, 255, 255));
        //因为我这边是生成一个圆形,这边设置圆的半径
        wordCloud.setBackground(new CircleBackground(255));
        //设置颜色
        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
        //将文字写入图片
        wordCloud.build(wordFrequencies);
        //生成图片
        wordCloud.writeToFile("./target/chinese_language_circle.png");
    }

    public static List<String> getName(List<Term> terms) {
        List<String> nameList = new ArrayList<String>();
        for (int i = terms.size() - 1; i >= 0; i--) {
            String word = terms.get(i).word;
            String nature = terms.get(i).nature.toString();
            if (nature == "nr") {
                nameList.add(word);
            }
        }
        return nameList;
    }

    public static List<String> getTranslatedName(List<Term> terms){
        List<String> nameList = new ArrayList<String>();
        for (int i = terms.size() - 1; i >= 0; i--) {
            String word = terms.get(i).word;
            String nature = terms.get(i).nature.toString();
            if (nature == "nrf") {
                nameList.add(word);
            }
        }
        return nameList;
    }

    public static List<String> getPlace(List<Term> terms){
        List<String> nameList = new ArrayList<String>();
        for (int i = terms.size() - 1; i >= 0; i--) {
            String word = terms.get(i).word;
            String nature = terms.get(i).nature.toString();
            if (nature == "ns") {
                nameList.add(word);
            }
        }
        return nameList;
    }
}