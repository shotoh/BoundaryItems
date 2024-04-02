package io.github.shotoh.boundaryitems.enchants;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.enchantments.Enchantment;

import java.io.IOException;

public class EnchantmentTypeAdapter extends TypeAdapter<Enchantment> {
    @Override
    public void write(JsonWriter jsonWriter, Enchantment enchantment) throws IOException {
        if (enchantment == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(enchantment.getName());
    }

    @Override
    public Enchantment read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return Enchantment.getByName(jsonReader.nextString());
    }
}
