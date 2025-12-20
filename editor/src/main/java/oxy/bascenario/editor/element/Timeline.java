package oxy.bascenario.editor.element;

import imgui.*;
import lombok.Getter;
import lombok.Setter;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.utils.TrackParser;
import oxy.bascenario.utils.FontUtils;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.HashMap;
import java.util.Map;

// Shit code but whatever.
public class Timeline {
    @Getter
    private final ScenarioEditorScreen screen;

    public static final long DEFAULT_MAX_TIME = 15000; // 15 seconds

    @Getter
    private final Map<Integer, Track> tracks;
    @Getter @Setter
    private Object selectedElement;

    public Timeline(ScenarioEditorScreen screen, Scenario.Builder scenario) {
        this.screen = screen;
        if (scenario == null) {
            tracks = new HashMap<>();
        } else {
            tracks = TrackParser.parse(this, scenario.build());
        }
    }

    @Setter @Getter
    private boolean playing;

    @Getter
    private float scroll = 0, scale = 1;
    @Getter
    private int verticalScroll = 0;
    @Setter @Getter
    private long timestamp;
    private long since;

    private ImFont timestampFont, elapsedTimestampFont, trackNameFont;
    public void init() {
        this.timestampFont = FontUtils.getImFont("NotoSansSemiBold", 20);
        this.elapsedTimestampFont = FontUtils.getImFont("NotoSansSemiBold", 30);
        this.trackNameFont = FontUtils.getImFont("NotoSansRegular", 35);


    }

    public void render() {
        if (since == 0) {
            since = System.currentTimeMillis();
        }
        if (playing) {
            timestamp += System.currentTimeMillis() - since;
        }
        since = System.currentTimeMillis();

        scroll = Math.max(0, scroll);

        ImGui.begin("Timeline");
        if (ImGui.getIO().getMouseDown(0)) {
            onMouseDown(ImGui.getIO().getMousePos());
        }
        if (playing || ImGui.getIO().getMouseDown(0)) {
            if (timestamp != 0 && timestamp >= (scroll + 1) * DEFAULT_MAX_TIME * scale) {
                long distance = (long) (timestamp - ((scroll + 1) * DEFAULT_MAX_TIME * scale));
                float ratio = (float) distance / DEFAULT_MAX_TIME;
                scroll += ratio + 0.2f;
            } else if (timestamp != 0 && timestamp <= DEFAULT_MAX_TIME * scroll * scale) {
                long distance = (long) (DEFAULT_MAX_TIME * scroll * scale - timestamp);
                float ratio = (float) distance / DEFAULT_MAX_TIME;
                scroll -= ratio + 0.2f;
            }
        }


        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();
        final ImVec2 mouse = ImGui.getMousePos();
        if (mouse.x >= pos.x && mouse.x <= pos.x + size.x && mouse.y >= pos.y && mouse.y <= pos.y + size.y) {
            final float scroll = ImGui.getIO().getMouseWheel();
            if (mouse.x < pos.x + size.x / 4) {
                this.verticalScroll = Math.max(0, this.verticalScroll - (int)scroll);
            } else if (ImGui.getIO().getKeyCtrl()) {
                if (scroll > 0 || this.scale > Math.abs(scroll)) {
                    this.scale += scroll;
                } else if (scroll < 0) {
                    this.scale *= (Math.abs(scroll) * 0.9f);
                }
            } else {
                this.scroll = Math.max(0, this.scroll + scroll);
            }
        }

        ImGui.getWindowDrawList().addRectFilled(new ImVec2(pos.x, pos.y), new ImVec2(pos.x + (size.x / 4), pos.y + size.y), ImColor.rgb(25, 25, 25));
        drawElapsedTimeSegments(size.x / 4, pos, size);
        drawTimelineSegments(size.x / 4, pos, size);
        drawElapsedTime(size.x / 4, pos, size);
        drawTimelineCursor(size.x / 4, pos, size);

        ImGui.end();
    }

    private void drawTimelineSegments(float timelineManagerWidth, ImVec2 pos, ImVec2 size) {
        final ImDrawList drawList = ImGui.getWindowDrawList();

        float y = pos.y + 80;

//        GLFW.glfwSetCursor(windowHandle, GLFW.glfwCreateStandardCursor(GLFW.GLFW_VRESIZE_CURSOR));

        for (int i = verticalScroll; i <= ((size.y - 80) / 50) + verticalScroll; i++) {
            if (tracks.get(i) == null) {
                tracks.put(i, new Track(this, i));
            }

            drawList.addRectFilled(new ImVec2(pos.x, y), new ImVec2(pos.x + timelineManagerWidth, y + 50), ImColor.rgb(33, 33, 33));

            ImGui.pushFont(this.trackNameFont);
            drawList.addText(new ImVec2(pos.x + 10, y), ImColor.rgb(255, 255, 255), "Track " + i);
            ImGui.popFont();

            drawList.addRect(new ImVec2(pos.x, y), new ImVec2(pos.x + size.x, y + 0.5f), ImColor.rgb(50, 50, 50));

            y += 50;
        }

        y = pos.y + 80;
        for (int i = verticalScroll; i <= ((size.y - 80) / 50) + verticalScroll; i++) {
            tracks.get(i).render(pos.x + timelineManagerWidth, y, size.x - timelineManagerWidth);
            y += 50;
        }
    }

