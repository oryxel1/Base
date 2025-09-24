package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.elements.Dialogue;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.DialogueRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImInt;
import imgui.type.ImString;

public class PlayDialogueEvent extends Event<PlayDialogueEvent> {
    private final Dialogue dialogue;
    public PlayDialogueEvent(Dialogue dialogue) {
        super(0);
        this.dialogue = dialogue;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (screen.getDialogueIndex() != dialogue.index()) {
            return;
        }

        screen.setDialogue(new DialogueRender(this.dialogue));
    }

    @Override
    public void renderImGui() {
        this.dialogue.index(ImGuiUtil.inputInt("Dialogue index", this.dialogue.index()));
        this.dialogue.dialogue(ImGuiUtil.inputMultiLineText("Dialogue", this.dialogue.dialogue()));
        this.dialogue.name(ImGuiUtil.inputText("Name", this.dialogue.name()));
        this.dialogue.association(ImGuiUtil.inputText("Association", this.dialogue.association()));

        this.dialogue.playSpeed(ImGuiUtil.sliderFloat("Play speed", this.dialogue.playSpeed(), 0.01F, 10));
        this.dialogue.textScale(ImGuiUtil.sliderFloat("Text scale", this.dialogue.textScale(), 0.01F, 10));

        int index = this.dialogue.fontType() == null ? 0 : this.dialogue.fontType().ordinal();
        ImInt fontTypeIndex = new ImInt(index);
        ImGui.combo("Font Type", fontTypeIndex, new String[] {"Regular", "Semi Bold", "Bold"});
        if (fontTypeIndex.get() != index) {
            this.dialogue.fontType(Dialogue.FontType.values()[fontTypeIndex.get()]);
        }

        this.dialogue.cutscene(ImGuiUtil.checkbox("Cutscene", this.dialogue.cutscene()));
        this.dialogue.closeOnClick(ImGuiUtil.checkbox("Close on click", this.dialogue.closeOnClick()));
    }

    @Override
    public PlayDialogueEvent defaultEvent() {
        return new PlayDialogueEvent(
                Dialogue.builder()
                        .index(0).dialogue("").name("").association("")
                        .playSpeed(1).textScale(1).fontType(Dialogue.FontType.REGULAR)
                        .build()
        );
    }

    @Override
    public PlayDialogueEvent deserialize(JsonObject serialized) {
        return new PlayDialogueEvent(GsonUtil.getGson().fromJson(serialized.get("dialogue"), Dialogue.class));
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("dialogue", GsonUtil.toJson(this.dialogue));
    }

    @Override
    public String type() {
        return "play-dialogue";
    }
}
