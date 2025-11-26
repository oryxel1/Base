import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.elements.*;
import oxy.bascenario.api.elements.image.FadeImage;
import oxy.bascenario.api.elements.image.Image;
import oxy.bascenario.api.elements.text.FontType;
import oxy.bascenario.api.elements.text.Text;
import oxy.bascenario.api.elements.text.TextSegment;
import oxy.bascenario.api.event.impl.ColorOverlayEvent;
import oxy.bascenario.api.event.impl.SetBackgroundEvent;
import oxy.bascenario.api.event.impl.SpriteAnimationEvent;
import oxy.bascenario.api.event.impl.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.impl.dialogue.CloseDialogueEvent;
import oxy.bascenario.api.event.impl.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.impl.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.impl.element.AddElementEvent;
import oxy.bascenario.api.event.impl.element.MoveElementEvent;
import oxy.bascenario.api.event.impl.element.ScaleElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static oxy.bascenario.Launcher.*;

public class ScenarioPreviewTest {
    public static void main(String[] args) {
        final Scenario.Builder builder = Scenario.builder();
        builder.title("Title");
        builder.subtitle("Subtitle");
        builder.previewBackground(new Image(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg")));

        builder.add(0, new SetBackgroundEvent(new FadeImage(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg"), Fade.DISABLED, Fade.DISABLED)));

        final Sprite sprite = new Sprite(FileInfo.from("C:\\Users\\Computer\\BAAS\\JPSpine\\CH0326_spr.skel"), FileInfo.from("C:\\Users\\Computer\\BAAS\\JPSpine\\CH0326_spr.atlas"), null);
        builder.add(0, new AddElementEvent(0, sprite, RenderLayer.BEHIND_DIALOGUE),
                new SpriteAnimationEvent(0, 0,  "Idle_01", 0),
                new MoveElementEvent(0, 0, 960, 540));
        builder.add(0, new AddElementEvent(2, new Text(List.of(TextSegment.builder().text("Hello World").outline(Color.BLACK).build()), 42), RenderLayer.BEHIND_DIALOGUE), new MoveElementEvent(0, 2, 100, 100) , new MoveElementEvent(0, 0, 960, 540));

        builder.add(1000, new ColorOverlayEvent(new Fade(100), Color.WHITE));
        builder.add(120, new ColorOverlayEvent(new Fade(500), Color.fromRGBA(255, 255, 255, 0)));
        builder.add(1000, new ColorOverlayEvent(0, Fade.DISABLED, Color.fromRGBA(0, 0, 0, 0)));
        builder.add(0, new MoveElementEvent(500, 0, 560, MoveElementEvent.Type.X_ONLY), new ColorOverlayEvent(0, new Fade(1000), Color.fromRGBA(0, 0, 0, 100)));

        final Dialogue dialogue = Dialogue.builder().add("Sorry for being sexy Reisa.").build();
        builder.add(1, new StartDialogueEvent(0, "Reisa", "Trinity's Vigilante Crew", dialogue));

        builder.add(true, 1, new StartDialogueEvent(0, "Reisa", "Trinity's Vigilante Crew", Dialogue.builder().add("Of course Sensei.").build()));
        builder.add(true, 1, new AddDialogueEvent(0, Dialogue.builder().add("I can't expect you to be skibidi like me.").build()));
        builder.add(true, 1, new CloseDialogueEvent());

        builder.add(true, 1, new ColorOverlayEvent(0, new Fade(500), Color.fromRGBA(0, 0, 0, 0)));
        builder.add(0, new MoveElementEvent(1000, 0, 960, 540));

        builder.add(true, 1000, new StartDialogueEvent(0, "Reisa", "Trinity's Vigilante Crew", Dialogue.builder().add("Perhaps you will...\nsign up for my how to be skibidi course?").build()));
        builder.add(true, 1, new StartDialogueEvent(0, "Reisa", "Trinity's Vigilante Crew", Dialogue.builder().add("Come... JOIN ME", Color.RED.darker().darker()).build()));

        {
            final Map<String, Integer> map = new LinkedHashMap<>();
            map.put("Test dialogue 1", 0);
            map.put("Test dialogue 2", 0);

            builder.add(true, 1, new ShowOptionsEvent(map));
        }

        builder.add(true, 1, new CloseDialogueEvent());
        builder.add(true, 1, new StartDialogueEvent(0, "", "", false, Dialogue.builder().add("Also this is a dialogue without background!").build()));
        builder.add(true, 1, new ScaleElementEvent(2, 1000, 1.5F, Easing.QUAD));
        builder.add(true, 1, new StartDialogueEvent(0, "", "", false, Dialogue.builder().add("And this a text... ").add("with multiple segments!", FontType.SEMI_BOLD).build()));
        builder.add(true, 1, new StartDialogueEvent(0, "", "", false, Dialogue.builder().add("So ", Color.RED).add("you ", Color.BLACK).add("can ").add("do ", Color.PINK).add("this!", Color.CYAN).build()));
        builder.add(true, 1, new StartDialogueEvent(0, "", "", false, Dialogue.builder().add(TextSegment.builder().text("Test strikethrough").strikethrough(true).build()).add(TextSegment.builder().text(" and also this").type(FontType.BOLD).italic(true).build()).build()));

        launch(new ScenarioScreen(builder.build()), false);
//        launch(new ScenarioPreviewScreen(builder.build()), true);
    }
}
