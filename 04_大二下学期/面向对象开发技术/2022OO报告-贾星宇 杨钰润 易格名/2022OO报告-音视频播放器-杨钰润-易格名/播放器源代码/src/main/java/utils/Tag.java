package utils;

import com.sun.xml.internal.bind.v2.runtime.output.Encoded;
import com.sun.xml.internal.fastinfoset.Encoder;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v23Tag;

import java.io.File;
import java.io.IOException;

public class Tag {
    public static void main(String[] args) throws TagException, IOException, InvalidAudioFrameException, CannotReadException, ReadOnlyFileException {
        MP3File f = (MP3File) AudioFileIO.read(new File("D:\\Audio\\Audio\\爱我没差.mp3"));
        ID3v23Tag tag;
        AbstractID3v2Tag tagV2 = f.getID3v2Tag();
        if (tagV2 instanceof ID3v23Tag) {
            tag = (ID3v23Tag) tagV2;
        } else {
            tag = new ID3v23Tag(tagV2);
        }
     String s ="\t\t作词：黄凌嘉 作曲：周杰伦\n" +
             "\n" +
             "\n" +
             "\n" +
             "\n" +
             "\t\t没有圆周的钟失去旋转意义\n" +
             "\t\t下雨这天好安静\n" +
             "\t\t远行没有目的距离不是问题\n" +
             "\t\t不爱了是你的谜底\n" +
             "\n" +
             "\t\t我占据格林威治守候着你\n" +
             "\t\t在时间标准起点回忆过去\n" +
             "\t\t你却在永夜了的极地旅行\n" +
             "\t\t等爱在失温后渐渐死去\n" +
             "\n" +
             "\t\t喔「对不起」 这句话打乱了时区\n" +
             "\t\t喔你要我在最爱的时候睡去\n" +
             "\t\t我越想越清醒\n" +
             "\t\t\n" +
             "\t\t喔爱你没差那一点时差喔～\n" +
             "\t\t你离开这一拳给的太重\n" +
             "\t\t我的心找不到换日线它在哪\n" +
             "\t\t我只能不停的飞\n" +
             "\t\t直到我将你挽回\n" +
             "\n" +
             "\t\t爱你不怕那一点时差喔～\n" +
             "\t\t就让我静静一个人出发\n" +
             "\t\t你的心总有个经纬度会留下\n" +
             "\t\t我会回到你世界\n" +
             "\t\t跨越爱的时差\n" +
             "\n" +
             "\t\t没有圆周的钟失去旋转意义\n" +
             "\t\t下雨这天好安静\n" +
             "\t\t远行没有目的距离不是问题\n" +
             "\t\t不爱了是你的谜底\n" +
             "\t\t\n" +
             "\t\t我占据格林威治守候着你\n" +
             "\t\t在时间标准起点回忆过去\n" +
             "\t\t你却在永夜了的极地旅行\n" +
             "\t\t等爱在失温后渐渐死去\n" +
             "\n" +
             "\t\t喔「对不起」 这句话打乱了时区\n" +
             "\t\t喔你要我在最爱的时候睡去\n" +
             "\t\t我越想越清醒\n" +
             "\t\t\n" +
             "\t\t喔爱你没差那一点时差喔～\n" +
             "\t\t你离开这一拳给的太重\n" +
             "\t\t我的心找不到换日线它在哪\n" +
             "\t\t我只能不停的飞\n" +
             "\t\t直到我将你挽回\n" +
             "\n" +
             "\t\t那一点时差喔～\n" +
             "\t\t就让我静静一个人出发\n" +
             "\t\t你的心总有个经纬度会留下\n" +
             "\t\t我会回到你世界\n" +
             "\t\t跨越爱的时差";
        tag.setField(FieldKey.LYRICS,s);
        f.setID3v2Tag(tag);
        f.save();

    }
}
