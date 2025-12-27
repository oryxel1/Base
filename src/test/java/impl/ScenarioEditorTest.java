package impl;

import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.editor.screen.ScenarioEditorScreen;
import oxy.bascenario.utils.Launcher;

public class ScenarioEditorTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.name("EditorTest");

        final Sprite sprite = new Sprite(new FileInfo("CH0326_spr.skel", false, true), new FileInfo("CH0326_spr.atlas", false, true));

//        scenario.add(0, new AddElementEvent(0, new Preview("Scenario Preview Test", "Episode: 1", null), RenderLayer.TOP));
        scenario.add(0, new AddElementEvent(1, sprite, RenderLayer.BEHIND_DIALOGUE));
        scenario.add(0, new PositionElementEvent(1, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION));
//
//        final List<TextSegment> segments = new ArrayList<>();
//        segments.add(TextSegment.builder().text("The ").build());
//        segments.add(TextSegment.builder().text("quick ").color(Color.CYAN).build());
//        segments.add(TextSegment.builder().text("brown ").bold(true).build());
//        segments.add(TextSegment.builder().text("fox ").italic(true).build());
//        segments.add(TextSegment.builder().text("jump  ").font(new FileInfo("PlaywriteUSTradGuides-Regular.ttf", false, true)).build());
//        segments.add(TextSegment.builder().text("over ").type(FontType.SEMI_BOLD).build());
//        segments.add(TextSegment.builder().text("the ").type(FontType.BOLD).build());
//        segments.add(TextSegment.builder().text("lazy ").shadow(true).build());
//        segments.add(TextSegment.builder().text("dog ").underline(true).build());
//
//        scenario.add(4000L, new AddElementEvent(0, new LocationInfo("The gray room", 2000, 800), RenderLayer.ABOVE_DIALOGUE));
//
//        scenario.add(2800, new StartDialogueEvent(0, "Name", "Association", true, Dialogue.builder().add(segments).build()));
//        scenario.add(true, 400, new AddDialogueEvent(0, Dialogue.builder().add("This is a delayed dialogue, happens after the first one.").build()));
//        scenario.add(true, 1, new StartDialogueEvent(0, "", "", false,
//                Dialogue.builder().add("- And this is a really long dialogue that should automatically add line breaks to itself along side with no background like in game cutscene.").build()));
//
//        scenario.add(true, 1, new StartDialogueEvent(0, "", "", false,
//                Dialogue.builder().add("- First line.").add("\n- Second line").add("\n- Third line").build()));
//
//        scenario.add(true, 1, new StartDialogueEvent(0, "Name", "Association", false, Dialogue.builder().add("Also this dialogue should play very fast.").playSpeed(3).build()));

        Launcher.launch(new ScenarioEditorScreen(scenario.build(), scenario), false);
    }
}
