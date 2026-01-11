package oxy.bascenario.api.render.elements.text;

import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.render.elements.text.font.Font;
import oxy.bascenario.api.render.elements.text.font.FontStyle;
import oxy.bascenario.api.utils.FileInfo;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("ALL")
public class TextSegment {
    private String text = "";
    private Font font = Font.DEFAULT;
    private Color color = Color.WHITE;
    private Optional<Color> outline = Optional.empty();
    private final Set<TextStyle> styles = EnumSet.noneOf(TextStyle.class);

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TextSegment that = (TextSegment) o;
        return Objects.equals(text, that.text) && Objects.equals(font, that.font) &&
                Objects.equals(font, that.font) && Objects.equals(color, that.color) &&
                Objects.equals(outline, that.outline) && toFlags() == that.toFlags();
    }

    public Builder toBuilder() {
        Builder builder = builder();
        builder.color = color;
        builder.text = text;
        builder.font = font;
        builder.outline = outline;
        builder.styles.addAll(styles);
        builder.outline = outline;
        return builder;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String text = "";
        private Font font = Font.DEFAULT;
        private Color color = Color.WHITE;
        private Optional<Color> outline = Optional.empty();
        private final Set<TextStyle> styles = EnumSet.noneOf(TextStyle.class);

        private Builder() {}

        public TextSegment build() {
            final TextSegment segment = new TextSegment();
            segment.text = text;
            segment.font = font;
            segment.color = color;
            segment.outline = outline;
            segment.styles.addAll(styles);
            return segment;
        }

//        public Optional<FileInfo> font() {
//            return font;
//        }

//        public Builder font(FileInfo font) {
//            this.font = font == null ? Optional.empty() : Optional.of(font);
//            return this;
//        }

        public Optional<Color> outline() {
            return outline;
        }

        public Builder outline(Color outline) {
            this.outline = outline == null ? Optional.empty() : Optional.of(outline);
            return this;
        }

        public String text() {
            return text;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public FontStyle type() {
            return type;
        }

        public Builder type(FontStyle type) {
            this.type = type;
            return this;
        }

        public Color color() {
            return color;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Set<TextStyle> styles() {
            return styles;
        }

        public Builder bold(boolean value) {
            if (!value) {
                this.styles.remove(TextStyle.BOLD);
            } else {
                this.styles.add(TextStyle.BOLD);
            }
            return this;
        }

        public Builder shadow(boolean value) {
            if (!value) {
                this.styles.remove(TextStyle.SHADOW);
            } else {
                this.styles.add(TextStyle.SHADOW);
            }
            return this;
        }

        public Builder italic(boolean value) {
            if (!value) {
                this.styles.remove(TextStyle.ITALIC);
            } else {
                this.styles.add(TextStyle.ITALIC);
            }
            return this;
        }

        public Builder underline(boolean value) {
            if (!value) {
                this.styles.remove(TextStyle.UNDERLINE);
            } else {
                this.styles.add(TextStyle.UNDERLINE);
            }
            return this;
        }

        public Builder strikethrough(boolean value) {
            if (!value) {
                this.styles.remove(TextStyle.STRIKETHROUGH);
            } else {
                this.styles.add(TextStyle.STRIKETHROUGH);
            }
            return this;
        }
    }

    public int toFlags() {
        int flags = 0;
        for (TextStyle style : this.styles) {
            flags |= style.getValue();
        }
        return flags;
    }

    public String text() {
        return text;
    }

    public FontStyle type() {
        return type;
    }

    public Color color() {
        return color;
    }

    public Optional<FileInfo> font() {
        return font;
    }

    public Optional<Color> outline() {
        return outline;
    }

    public Set<TextStyle> styles() {
        return styles;
    }
}