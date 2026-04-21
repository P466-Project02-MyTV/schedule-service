package p466.team2.scheduleservice.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Utility {
    public static String getDurationFromLink(String videoUrl) throws IOException {
        String streamUrl = executeCommand("yt-dlp", "-g", "-f", "best", videoUrl);

        return executeCommand("ffprobe", "-v", "error",
                "-show_entries", "format=duration",
                "-of", "default=noprint_wrappers=1:nokey=1",
                streamUrl);
    }

    public static String getTitleFromLink(String videoUrl) throws IOException {
        return executeCommand("yt-dlp", "--get-title", videoUrl);
    }

    public static String executeCommand(String... command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.readLine();
        }
    }
}
