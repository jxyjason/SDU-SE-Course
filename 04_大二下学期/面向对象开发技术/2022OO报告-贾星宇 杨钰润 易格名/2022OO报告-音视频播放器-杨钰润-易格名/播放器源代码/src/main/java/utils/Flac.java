package utils;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;

import java.io.File;
import java.io.IOException;

public class Flac {
    public static void main(String[] args) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, CannotWriteException {
        AudioFile audioFile= AudioFileIO.read(new File("D:\\Audio\\Audio\\willow.flac"));
        System.out.println(audioFile.toString());
        audioFile.getTag().setField(FieldKey.LYRICS,"[00:00.000] 作词 : Taylor Swift/Aaron Dessner\n" +
                "[00:01.000] 作曲 : Taylor Swift/Aaron Dessner\n" +
                "[00:11.969]I'm like the water when your ship rolled in that night\n" +
                "[00:11.969]我就像那夜令你的船儿颠簸的波浪\n" +
                "[00:17.646]Rough on the surface, but you cut through like a knife\n" +
                "[00:17.646]汹涌在表象 而你却如利刃般劈风斩浪\n" +
                "[00:23.944]And if it was an open-shut case\n" +
                "[00:23.944]若一切都能一目了然\n" +
                "[00:26.831]I never would've known from the look on your face\n" +
                "[00:26.831]我根本无从分辨你脸上的表情\n" +
                "[00:29.799]Lost in your current like a priceless wine\n" +
                "[00:29.799]当即迷失在你如佳酿般的魅力之下\n" +
                "[00:35.450]The more that you say, the less I know\n" +
                "[00:35.450]你所言越多 我越觉得不了解你\n" +
                "[00:38.165]Wherever you stray, I follow\n" +
                "[00:38.165]无论你漂泊何处 我亦相随\n" +
                "[00:41.079]I'm begging for you to take my hand\n" +
                "[00:41.079]我只祈求你能牵起我的手\n" +
                "[00:43.937]Wreck my plans, that's my man\n" +
                "[00:43.937]推翻我的人生计划 只为我的意中人\n" +
                "[00:47.016]Life was a willow and it bent right to your wind\n" +
                "[00:47.016]我的生命是一棵柳树 随着你的风向弯曲枝桠\n" +
                "[00:53.405]Head on the pillow, I could feel you sneakin' in\n" +
                "[00:53.405]我枕着枕头 察觉到你偷偷溜进来\n" +
                "[00:59.749]As if you were a mythical thing\n" +
                "[00:59.749]如果你是一件神秘的物品\n" +
                "[01:02.669]Like you were a trophy or a champion ring\n" +
                "[01:02.669]比方说你是一座奖杯或一枚冠军戒指\n" +
                "[01:05.481]And there was one prize I'd cheat to win\n" +
                "[01:05.481]但你一定是我不择手段必须赢得的奖赏\n" +
                "[01:11.110]The more that you say, the less I know\n" +
                "[01:11.110]你所言越多 我越觉得不了解你\n" +
                "[01:13.983]Wherever you stray, I follow\n" +
                "[01:13.983]无论你漂泊何处 我亦相随\n" +
                "[01:16.918]I'm begging for you to take my hand\n" +
                "[01:16.918]我只祈求你能牵起我的手\n" +
                "[01:19.866]Wreck my plans, that's my man\n" +
                "[01:19.866]推翻我的人生计划 只为我的意中人\n" +
                "[01:22.915]You know that my train could take you home\n" +
                "[01:22.915]你知道我就是那趟可以带你回家的火车\n" +
                "[01:25.850]Anywhere else is hollow\n" +
                "[01:25.850]其他任何地方都只是镜花水月一场\n" +
                "[01:28.392]I'm begging for you to take my hand\n" +
                "[01:28.392]我只祈求你能牵起我的手\n" +
                "[01:31.180]Wreck my plans, that's my man\n" +
                "[01:31.180]推翻我的人生计划 只为我的意中人\n" +
                "[01:37.861]Life was a willow and it bent right to your wind\n" +
                "[01:37.861]我的生命是一棵柳树 随着你的风向弯曲枝桠\n" +
                "[01:43.480]They count me out time and time again\n" +
                "[01:43.480]他们一次次地将我排除在外\n" +
                "[01:49.235]Life was a willow and it bent right to your wind\n" +
                "[01:49.235]我的生命是一棵柳树 随着你的风向弯曲枝桠\n" +
                "[01:55.331]But I come back stronger than a '90s trend\n" +
                "[01:55.331]而我的回归远盛于90年代的潮流\n" +
                "[01:59.308]Wait for the signal, and I'll meet you after dark\n" +
                "[01:59.308]耐心等待信号 我会在天黑后与你相会\n" +
                "[02:04.909]Show me the places where the others gave you scars\n" +
                "[02:04.909]给我看看其他人给你留下的疤痕\n" +
                "[02:11.161]Now this is an open shut case\n" +
                "[02:11.161]现在一切都一目了然了\n" +
                "[02:13.931]I guess I should've known from the look on your face\n" +
                "[02:13.931]我想我从你的表情就能看出来\n" +
                "[02:16.939]Every bait-and-switch was a work of art\n" +
                "[02:16.939]每场诱饵游戏都是精妙的艺术\n" +
                "[02:22.617]The more that you say, the less I know\n" +
                "[02:22.617]你所言越多 我越觉得不了解你\n" +
                "[02:25.380]Wherever you stray, I follow\n" +
                "[02:25.380]无论你走到哪里 我都跟着你\n" +
                "[02:28.350]I'm begging for you to take my hand\n" +
                "[02:28.350]我只祈求你能牵起我的手\n" +
                "[02:31.139]Wreck my plans, that's my man\n" +
                "[02:31.139]推翻我的人生计划 只为我的意中人\n" +
                "[02:34.131]You know that my train could take you home\n" +
                "[02:34.131]你知道我就是那趟可以带你回家的火车\n" +
                "[02:36.899]Anywhere else is hollow\n" +
                "[02:36.899]其他任何地方都只是镜花水月一场\n" +
                "[02:39.676]I'm begging for you to take my hand\n" +
                "[02:39.676]我只祈求你能牵起我的手\n" +
                "[02:42.611]Wreck my plans, that's my man\n" +
                "[02:42.611]推翻我的人生计划 只为我的意中人\n" +
                "[02:46.207]The more that you say, the less I know\n" +
                "[02:46.207]你所言越多 我越觉得不了解你\n" +
                "[02:48.370]Wherever you stray, I follow\n" +
                "[02:48.370]无论你走到哪里 我都跟着你\n" +
                "[02:51.209]I'm begging for you to take my hand\n" +
                "[02:51.209]我只祈求你能牵起我的手\n" +
                "[02:54.034]Wreck my plans, that's my man\n" +
                "[02:54.034]推翻我的人生计划 只为我的意中人\n" +
                "[02:57.006]You know that my train could take you home\n" +
                "[02:57.006]你知道我就是那趟可以带你回家的火车\n" +
                "[02:59.810]Anywhere else is hollow\n" +
                "[02:59.810]其他任何地方都只是镜花水月一场\n" +
                "[03:02.714]I'm begging for you to take my hand\n" +
                "[03:02.714]我只祈求你能牵起我的手\n" +
                "[03:05.562]Wreck my plans, that's my man\n" +
                "[03:05.562]推翻我的人生计划 只为我的意中人\n" +
                "[03:09.932]That's my man\n" +
                "[03:09.932]只为我的意中人\n" +
                "[03:12.683]That's my man\n" +
                "[03:12.683]只为我的意中人\n" +
                "[03:15.519]That's my man\n" +
                "[03:15.519]只为我的意中人\n" +
                "[03:16.992]Every bait-and-switch was a work of art\n" +
                "[03:16.992]每场诱饵游戏都是精妙的艺术\n" +
                "[03:20.230]That's my man\n" +
                "[03:20.230]只为我的意中人\n" +
                "[03:22.701]That's my man\n" +
                "[03:22.701]只为我的意中人\n" +
                "[03:25.593]I'm begging for you to take my hand\n" +
                "[03:25.593]我只祈求你能牵起我的手\n" +
                "[03:28.264]Wreck my plans, that's my man\n" +
                "[03:28.264]推翻我的人生计划 只为我的意中人\n");
        audioFile.commit();
        System.out.println("标题:" + audioFile.getTag().getFirst(FieldKey.TITLE));
        System.out.println("作者:" + audioFile.getTag().getFirst(FieldKey.ARTIST));
        System.out.println("专辑:" + audioFile.getTag().getFirst(FieldKey.ALBUM));
        System.out.println("歌词"+audioFile.getTag().getFirst(FieldKey.LYRICS));
        System.out.println("比特率:" + audioFile.getAudioHeader().getBitRate());
        System.out.println("时长:(" + audioFile.getAudioHeader().getTrackLength() + "s)");
        System.out.println("大小:" + audioFile.getFile().length() + " B");
        System.out.println("文件名称:" + audioFile.getBaseFilename(audioFile.getFile()));

    }
}
