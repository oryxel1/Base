package oxy.bascenario.editor.element;

import imgui.*;
import lombok.Getter;
import lombok.Setter;
import oxy.bascenario.utils.FontUtils;

// Shit code but whatever.
public class Timeline {
    private static final long DEFAULT_MAX_TIME = 15000; // 15 seconds

    @Setter @Getter
    private boolean playing;

    @Getter
    private float scroll = 0, scale = 1;
    @Setter @Getter
    private long timestamp;
    private long since;

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

        scroll = Math.max(0, scroll);

        if (ImGui.getIO().getKeyCtrl()) {
            final float scroll = ImGui.getIO().getMouseWheel();
            if (scroll > 0) {
                this.scale += scroll;
            } else if (scroll < 0) {
                if (this.scale > Math.abs(scroll)) {
                    this.scale += scroll;
                } else {
                    this.scale *= (Math.abs(scroll) * 0.9f);
                }
            }
        }

        if (timestamp != 0 && timestamp >= (scroll + 1) * DEFAULT_MAX_TIME * scale) {
            scroll++;
        } else if (timestamp != 0 && timestamp <= DEFAULT_MAX_TIME * scroll * scale) {
            scroll--;
        }

        ImGui.begin("Timeline");
        if (ImGui.getIO().getMouseDown(0)) {
            onMouseDown(ImGui.getIO().getMousePos());
        }

        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();

        ImGui.getWindowDrawList().addRectFilled(new ImVec2(pos.x, pos.y), new ImVec2(pos.x + (size.x / 4), pos.y + size.y), ImColor.rgb(25, 25, 25));
        drawTimelineCursor(size.x / 4, pos, size);
        drawElapsedTime(size.x / 4, pos, size);
        drawTimelineTimeSegments(size.x / 4, pos, size);

        ImGui.end();
    }

    private void drawTimelineTimeSegments(float timelineManagerWidth, ImVec2 pos, ImVec2 size) {
        final ImDrawList drawList = ImGui.getWindowDrawList();

        for (int i = 0; i <= 5; i++) {
            ImGui.pushFont(this.bigTimestampFont);
            long time = (long) ((DEFAULT_MAX_TIME * scale * scroll) + (DEFAULT_MAX_TIME * scale * (i / 5f)));
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
        drawList.addText(pos.x + 20, pos.y + 21, ImColor.rgb(255, 255, 255), format(timestamp));
        ImGui.popFont();
    }

    private void drawTimelineCursor(float timelineManagerWidth, ImVec2 pos, ImVec2 size) {
        final ImDrawList drawList = ImGui.getWindowDrawList();

        final float cursorX = timestampToPosition(timestamp, timelineManagerWidth + pos.x, size.x - timelineManagerWidth);
        if (cursorX <= timelineManagerWidth + pos.x) {
            return;
        }

        drawList.addRect(new ImVec2(cursorX, pos.y), new ImVec2(cursorX + 1, pos.y + size.y), ImColor.rgb(255, 255, 255));
    }

    private void onMouseDown(ImVec2 vec2) {
        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();

        if (vec2.x < pos.x || vec2.x > pos.x + size.x || vec2.y < pos.y || vec2.y > pos.y + size.y) {
            return;
        }

        if (vec2.x > pos.x + size.x - 20) {
            scroll += 0.001f;
        }

        if (vec2.x < pos.x + size.x / 4) {
            final float distance = pos.x + size.x / 4 - vec2.x;
            final float ratio = distance / (size.x - size.x / 4);
            final long backtrackTime = (long) (ratio * (DEFAULT_MAX_TIME * scale * 0.1));
            timestamp = Math.max(0, timestamp - backtrackTime);
            return;
        }

        final float ratio = (vec2.x - pos.x - size.x / 4) / (size.x - size.x / 4);
        timestamp = (long) (DEFAULT_MAX_TIME * scale * scroll + ratio * DEFAULT_MAX_TIME * scale);
    }

    private float timestampToPosition(long timestamp, float offsetX, float size) {
        return offsetX + ((timestamp - (Timeline.DEFAULT_MAX_TIME * scale * scroll)) / (Timeline.DEFAULT_MAX_TIME  * scale)) * size;
    }

    private static String format(long ms) {
        long millis = ms % 1000;
        long second = (ms / 1000) % 60;
        long minute = (ms / (1000 * 60)) % 60;
        long hour = (ms / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
    }
}
