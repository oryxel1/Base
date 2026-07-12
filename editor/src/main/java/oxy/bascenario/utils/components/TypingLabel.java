package oxy.bascenario.utils.components;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.math.MathUtils;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import net.lenni0451.rivet.theme.Theme;

@Accessors(fluent = true, chain = true, makeFinal = true)
public class TypingLabel extends Component {

    @Getter
    private String text;
    private ShapedText shapedText;
    private boolean reshape;
    @Getter
    @Setter
    private TextOrigin.Horizontal horizontalOrigin = TextOrigin.Horizontal.VISUAL_CENTER;
    @Getter
    @Setter
    private TextOrigin.Vertical verticalOrigin = TextOrigin.Vertical.LOGICAL_CENTER;
    @Getter
    private float scale = 1F;

    public TypingLabel(final String text) {
        this.text = text;
    }

    public TypingLabel text(final String text) {
        if (!this.text.equals(text)) {
            this.text = text;
            this.reshape = true;
            if (this.parent() != null) {
                this.parent().requestLayoutRecalculation();
            }
        }
        return this;
    }

    public TypingLabel scale(final float scale) {
        if (this.scale != scale) {
            this.scale = scale;
            if (this.parent() != null) {
                this.parent().requestLayoutRecalculation();
            }
        }
        return this;
    }

    private void shapeText() {
        if (this.reshape) {
            this.shapedText = this.rivet().backend().font().shapeText(this.text, this.rivet().theme().get(Theme.TEXT_COLOR));
            this.reshape = false;
        }
    }

    @Override
    protected void onComponentAdded() {
        this.reshape = true;
    }

    @Override
    protected void onComponentThemeChanged() {
        this.reshape = true;
    }

    @Override
    public void render(final Renderer renderer, final Size bounds) {
        this.shapeText();
        float x = this.horizontalOrigin.position(bounds.width() / this.scale);
        float y = this.verticalOrigin.position(bounds.height() / this.scale);

        float progress = (System.currentTimeMillis() % 3000) / 3000f;
        net.lenni0451.rivet.backend.text.ShapedText text1 = this.rivet().backend().font().shapeText(this.text.substring(0, MathUtils.ceilInt(progress * this.text.length())), this.rivet().theme().get(Theme.TEXT_COLOR));
        renderer.scale(this.scale, () -> renderer.text(text1, x, y, this.horizontalOrigin, this.verticalOrigin));
    }

    @Override
    public Size computeIdealSize(final Size constraints) {
        this.shapeText();
        return new Size(
                this.shapedText.visualBounds().width() * this.scale,
                this.shapedText.logicalBounds().height() * this.scale
        );
    }

}
