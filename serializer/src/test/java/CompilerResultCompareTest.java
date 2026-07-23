import com.google.gson.JsonElement;

import net.lenni0451.commons.color.Color;
import oxy.base.api.Scenario;
import oxy.base.api.effects.Easing;
import oxy.base.api.event.animation.PlayAnimationEvent;
import oxy.base.api.event.animation.SpriteAnimationEvent;
import oxy.base.api.event.animation.StopAnimationEvent;
import oxy.base.api.event.color.ColorOverlayEvent;
import oxy.base.api.event.color.SetColorEvent;
import oxy.base.api.event.dialogue.AddDialogueEvent;
import oxy.base.api.event.dialogue.ShowOptionsEvent;
import oxy.base.api.event.dialogue.StartDialogueEvent;
import oxy.base.api.event.element.AddElementEvent;
import oxy.base.api.event.element.values.PositionElementEvent;
import oxy.base.api.event.element.values.RotateElementEvent;
import oxy.base.api.event.sound.SoundVolumeEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.Dialogue;
import oxy.base.api.render.elements.Preview;
import oxy.base.api.render.elements.Sprite;
import oxy.base.api.render.elements.emoticon.Emoticon;
import oxy.base.api.render.elements.emoticon.EmoticonType;
import oxy.base.api.render.elements.text.Text;
import oxy.base.api.render.elements.text.TextSegment;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.api.utils.FileInfo;
import oxy.base.api.utils.math.Vec2;
import oxy.base.api.utils.math.Vec3;
import oxy.base.serializers.Types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CompilerResultCompareTest {
    private static final Dialogue DUMMY_DIALOGUE = Dialogue.builder().add("Hello World!").build();

    public static void main(String[] args) {
        final Scenario scenario = scenario();
        System.out.println("Testing json type serializer....");
        {
            final JsonElement object = Types.SCENARIO_TYPE.write(scenario);
            Scenario parseBack = Types.SCENARIO_TYPE.read(object);

            if (!parseBack.equals(scenario)) {
                throw new RuntimeException("Scenario doesn't match (type JSON)!");
            }

            System.out.println("Finished, match!");
        }

        System.out.println("All test passed!");
    }

    public static Scenario scenario() {
        final Scenario.Builder builder = Scenario.builder();
        builder.name("Test");

        builder.add(0, new PlayAnimationEvent(0, "base:default-shake", false));
        builder.add(0, new SpriteAnimationEvent(0, 0.2f, "Idle_01", 1));
        builder.add(0, new StopAnimationEvent(0, "base:default-shake"));
        builder.add(100, new StartDialogueEvent(FontType.NotoSans, 0, "", "", true, DUMMY_DIALOGUE));
        builder.add(200, new AddDialogueEvent(0, DUMMY_DIALOGUE));

        {
            final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
            map.put("Yes", 0);
            map.put("No", 0);
            builder.add(300, new ShowOptionsEvent(map));
        }

        for (PositionElementEvent.Type type : PositionElementEvent.Type.values()) {
            builder.add(true, 1, new PositionElementEvent(0, 1000, new Vec2(0, 0), Easing.LINEAR, type));
        }
        builder.add(5, new RotateElementEvent(0, 1000, new Vec3(0, 0, 0), Easing.LINEAR));
//        builder.add(1, new ElementEffectEvent(0, Effect.HOLOGRAM, Axis.Y));
//        builder.add(1, new ElementEffectEvent(0, Effect.HOLOGRAM, ElementEffectEvent.Type.REMOVE));
        builder.add(1, new ColorOverlayEvent(0, 500, Color.WHITE));
        builder.add(1, new SetColorEvent(0, 500, Color.WHITE));
        builder.add(10, new SoundVolumeEvent(0, 1000, 1, Easing.LINEAR));
        builder.add(10, new ColorOverlayEvent(RenderLayer.TOP, 500, Color.WHITE));

        builder.add(1, new AddElementEvent(1, new Preview(FontType.NotoSans, "Title", "Subtitle", null), RenderLayer.TOP));
        builder.add(1, new AddElementEvent(1, new Emoticon(1000, EmoticonType.NOTE, true), RenderLayer.TOP));
        builder.add(1, new AddElementEvent(1, new Sprite(FileInfo.from("test"), FileInfo.from("test")), RenderLayer.TOP));

        builder.add(1, new AddElementEvent(1, new Text(new ArrayList<>(List.of(TextSegment.builder().text("Hello World!").build())), 42), RenderLayer.ABOVE_DIALOGUE));

        return builder.build();
    }
}
