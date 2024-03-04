package utils;

import com.mpatric.mp3agic.*;
import models.Song;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;

import java.beans.Encoder;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MusicUtil {
    public static Song song = new Song();
    public static void getSong(String ml) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        AudioFile audioFile= AudioFileIO.read(new File(ml));
        song.setTitle( audioFile.getTag().getFirst(FieldKey.TITLE));
        song.setSinger(audioFile.getTag().getFirst(FieldKey.ARTIST));
        song.setAlbum(audioFile.getTag().getFirst(FieldKey.ALBUM));
        song.setLyrics(audioFile.getTag().getFirst(FieldKey.LYRICS));
        if (ml.contains("mp3")){
            MP3File mp3file = new MP3File(new File(ml));
            AbstractID3v2Tag tag;
            tag = mp3file.getID3v2Tag();
            AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
            FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
            song.setPic(body.getImageData());
        }
    }
}
