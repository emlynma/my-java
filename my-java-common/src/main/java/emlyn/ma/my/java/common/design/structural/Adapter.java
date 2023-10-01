package emlyn.ma.my.java.common.design.structural;

public class Adapter {

    // 目标接口
    interface MediaPlayer {
        void play(String audioType, String filename);
    }

    // 被适配者
    static class AdvancedMediaPlayer {
        public void playVlc(String filename) {
            System.out.println("Playing VLC file: " + filename);
        }

        public void playMp4(String filename) {
            System.out.println("Playing MP4 file: " + filename);
        }
    }

    // 适配器
    static class MediaAdapter implements MediaPlayer {
        private AdvancedMediaPlayer advancedMediaPlayer;

        public MediaAdapter(String audioType) {
            if (audioType.equalsIgnoreCase("vlc")) {
                advancedMediaPlayer = new AdvancedMediaPlayer();
            }
        }

        @Override
        public void play(String audioType, String filename) {
            if (audioType.equalsIgnoreCase("vlc")) {
                advancedMediaPlayer.playVlc(filename);
            } else {
                System.out.println("Invalid media type: " + audioType);
            }
        }
    }

    // 客户端代码
    public static void main(String[] args) {
        MediaPlayer mediaPlayer = new MediaAdapter("vlc");
        mediaPlayer.play("vlc", "movie.vlc");
    }
}
