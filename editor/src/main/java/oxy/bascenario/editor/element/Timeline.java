package oxy.bascenario.editor.element;

import imgui.*;
import lombok.Setter;
import oxy.bascenario.utils.FontUtils;

// Shit code but whatever.
public class Timeline {
    private static final long DEFAULT_MAX_TIME = 15000; // 15 seconds

    @Setter
    private boolean playing = true;

    private float scrollValue, scaleValue = 1;
    private long timestamp, since;

    private ImFont bigTimestampFont;
    public void init() {
        this.bigTimestampFont = FontUtils.getImFont("NotoSansSemiBold", 20);
    }

    public void render() {
        if (since == 0) {
            since = System.currentTimeMillis();
        }
        if (playing) {
            timestamp += System.currentTimeMillis() - since;
            since = System.currentTimeMillis();
        }
//        if (ImGui.getIO().getKeyCtrl()) {
//            System.out.println("Ctrl!");
//        }

        if (timestamp >= (scrollValue + 1) * DEFAULT_MAX_TIME * scaleValue) {
            scrollValue++;
        } /*else if (timestamp <= scrollValue * defaultMaxTime) {
            scrollValue--;
        }*/

        if (ImGui.getIO().getMouseDown(0)) {
            onMouseDown(ImGui.getIO().getMousePos());
        }

        ImGui.begin("Timeline");
        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();
        drawTimelineCursor(size.x / 4, pos, size);
        drawElapsedTime(size.x / 4, pos, size);
        drawTimelineTimeSegments(size.x / 4, pos, size);
        ImGui.end();
    }

    private void drawTimelineTimeSegments(float timelineManagerWidth, ImVec2 pos, ImVec2 size) {
        final ImDrawList drawList = ImGui.getWindowDrawList();

        for (int i = 0; i <= 5; i++) {
            ImGui.pushFont(this.bigTimestampFont);
            long time = (long) ((DEFAULT_MAX_TIME * scaleValue * scrollValue) + (DEFAULT_MAX_TIME * scaleValue * (i / 5f)));
            float segmentX = timestampToPosition(time, timelineManagerWidth + pos.x, size.x - timelineManagerWidth);

            drawList.addRect(new ImVec2(segmentX, pos.y), new ImVec2(segmentX + 0.5f, pos.y + size.y), ImColor.rgb(50, 50, 50));
            drawList.addText(segmentX + 5, pos.y + 30, ImColor.rgb(255, 255, 255), format(time));

            ImGui.popFont();
        }
    }

    private void drawElapsedTime(float timelineManagerWidth, ImVec2 pos, ImVec2 size) {
        final ImDrawList drawList = ImGui.getWindowDrawList();

        drawList.addRectFilled(new ImVec2(pos.x, pos.y), new ImVec2(pos.x + timelineManagerWidth, pos.y + (float) 50.0), ImColor.rgb(50, 50, 50));
        drawList.addRect(new ImVec2(pos.x, pos.y), new ImVec2(pos.x + size.x, pos.y + (float) 50.0), ImColor.rgb(50, 50, 50));
        ImGui.pushFont(FontUtils.getImFont("NotoSansSemiBold", 30));
        String elapedText = format(timestamp);
        drawList.addText(pos.x + 20, pos.y + 21, ImColor.rgb(255, 255, 255), elapedText);
        ImGui.popFont();
    }

    private void drawTimelineCursor(float timelineManagerWidth, ImVec2 pos, ImVec2 size) {
        final ImDrawList drawList = ImGui.getWindowDrawList();

        final float cursorX = timestampToPosition(timestamp, timelineManagerWidth + pos.x, size.x - timelineManagerWidth);
        drawList.addRectFilled(new ImVec2(pos.x, pos.y), new ImVec2(pos.x + timelineManagerWidth, pos.y + size.y), ImColor.rgb(25, 25, 25));
        drawList.addRect(new ImVec2(cursorX, pos.y), new ImVec2(cursorX + 1, pos.y + size.y), ImColor.rgb(255, 255, 255));
    }

    private void onMouseDown(ImVec2 vec2) {
        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();
        if (vec2.x >= pos.x && vec2.x <= pos.x + size.x && vec2.y >= pos.y && vec2.y <= pos.y + size.y) {
            // Tis is broken.
//                final float ratio = (vec2.x - pos.x) / size.x;
//                System.out.println(timestamp + "," + (ratio * defaultMaxTime * scrollValue));
//                timestamp = (long) (ratio * defaultMaxTime);
        }
    }

    private float timestampToPosition(long timestamp, float offsetX, float size) {
        return offsetX + ((timestamp - (Timeline.DEFAULT_MAX_TIME * scaleValue * scrollValue)) / (Timeline.DEFAULT_MAX_TIME * scaleValue)) * size;
    }

    private static String format(long ms) {
        long millis = ms % 1000;
        long second = (ms / 1000) % 60;
        long minute = (ms / (1000 * 60)) % 60;
        long hour = (ms / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
    }
}
