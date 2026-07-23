package scenario;

import net.lenni0451.commons.color.Color;
import oxy.base.api.event.ShowButtonsEvent;
import oxy.base.api.event.dialogue.enums.TextOffset;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.utils.Launcher;
import oxy.base.api.Scenario;
import oxy.base.api.event.dialogue.AddDialogueEvent;
import oxy.base.api.event.dialogue.StartDialogueEvent;
import oxy.base.api.event.element.AddElementEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.Dialogue;
import oxy.base.api.render.elements.shape.Rectangle;
import oxy.base.api.render.elements.text.font.FontStyle;
import oxy.base.api.render.elements.text.TextSegment;
import oxy.base.api.utils.FileInfo;
import oxy.base.screens.ScenarioScreen;

import java.util.ArrayList;
import java.util.List;

public class DialogueRenderTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        scenario.add(0, new ShowButtonsEvent(true));
        scenario.add(0, new AddElementEvent(0, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE));

        final List<TextSegment> segments = new ArrayList<>();
        segments.add(TextSegment.builder().text("The ").build());
        segments.add(TextSegment.builder().text("quick ").color(Color.CYAN).build());
        segments.add(TextSegment.builder().text("brown ").bold(true).build());
        segments.add(TextSegment.builder().text("fox ").italic(true).strikethrough(true).build());
        segments.add(TextSegment.builder().text("jump  ").font(new FileInfo("PlaywriteUSTradGuides-Regular.ttf", false, true)).build());
        segments.add(TextSegment.builder().text("over ").style(FontStyle.SEMI_BOLD).build());
        segments.add(TextSegment.builder().text("the ").style(FontStyle.BOLD).build());
        segments.add(TextSegment.builder().text("lazy ").shadow(true).build());
        segments.add(TextSegment.builder().text("dog ").underline(true).build());

        scenario.add(0, new StartDialogueEvent(FontType.NotoSans, 0, "Name", "Association", true, Dialogue.builder().add(segments).build()));
        scenario.add(true, 400, new AddDialogueEvent(0, Dialogue.builder().add("This is a delayed dialogue, happens after the first one.").build()));
        scenario.add(true, 1, new StartDialogueEvent(FontType.NotoSans, 0, "", "", false,
                Dialogue.builder().add("- And this is a really long dialogue that should automatically add line breaks to itself along side with no background like in game cutscene.").build()));

        scenario.add(true, 1, new StartDialogueEvent(FontType.NotoSans, 0, "", "", false,
                Dialogue.builder().add("- First line.").add("\n- Second line").add("\n- Third line").build()));

        scenario.add(true, 1, new StartDialogueEvent(FontType.NotoSans, 0, "Name", "Association", false, Dialogue.builder().add("Also this dialogue should play very fast.").playSpeed(3).build()));

        scenario.add(true, 1, new StartDialogueEvent(FontType.NotoSans, 0, "Name", "Association", false, Dialogue.builder().add("Now also, ").build()));
        scenario.add(3000, new AddDialogueEvent(0, false, Dialogue.builder().add("there should be a slight delay playing this dialogue!").build()));

        scenario.add(true, 1, new StartDialogueEvent(TextOffset.CENTER, FontType.NotoSans, 0,
                "Name", "Association", false, Dialogue.builder().add("This should play in the center!").build()));

        scenario.add(true, 1, new StartDialogueEvent(TextOffset.RIGHT, FontType.NotoSans, 0,
                "Name", "Association", false, Dialogue.builder().add("This should play on the right!").build()));

        // An actual dialogue in Vol Ex that plays on the right to see how it looks :P
        scenario.add(true, 1, new StartDialogueEvent(TextOffset.RIGHT, FontType.NotoSans, 0,
                "", "", false, Dialogue.builder().add("Even if she isn't understood, Aris is fine with it.").build()));

        scenario.add(true, 1, new AddDialogueEvent(0, Dialogue.builder().add("That dialogue was for testing!").build()));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}
