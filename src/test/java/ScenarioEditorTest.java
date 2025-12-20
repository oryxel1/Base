import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.text.FontType;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.screens.ScenarioEditorScreen;
import oxy.bascenario.utils.Launcher;

import java.util.ArrayList;
import java.util.List;

public class ScenarioEditorTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new AddElementEvent(0, new Preview("Scenario Preview Test", "Episode: 1", null), RenderLayer.TOP));
//        scenario.add(0, new AddElementEvent(1, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE));

        final List<TextSegment> segments = new ArrayList<>();
        segments.add(TextSegment.builder().text("The ").build());
        segments.add(TextSegment.builder().text("quick ").color(Color.CYAN).build());
        segments.add(TextSegment.builder().text("brown ").bold(true).build());
        segments.add(TextSegment.builder().text("fox ").italic(true).build());
        segments.add(TextSegment.builder().text("jump  ").font(new FileInfo("PlaywriteUSTradGuides-Regular.ttf", false, true)).build());
        segments.add(TextSegment.builder().text("over ").type(FontType.SEMI_BOLD).build());
        segments.add(TextSegment.builder().text("the ").type(FontType.BOLD).build());
        segments.add(TextSegment.builder().text("lazy ").shadow(true).build());
        segments.add(TextSegment.builder().text("dog ").underline(true).build());

        scenario.add(4000L, new StartDialogueEvent(0, "Name", "Association", true, Dialogue.builder().add(segments).build()));
        scenario.add(true, 400, new AddDialogueEvent(0, Dialogue.builder().add("This is a delayed dialogue, happens after the first one.").build()));
        scenario.add(true, 1, new StartDialogueEvent(0, "", "", false,
                Dialogue.builder().add("- And this is a really long dialogue that should automatically add line breaks to itself along side with no background like in game cutscene.").build()));

        scenario.add(true, 1, new StartDialogueEvent(0, "", "", false,
                Dialogue.builder().add("- First line.").add("\n- Second line").add("\n- Third line").build()));

        scenario.add(true, 1, new StartDialogueEvent(0, "Name", "Association", false, Dialogue.builder().add("Also this dialogue should play very fast.").playSpeed(3).build()));


        Launcher.launch(new ScenarioEditorScreen(scenario), false);
    }
}