    private void drawElapsedTimeSegments(float timelineManagerWidth, ImVec2 pos, ImVec2 size) {
        final ImDrawList drawList = ImGui.getWindowDrawList();

        drawList.addRectFilled(new ImVec2(pos.x + timelineManagerWidth, pos.y), new ImVec2(pos.x + size.x, pos.y + size.y), ImColor.rgb(20, 19, 24));
        for (int i = 0; i <= 5; i++) {
            ImGui.pushFont(this.timestampFont);
            long time = (long) ((DEFAULT_MAX_TIME * scale * scroll) + (DEFAULT_MAX_TIME * scale * (i / 5f)));
            float segmentX = timestampToPosition(time, timelineManagerWidth + pos.x, size.x - timelineManagerWidth);

            drawList.addRect(new ImVec2(segmentX, pos.y), new ImVec2(segmentX + 0.5f, pos.y + size.y), ImColor.rgb(50, 50, 50));
            drawList.addText(segmentX + 5, pos.y + 30, ImColor.rgb(255, 255, 255), format(time));

            ImGui.popFont();
        }
    }

    private void drawElapsedTime(float timelineManagerWidth, ImVec2 pos, ImVec2 size) {
        final ImDrawList drawList = ImGui.getWindowDrawList();

        drawList.addRectFilled(new ImVec2(pos.x, pos.y), new ImVec2(pos.x + timelineManagerWidth, pos.y + 80), ImColor.rgb(50, 50, 50));
        drawList.addRect(new ImVec2(pos.x, pos.y), new ImVec2(pos.x + size.x, pos.y + 80), ImColor.rgb(50, 50, 50));
        ImGui.pushFont(this.elapsedTimestampFont);
        drawList.addText(pos.x + 20, pos.y + 21, ImColor.rgb(255, 255, 255), format(timestamp));
        ImGui.popFont();

        long millis = timestamp % 1000;
        long second = (timestamp / 1000) % 60;
        long minute = (timestamp / (1000 * 60)) % 60;
        long hour = (timestamp / (1000 * 60 * 60)) % 24;
        ImGui.pushItemWidth(timelineManagerWidth - 15);
        ImGui.setCursorPos(5, 55);
        int[] time = new int[] {Math.toIntExact(hour), Math.toIntExact(minute), Math.toIntExact(second), Math.toIntExact(millis)};
        ImGuiUtils.inputInt4("", time);
        ImGui.popItemWidth();

        timestamp = Math.min(time[0], 99) * (long)3.6e+6 + Math.min(69, time[1]) * 60000L + Math.min(59, time[2]) * 1000L + Math.min(999, time[3]);
    }

    private void drawTimelineCursor(float timelineManagerWidth, ImVec2 pos, ImVec2 size) {
        final ImDrawList drawList = ImGui.getWindowDrawList();

        final float cursorX = timestampToPosition(timestamp, timelineManagerWidth + pos.x, size.x - timelineManagerWidth);
        if (cursorX < timelineManagerWidth + pos.x) {
            return;
        }

        drawList.addRect(new ImVec2(cursorX, pos.y), new ImVec2(cursorX + 1, pos.y + size.y), ImColor.rgb(255, 255, 255));
    }

    private void onMouseDown(ImVec2 vec2) {
        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();

        if (vec2.x < pos.x || vec2.x > pos.x + size.x || vec2.y < pos.y + 20 || vec2.y > pos.y + size.y) {
            return;
        }

        if (vec2.x > pos.x + size.x - 20) {
            scroll += 0.01f * ((pos.x + size.x - vec2.x) / 20) * scale;
        }

        if (vec2.x < pos.x + size.x / 4) {
            if (vec2.y > pos.y + 80) {
                final float distance = pos.x + size.x / 4 - vec2.x;
                final float ratio = distance / (size.x - size.x / 4);
                final long backtrackTime = (long) (ratio * (DEFAULT_MAX_TIME * scale * 0.1));
                timestamp = Math.max(0, timestamp - backtrackTime);
            }
            return;
        }

        if (vec2.y > pos.y + 80) {
            return;
        }

        final float ratio = (vec2.x - pos.x - size.x / 4) / (size.x - size.x / 4);
        timestamp = (long) (DEFAULT_MAX_TIME * scale * scroll + ratio * DEFAULT_MAX_TIME * scale);
    }

    private float timestampToPosition(long timestamp, float offsetX, float size) {
        return offsetX + ((timestamp - (Timeline.DEFAULT_MAX_TIME * scale * scroll)) / (Timeline.DEFAULT_MAX_TIME * scale)) * size;
    }

    private static String format(long ms) {
        long millis = ms % 1000;
        long second = (ms / 1000) % 60;
        long minute = (ms / (1000 * 60)) % 60;
        long hour = (ms / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
    }
}
