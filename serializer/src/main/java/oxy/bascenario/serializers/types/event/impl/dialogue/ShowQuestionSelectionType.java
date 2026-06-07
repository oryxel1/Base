package oxy.bascenario.serializers.types.event.impl.dialogue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import oxy.bascenario.api.event.dialogue.ShowQuestionSelectionEvent;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.serializers.base.TypeWithName;
import oxy.bascenario.serializers.types.element.ElementTypes;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionSelectionType implements TypeWithName<ShowQuestionSelectionEvent> {
    @Override
    public String type() {
        return "show-question-selection";
    }

    @Override
    public JsonElement write(ShowQuestionSelectionEvent event) {
        final JsonObject object = new JsonObject();
        object.add("font-type", ElementTypes.FONT_TYPE_TYPE.write(event.type()));
        object.addProperty("question", event.question());

        final JsonArray array = new JsonArray();
        for (ShowQuestionSelectionEvent.Answer answer : event.answers()) {
            final JsonObject answerObject = new JsonObject();
            answerObject.addProperty("index", answer.dialogueIndex());
            answerObject.addProperty("text", answer.answer());
            answerObject.addProperty("grayed-out", answer.grayedOut());
            array.add(answerObject);
        }

        object.add("answers", array);

        return object;
    }

    @Override
    public ShowQuestionSelectionEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        FontType type = ElementTypes.FONT_TYPE_TYPE.read(object.get("font-type"));
        String question = object.get("question").getAsString();

        final List<ShowQuestionSelectionEvent.Answer> answers = new ArrayList<>();
        for (JsonElement element1 : object.getAsJsonArray("answers")) {
            final JsonObject answerObject = element1.getAsJsonObject();

            answers.add(new ShowQuestionSelectionEvent.Answer(
                    answerObject.get("index").getAsInt(),
                    answerObject.get("text").getAsString(),
                    answerObject.get("grayed-").getAsBoolean()
            ));
        }

        return new ShowQuestionSelectionEvent(type, question, answers);
    }
}
