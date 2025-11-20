package oxy.bascenario.api.elements;

import lombok.Builder;
import net.lenni0451.commons.color.Color;

// Yep there are no name, associations, etc... I want this to be a separate thing.
// So for example, a new dialogue line could be added after 600ms for eg, you will see what I mean.
@Builder(toBuilder = true)
public class Dialogue {
    private int index;
    @Builder.Default
    private String dialogue = "";
    @Builder.Default
    private float playSpeed = 1;
    @Builder.Default
    private FontType fontType = FontType.REGULAR;
    @Builder.Default // Hey it should always be white but why not.... Maybe some part of the game the text is red as well mb.
    private Color color = Color.WHITE;
}
