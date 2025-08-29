package com.bascenario.engine.scenario.event;

import com.bascenario.engine.scenario.event.api.Event;
import com.google.gson.*;
import lombok.SneakyThrows;
import net.lenni0451.commons.animation.DynamicAnimation;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// Holy shit wtf is this, I'm actually ashamed that I wrote this but since it works, I guess it's ok?
// It's really hardcoded and really not good design wise but ehhhhh it's the fatest solution to (de)serialize event so yeh.
// TODO: Also, I can imagine me breaking every json scenario possible by eg: renaming the class name or package so we should have a more
// TODO: reliable way to check for class eg: ENUM?
public class EventSerializer implements JsonDeserializer<Event>, JsonSerializer<Event> {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    @Override
    public JsonElement serialize(Event event, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject object = new JsonObject();
        object.addProperty("duration", event.getDuration());
        object.addProperty("class", event.getClass().getName());

        final JsonArray fields = new JsonArray();
        for (Field field : event.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object object1 = field.get(event);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("class", object1.getClass().getName());
                jsonObject.addProperty("value", GSON.toJson(object1));

                fields.add(jsonObject);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        object.add("fields", fields);

        return object;
    }

    @SneakyThrows
    @Override
    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject object = json.getAsJsonObject();

        Class<?> klass = Class.forName(object.get("class").getAsString());

        //. System.out.println(klass);

        List<Object> objects = new ArrayList<>();
        List<Class<?>> classes = new ArrayList<>();
        for (JsonElement element : object.getAsJsonArray("fields")) {
            final JsonObject jsonObject = element.getAsJsonObject();
            Class<?> klass1 = Class.forName(jsonObject.get("class").getAsString());
            if (klass1 == Float.class) {
                klass1 = float.class;
            } else if (klass1 == Long.class) {
                klass1 = long.class;
            } else if (klass1 == Integer.class) {
                klass1 = int.class;
            } else if (klass1 == Double.class) {
                klass1 = double.class;
            } else if (klass1 == Boolean.class) {
                klass1 = boolean.class;
            }
            if (klass1 == DynamicAnimation.class) {
                continue;
            }

            classes.add(klass1);
            objects.add(GSON.fromJson(jsonObject.get("value").getAsString(), klass1));
        }

        Event event;
        try {
            event = (Event) klass.getDeclaredConstructor(classes.toArray(new Class[0])).newInstance(objects.toArray());;
        } catch (Exception exception) {
            final List<Class<?>> secondClasses = new ArrayList<>();
            secondClasses.add(long.class);
            secondClasses.addAll(classes);
            final List<Object> secondList = new ArrayList<>();
            secondList.add(object.get("duration").getAsLong());
            secondList.addAll(objects);

            event = (Event) klass.getDeclaredConstructor(secondClasses.toArray(new Class[0])).newInstance(secondList.toArray());
        }

        return event;
    }
}
